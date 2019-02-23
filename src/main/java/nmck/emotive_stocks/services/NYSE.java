package nmck.emotive_stocks.services;

import java.time.LocalDate;

public interface NYSE {
    double getDailyGrowth(LocalDate localDate, String symbol);
    boolean isMarketDay(LocalDate localDate);
}
