package nmck.emotive_stocks.services.twitter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class ExternalTwitterBot implements TwitterBot {
    private static final Logger LOGGER = LogManager.getLogger(ExternalTwitterBot.class);
    private Twitter twitter;

    public ExternalTwitterBot(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);
        twitter = new TwitterFactory(cb.build()).getInstance();
    }

    @Override
    public void tweet(String content) {
        try {
            twitter.updateStatus(content);
        } catch (TwitterException e) {
            LOGGER.error("Exception while tweeting", e);
            throw new RuntimeException(e);
        }
    }
}
