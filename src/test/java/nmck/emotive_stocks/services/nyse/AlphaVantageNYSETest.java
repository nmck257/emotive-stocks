package nmck.emotive_stocks.services.nyse;

import nmck.emotive_stocks.util.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AlphaVantageNYSETest {
    /*
        For the demo api key, this is the only accepted symbol.
        https://www.alphavantage.co/documentation/
     */
    private static final String validSymbol = "MSFT";
    private NYSE nyse;

    @BeforeEach
    void setup() {
        nyse = new AlphaVantageNYSE();
    }

    @Nested
    class IsMarketDay {
        @Test
        void recentWeekdays() {
            for (LocalDate day : Utils.recentFullWeek()) {
                assertTrue(nyse.isMarketDay(day),
                        String.format("Recent weekday %s is market day (THIS TEST WILL FAIL FOR HOLIDAYS)",
                                day.toString()));
            }
        }

        @Test
        void recentWeekend() {
            for (LocalDate day : Utils.recentFullWeekend()) {
                assertTrue(!nyse.isMarketDay(day), String.format("Recent weekend date %s is NOT market day",
                        day.toString()));
            }
        }
    }

    @Nested
    class IsValidSymbol {
        @Test
        void realSymbol() {
            assertTrue(nyse.isValidSymbol(validSymbol), "Symbol should be valid: " + validSymbol);
        }

//        /*
//            False fail with demo api key (only supports requests/symbol from sample spec)
//         */
//        @Test
//        void junkValue() {
//            String symbol = "bad ticker 123";
//            assertTrue(nyse.isValidSymbol(symbol), "Symbol should be invalid: " + symbol);
//        }
    }

    @Nested
    class GetDailyGrowth {
        @Test
        void weekendReturnsFlatGrowth() {
            LocalDate day = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SATURDAY));
            double growth = nyse.getDailyGrowthPercentage(day, validSymbol);

            assertEquals(0.0, growth);
        }

        @Test
        void weekdayReturnsPlausibleValue() {
            LocalDate day = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.WEDNESDAY));
            double growth = nyse.getDailyGrowthPercentage(day, validSymbol);

            assertTrue(Utils.isBetween(growth, -50.0, 50.0),
                    String.format("Returned growth value looks very unlikely: %.2f", growth));
        }

        @Test
        void reportLastThirtyDaysGrowth() {
            for (LocalDate day : Utils.pastDays(30)) {
                nyse.getDailyGrowthPercentage(day, validSymbol);
            }
        }
    }
}

