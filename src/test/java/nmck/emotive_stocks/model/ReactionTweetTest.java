package nmck.emotive_stocks.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReactionTweetTest {
    private static final String PHRASE = "phrase";
    private static final double GROWTH = 1.345;
    private static final List<Hashtag> HASHTAG_LIST = Hashtag.fromStringList(HashtagTest.GOOD_LIST);
    private ReactionTweet reactionTweet;

    @Test
    void getters() {
        reactionTweet = new ReactionTweet(PHRASE, GROWTH, HASHTAG_LIST);
        assertEquals(PHRASE, reactionTweet.getPhrase());
        assertEquals(GROWTH, reactionTweet.getGrowth());
        assertEquals(HASHTAG_LIST, reactionTweet.getHashtags());
    }

    @Nested
    class ToString {
        private final static String GROWTH_PATTERN = "\\(-?\\d+\\.\\d{2}%\\)";
        private final static String HASHTAG_PATTERN = "#\\w+( #\\w+)*";
        private String reactionTweetString;

        @BeforeEach
        void setUp() {
            reactionTweet = new ReactionTweet(PHRASE, GROWTH, HASHTAG_LIST);
            reactionTweetString = reactionTweet.toString();
        }

        @Test
        void phraseFirst() {
            assertTrue(reactionTweetString.startsWith(reactionTweet.getPhrase()),
                    String.format("String \"%s\" must start with phrase \"%s\"", reactionTweetString, reactionTweet.getPhrase()));
        }

        @Test
        void growthMiddle() {
            assertTrue(reactionTweetString.matches(".+ " + GROWTH_PATTERN + ".+"),
                    String.format("String \"%s\" must have growth %.2f in the middle", reactionTweetString, GROWTH));
        }

        @Test
        void hashtagsLast() {
            assertTrue(reactionTweetString.matches(".+ .+ " + HASHTAG_PATTERN),
                    String.format("String \"%s\" must end with hashtags %s", reactionTweetString, Hashtag.toString(HASHTAG_LIST)));
        }
    }
}
