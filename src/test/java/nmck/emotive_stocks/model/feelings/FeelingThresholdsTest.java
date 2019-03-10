package nmck.emotive_stocks.model.feelings;

import nmck.emotive_stocks.model.feelings.Feeling;
import nmck.emotive_stocks.model.feelings.FeelingThresholds;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeelingThresholdsTest {
    private static final int RUN_COUNT = 100;
    private static final double TEST_NEUTRAL_THRESHOLD = 0.10;
    private static final double TEST_GOOD_THRESHOLD = 0.20;
    private static final double TEST_VERY_GOOD_THRESHOLD = 0.30;
    private FeelingThresholds feelingThresholds;

    @Test
    void getDefault() {
        feelingThresholds = FeelingThresholds.getDefault();
        assertEquals(FeelingThresholds.getDefaultNeutralPercentageThreshold(), feelingThresholds.getNeutralThreshold());
        assertEquals(FeelingThresholds.getDefaultGoodPercentageThreshold(), feelingThresholds.getGoodThreshold());
        assertEquals(FeelingThresholds.getDefaultVeryGoodPercentageThreshold(), feelingThresholds.getVeryGoodThreshold());
    }

    @Nested
    class Builder {
        private FeelingThresholds.Builder builder;

        @BeforeEach
        void setUp() {
            builder = FeelingThresholds.newBuilder();
        }

        @Test
        void setNothing() {
            feelingThresholds = builder.build();
            assertEquals(FeelingThresholds.getDefaultNeutralPercentageThreshold(), feelingThresholds.getNeutralThreshold());
            assertEquals(FeelingThresholds.getDefaultGoodPercentageThreshold(), feelingThresholds.getGoodThreshold());
            assertEquals(FeelingThresholds.getDefaultVeryGoodPercentageThreshold(), feelingThresholds.getVeryGoodThreshold());
        }

        @Test
        void setNeutral() {
            builder.setNeutralThreshold(TEST_NEUTRAL_THRESHOLD);
            feelingThresholds = builder.build();
            assertEquals(TEST_NEUTRAL_THRESHOLD, feelingThresholds.getNeutralThreshold());
            assertEquals(FeelingThresholds.getDefaultGoodPercentageThreshold(), feelingThresholds.getGoodThreshold());
            assertEquals(FeelingThresholds.getDefaultVeryGoodPercentageThreshold(), feelingThresholds.getVeryGoodThreshold());
        }

        @Test
        void setGood() {
            builder.setGoodThreshold(TEST_GOOD_THRESHOLD);
            feelingThresholds = builder.build();
            assertEquals(FeelingThresholds.getDefaultNeutralPercentageThreshold(), feelingThresholds.getNeutralThreshold());
            assertEquals(TEST_GOOD_THRESHOLD, feelingThresholds.getGoodThreshold());
            assertEquals(FeelingThresholds.getDefaultVeryGoodPercentageThreshold(), feelingThresholds.getVeryGoodThreshold());
        }

        @Test
        void setVeryGood() {
            builder.setVeryGoodThreshold(TEST_VERY_GOOD_THRESHOLD);
            feelingThresholds = builder.build();
            assertEquals(FeelingThresholds.getDefaultNeutralPercentageThreshold(), feelingThresholds.getNeutralThreshold());
            assertEquals(FeelingThresholds.getDefaultGoodPercentageThreshold(), feelingThresholds.getGoodThreshold());
            assertEquals(TEST_VERY_GOOD_THRESHOLD, feelingThresholds.getVeryGoodThreshold());
        }

        @Test
        void setAll() {
            builder.setNeutralThreshold(TEST_NEUTRAL_THRESHOLD);
            builder.setGoodThreshold(TEST_GOOD_THRESHOLD);
            builder.setVeryGoodThreshold(TEST_VERY_GOOD_THRESHOLD);
            feelingThresholds = builder.build();
            assertEquals(TEST_NEUTRAL_THRESHOLD, feelingThresholds.getNeutralThreshold());
            assertEquals(TEST_GOOD_THRESHOLD, feelingThresholds.getGoodThreshold());
            assertEquals(TEST_VERY_GOOD_THRESHOLD, feelingThresholds.getVeryGoodThreshold());
        }
    }

    @Nested
    class Assess {
        private double value;

        @BeforeEach
        void setUp() {
            FeelingThresholds.Builder builder = FeelingThresholds.newBuilder();
            builder.setNeutralThreshold(TEST_NEUTRAL_THRESHOLD);
            builder.setGoodThreshold(TEST_GOOD_THRESHOLD);
            builder.setVeryGoodThreshold(TEST_VERY_GOOD_THRESHOLD);
            feelingThresholds = builder.build();
        }

        @RepeatedTest(RUN_COUNT)
        void veryNeutral() {
            value = ThreadLocalRandom.current().nextDouble(TEST_NEUTRAL_THRESHOLD * -1, TEST_NEUTRAL_THRESHOLD);
            assertEquals(Feeling.VERY_NEUTRAL, feelingThresholds.assess(value));
        }

        @RepeatedTest(RUN_COUNT)
        void neutral() {
            value = ThreadLocalRandom.current().nextDouble(TEST_NEUTRAL_THRESHOLD, TEST_GOOD_THRESHOLD);
            if (ThreadLocalRandom.current().nextBoolean()) value = value * -1;
            assertEquals(Feeling.NEUTRAL, feelingThresholds.assess(value));
        }

        @RepeatedTest(RUN_COUNT)
        void good() {
            value = ThreadLocalRandom.current().nextDouble(TEST_GOOD_THRESHOLD, TEST_VERY_GOOD_THRESHOLD);
            assertEquals(Feeling.GOOD, feelingThresholds.assess(value));
        }

        @RepeatedTest(RUN_COUNT)
        void veryGood() {
            value = ThreadLocalRandom.current().nextDouble(TEST_VERY_GOOD_THRESHOLD, Double.POSITIVE_INFINITY);
            assertEquals(Feeling.VERY_GOOD, feelingThresholds.assess(value));
        }

        @RepeatedTest(RUN_COUNT)
        void bad() {
            value = ThreadLocalRandom.current().nextDouble(TEST_GOOD_THRESHOLD, TEST_VERY_GOOD_THRESHOLD);
            value = value * -1;
            assertEquals(Feeling.BAD, feelingThresholds.assess(value));
        }

        @RepeatedTest(RUN_COUNT)
        void veryBad() {
            value = ThreadLocalRandom.current().nextDouble(TEST_VERY_GOOD_THRESHOLD, Double.POSITIVE_INFINITY);
            value = value * -1;
            assertEquals(Feeling.VERY_BAD, feelingThresholds.assess(value));
        }
    }
}
