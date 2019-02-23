package nmck.emotive_stocks.model;

import com.google.common.collect.Sets;
import nmck.emotive_stocks.services.NYSE;

import java.time.LocalDate;
import java.util.*;

public class EmotiveStock {
    private enum Feeling { GOOD, VERY_GOOD, NEUTRAL, VERY_NEUTRAL, BAD, VERY_BAD}
    private static final Set<String> GOOD_FEELINGS = Sets.newHashSet("yay", "nice", "oh baby", "wow", "yeehaw", "mmm");
    private static final Set<String> BAD_FEELINGS = Sets.newHashSet("wait", "no", "bad", "stop");
    private static final Set<String> NEUTRAL_FEELINGS = Sets.newHashSet("meh", "zzz");
    private static final float NEUTRAL_THRESHOLD = 0.1f;
    private static final float GOOD_THRESHOLD = 0.5f;
    private static final float VERY_GOOD_THRESHOLD = 1.0f;
    private final NYSE nyse;
    private final String ticker;

    Set<String> getGoodFeelings() {
        return new HashSet<>(GOOD_FEELINGS);
    }

    Set<String> getBadFeelings() {
        return new HashSet<>(BAD_FEELINGS);
    }

    Set<String> getNeutralFeelings() {
        return new HashSet<>(NEUTRAL_FEELINGS);
    }

    public EmotiveStock(NYSE nyse, String ticker) {
        this.nyse = nyse;
        this.ticker = ticker;
    }

    public String reactTo(LocalDate localDate) {
        switch (assessFeelings(nyse.getDailyGrowth(localDate, ticker))) {
            case GOOD:
                return getRandomElement(GOOD_FEELINGS);
            case VERY_GOOD:
                return getRandomElement(GOOD_FEELINGS).toUpperCase();
            case BAD:
                return getRandomElement(BAD_FEELINGS);
            case VERY_BAD:
                return getRandomElement(BAD_FEELINGS).toUpperCase();
            case NEUTRAL:
                return getRandomElement(NEUTRAL_FEELINGS);
            case VERY_NEUTRAL:
                return getRandomElement(NEUTRAL_FEELINGS).toUpperCase();
            default:
                return "";
        }
    }

    public String getTicker() {
        return ticker;
    }

    private Feeling assessFeelings(float dailyGrowth) {
        if (isBetween(Math.abs(dailyGrowth), 0.0f, NEUTRAL_THRESHOLD)) {
            return Feeling.VERY_NEUTRAL;
        } else if (isBetween(Math.abs(dailyGrowth), NEUTRAL_THRESHOLD, GOOD_THRESHOLD)) {
            return Feeling.NEUTRAL;
        } else if (isBetween(Math.abs(dailyGrowth), GOOD_THRESHOLD, VERY_GOOD_THRESHOLD)) {
            return dailyGrowth > 0.0f ? Feeling.GOOD : Feeling.BAD;
        } else {
            return dailyGrowth > 0.0f ? Feeling.VERY_GOOD : Feeling.VERY_BAD;
        }
    }

    private boolean isBetween(float x, float lower, float higher) {
        return Float.compare(x, lower) >= 0 && Float.compare(x, higher) <= 0;
    }

    private <T> T getRandomElement(Set<T> collection) {
        List<T> shuffled = new ArrayList<>(collection);
        Collections.shuffle(shuffled);
        return shuffled.get(0);
    }
}
