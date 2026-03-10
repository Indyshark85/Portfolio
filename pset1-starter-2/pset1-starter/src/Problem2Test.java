import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Problem2Test {
    private static final double DELTA = .01;
    @Test
    void testProblem2() {

        assertAll(
                () -> assertEquals(5.66,Problem2.grocery(1,1,1,1,1),DELTA),
                () -> assertEquals(0,Problem2.grocery(0,0,0,0,0),DELTA),
                () -> assertEquals(-5.66,Problem2.grocery(-1,-1,-1,-1,-1),DELTA),
                () -> assertEquals(370.70,Problem2.grocery(30,50,60,70,80),DELTA),
                () -> assertEquals(6941.24,Problem2.grocery(100,1000,10000,1000,1),DELTA)

        );
    }
}
