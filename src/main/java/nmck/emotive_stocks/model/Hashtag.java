package nmck.emotive_stocks.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hashtag {
    private static final Logger LOGGER = LogManager.getLogger(Hashtag.class);
    private String title;

    public static List<Hashtag> getDefaultList() {
        return Stream.of("business", "finance", "trading")
                .map(Hashtag::fromString).collect(Collectors.toList());
    }

    public static boolean isValid(String title) {
        boolean acceptable = title.matches("#?[a-zA-Z]\\w*");
        if (!acceptable) LOGGER.warn("Unacceptable hashtag: " + title);
        return acceptable;
    }

    public static Hashtag fromString(String title) {
        return new Hashtag(title);
    }

    public static List<Hashtag> fromStringList(List<String> stringList) {
        return stringList.stream()
                .filter(Hashtag::isValid)
                .map(Hashtag::fromString)
                .collect(Collectors.toList());
    }

    public static String toString(List<Hashtag> hashtagList) {
        return hashtagList.stream().map(Hashtag::toString).collect(Collectors.joining(" "));
    }

    private Hashtag(String title) {
        if (!isValid(title)) throw new IllegalArgumentException("Cannot use bad hashtag: " + title);
        this.title = guaranteeOctothorp(title);
    }

    private static String guaranteeOctothorp(String string) {
        return string.startsWith("#")
                ? string
                : "#" + string;
    }

    @Override
    public String toString() {
        return title;
    }
}
