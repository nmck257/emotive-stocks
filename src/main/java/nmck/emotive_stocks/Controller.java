package nmck.emotive_stocks;

import nmck.emotive_stocks.model.*;
import nmck.emotive_stocks.model.feelings.FeelingThresholds;
import nmck.emotive_stocks.model.feelings.FeelingWords;
import nmck.emotive_stocks.services.nyse.NYSE;
import nmck.emotive_stocks.services.twitter.TwitterBot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private static final Logger LOGGER = LogManager.getLogger(Lambda.class);
    private final NYSE nyse;
    private final TwitterBot twitterBot;
    private FeelingWords feelingWords = FeelingWords.getDefault();
    private FeelingThresholds feelingThresholds = FeelingThresholds.getDefault();
    private List<Hashtag> hashtagList = Hashtag.getDefaultList();

    public Controller(@Nonnull NYSE nyse, @Nonnull TwitterBot twitterBot) {
        this.nyse = nyse;
        this.twitterBot = twitterBot;
    }

    public FeelingWords getFeelingWords() {
        return feelingWords;
    }

    public void setFeelingWords(FeelingWords feelingWords) {
        this.feelingWords = feelingWords;
    }

    public FeelingThresholds getFeelingThresholds() {
        return feelingThresholds;
    }

    public void setFeelingThresholds(FeelingThresholds feelingThresholds) {
        this.feelingThresholds = feelingThresholds;
    }

    public List<Hashtag> getHashtagList() {
        return new ArrayList<>(hashtagList);
    }

    public void setHashtagList(List<Hashtag> hashtagList) {
        this.hashtagList = new ArrayList<>(hashtagList);
    }

    @Nullable
    public String reactTo(String ticker, LocalDate reactionDate) {
        requireTickerIsValid(ticker, nyse);
        if (nyse.isMarketDay(reactionDate)) {
            EmotiveStock emotiveStock = new EmotiveStock(nyse, ticker, feelingWords, feelingThresholds, hashtagList);

            String reaction = emotiveStock.reactTo(reactionDate).toString();
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
