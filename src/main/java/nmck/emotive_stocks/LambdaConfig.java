package nmck.emotive_stocks;

import java.time.LocalDate;
import java.util.Set;

public class LambdaConfig {
    private String reactionDate;
    private String ticker;
    private String alphaVantageApiKey;
    private String twitterApiKey;
    private String twitterHandle;
    private Set<String> goodFeelingWords;
    private Set<String> badFeelingWords;
    private Set<String> neutralFeelingWords;

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

    public String getTwitterApiKey() {
        return twitterApiKey;
    }

    public void setTwitterApiKey(String twitterApiKey) {
        this.twitterApiKey = twitterApiKey;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
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
}
