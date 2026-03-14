import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Problem5Test {
    private static final double DELTA = .01;
    @Test
    void testProblem5() {
        assertAll(
                () -> assertEquals(1,Problem5.lawOfCosines(1,1,60),DELTA),
                () -> assertEquals(14.30295,Problem5.lawOfCosines( 16,2,30),DELTA),
                () -> assertEquals(4.0465,Problem5.lawOfCosines(6,2,10.13),DELTA),
                () -> assertEquals(7.40253,Problem5.lawOfCosines(10.3,2.9,2.03),DELTA),
                () -> assertEquals(25.3571,Problem5.lawOfCosines(30,30,50),DELTA)
        );
    }
}
