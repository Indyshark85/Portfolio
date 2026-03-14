import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Problem6Test {

    @Test
    void testProblem6() {
        assertAll(
                () -> assertEquals(81000,Problem6.distanceTraveled(150,40,60)),
                () -> assertEquals(0,Problem6.distanceTraveled(0,0,1)),
                () -> assertEquals(870,Problem6.distanceTraveled(-1,2,30)),
                () -> assertEquals(510000,Problem6.distanceTraveled(100,100,100)),
                () -> assertEquals(-0.5,Problem6.distanceTraveled(1,1,-1))


        );
    }
}
