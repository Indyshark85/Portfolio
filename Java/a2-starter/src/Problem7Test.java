import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Problem7Test {

    @Test
    void testProblem7() {
        assertAll(
                () -> assertEquals("lo", Problem7.substring("hello", 3, 5)),
                () -> assertEquals("hello", Problem7.substring("hello world", 0, 5)),
                () -> assertEquals("world", Problem7.substring("hello world", 6, 11)),
                () -> assertEquals("bstring", Problem7.substring("substring", 2, 9)),
                () -> assertEquals("", Problem7.substring("", 2, 9)),
                () -> assertEquals("", Problem7.substring("substring", 2, 2)),
                () -> assertEquals(null, Problem7.substring("string", -1, 5)),
                () -> assertEquals(null, Problem7.substring("string", 3, 10)),
                () -> assertEquals("", Problem7.substring("empty", 2, 2)),
                () -> assertEquals("und", Problem7.substring("outofbounds", 7, 10)),
                () -> assertEquals(null, Problem7.substring("incorrect", 11, 12))
        );
    }
}
