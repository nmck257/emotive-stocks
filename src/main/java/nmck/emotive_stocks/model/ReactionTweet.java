package nmck.emotive_stocks.model;

import java.util.List;

public class ReactionTweet {
    private final String phrase;
    private final double growth;
    private final List<Hashtag> hashtags;

    public ReactionTweet(String phrase, double growth, List<Hashtag> hashtags) {
        this.phrase = phrase;
        this.growth = growth;
        this.hashtags = hashtags;
    }

    public String getPhrase() {
        return phrase;
    }

    public double getGrowth() {
        return growth;
    }

    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f%%) %s", phrase, growth, Hashtag.toString(hashtags)).trim();
    }

}
