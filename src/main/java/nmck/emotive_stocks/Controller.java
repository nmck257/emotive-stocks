package nmck.emotive_stocks;

import nmck.emotive_stocks.model.EmotiveStock;
import nmck.emotive_stocks.services.NYSE;
import nmck.emotive_stocks.services.TwitterBot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;

public class Controller {
    private static final Logger LOGGER = LogManager.getLogger(Lambda.class);
    private NYSE nyse;
    private TwitterBot twitterBot;

    public Controller(@Nonnull NYSE nyse, @Nonnull TwitterBot twitterBot) {
        this.nyse = nyse;
        this.twitterBot = twitterBot;
    }

    @Nullable
    public String reactTo(String ticker, LocalDate reactionDate) {
        requireTickerIsValid(ticker, nyse);
        if (nyse.isMarketDay(reactionDate)) {
            EmotiveStock emotiveStock = new EmotiveStock(nyse, ticker);

            String reaction = emotiveStock.reactTo(reactionDate);
            twitterBot.tweet(reaction);

            return reaction;
        } else {
            LOGGER.info("Markets closed; no tweet");
            return null;
        }
    }

    private void requireTickerIsValid(String ticker, NYSE nyse) {
        if (!nyse.isValidSymbol(ticker)) {
            String msg = "Ticker from config is not recognized by NYSE service: " + ticker;
            LOGGER.error(msg);
            throw new RuntimeException(msg);
        }
    }
}