package nmck.emotive_stocks.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Utils {
    private Utils() {}

    public static <T> T getRandomElement(Collection<T> collection) {
        List<T> shuffled = new ArrayList<>(collection);
        Collections.shuffle(shuffled);
        return shuffled.get(0);
    }

    public static boolean isBetween(double x, double lower, double higher) {
        return Double.compare(x, lower) >= 0 && Double.compare(x, higher) < 0;
    }

    public static double generateRandomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static boolean containsCaseInsensitive(Set<String> set, String string) {
        return set.stream().map(String::toUpperCase).collect(Collectors.toSet()).contains(string.toUpperCase());
    }

    private static List<LocalDate> daysStartingOn(LocalDate localDate, int numberOfDays) {
        List<LocalDate> days = new ArrayList<>();
        for (int i = 0; i < numberOfDays; i++) {
            days.add(localDate.plusDays(i));
        }
        return days;
    }

    public static List<LocalDate> recentFullWeek() {
        return daysStartingOn(
                LocalDate.now().minusWeeks(1).with(TemporalAdjusters.previous(DayOfWeek.MONDAY)),
                5);
    }

    public static List<LocalDate> recentFullWeekend() {
        return daysStartingOn(
                LocalDate.now().minusWeeks(1).with(TemporalAdjusters.previous(DayOfWeek.SATURDAY)),
                2);
    }

    public static List<LocalDate> pastDays(int i) {
        return daysStartingOn(LocalDate.now().minusDays(i - 1), i);
    }

    /**
     * @return the next key after the provided key (or null if given key is last key)
     * @throws IllegalArgumentException if map does not contain key
     */
    public static <K, V> K getNextKey(LinkedHashMap<K, V> map, K key) {
        if (!map.containsKey(key))
            throw new IllegalArgumentException("Can't get next key; map does not contain given key");
        Iterator<K> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            K current = iterator.next();
            if (current.equals(key)) {
                if (iterator.hasNext()) {
                    return iterator.next();
                } else {
                    return null;
                }
            }
        }
        return null;
    }
}
