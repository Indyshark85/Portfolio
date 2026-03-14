import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CoinChangeTest {

    @Test
    void testBottomUp() {
        int[] denominations = {1, 5, 10, 25};

        // Base case
        assertEquals(0, CoinChange.minChange(0, denominations));

        // Exact matches
        assertEquals(1, CoinChange.minChange(1, denominations));
        assertEquals(1, CoinChange.minChange(5, denominations));
        assertEquals(1, CoinChange.minChange(25, denominations));

        // Combinations
        assertEquals(2, CoinChange.minChange(6, denominations));   // 5 + 1
        assertEquals(2, CoinChange.minChange(30, denominations));  // 25 + 5
        assertEquals(2, CoinChange.minChange(11, denominations));  // 10 + 1

        // Larger tricky case
        assertEquals(6, CoinChange.minChange(63, denominations));

    }

    @Test
    void testTopDown() {
        int[] denominations = {1, 5, 10, 25};

        // Base case
        assertEquals(0, CoinChangeTopDown.minChangeMemo(0, denominations));

        // Exact matches
        assertEquals(1, CoinChangeTopDown.minChangeMemo(1, denominations));
        assertEquals(1, CoinChangeTopDown.minChangeMemo(5, denominations));
        assertEquals(1, CoinChangeTopDown.minChangeMemo(25, denominations));

        // Combinations
        assertEquals(2, CoinChangeTopDown.minChangeMemo(6, denominations));   // 5 + 1
        assertEquals(2, CoinChangeTopDown.minChangeMemo(30, denominations));  // 25 + 5
        assertEquals(2, CoinChangeTopDown.minChangeMemo(11, denominations));  // 10 + 1

        // Larger tricky case
        assertEquals(6, CoinChangeTopDown.minChangeMemo(63, denominations));  // 25+25+10+1+1+1
    }
}
