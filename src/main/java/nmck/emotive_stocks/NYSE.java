package nmck.emotive_stocks;

import java.util.Date;

public interface NYSE {
    String getDailyGrowth(Date date, String symbol);
    boolean isMarketDay(Date date);
}
