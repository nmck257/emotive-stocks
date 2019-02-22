package nmck.emotive_stocks;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.util.Date;

public class Lambda {
    public void myHandler(String ticker, String apiKey, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log(String.format("Using ticker: %s", ticker));
        NYSE nyse;
        EmotiveStock emotiveStock;
        TwitterBot twitterBot;

        twitterBot.tweet(emotiveStock.reactTo(new Date()));
    }

    public static void main(String[] args) {
        System.out.println("");
    }
}
