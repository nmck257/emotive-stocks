package nmck.emotive_stocks.model;

import nmck.emotive_stocks.services.nyse.NYSE;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class EmotiveStockTest {
    private static final NYSE mockNYSE = mock(NYSE.class);
    private static final LocalDate GOOD_DAY = LocalDate.of(1993, 2, 23);
    private static final LocalDate VERY_GOOD_DAY = LocalDate.of(1993, 2, 24);
    private static final LocalDate BAD_DAY = LocalDate.of(2005, 1, 9);
    private static final LocalDate VERY_BAD_DAY = LocalDate.of(2005, 2, 22);
    private static final LocalDate NEUTRAL_DAY = LocalDate.of(1930, 4, 18);
    private static final LocalDate VERY_NEUTRAL_DAY = LocalDate.of(1954, 4, 11);
    private static final double GOOD_GROWTH_PCT = 0.23;
    private static final double VERY_GOOD_GROWTH_PCT = 1.1;
    private static final double BAD_GROWTH_PCT = -0.4;
    private static final double VERY_BAD_GROWTH_PCT = -1.0;
    private static final double NEUTRAL_GROWTH_PCT = 0.11;
    private static final double VERY_NEUTRAL_GROWTH_PCT = 0.03;
    private static final String TICKER = "CASH";
    private static final FeelingWords feelingWords = spy(FeelingWords.getDefault());
    private EmotiveStock emotiveStock;

    @BeforeAll
    static void setUpNYSE() {
        when(mockNYSE.getDailyGrowthPercentage(GOOD_DAY, TICKER)).thenReturn(GOOD_GROWTH_PCT);
        when(mockNYSE.getDailyGrowthPercentage(VERY_GOOD_DAY, TICKER)).thenReturn(VERY_GOOD_GROWTH_PCT);
        when(mockNYSE.getDailyGrowthPercentage(BAD_DAY, TICKER)).thenReturn(BAD_GROWTH_PCT);
        when(mockNYSE.getDailyGrowthPercentage(VERY_BAD_DAY, TICKER)).thenReturn(VERY_BAD_GROWTH_PCT);
        when(mockNYSE.getDailyGrowthPercentage(NEUTRAL_DAY, TICKER)).thenReturn(NEUTRAL_GROWTH_PCT);
        when(mockNYSE.getDailyGrowthPercentage(VERY_NEUTRAL_DAY, TICKER)).thenReturn(VERY_NEUTRAL_GROWTH_PCT);
    }

    @BeforeEach
    void setUpEmotiveStock() {
        emotiveStock = new EmotiveStock(mockNYSE, TICKER, feelingWords);
    }

    @Test
    void getTicker() {
        assertEquals(TICKER, emotiveStock.getTicker());
    }

    @Nested
    class ReactTo {
        final static String reactionPattern = ".* \\(-?\\d+\\.\\d{2}%\\)";
        String reaction;

        private void dayTest(LocalDate day, Feeling feeling, double growth) {
            reaction = emotiveStock.reactTo(day);
            verify(feelingWords).getRandom(feeling);
            assertTrue(reaction.matches(reactionPattern),
                    String.format("Reaction \"%s\" must look like \"words (-0.00%%)\"", reaction));
            assertTrue(reaction.contains(String.valueOf(growth)),
                    String.format("Reaction %s must contain growth %.2f", reaction, growth));
        }

        @Test
        void goodDay() {
            dayTest(GOOD_DAY, Feeling.GOOD, GOOD_GROWTH_PCT);
        }

        @Test
        void veryGoodDay() {
            dayTest(VERY_GOOD_DAY, Feeling.VERY_GOOD, VERY_GOOD_GROWTH_PCT);
        }

        @Test
        void badDay() {
            dayTest(BAD_DAY, Feeling.BAD, BAD_GROWTH_PCT);
        }

        @Test
        void veryBadDay() {
            dayTest(VERY_BAD_DAY, Feeling.VERY_BAD, VERY_BAD_GROWTH_PCT);
        }

        @Test
        void neutralDay() {
            dayTest(NEUTRAL_DAY, Feeling.NEUTRAL, NEUTRAL_GROWTH_PCT);
        }

        @Test
        void veryNeutralDay() {
            dayTest(VERY_NEUTRAL_DAY, Feeling.VERY_NEUTRAL, VERY_NEUTRAL_GROWTH_PCT);
        }
    }
}
