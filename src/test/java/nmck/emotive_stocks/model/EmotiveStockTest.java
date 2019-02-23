package nmck.emotive_stocks.model;

import nmck.emotive_stocks.services.nyse.NYSE;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmotiveStockTest {
    private static final NYSE mockNYSE = mock(NYSE.class);
    private static final LocalDate GOOD_DAY = LocalDate.of(1993, 2, 23);
    private static final LocalDate VERY_GOOD_DAY = LocalDate.of(1993, 2, 24);
    private static final LocalDate BAD_DAY = LocalDate.of(2005, 1, 9);
    private static final LocalDate VERY_BAD_DAY = LocalDate.of(2005, 2, 22);
    private static final LocalDate NEUTRAL_DAY = LocalDate.of(1930, 4, 18);
    private static final LocalDate VERY_NEUTRAL_DAY = LocalDate.of(1954, 4, 11);
    private static final String TICKER = "cash";
    private static final FeelingWords feelingWords = spy(FeelingWords.getDefault());
    private EmotiveStock emotiveStock;

    @BeforeAll
    static void setUpNYSE() {
        when(mockNYSE.getDailyGrowthPercentage(GOOD_DAY, TICKER)).thenReturn(0.8);
        when(mockNYSE.getDailyGrowthPercentage(VERY_GOOD_DAY, TICKER)).thenReturn(1.3);
        when(mockNYSE.getDailyGrowthPercentage(BAD_DAY, TICKER)).thenReturn(-0.8);
        when(mockNYSE.getDailyGrowthPercentage(VERY_BAD_DAY, TICKER)).thenReturn(-1.2);
        when(mockNYSE.getDailyGrowthPercentage(NEUTRAL_DAY, TICKER)).thenReturn(0.2);
        when(mockNYSE.getDailyGrowthPercentage(VERY_NEUTRAL_DAY, TICKER)).thenReturn(0.0);
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

        @Test
        void goodDay() {
            emotiveStock.reactTo(GOOD_DAY);
            verify(feelingWords).getRandom(Feeling.GOOD);
        }

        @Test
        void veryGoodDay() {
            emotiveStock.reactTo(VERY_GOOD_DAY);
            verify(feelingWords).getRandom(Feeling.VERY_GOOD);
        }

        @Test
        void badDay() {
            emotiveStock.reactTo(BAD_DAY);
            verify(feelingWords).getRandom(Feeling.BAD);
        }

        @Test
        void veryBadDay() {
            emotiveStock.reactTo(VERY_BAD_DAY);
            verify(feelingWords).getRandom(Feeling.VERY_BAD);
        }

        @Test
        void neutralDay() {
            emotiveStock.reactTo(NEUTRAL_DAY);
            verify(feelingWords).getRandom(Feeling.NEUTRAL);
        }

        @Test
        void veryNeutralDay() {
            emotiveStock.reactTo(VERY_NEUTRAL_DAY);
            verify(feelingWords).getRandom(Feeling.VERY_NEUTRAL);
        }
    }
}
