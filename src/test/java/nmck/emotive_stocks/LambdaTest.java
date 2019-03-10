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
        lambdaConfigBuilder.setTicker(TICKER);
        lambda.handle(lambdaConfigBuilder.build());
    }

    @Nested
    class CustomFeelingWords {
        @BeforeEach
        void setUp() {
            lambdaConfigBuilder.setTicker(TICKER);
        }

        @Test
        void customGoodNoException() {
            lambdaConfigBuilder.setGoodFeelingWords(Sets.newHashSet("word"));
            lambda.handle(lambdaConfigBuilder.build());
        }

        @Test
        void customBadNoException() {
            lambdaConfigBuilder.setBadFeelingWords(Sets.newHashSet("word"));
            lambda.handle(lambdaConfigBuilder.build());
        }

        @Test
        void customNeutralNoException() {
            lambdaConfigBuilder.setNeutralFeelingWords(Sets.newHashSet("word"));
            lambda.handle(lambdaConfigBuilder.build());
        }

        @Test
        void allCustomNoException() {
            lambdaConfigBuilder.setGoodFeelingWords(Sets.newHashSet("word"));
            lambdaConfigBuilder.setBadFeelingWords(Sets.newHashSet("word"));
            lambdaConfigBuilder.setNeutralFeelingWords(Sets.newHashSet("word"));
            lambda.handle(lambdaConfigBuilder.build());
        }

        @Nested
        class EmptyCollectionsThrowExceptions {
            @Test
            void emptyGood() {
                lambdaConfigBuilder.setGoodFeelingWords(new HashSet<>());
                assertThrows(IllegalArgumentException.class, () -> lambda.handle(lambdaConfigBuilder.build()));
            }

            @Test
            void emptyBad() {
                lambdaConfigBuilder.setBadFeelingWords(new HashSet<>());
                assertThrows(IllegalArgumentException.class, () -> lambda.handle(lambdaConfigBuilder.build()));
            }

            @Test
            void emptyNeutral() {
                lambdaConfigBuilder.setNeutralFeelingWords(new HashSet<>());
                assertThrows(IllegalArgumentException.class, () -> lambda.handle(lambdaConfigBuilder.build()));
            }

            @Test
            void mixedEmptyNonEmpty() {
                lambdaConfigBuilder.setGoodFeelingWords(new HashSet<>());
                lambdaConfigBuilder.setBadFeelingWords(Sets.newHashSet("word"));
                assertThrows(IllegalArgumentException.class, () -> lambda.handle(lambdaConfigBuilder.build()));
            }
        }
    }
}
