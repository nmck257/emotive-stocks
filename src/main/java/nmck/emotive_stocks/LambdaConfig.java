package nmck.emotive_stocks;

import java.time.LocalDate;
import java.util.Set;

public class LambdaConfig {
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

    public LocalDate getReactionDate() {
        return reactionDate == null ? null : LocalDate.parse(reactionDate);
    }

    public void setReactionDate(String reactionDate) {
        this.reactionDate = reactionDate;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getAlphaVantageApiKey() {
        return alphaVantageApiKey;
    }

    public void setAlphaVantageApiKey(String alphaVantageApiKey) {
        this.alphaVantageApiKey = alphaVantageApiKey;
    }

    public String getTwitterConsumerKey() {
        return twitterConsumerKey;
    }

    public void setTwitterConsumerKey(String twitterConsumerKey) {
        this.twitterConsumerKey = twitterConsumerKey;
    }

    public String getTwitterConsumerSecret() {
        return twitterConsumerSecret;
    }

    public void setTwitterConsumerSecret(String twitterConsumerSecret) {
        this.twitterConsumerSecret = twitterConsumerSecret;
    }

    public String getTwitterAccessToken() {
        return twitterAccessToken;
    }

    public void setTwitterAccessToken(String twitterAccessToken) {
        this.twitterAccessToken = twitterAccessToken;
    }

    public String getTwitterAccessTokenSecret() {
        return twitterAccessTokenSecret;
    }

    public void setTwitterAccessTokenSecret(String twitterAccessTokenSecret) {
        this.twitterAccessTokenSecret = twitterAccessTokenSecret;
    }

    public Set<String> getGoodFeelingWords() {
        return goodFeelingWords;
    }

    public void setGoodFeelingWords(Set<String> goodFeelingWords) {
        this.goodFeelingWords = goodFeelingWords;
    }

    public Set<String> getBadFeelingWords() {
        return badFeelingWords;
    }

    public void setBadFeelingWords(Set<String> badFeelingWords) {
        this.badFeelingWords = badFeelingWords;
    }

    public Set<String> getNeutralFeelingWords() {
        return neutralFeelingWords;
    }

    public void setNeutralFeelingWords(Set<String> neutralFeelingWords) {
        this.neutralFeelingWords = neutralFeelingWords;
    }

    public Double getNeutralThreshold() {
        return neutralThreshold;
    }

    public void setNeutralThreshold(Double neutralThreshold) {
        this.neutralThreshold = neutralThreshold;
    }

    public Double getGoodThreshold() {
        return goodThreshold;
    }

    public void setGoodThreshold(Double goodThreshold) {
        this.goodThreshold = goodThreshold;
    }

    public Double getVeryGoodThreshold() {
        return veryGoodThreshold;
    }

    public void setVeryGoodThreshold(Double veryGoodThreshold) {
        this.veryGoodThreshold = veryGoodThreshold;
    }
}
