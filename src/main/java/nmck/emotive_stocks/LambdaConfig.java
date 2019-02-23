package nmck.emotive_stocks;

import java.time.LocalDate;

public class LambdaConfig {
    private LocalDate reactionDate;
    private String ticker;
    private String nyseApiKey;
    private String twitterApiKey;
    private String twitterHandle;

    public LocalDate getReactionDate() {
        return reactionDate;
    }

    public void setReactionDate(LocalDate reactionDate) {
        this.reactionDate = reactionDate;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getNyseApiKey() {
        return nyseApiKey;
    }

    public void setNyseApiKey(String nyseApiKey) {
        this.nyseApiKey = nyseApiKey;
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
}
