package nmck.emotive_stocks.services.nyse;

import java.time.LocalDate;

public interface NYSE {
    double getDailyGrowthPercentage(LocalDate localDate, String symbol);
    boolean isMarketDay(LocalDate localDate);
    boolean isValidSymbol(String symbol);
}
