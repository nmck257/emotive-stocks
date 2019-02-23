package nmck.emotive_stocks.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class NYSETest {
    private NYSE nyse;
    abstract protected NYSE createInstance();

    @Nested
    class IsMarketDay {
        private List<LocalDate> daysStartingOn(int year, int month, int dayOfMonth, int numberOfDays) {
            List<LocalDate> days = new ArrayList<>();
            for (int i = 0; i < numberOfDays; i++) {
                days.add(LocalDate.of(year, month, dayOfMonth + i));
            }
            return days;
        }

        @BeforeEach
        void setup() {
            nyse = createInstance();
        }

        @Test
        void averageWeekdays() {
            List<LocalDate> averageWeek;

            averageWeek = daysStartingOn(2019, 2, 11, 5);
            for (LocalDate day : averageWeek) {
                assertTrue(nyse.isMarketDay(day), String.format("Average weekday %s is market day", day.toString()));
            }

            averageWeek = daysStartingOn(2018, 8, 20, 5);
            for (LocalDate day : averageWeek) {
                assertTrue(nyse.isMarketDay(day), String.format("Average weekday %s is market day", day.toString()));
            }
        }

        @Test
        void averageWeekend() {
            List<LocalDate> averageWeek = daysStartingOn(2019, 2, 16, 2);
            for (LocalDate day : averageWeek) {
                assertTrue(!nyse.isMarketDay(day), String.format("Average weekend date %s is NOT market day", day.toString()));
            }
        }

        @Test
        void holidays() {
            List<LocalDate> holidays = new ArrayList<>();
            holidays.add(LocalDate.of(2018, 12, 25));
            holidays.add(LocalDate.of(2016, 1, 1));
            holidays.add(LocalDate.of(2012, 10, 29)); // hurricane sandy

            for (LocalDate day : holidays) {
                assertTrue(!nyse.isMarketDay(day), String.format("Special day %s is NOT market day", day.toString()));
            }
        }
    }

    @Nested
    class GetDailyGrowth {
        /*
        does it really make sense to write unit tests for external services?
         */
    }
}
