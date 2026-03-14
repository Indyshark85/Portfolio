import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Problem11Test {

    @Test
    void testProblem11() {
        assertAll(
                () -> assertEquals(true,Problem11.cond(false,true)),
                () -> assertEquals(false,Problem11.bicond(true,false)),
                () -> assertEquals(true,Problem11.and(true,true)),
                () -> assertEquals(true,Problem11.or(false,true)),
                () -> assertEquals(true,Problem11.not(false))
        );
    }
}
