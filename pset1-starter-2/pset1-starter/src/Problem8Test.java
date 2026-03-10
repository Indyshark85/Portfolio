import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Problem8Test {

    @Test
    void testProblem8() {
        assertAll(
                () -> assertEquals(true, Problem8.lessThan20(19, 2, 412)),
                () -> assertEquals(false, Problem8.lessThan20(999, 888, 777)),
                () -> assertEquals(true, Problem8.lessThan20(10, 5, 30)),
                () -> assertEquals(false, Problem8.lessThan20(10, 50, 100)),
                () -> assertEquals(true, Problem8.lessThan20(20, 35, 25)),
                () -> assertEquals(true, Problem8.lessThan20(100, 110, 150)),
                () -> assertEquals(false, Problem8.lessThan20(1, 50, 100)),
                () -> assertEquals(false, Problem8.lessThan20(200, 180, 250)),
                () -> assertEquals(false, Problem8.lessThan20(0, 50, 90)),
                () -> assertEquals(true, Problem8.lessThan20(15, 35, 50)));



    }
}
