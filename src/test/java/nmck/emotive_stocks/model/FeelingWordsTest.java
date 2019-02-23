package nmck.emotive_stocks.model;

import nmck.emotive_stocks.util.Utils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.collections.Sets;

import java.util.HashSet;
import java.util.Set;

import static nmck.emotive_stocks.model.Feeling.*;
import static org.junit.jupiter.api.Assertions.*;

class FeelingWordsTest {
    private static final int RUN_COUNT = 100;

    @Nested
    class CustomWords {
        private final Set<String> emptySet = new HashSet<>();
        private final String elemA = "a";
        private final String elemB = "b";
        private final String elemC = "c";
        private final Set<String> setA = Sets.newSet(elemA);
        private final Set<String> setB = Sets.newSet(elemB);
        private final Set<String> setC = Sets.newSet(elemC);

        @Test
        void getRandom() {
            FeelingWords feelingWords = new FeelingWords(setA, setB, setC);

            for (int i = 0; i < RUN_COUNT; i++) {
                assertEquals(elemA, feelingWords.getRandom(GOOD));
                assertEquals(elemB, feelingWords.getRandom(BAD));
                assertEquals(elemC, feelingWords.getRandom(NEUTRAL));
            }
        }

        @Test
        void emptyCollectionsThrowExceptions() {
            assertThrows(IllegalArgumentException.class, () -> new FeelingWords(emptySet, setB, setC));
            assertThrows(IllegalArgumentException.class, () -> new FeelingWords(setA, emptySet, setC));
            assertThrows(IllegalArgumentException.class, () -> new FeelingWords(setA, setB, emptySet));
        }
    }

    @Nested
    class GetRandom {
        private final FeelingWords defaultFeelingWords = FeelingWords.getDefault();
        private String reaction;

        private void assertAllCaps(String reaction) {
            assertTrue(reaction.matches("[^a-z]+"), String.format("Reaction must be all-caps: %s", reaction));
        }

        private void assertMixedCase(String reaction) {
            assertTrue(reaction.matches(".*[a-z]+.*"), String.format("Reaction must be mixed case: %s", reaction));
        }

        @Test
        void goodFeelings() {
            for (int i = 0; i < RUN_COUNT; i++) {
                reaction = defaultFeelingWords.getRandom(GOOD);
                assertMixedCase(reaction);
                assertTrue(Utils.containsCaseInsensitive(defaultFeelingWords.getGoodSet(), reaction),
                        String.format("Reaction must be from good list: %s", reaction));
            }
        }

        @Test
        void veryGoodFeelings() {
            for (int i = 0; i < RUN_COUNT; i++) {
                reaction = defaultFeelingWords.getRandom(VERY_GOOD);
                assertAllCaps(reaction);
                assertTrue(Utils.containsCaseInsensitive(defaultFeelingWords.getGoodSet(), reaction),
                        String.format("Reaction must be from good list: %s", reaction));
            }
        }

        @Test
        void badFeelings() {
            for (int i = 0; i < RUN_COUNT; i++) {
                reaction = defaultFeelingWords.getRandom(BAD);
                assertMixedCase(reaction);
                assertTrue(Utils.containsCaseInsensitive(defaultFeelingWords.getBadSet(), reaction),
                        String.format("Reaction must be from bad list: %s", reaction));
            }
        }

        @Test
        void veryBadFeelings() {
            for (int i = 0; i < RUN_COUNT; i++) {
                reaction = defaultFeelingWords.getRandom(VERY_BAD);
                assertAllCaps(reaction);
                assertTrue(Utils.containsCaseInsensitive(defaultFeelingWords.getBadSet(), reaction),
                        String.format("Reaction must be from bad list: %s", reaction));
            }
        }

        @Test
        void neutralFeelings() {
            for (int i = 0; i < RUN_COUNT; i++) {
                reaction = defaultFeelingWords.getRandom(NEUTRAL);
                assertMixedCase(reaction);
                assertTrue(Utils.containsCaseInsensitive(defaultFeelingWords.getNeutralSet(), reaction),
                        String.format("Reaction must be from neutral list: %s", reaction));
            }
        }

        @Test
        void veryNeutralFeelings() {
            for (int i = 0; i < RUN_COUNT; i++) {
                reaction = defaultFeelingWords.getRandom(VERY_NEUTRAL);
                assertAllCaps(reaction);
                assertTrue(Utils.containsCaseInsensitive(defaultFeelingWords.getNeutralSet(), reaction),
                        String.format("Reaction must be from neutral list: %s", reaction));
            }
        }
    }
}
