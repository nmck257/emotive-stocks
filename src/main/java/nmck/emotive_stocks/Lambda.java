package nmck.emotive_stocks;

import com.amazonaws.services.lambda.runtime.Context;
import nmck.emotive_stocks.model.EmotiveStock;
import nmck.emotive_stocks.services.NYSE;
import nmck.emotive_stocks.services.TwitterBot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Lambda {
    private static final Logger LOGGER = LogManager.getLogger(Lambda.class);

    public void myHandler(AppConfig appConfig, Context context) {
        LOGGER.info("woohoo");
        NYSE nyse;
        EmotiveStock emotiveStock;
        TwitterBot twitterBot;

        //twitterBot.tweet(emotiveStock.reactTo(new Date()));
    }
}
