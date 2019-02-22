package nmck.emotive_stocks;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class AppConfig {
    private String ticker;
    private String nyseApiKey;
    private String twitterApiKey;
    private String twitterHandle;

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

    public Set<String> getMissingConfigEntries() {
        return getNullFieldList();
    }

    private Set<String> getNullFieldList() {
        return Arrays.stream(this.getClass().getDeclaredFields())
                .filter(field -> {
                    try {
                        return field.get(this) == null;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Exception using reflection to check fields");
                    }
                }).map(Field::getName).collect(Collectors.toSet());
    }
}
