import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Problem10Test {

    @Test
    void testProblem10() {
        assertAll(
                () -> assertEquals("I will ", Problem10.cutTry("I will try")),
                () -> assertEquals("No ", Problem10.cutTry("No try")),
                () -> assertEquals("Just do it", Problem10.cutTry("Just do it")),
                () -> assertEquals("No try, no success", Problem10.cutTry("No try, no success")),
                () -> assertEquals("This is a test", Problem10.cutTry("This is a test")),
                () -> assertEquals("This is not a test", Problem10.cutTry("This is not a testtry")),
                () -> assertEquals("AAAtryAAAAA", Problem10.cutTry("AAAtryAAAAAtry")),
                () -> assertEquals("Always try your best", Problem10.cutTry("Always try your best"))
        );


    }
}
