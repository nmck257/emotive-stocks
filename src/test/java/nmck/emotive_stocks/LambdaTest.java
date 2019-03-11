package nmck.emotive_stocks;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertThrows;

class LambdaTest {
    private static final String TICKER = "CASH";
    private Lambda lambda;
    private LambdaConfig lambdaConfig;
    private LambdaConfig.Builder lambdaConfigBuilder;

    @BeforeEach
    void setUp() {
        lambda = new Lambda();
        lambdaConfigBuilder = new LambdaConfig.Builder();
    }

    @Test
    void missingTickerThrowsException() {
        assertThrows(RuntimeException.class, () -> lambda.handle(lambdaConfigBuilder.build()));
    }

    @Test
    void validTickerNoException() {
        lambdaConfig = lambdaConfigBuilder.setTicker(TICKER).build();
        lambda.handle(lambdaConfig);
    }

    @Nested
    class CustomFeelingWords {
        @BeforeEach
        void setUp() {
            lambdaConfigBuilder.setTicker(TICKER);
        }

        @Test
        void customGoodNoException() {
            LambdaConfig lambdaConfig = lambdaConfigBuilder
                    .setGoodFeelingWords(Sets.newHashSet("word"))
                    .build();
            lambda.handle(lambdaConfig);
        }

        @Test
        void customBadNoException() {
            lambdaConfig = lambdaConfigBuilder
                    .setBadFeelingWords(Sets.newHashSet("word"))
                    .build();
            lambda.handle(lambdaConfig);
        }

        @Test
        void customNeutralNoException() {
            lambdaConfig = lambdaConfigBuilder
                    .setNeutralFeelingWords(Sets.newHashSet("word"))
                    .build();
            lambda.handle(lambdaConfig);
        }

        @Test
        void allCustomNoException() {
            lambdaConfig = lambdaConfigBuilder
                    .setGoodFeelingWords(Sets.newHashSet("word"))
                    .setBadFeelingWords(Sets.newHashSet("word"))
                    .setNeutralFeelingWords(Sets.newHashSet("word"))
                    .build();
            lambda.handle(lambdaConfig);
        }

        @Nested
        class EmptyCollectionsThrowExceptions {
            @Test
            void emptyGood() {
                lambdaConfig = lambdaConfigBuilder.setGoodFeelingWords(new HashSet<>()).build();
                assertThrows(IllegalArgumentException.class, () -> lambda.handle(lambdaConfig));
            }

            @Test
            void emptyBad() {
                lambdaConfig = lambdaConfigBuilder.setBadFeelingWords(new HashSet<>()).build();
                assertThrows(IllegalArgumentException.class, () -> lambda.handle(lambdaConfig));
            }

            @Test
            void emptyNeutral() {
                lambdaConfig = lambdaConfigBuilder.setNeutralFeelingWords(new HashSet<>()).build();
                assertThrows(IllegalArgumentException.class, () -> lambda.handle(lambdaConfig));
            }

            @Test
            void mixedEmptyNonEmpty() {
                lambdaConfig = lambdaConfigBuilder
                        .setGoodFeelingWords(new HashSet<>())
                        .setBadFeelingWords(Sets.newHashSet("word"))
                        .build();
                assertThrows(IllegalArgumentException.class, () -> lambda.handle(lambdaConfig));
            }
        }
    }
}
