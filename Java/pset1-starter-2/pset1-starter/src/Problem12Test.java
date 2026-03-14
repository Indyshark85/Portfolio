import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Problem12Test {

    @Test
    void testProblem12() {
        assertAll(
                () -> assertEquals(true, Problem12.isInsideRectangle(0.0, 0.0, 10.0, 5.0, 1.0, 1.0)),
                () -> assertEquals(false, Problem12.isInsideRectangle(0.0, 0.0, 10.0, 5.0, 6.0, 3.0)),
                () -> assertEquals(true, Problem12.isInsideRectangle(1.0, 1.0, 4.0, 2.0, 2.0, 1.5)),
                () -> assertEquals(false, Problem12.isInsideRectangle(-2.0, -2.0, 8.0, 6.0, -7.0, -3.0)),
                () -> assertEquals(false, Problem12.isInsideRectangle(0.0, 0.0, 10.0, 5.0, -6.0, -2.6))
        );
    }
}
