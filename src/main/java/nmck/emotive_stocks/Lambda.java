package nmck.emotive_stocks;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import nmck.emotive_stocks.model.EmotiveStock;
import nmck.emotive_stocks.services.NYSE;
import nmck.emotive_stocks.services.TwitterBot;

import java.util.Date;

public class Lambda {
    public void myHandler(AppConfig appConfig, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("woohoo");
        NYSE nyse;
        EmotiveStock emotiveStock;
        TwitterBot twitterBot;

        //twitterBot.tweet(emotiveStock.reactTo(new Date()));
    }
}
