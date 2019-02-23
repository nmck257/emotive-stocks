package nmck.emotive_stocks;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertThrows;

class LambdaTest {
    private static final String TICKER = "cash";
    private Lambda lambda;
    private LambdaConfig lambdaConfig;

    @BeforeEach
    void setUp() {
        lambda = new Lambda();
        lambdaConfig = new LambdaConfig();
    }

    @Test
    void missingTickerThrowsException() {
        assertThrows(RuntimeException.class, () -> lambda.handle(lambdaConfig));
    }

    @Test
    void validTickerNoException() {
        lambdaConfig.setTicker(TICKER);
        lambda.handle(lambdaConfig);
    }

    @Nested
    class CustomFeelingWords {
        @BeforeEach
        void setUp() {
            lambdaConfig.setTicker(TICKER);
        }

        @Test
        void customGoodNoException() {
            lambdaConfig.setGoodFeelingWords(Sets.newHashSet("word"));
            lambda.handle(lambdaConfig);
        }

        @Test
        void customBadNoException() {
            lambdaConfig.setBadFeelingWords(Sets.newHashSet("word"));
            lambda.handle(lambdaConfig);
        }

        @Test
        void customNeutralNoException() {
            lambdaConfig.setNeutralFeelingWords(Sets.newHashSet("word"));
            lambda.handle(lambdaConfig);
        }

        @Test
        void allCustomNoException() {
            lambdaConfig.setGoodFeelingWords(Sets.newHashSet("word"));
            lambdaConfig.setBadFeelingWords(Sets.newHashSet("word"));
            lambdaConfig.setNeutralFeelingWords(Sets.newHashSet("word"));
            lambda.handle(lambdaConfig);
        }

        @Nested
        class EmptyCollectionsThrowExceptions {
            @Test
            void emptyGood() {
                lambdaConfig.setGoodFeelingWords(new HashSet<>());
                assertThrows(IllegalArgumentException.class, () -> lambda.handle(lambdaConfig));
            }

            @Test
            void emptyBad() {
                lambdaConfig.setBadFeelingWords(new HashSet<>());
                assertThrows(IllegalArgumentException.class, () -> lambda.handle(lambdaConfig));
            }

            @Test
            void emptyNeutral() {
                lambdaConfig.setNeutralFeelingWords(new HashSet<>());
                assertThrows(IllegalArgumentException.class, () -> lambda.handle(lambdaConfig));
            }

            @Test
            void mixedEmptyNonEmpty() {
                lambdaConfig.setGoodFeelingWords(new HashSet<>());
                lambdaConfig.setBadFeelingWords(Sets.newHashSet("word"));
                assertThrows(IllegalArgumentException.class, () -> lambda.handle(lambdaConfig));
            }
        }
    }
}
