package nmck.emotive_stocks;

import com.google.common.base.Strings;
import nmck.emotive_stocks.model.FeelingThresholds;
import nmck.emotive_stocks.model.FeelingWords;
import nmck.emotive_stocks.services.nyse.AlphaVantageNYSE;
import nmck.emotive_stocks.services.nyse.NYSE;
import nmck.emotive_stocks.services.nyse.SimpleRandomNYSE;
import nmck.emotive_stocks.services.twitter.FakeTwitterBot;
import nmck.emotive_stocks.services.twitter.TwitterBot;
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
        controller.setFeelingThresholds(getFeelingThresholds(lambdaConfig));
        controller.reactTo(ticker, reactionDate);
    }

    private FeelingThresholds getFeelingThresholds(LambdaConfig lambdaConfig) {
        FeelingThresholds.Builder builder = FeelingThresholds.newBuilder();
        if (lambdaConfig.getNeutralThreshold() != null) builder.setNeutralThreshold(lambdaConfig.getNeutralThreshold());
        if (lambdaConfig.getGoodThreshold() != null) builder.setGoodThreshold(lambdaConfig.getGoodThreshold());
        if (lambdaConfig.getVeryGoodThreshold() != null) builder.setVeryGoodThreshold(lambdaConfig.getVeryGoodThreshold());
        return builder.build();
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
            // TODO implement twitter
            throw new RuntimeException("Real twitter not yet implemented");
        }
    }

    private NYSE initializeNYSE(LambdaConfig lambdaConfig) {
        String alphaVantageApiKey = lambdaConfig.getAlphaVantageApiKey();
        if (alphaVantageApiKey == null) {
            LOGGER.info("No nyse api key; using simple random");
            return new SimpleRandomNYSE();
        } else {
            LOGGER.info("Using NYSE data from Alpha Vantage");
            return new AlphaVantageNYSE(alphaVantageApiKey);
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
