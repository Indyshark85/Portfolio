import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Problem10Test {

    @Test
    void testProblem10() {
        assertAll(
                () -> assertEquals(Math.PI * 1 * 1, Problem10.circleArea(1, 0.0001), 0.0001),
                () -> assertEquals(Math.PI * 2 * 2, Problem10.circleArea(2, 0.0001), 0.0001),
                () -> assertEquals(Math.PI * 3 * 3, Problem10.circleArea(3, 0.0001), 0.0001),
                () -> assertEquals(Math.PI * 4 * 4, Problem10.circleArea(4, 0.0001), 0.0001),
                () -> assertEquals(Math.PI * 5 * 5, Problem10.circleArea(5, 0.0001), 0.0001)
        );//why DO NONE OF THESE WORK BUT THEY WORK IN THE AUTO GRADER AAAAAAH

    }
}
