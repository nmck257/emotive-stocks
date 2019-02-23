package nmck.emotive_stocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppConfigTest {
    private AppConfig appConfig;

    @Nested
    class GetMissingConfigEntries {
        @BeforeEach
        void setup() {
            appConfig = new AppConfig();
        }

        @Test
        void noFieldsSet() {
            Set<String> entries = appConfig.getMissingConfigEntries();
            assertEquals(4, entries.size());
            assertTrue(entries.contains("ticker"), "Result must include ticker");
            assertTrue(entries.contains("nyseApiKey"), "Result must include nyseApiKey");
            assertTrue(entries.contains("twitterApiKey"), "Result must include twitterApiKey");
            assertTrue(entries.contains("twitterHandle"), "Result must include twitterHandle");
        }

        @Test
        void allFieldsSet() {
            appConfig.setNyseApiKey("dummy");
            appConfig.setTicker("dummy");
            appConfig.setTwitterApiKey("dummy");
            appConfig.setTwitterHandle("dummy");
            Set<String> entries = appConfig.getMissingConfigEntries();
            assertEquals(0, entries.size());
        }
    }
}
