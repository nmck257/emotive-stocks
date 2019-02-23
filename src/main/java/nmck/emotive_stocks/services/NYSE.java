package nmck.emotive_stocks.services;

import java.time.LocalDate;

public interface NYSE {
    float getDailyGrowth(LocalDate localDate, String symbol);
    boolean isMarketDay(LocalDate localDate);
}
