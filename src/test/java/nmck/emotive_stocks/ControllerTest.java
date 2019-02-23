package nmck.emotive_stocks;

import com.google.common.base.Strings;
import nmck.emotive_stocks.model.EmotiveStock;
import nmck.emotive_stocks.services.FakeTwitterBot;
import nmck.emotive_stocks.services.NYSE;
import nmck.emotive_stocks.services.SimpleRandomNYSE;
import nmck.emotive_stocks.services.TwitterBot;
import nmck.emotive_stocks.util.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private static final String TICKER = "cash";
    private static final String BAD_TICKER = "rjsdngfvernstjih34985y4378y54378 5";
    private final static LocalDate A_WEDNESDAY = LocalDate.of(2019, 2, 20);
    private final static LocalDate A_SUNDAY = LocalDate.of(2019, 2, 24);
    private final NYSE nyse = new SimpleRandomNYSE();
    private final TwitterBot twitterBot = new FakeTwitterBot();
    private final Controller controller = new Controller(nyse, twitterBot);

    @Test
    void marketDay() {
        String reaction = controller.reactTo(TICKER, A_WEDNESDAY);
        assertTrue(!Strings.isNullOrEmpty(reaction), "Must return a reaction");
    }

    @Test
    void nonMarketDay() {
        String reaction = controller.reactTo(TICKER, A_SUNDAY);
        assertNull(reaction, "Reaction must be null if markets are closed");
    }

    @Test
    void badTickerThrowsException() {
        assertThrows(RuntimeException.class, () -> controller.reactTo(BAD_TICKER, LocalDate.now()));
    }

}

