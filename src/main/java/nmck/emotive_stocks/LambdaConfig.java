package nmck.emotive_stocks;

import com.google.common.base.Strings;
import nmck.emotive_stocks.model.Hashtag;
import nmck.emotive_stocks.model.feelings.FeelingThresholds;
import nmck.emotive_stocks.model.feelings.FeelingWords;
import nmck.emotive_stocks.services.nyse.AlphaVantageNYSE;
import nmck.emotive_stocks.services.nyse.NYSE;
import nmck.emotive_stocks.services.nyse.SimpleRandomNYSE;
import nmck.emotive_stocks.services.twitter.FakeTwitterBot;
import nmck.emotive_stocks.services.twitter.Twitter4jTwitterBot;
import nmck.emotive_stocks.services.twitter.TwitterBot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Deserialized from AWS Lambda context (hence the protected constructor and setters)
 */
@SuppressWarnings("unused")
public class LambdaConfig {
    private static final Logger LOGGER = LogManager.getLogger(LambdaConfig.class);
    private String reactionDate;
    private String ticker;
    private String alphaVantageApiKey;
    private String twitterConsumerKey;
    private String twitterConsumerSecret;
    private String twitterAccessToken;
    private String twitterAccessTokenSecret;
    private Set<String> goodFeelingWords;
    private Set<String> badFeelingWords;
    private Set<String> neutralFeelingWords;
    private Double neutralThreshold;
    private Double goodThreshold;
    private Double veryGoodThreshold;
    private List<String> hashtags;

    protected LambdaConfig() {}

    protected void setReactionDate(String reactionDate) {
        this.reactionDate = reactionDate;
    }

    protected void setTicker(String ticker) {
        this.ticker = ticker;
    }

    protected void setAlphaVantageApiKey(String alphaVantageApiKey) {
        this.alphaVantageApiKey = alphaVantageApiKey;
    }

    protected void setTwitterConsumerKey(String twitterConsumerKey) {
        this.twitterConsumerKey = twitterConsumerKey;
    }

    protected void setTwitterConsumerSecret(String twitterConsumerSecret) {
        this.twitterConsumerSecret = twitterConsumerSecret;
    }

    protected void setTwitterAccessToken(String twitterAccessToken) {
        this.twitterAccessToken = twitterAccessToken;
    }

    protected void setTwitterAccessTokenSecret(String twitterAccessTokenSecret) {
        this.twitterAccessTokenSecret = twitterAccessTokenSecret;
    }

    protected void setGoodFeelingWords(Set<String> goodFeelingWords) {
        this.goodFeelingWords = goodFeelingWords;
    }

    protected void setBadFeelingWords(Set<String> badFeelingWords) {
        this.badFeelingWords = badFeelingWords;
    }

    protected void setNeutralFeelingWords(Set<String> neutralFeelingWords) {
        this.neutralFeelingWords = neutralFeelingWords;
    }

    protected void setNeutralThreshold(Double neutralThreshold) {
        this.neutralThreshold = neutralThreshold;
    }

    protected void setGoodThreshold(Double goodThreshold) {
        this.goodThreshold = goodThreshold;
    }

    protected void setVeryGoodThreshold(Double veryGoodThreshold) {
        this.veryGoodThreshold = veryGoodThreshold;
    }

    protected void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    private LambdaConfig(String reactionDate, String ticker, String alphaVantageApiKey, String twitterConsumerKey,
                         String twitterConsumerSecret, String twitterAccessToken, String twitterAccessTokenSecret,
                         Set<String> goodFeelingWords, Set<String> badFeelingWords, Set<String> neutralFeelingWords,
                         Double neutralThreshold, Double goodThreshold, Double veryGoodThreshold,
                         List<String> hashtags) {
        this.reactionDate = reactionDate;
        this.ticker = ticker;
        this.alphaVantageApiKey = alphaVantageApiKey;
        this.twitterConsumerKey = twitterConsumerKey;
        this.twitterConsumerSecret = twitterConsumerSecret;
        this.twitterAccessToken = twitterAccessToken;
        this.twitterAccessTokenSecret = twitterAccessTokenSecret;
        this.goodFeelingWords = goodFeelingWords;
        this.badFeelingWords = badFeelingWords;
        this.neutralFeelingWords = neutralFeelingWords;
        this.neutralThreshold = neutralThreshold;
        this.goodThreshold = goodThreshold;
        this.veryGoodThreshold = veryGoodThreshold;
        this.hashtags = hashtags;
    }

    public LocalDate getReactionDate() {
        LocalDate date = reactionDate == null
                ? LocalDate.now()
                : LocalDate.parse(reactionDate);
        LOGGER.info("Using date: " + date.toString());
        return date;
    }

    public FeelingThresholds getFeelingThresholds() {
        FeelingThresholds.Builder builder = FeelingThresholds.newBuilder();
        Optional.ofNullable(neutralThreshold).ifPresent(builder::setNeutralThreshold);
        Optional.ofNullable(goodThreshold).ifPresent(builder::setGoodThreshold);
        Optional.ofNullable(veryGoodThreshold).ifPresent(builder::setVeryGoodThreshold);
        return builder.build();
    }

    public FeelingWords getFeelingWords() {
        Set<String> good = Optional.ofNullable(goodFeelingWords)
                .orElse(FeelingWords.getDefaultGoodFeelings());
        Set<String> bad = Optional.ofNullable(badFeelingWords)
                .orElse(FeelingWords.getDefaultBadFeelings());
        Set<String> neutral = Optional.ofNullable(neutralFeelingWords)
                .orElse(FeelingWords.getDefaultNeutralFeelings());
        return new FeelingWords(good, bad, neutral);
    }

    public List<Hashtag> getHashtagList() {
        if (hashtags != null) {
            return Hashtag.fromStringList(hashtags);
        } else {
            LOGGER.info("No hashtag list; using default");
            return Hashtag.getDefaultList();
        }
    }

    public TwitterBot initializeTwitterBot() {
        if (twitterConsumerKey == null || twitterConsumerSecret == null ||
                twitterAccessToken == null || twitterAccessTokenSecret == null) {
            LOGGER.info("Incomplete twitter config; using fake");
            return new FakeTwitterBot();
        } else {
            LOGGER.info("Connecting to twitter");
            return new Twitter4jTwitterBot(twitterConsumerKey, twitterConsumerSecret,
                    twitterAccessToken, twitterAccessTokenSecret);
        }
    }

    public NYSE initializeNYSE() {
        if (alphaVantageApiKey == null) {
            LOGGER.info("No nyse api key; using simple random");
            return new SimpleRandomNYSE();
        } else {
            LOGGER.info("Using NYSE data from Alpha Vantage");
            return new AlphaVantageNYSE(alphaVantageApiKey);
        }
    }

    public String getTicker() {
        if (Strings.isNullOrEmpty(ticker)) {
            LOGGER.error("No stock symbol in config");
            throw new RuntimeException();
        }
        LOGGER.info("Using ticker: " + ticker);
        return ticker;
    }

    @SuppressWarnings("unused")
    public static class Builder {
        private String reactionDate;
        private String ticker;
        private String alphaVantageApiKey;
        private String twitterConsumerKey;
        private String twitterConsumerSecret;
        private String twitterAccessToken;
        private String twitterAccessTokenSecret;
        private Set<String> goodFeelingWords;
        private Set<String> badFeelingWords;
        private Set<String> neutralFeelingWords;
        private Double neutralThreshold;
        private Double goodThreshold;
        private Double veryGoodThreshold;
        private List<String> hashtagStringList;

        public Builder setReactionDate(String reactionDate) {
            this.reactionDate = reactionDate;
            return this;
        }

        public Builder setTicker(String ticker) {
            this.ticker = ticker;
            return this;
        }

        public Builder setAlphaVantageApiKey(String alphaVantageApiKey) {
            this.alphaVantageApiKey = alphaVantageApiKey;
            return this;
        }

        public Builder setTwitterConsumerKey(String twitterConsumerKey) {
            this.twitterConsumerKey = twitterConsumerKey;
            return this;
        }

        public Builder setTwitterConsumerSecret(String twitterConsumerSecret) {
            this.twitterConsumerSecret = twitterConsumerSecret;
            return this;
        }

        public Builder setTwitterAccessToken(String twitterAccessToken) {
            this.twitterAccessToken = twitterAccessToken;
            return this;
        }

        public Builder setTwitterAccessTokenSecret(String twitterAccessTokenSecret) {
            this.twitterAccessTokenSecret = twitterAccessTokenSecret;
            return this;
        }

        public Builder setGoodFeelingWords(Set<String> goodFeelingWords) {
            this.goodFeelingWords = goodFeelingWords;
            return this;
        }

        public Builder setBadFeelingWords(Set<String> badFeelingWords) {
            this.badFeelingWords = badFeelingWords;
            return this;
        }

        public Builder setNeutralFeelingWords(Set<String> neutralFeelingWords) {
            this.neutralFeelingWords = neutralFeelingWords;
            return this;
        }

        public Builder setNeutralThreshold(Double neutralThreshold) {
            this.neutralThreshold = neutralThreshold;
            return this;
        }

        public Builder setGoodThreshold(Double goodThreshold) {
            this.goodThreshold = goodThreshold;
            return this;
        }

        public Builder setVeryGoodThreshold(Double veryGoodThreshold) {
            this.veryGoodThreshold = veryGoodThreshold;
            return this;
        }

        public Builder setHashtagStringList(List<String> hashtagStringList) {
            this.hashtagStringList = hashtagStringList;
            return this;
        }

        public LambdaConfig build() {
            return new LambdaConfig(reactionDate, ticker, alphaVantageApiKey, twitterConsumerKey, twitterConsumerSecret,
                    twitterAccessToken, twitterAccessTokenSecret, goodFeelingWords, badFeelingWords,
                    neutralFeelingWords, neutralThreshold, goodThreshold, veryGoodThreshold, hashtagStringList);
        }
    }
}
