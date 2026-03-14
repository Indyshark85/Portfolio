import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class P3StudentTest {
    @Test
    public void testSmallValuesRecursive() {
        RecursiveBinomial rec = new RecursiveBinomial();

        assertEquals(1, rec.binom(0, 0));   // C(0,0)
        assertEquals(1, rec.binom(5, 0));   // C(5,0)
        assertEquals(1, rec.binom(5, 5));   // C(5,5)
        assertEquals(5, rec.binom(5, 1));   // C(5,1)
        assertEquals(10, rec.binom(5, 2));  // C(5,2)
        assertEquals(10, rec.binom(5, 3));  // C(5,3)
    }

    @Test
    public void testSmallValuesDP() {
        DPBinomial dp = new DPBinomial();

        assertEquals(1, dp.binom(0, 0));
        assertEquals(1, dp.binom(5, 0));
        assertEquals(1, dp.binom(5, 5));
        assertEquals(5, dp.binom(5, 1));
        assertEquals(10, dp.binom(5, 2));
        assertEquals(10, dp.binom(5, 3));
    }

    @Test
    public void testConsistencyRecursiveVsDP() {
        RecursiveBinomial rec = new RecursiveBinomial();
        DPBinomial dp = new DPBinomial();

        for (int n = 0; n <= 10; n++) {
            for (int k = 0; k <= n; k++) {
                assertEquals(dp.binom(n, k), rec.binom(n, k),
                        "Mismatch at n=" + n + ", k=" + k);
            }
        }
    }

    @Test
    public void testInvalidInputs() {
        DPBinomial dp = new DPBinomial();

        assertThrows(IllegalArgumentException.class, () -> dp.binom(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> dp.binom(5, -1));
        assertThrows(IllegalArgumentException.class, () -> dp.binom(5, 6));
    }
}
