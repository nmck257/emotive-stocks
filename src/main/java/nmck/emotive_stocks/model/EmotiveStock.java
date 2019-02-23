package nmck.emotive_stocks.model;

import com.google.common.collect.Sets;
import nmck.emotive_stocks.services.NYSE;
import nmck.emotive_stocks.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.*;

public class EmotiveStock {
    private enum Feeling { GOOD, VERY_GOOD, NEUTRAL, VERY_NEUTRAL, BAD, VERY_BAD}
    private static final Set<String> GOOD_FEELINGS = Sets.newHashSet("yay", "nice", "oh baby", "wow", "yeehaw", "mmm");
    private static final Set<String> BAD_FEELINGS = Sets.newHashSet("wait", "no", "bad", "stop", "um");
    private static final Set<String> NEUTRAL_FEELINGS = Sets.newHashSet("meh", "zzz", "whatever");
    private static final double NEUTRAL_THRESHOLD = 0.1;
    private static final double GOOD_THRESHOLD = 0.5;
    private static final double VERY_GOOD_THRESHOLD = 1.0;
    private final NYSE nyse;
    private final String ticker;

    public Set<String> getGoodFeelings() {
        return new HashSet<>(GOOD_FEELINGS);
    }

    public Set<String> getBadFeelings() {
        return new HashSet<>(BAD_FEELINGS);
    }

    public Set<String> getNeutralFeelings() {
        return new HashSet<>(NEUTRAL_FEELINGS);
    }

    public EmotiveStock(NYSE nyse, String ticker) {
        this.nyse = nyse;
        this.ticker = ticker;
    }

    public String reactTo(LocalDate localDate) {
        switch (assessFeelings(nyse.getDailyGrowth(localDate, ticker))) {
            case GOOD:
                return Utils.getRandomElement(GOOD_FEELINGS);
            case VERY_GOOD:
                return Utils.getRandomElement(GOOD_FEELINGS).toUpperCase();
            case BAD:
                return Utils.getRandomElement(BAD_FEELINGS);
            case VERY_BAD:
                return Utils.getRandomElement(BAD_FEELINGS).toUpperCase();
            case NEUTRAL:
                return Utils.getRandomElement(NEUTRAL_FEELINGS);
            case VERY_NEUTRAL:
                return Utils.getRandomElement(NEUTRAL_FEELINGS).toUpperCase();
            default:
                return "";
        }
    }

    public String getTicker() {
        return ticker;
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
