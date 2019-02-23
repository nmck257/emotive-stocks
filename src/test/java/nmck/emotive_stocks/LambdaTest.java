package nmck.emotive_stocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class LambdaTest {
    private Lambda lambda;
    private LambdaConfig lambdaConfig;

    @BeforeEach
    void setUp() {
        lambda = new Lambda();
        lambdaConfig = new LambdaConfig();
    }

    @Test
    void missingTickerThrowsException() {
        assertThrows(RuntimeException.class, () -> lambda.handle(lambdaConfig));
    }
}
