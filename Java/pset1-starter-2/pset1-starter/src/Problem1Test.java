import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Problem1Test {
    private static final double DELTA = .01;
    @Test
    void testProblem1() {
        assertAll(
                () -> assertEquals(3.33, Problem1.gigameterToLightsecond(1),DELTA),
                () -> assertEquals(0, Problem1.gigameterToLightsecond(0),DELTA),
                () -> assertEquals(3335.64, Problem1.gigameterToLightsecond(1000),DELTA),
                () -> assertEquals(-3.33564, Problem1.gigameterToLightsecond(-1),DELTA),
                () -> assertEquals(833.91, Problem1.gigameterToLightsecond(250),DELTA)
        );
    }
}
