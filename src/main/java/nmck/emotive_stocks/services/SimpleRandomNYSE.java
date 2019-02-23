package nmck.emotive_stocks.services;

import nmck.emotive_stocks.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SimpleRandomNYSE implements NYSE {
    private static final Logger LOGGER = LogManager.getLogger(SimpleRandomNYSE.class);
    private static final double BIGGEST_GROWTH = 5.0;
    private final StockMemory stockMemory = new StockMemory();

    /**
     * Will remember and reuse previously-returned values for specific dates and symbols
     *
     * @param localDate the date to check
     * @param symbol the stock to check
     * @return 0.0 if not market day; otherwise, a randomly-assigned growth value
     */
    @Override
    public double getDailyGrowth(LocalDate localDate, String symbol) {
        return stockMemory.check(localDate, symbol);
    }

    /**
     * @param localDate the date to test
     * @return true if localDate is weekday (ignores special days)
     */
    @Override
    public boolean isMarketDay(LocalDate localDate) {
        return Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY).contains(localDate.getDayOfWeek());
    }

    /**
     * @param symbol to check
     * @return true if symbol length < 6
     */
    @Override
    public boolean isValidSymbol(String symbol) {
        return symbol.length() < 6;
    }

    private class StockMemory {
        private final Map<LocalDate, Map<String, Double>> memory = new HashMap<>();

        double check(LocalDate localDate, String symbol) {
            if (!isMarketDay(localDate)) return 0.0;
            if (!memory.containsKey(localDate)) {
                memory.put(localDate, new HashMap<>());
            }
            if (!memory.get(localDate).containsKey(symbol)) {
                double growth = Utils.generateRandomDouble(BIGGEST_GROWTH * -1, BIGGEST_GROWTH);
                LOGGER.info(String.format("Assigning growth: %s, %s, %.3f",
                        localDate.toString(), symbol, growth));
                memory.get(localDate).put(symbol, growth);
            }
            return memory.get(localDate).get(symbol);
        }
    }
}
