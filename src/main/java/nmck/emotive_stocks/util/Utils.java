package nmck.emotive_stocks.util;

import java.util.*;

public class Utils {
    private Utils () {}

    public static <T> T getRandomElement(Collection<T> collection) {
        List<T> shuffled = new ArrayList<>(collection);
        Collections.shuffle(shuffled);
        return shuffled.get(0);
    }

    public static boolean isBetween(double x, double lower, double higher) {
        return Double.compare(x, lower) >= 0 && Double.compare(x, higher) <= 0;
    }
}
