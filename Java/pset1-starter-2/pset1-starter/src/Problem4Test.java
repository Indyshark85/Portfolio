import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Problem4Test {

    @Test
    void testProblem4() {
        assertAll(
                () -> assertEquals(false,Problem4.isExtremeOutlier(1,1,1)),
                () -> assertEquals(false,Problem4.isExtremeOutlier(2,2,2)),
                () -> assertEquals(true,Problem4.isExtremeOutlier(9,4,1)),
                () -> assertEquals(false,Problem4.isExtremeOutlier(0,0,0)),
                () -> assertEquals(true,Problem4.isExtremeOutlier(1000,1,1))
        );
    }
}
