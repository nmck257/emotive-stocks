package nmck.emotive_stocks;

import com.google.common.base.Strings;
import nmck.emotive_stocks.model.FeelingWords;
import nmck.emotive_stocks.services.FakeTwitterBot;
import nmck.emotive_stocks.services.NYSE;
import nmck.emotive_stocks.services.SimpleRandomNYSE;
import nmck.emotive_stocks.services.TwitterBot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public class Lambda {
    private static final Logger LOGGER = LogManager.getLogger(Lambda.class);

    public void handle(LambdaConfig lambdaConfig) {
        LOGGER.info("Starting handler");

        String ticker = requireTicker(lambdaConfig);
        LocalDate reactionDate = getReactionDate(lambdaConfig);
        NYSE nyse = initializeNYSE(lambdaConfig);
        TwitterBot twitterBot = initializeTwitterBot(lambdaConfig);

        Controller controller = new Controller(nyse, twitterBot);
        controller.setFeelingWords(getFeelingWords(lambdaConfig));
        controller.reactTo(ticker, reactionDate);
    }

    private FeelingWords getFeelingWords(LambdaConfig lambdaConfig) {
        Set<String> good = Optional.ofNullable(lambdaConfig.getGoodFeelingWords())
                .orElse(FeelingWords.getDefaultGoodFeelings());
        Set<String> bad = Optional.ofNullable(lambdaConfig.getBadFeelingWords())
                .orElse(FeelingWords.getDefaultBadFeelings());
        Set<String> neutral = Optional.ofNullable(lambdaConfig.getNeutralFeelingWords())
                .orElse(FeelingWords.getDefaultNeutralFeelings());
        return new FeelingWords(good, bad, neutral);
    }

    private TwitterBot initializeTwitterBot(LambdaConfig lambdaConfig) {
        String twitterApiKey = lambdaConfig.getTwitterApiKey();
        if (twitterApiKey == null) {
            LOGGER.info("No twitter api key; using fake");
            return new FakeTwitterBot();
        } else {
            throw new RuntimeException("Real twitter not yet implemented");
        }
    }

    private NYSE initializeNYSE(LambdaConfig lambdaConfig) {
        String nyseApiKey = lambdaConfig.getNyseApiKey();
        if (nyseApiKey == null) {
            LOGGER.info("No nyse api key; using simple random");
            return new SimpleRandomNYSE();
        } else {
            throw new RuntimeException("Real NYSE not yet implemented");
        }
    }

    private String requireTicker(LambdaConfig lambdaConfig) {
        String ticker = lambdaConfig.getTicker();
        if (Strings.isNullOrEmpty(ticker)) {
            LOGGER.error("No stock symbol in config");
            throw new RuntimeException();
        }
        LOGGER.info("Using ticker: " + ticker);
        return ticker;
    }

    private LocalDate getReactionDate(LambdaConfig lambdaConfig) {
        LocalDate date = Optional.ofNullable(lambdaConfig.getReactionDate())
                .orElse(LocalDate.now());
        LOGGER.info("Using date: " + date.toString());
        return date;
    }
}
