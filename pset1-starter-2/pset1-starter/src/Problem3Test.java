import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Problem3Test {
    private static final double DELTA = .01;
    @Test
    void testProblem3() {
        assertAll(
                () -> assertEquals(3.23607, Problem3.pyramidSurfaceArea(1,1,1),DELTA),
                () -> assertEquals(0, Problem3.pyramidSurfaceArea(1,0,1),DELTA),
                () -> assertEquals(32360.68, Problem3.pyramidSurfaceArea(100,100,100),DELTA),
                () -> assertEquals(0, Problem3.pyramidSurfaceArea(-1,1,1),DELTA),
                () -> assertEquals(41.6, Problem3.pyramidSurfaceArea(1,5,6),DELTA)
        );
    }
}
