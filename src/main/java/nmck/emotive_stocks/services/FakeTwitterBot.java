package nmck.emotive_stocks.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FakeTwitterBot implements  TwitterBot {
    private static final Logger LOGGER = LogManager.getLogger(FakeTwitterBot.class);

    @Override
    public void tweet(String content) {
        LOGGER.info("Pretending to tweet: " + content);
    }
}
