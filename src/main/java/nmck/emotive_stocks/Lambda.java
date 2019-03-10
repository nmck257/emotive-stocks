package nmck.emotive_stocks;

import nmck.emotive_stocks.services.nyse.NYSE;
import nmck.emotive_stocks.services.twitter.TwitterBot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class Lambda {
    private static final Logger LOGGER = LogManager.getLogger(Lambda.class);

    public void handle(LambdaConfig lambdaConfig) {
        LOGGER.info("Starting handler");

        String ticker = lambdaConfig.getTicker();
        LocalDate reactionDate = lambdaConfig.getReactionDate();
        NYSE nyse = lambdaConfig.initializeNYSE();
        TwitterBot twitterBot = lambdaConfig.initializeTwitterBot();

        Controller controller = new Controller(nyse, twitterBot);
        controller.setFeelingWords(lambdaConfig.getFeelingWords());
        controller.setFeelingThresholds(lambdaConfig.getFeelingThresholds());
        controller.setHashtagList(lambdaConfig.getHashtagList());
        controller.reactTo(ticker, reactionDate);
    }
}
