package nmck.emotive_stocks.model.feelings;

import nmck.emotive_stocks.util.Utils;

public class FeelingThresholds {
    private static final double DEFAULT_NEUTRAL_PERCENTAGE_THRESHOLD = 0.05;
    private static final double DEFAULT_GOOD_PERCENTAGE_THRESHOLD = 0.2;
    private static final double DEFAULT_VERY_GOOD_PERCENTAGE_THRESHOLD = 1.0;
    private final double neutralThreshold;
    private final double goodThreshold;
    private final double veryGoodThreshold;

    private FeelingThresholds(double neutralThreshold, double goodThreshold, double veryGoodThreshold) {
        this.neutralThreshold = neutralThreshold;
        this.goodThreshold = goodThreshold;
        this.veryGoodThreshold = veryGoodThreshold;
    }

    public static double getDefaultNeutralPercentageThreshold() {
        return DEFAULT_NEUTRAL_PERCENTAGE_THRESHOLD;
    }

    public static double getDefaultGoodPercentageThreshold() {
        return DEFAULT_GOOD_PERCENTAGE_THRESHOLD;
    }

    public static double getDefaultVeryGoodPercentageThreshold() {
        return DEFAULT_VERY_GOOD_PERCENTAGE_THRESHOLD;
    }

    public static FeelingThresholds getDefault() {
        return new Builder().build();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public double getNeutralThreshold() {
        return neutralThreshold;
    }

    public double getGoodThreshold() {
        return goodThreshold;
    }

    public double getVeryGoodThreshold() {
        return veryGoodThreshold;
    }

    public Feeling assess(double growth) {
        if (Utils.isBetween(Math.abs(growth), 0.0, neutralThreshold)) {
            return Feeling.VERY_NEUTRAL;
        } else if (Utils.isBetween(Math.abs(growth), neutralThreshold, goodThreshold)) {
            return Feeling.NEUTRAL;
        } else if (Utils.isBetween(Math.abs(growth), goodThreshold, veryGoodThreshold)) {
            return growth > 0.0 ? Feeling.GOOD : Feeling.BAD;
        } else {
            return growth > 0.0 ? Feeling.VERY_GOOD : Feeling.VERY_BAD;
        }
    }

    public static class Builder {
        private double neutralThreshold = DEFAULT_NEUTRAL_PERCENTAGE_THRESHOLD;
        private double goodThreshold = DEFAULT_GOOD_PERCENTAGE_THRESHOLD;
        private double veryGoodThreshold = DEFAULT_VERY_GOOD_PERCENTAGE_THRESHOLD;

        public void setNeutralThreshold(double neutralThreshold) {
            this.neutralThreshold = neutralThreshold;
        }

        public void setGoodThreshold(double goodThreshold) {
            this.goodThreshold = goodThreshold;
        }

        public void setVeryGoodThreshold(double veryGoodThreshold) {
            this.veryGoodThreshold = veryGoodThreshold;
        }

        public FeelingThresholds build() {
            return new FeelingThresholds(neutralThreshold, goodThreshold, veryGoodThreshold);
        }
    }
}
