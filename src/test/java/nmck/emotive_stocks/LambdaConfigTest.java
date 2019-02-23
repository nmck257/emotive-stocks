package nmck.emotive_stocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LambdaConfigTest {
    private LambdaConfig lambdaConfig;

    @Nested
    class GetMissingConfigEntries {
        @BeforeEach
        void setup() {
            lambdaConfig = new LambdaConfig();
        }

        @Test
        void noFieldsSet() {
            Set<String> entries = lambdaConfig.getMissingConfigEntries();
            assertEquals(5, entries.size());
            assertTrue(entries.contains("ticker"), "Result must include ticker");
            assertTrue(entries.contains("nyseApiKey"), "Result must include nyseApiKey");
            assertTrue(entries.contains("twitterApiKey"), "Result must include twitterApiKey");
            assertTrue(entries.contains("twitterHandle"), "Result must include twitterHandle");
        }

        @Test
        void allFieldsSet() {
            lambdaConfig.setNyseApiKey("dummy");
            lambdaConfig.setTicker("dummy");
            lambdaConfig.setTwitterApiKey("dummy");
            lambdaConfig.setTwitterHandle("dummy");
            lambdaConfig.setReactionDate(LocalDate.now());
            Set<String> entries = lambdaConfig.getMissingConfigEntries();
            assertEquals(0, entries.size());
        }
    }
}
