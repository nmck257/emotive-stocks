package nmck.emotive_stocks.model;

import nmck.emotive_stocks.services.NYSE;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmotiveStockTest {
    private static final NYSE mockNYSE = mock(NYSE.class);
    private static final LocalDate GOOD_DAY = LocalDate.of(1993, 2, 23);
    private static final LocalDate VERY_GOOD_DAY = LocalDate.of(1993, 2, 24);
    private static final LocalDate BAD_DAY = LocalDate.of(2005, 1, 9);
    private static final LocalDate VERY_BAD_DAY = LocalDate.of(2005, 2, 22);
    private static final LocalDate NEUTRAL_DAY = LocalDate.of(1930, 4, 18);
    private static final LocalDate VERY_NEUTRAL_DAY = LocalDate.of(1954, 4, 11);
    private static final String TICKER = "cash-money";
    private EmotiveStock emotiveStock;

    @BeforeAll
    static void setUpNYSE() {
        when(mockNYSE.getDailyGrowth(GOOD_DAY, TICKER)).thenReturn(0.8f);
        when(mockNYSE.getDailyGrowth(VERY_GOOD_DAY, TICKER)).thenReturn(1.3f);
        when(mockNYSE.getDailyGrowth(BAD_DAY, TICKER)).thenReturn(-0.8f);
        when(mockNYSE.getDailyGrowth(VERY_BAD_DAY, TICKER)).thenReturn(-1.2f);
        when(mockNYSE.getDailyGrowth(NEUTRAL_DAY, TICKER)).thenReturn(0.2f);
        when(mockNYSE.getDailyGrowth(VERY_NEUTRAL_DAY, TICKER)).thenReturn(0.0f);
    }

    @BeforeEach
    void setUpEmotiveStock() {
        emotiveStock = new EmotiveStock(mockNYSE, TICKER);
    }

    @Test
    void getTicker() {
        assertEquals(TICKER, emotiveStock.getTicker());
    }

    @Nested
    class ReactTo {
        private void assertAllCaps(String reaction) {
            assertTrue(reaction.matches("[^a-z]+"), String.format("Reaction must be all-caps: %s", reaction));
        }

        private void assertMixedCase(String reaction) {
            assertTrue(reaction.matches("[a-z]*"), String.format("Reaction must be mixed case: %s", reaction));
        }

        private boolean containsCaseInsensitive(Set<String> set, String string) {
            return set.stream().map(String::toUpperCase).collect(Collectors.toSet()).contains(string.toUpperCase());
        }

        @Test
        void goodDay() {
            String reaction = emotiveStock.reactTo(GOOD_DAY);
            assertMixedCase(reaction);
            assertTrue(containsCaseInsensitive(emotiveStock.getGoodFeelings(), reaction),
                    String.format("Reaction must be from good list: %s", reaction));
        }

        @Test
        void veryGoodDay() {
            String reaction = emotiveStock.reactTo(VERY_GOOD_DAY);
            assertAllCaps(reaction);
            assertTrue(containsCaseInsensitive(emotiveStock.getGoodFeelings(), reaction),
                    String.format("Reaction must be from good list: %s", reaction));
        }

        @Test
        void badDay() {
            String reaction = emotiveStock.reactTo(BAD_DAY);
            assertMixedCase(reaction);
            assertTrue(containsCaseInsensitive(emotiveStock.getBadFeelings(), reaction),
                    String.format("Reaction must be from bad list: %s", reaction));
        }

        @Test
        void veryBadDay() {
            String reaction = emotiveStock.reactTo(VERY_BAD_DAY);
            assertAllCaps(reaction);
            assertTrue(containsCaseInsensitive(emotiveStock.getBadFeelings(), reaction),
                    String.format("Reaction must be from bad list: %s", reaction));
        }

        @Test
        void neutralDay() {
            String reaction = emotiveStock.reactTo(NEUTRAL_DAY);
            assertMixedCase(reaction);
            assertTrue(containsCaseInsensitive(emotiveStock.getNeutralFeelings(), reaction),
                    String.format("Reaction must be from neutral list: %s", reaction));
        }

        @Test
        void veryNeutralDay() {
            String reaction = emotiveStock.reactTo(VERY_NEUTRAL_DAY);
            assertAllCaps(reaction);
            assertTrue(containsCaseInsensitive(emotiveStock.getNeutralFeelings(), reaction),
                    String.format("Reaction must be from neutral list: %s", reaction));
        }
    }
}
