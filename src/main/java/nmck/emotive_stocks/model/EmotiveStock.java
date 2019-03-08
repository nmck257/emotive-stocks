package nmck.emotive_stocks.model;

import nmck.emotive_stocks.services.nyse.NYSE;
import nmck.emotive_stocks.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class EmotiveStock {
    private static final Logger LOGGER = LogManager.getLogger(EmotiveStock.class);
    private final FeelingWords feelingWords;
    private final FeelingThresholds feelingThresholds;
    private final NYSE nyse;
    private final String ticker;

    public EmotiveStock(NYSE nyse, String ticker, FeelingWords feelingWords, FeelingThresholds feelingThresholds) {
        this.nyse = nyse;
        this.ticker = ticker;
        this.feelingWords = feelingWords;
        this.feelingThresholds = feelingThresholds;
    }

    public String getTicker() {
        return ticker;
    }

    public String reactTo(LocalDate localDate) {
        double growth = nyse.getDailyGrowthPercentage(localDate, ticker);
        Feeling feeling = feelingThresholds.assess(growth);
        String reaction = feelingWords.getRandom(feeling);
        LOGGER.info(String.format("Symbol %s feels %s about growth of %.2f%% and says \"%s\"",
                ticker, feeling, growth, reaction));
        // TODO add hashtag support (possibly refactor to isolate reaction concept)
        return String.format("%s (%.2f%%)", reaction, growth);
    }
}
