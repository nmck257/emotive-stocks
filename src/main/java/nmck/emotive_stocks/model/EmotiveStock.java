package nmck.emotive_stocks.model;

import nmck.emotive_stocks.model.feelings.Feeling;
import nmck.emotive_stocks.model.feelings.FeelingThresholds;
import nmck.emotive_stocks.model.feelings.FeelingWords;
import nmck.emotive_stocks.services.nyse.NYSE;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmotiveStock {
    private static final Logger LOGGER = LogManager.getLogger(EmotiveStock.class);
    private final FeelingWords feelingWords;
    private final FeelingThresholds feelingThresholds;
    private final NYSE nyse;
    private final String ticker;
    private final List<Hashtag> hashtagList;

    public EmotiveStock(NYSE nyse, String ticker, FeelingWords feelingWords, FeelingThresholds feelingThresholds, List<Hashtag> hashtagList) {
        this.nyse = nyse;
        this.ticker = ticker;
        this.feelingWords = feelingWords;
        this.feelingThresholds = feelingThresholds;
        this.hashtagList = new ArrayList<>(hashtagList);
    }

    public String getTicker() {
        return ticker;
    }

    public ReactionTweet reactTo(LocalDate localDate) {
        double growth = nyse.getDailyGrowthPercentage(localDate, ticker);
        Feeling feeling = feelingThresholds.assess(growth);
        String reaction = feelingWords.getRandom(feeling);
        LOGGER.info(String.format("Symbol %s feels %s about growth of %.2f%% and says \"%s\"",
                ticker, feeling, growth, reaction));
        return new ReactionTweet(reaction, growth, hashtagList);
    }
}
