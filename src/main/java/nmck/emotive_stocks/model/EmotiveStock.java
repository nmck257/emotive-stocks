package nmck.emotive_stocks.model;

import nmck.emotive_stocks.services.nyse.NYSE;
import nmck.emotive_stocks.util.Utils;

import java.time.LocalDate;

public class EmotiveStock {
    private static final double NEUTRAL_THRESHOLD = 0.1;
    private static final double GOOD_THRESHOLD = 0.5;
    private static final double VERY_GOOD_THRESHOLD = 1.0;
    private final FeelingWords feelingWords;
    private final NYSE nyse;
    private final String ticker;

    public EmotiveStock(NYSE nyse, String ticker, FeelingWords feelingWords) {
        this.nyse = nyse;
        this.ticker = ticker;
        this.feelingWords = feelingWords;
    }

    public String getTicker() {
        return ticker;
    }

    public String reactTo(LocalDate localDate) {
        return feelingWords.getRandom(assessFeelings(nyse.getDailyGrowthPercentage(localDate, ticker)));
    }

    private Feeling assessFeelings(double dailyGrowth) {
        if (Utils.isBetween(Math.abs(dailyGrowth), 0.0, NEUTRAL_THRESHOLD)) {
            return Feeling.VERY_NEUTRAL;
        } else if (Utils.isBetween(Math.abs(dailyGrowth), NEUTRAL_THRESHOLD, GOOD_THRESHOLD)) {
            return Feeling.NEUTRAL;
        } else if (Utils.isBetween(Math.abs(dailyGrowth), GOOD_THRESHOLD, VERY_GOOD_THRESHOLD)) {
            return dailyGrowth > 0.0 ? Feeling.GOOD : Feeling.BAD;
        } else {
            return dailyGrowth > 0.0 ? Feeling.VERY_GOOD : Feeling.VERY_BAD;
        }
    }
}
