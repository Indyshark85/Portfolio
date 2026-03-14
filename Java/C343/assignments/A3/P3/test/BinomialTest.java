import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BinomialTest {

    @Test
    void smallValues() {
        BinomialCoefficient rec = new RecursiveBinomial();
        BinomialCoefficient dp  = new DPBinomial();
        int[][] expected = {
            {1},
            {1,1},
            {1,2,1},
            {1,3,3,1},
            {1,4,6,4,1},
        };
        for (int n = 0; n < expected.length; n++) {
            for (int k = 0; k <= n; k++) {
                assertEquals(expected[n][k], rec.binom(n,k));
                assertEquals(expected[n][k], dp.binom(n,k));
            }
        }
    }

    @Test
    void mediumValue() {
        BinomialCoefficient dp  = new DPBinomial();
        assertEquals(386206920L, dp.binom(60, 7));
    }

    @Test
    void symmetry() {
        BinomialCoefficient dp  = new DPBinomial();
        for (int n = 0; n <= 25; n++) {
            for (int k = 0; k <= n; k++) {
                assertEquals(dp.binom(n,k), dp.binom(n, n-k));
            }
        }
    }
}
