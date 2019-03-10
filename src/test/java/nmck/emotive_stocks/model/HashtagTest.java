package nmck.emotive_stocks.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HashtagTest {
    public final static List<String> GOOD_LIST = Arrays.asList("test", "test2", "t3est", "#test");
    private final static List<String> BAD_LIST = Arrays.asList("3test", "", "##", "#!", "234", "#");

    private void assertGood(String input) {
        assertTrue(Hashtag.isValid(input), "Input text must be accepted: " + input);
        Hashtag.fromString(input);
    }

    private void assertBad(String input) {
        assertTrue(!Hashtag.isValid(input), "Input text must be rejected: " + input);
        assertThrows(IllegalArgumentException.class, () -> Hashtag.fromString(input));
    }

    @Test
    void validCases() {
        GOOD_LIST.forEach(this::assertGood);
    }

    @Test
    void invalidCases() {
        BAD_LIST.forEach(this::assertBad);
    }

    @Test
    void alwaysOctothorp() {
        GOOD_LIST.stream()
                .map(Hashtag::fromString)
                .map(Hashtag::toString)
                .forEach(s -> assertTrue(s.startsWith("#"), "String output must always lead with '#': " + s));
    }

    @Nested
    class FromList {
        private List<Hashtag> hashtagList;

        /**
         * @return a shuffled list which exactly combines the GOOD_LIST and BAD_LIST
         */
        private List<String> getMixedList() {
            List<String> mixedList = new ArrayList<>(GOOD_LIST);
            mixedList.addAll(BAD_LIST);
            Collections.shuffle(mixedList);
            return mixedList;
        }

        @Test
        void allGood() {
            hashtagList = Hashtag.fromStringList(GOOD_LIST);
            assertEquals(GOOD_LIST.size(), hashtagList.size());
        }

        @Test
        void allBad() {
            hashtagList = Hashtag.fromStringList(BAD_LIST);
            assertEquals(0, hashtagList.size());
        }

        @Test
        void mixedGoodBad() {
            hashtagList = Hashtag.fromStringList(getMixedList());
            assertEquals(GOOD_LIST.size(), hashtagList.size());
        }
    }
}
