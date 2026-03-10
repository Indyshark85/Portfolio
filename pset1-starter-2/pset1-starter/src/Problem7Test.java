import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Problem7Test {

    @Test
    void testProblem7() {
        assertAll(
                () -> assertEquals("skywalker",Problem7.cutUsername("skywalker@gmail.com")),
                () -> assertEquals("6shooterw7rounds",Problem7.cutUsername("6shooterw7rounds@gmail.com")),
                () -> assertEquals("whiteboard345",Problem7.cutUsername("whiteboard345@gmail.com")),
                () -> assertEquals("jack.andthebeanstock",Problem7.cutUsername("jack.andthebeanstock@gmail.com")),
                () -> assertEquals("java.when",Problem7.cutUsername("java.when@gmail.com"))

        );
    }
}
