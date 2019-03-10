package nmck.emotive_stocks.model.feelings;

import com.google.common.collect.Sets;
import nmck.emotive_stocks.util.Utils;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class FeelingWords {
    private static final Set<String> DEFAULT_GOOD_FEELINGS = Sets.newHashSet(
            "yay", "nice", "oh baby", "wow", "yeehaw", "mmm", "yeah buddy", "hot dog", "yas");
    private static final Set<String> DEFAULT_BAD_FEELINGS = Sets.newHashSet(
            "wait", "no", "bad", "stop", "um", "hold the phone", "oof", "ow");
    private static final Set<String> DEFAULT_NEUTRAL_FEELINGS = Sets.newHashSet(
            "meh", "zzz", "whatever", "i sleep");
    private final Set<String> good;
    private final Set<String> bad;
    private final Set<String> neutral;

    public static FeelingWords getDefault() {
        return new FeelingWords();
    }

    public static Set<String> getDefaultGoodFeelings() {
        return new HashSet<>(DEFAULT_GOOD_FEELINGS);
    }

    public static Set<String> getDefaultBadFeelings() {
        return new HashSet<>(DEFAULT_BAD_FEELINGS);
    }

    public static Set<String> getDefaultNeutralFeelings() {
        return new HashSet<>(DEFAULT_NEUTRAL_FEELINGS);
    }

    private FeelingWords() {
        good = new HashSet<>(DEFAULT_GOOD_FEELINGS);
        bad = new HashSet<>(DEFAULT_BAD_FEELINGS);
        neutral = new HashSet<>(DEFAULT_NEUTRAL_FEELINGS);
    }

    public FeelingWords(Collection<String> good, Collection<String> bad, Collection<String> neutral) {
        if (good.size() == 0) throw new IllegalArgumentException("Empty collection for good feeling words");
        if (bad.size() == 0) throw new IllegalArgumentException("Empty collection for bad feeling words");
        if (neutral.size() == 0) throw new IllegalArgumentException("Empty collection for neutral feeling words");
        this.good = new HashSet<>(good);
        this.bad = new HashSet<>(bad);
        this.neutral = new HashSet<>(neutral);
    }

    public Set<String> getGoodSet() {
        return new HashSet<>(good);
    }

    public Set<String> getBadSet() {
        return new HashSet<>(bad);
    }

    public Set<String> getNeutralSet() {
        return new HashSet<>(neutral);
    }

    public String getRandom(@Nonnull Feeling feeling) {
        switch (feeling) {
            case GOOD:
                return Utils.getRandomElement(good);
            case VERY_GOOD:
                return Utils.getRandomElement(good).toUpperCase();
            case BAD:
                return Utils.getRandomElement(bad);
            case VERY_BAD:
                return Utils.getRandomElement(bad).toUpperCase();
            case NEUTRAL:
                return Utils.getRandomElement(neutral);
            case VERY_NEUTRAL:
                return Utils.getRandomElement(neutral).toUpperCase();
            default:
                throw new RuntimeException("Unhandled feeling from enum");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeelingWords that = (FeelingWords) o;
        return Objects.equals(good, that.good) &&
                Objects.equals(bad, that.bad) &&
                Objects.equals(neutral, that.neutral);
    }
}
