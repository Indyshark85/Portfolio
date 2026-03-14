import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestDriverTest {

    @Test
    void add0ne() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(4, TestDriver.add0ne(3)),
                () -> Assertions.assertEquals(13, TestDriver.add0ne(12)),
                () -> Assertions.assertEquals(1, TestDriver.add0ne(0))
        );
    }

    @Test
    void sub0ne() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(2, TestDriver.sub0ne(3)),
                () -> Assertions.assertEquals(11, TestDriver.sub0ne(12)),
                () -> Assertions.assertEquals(0, TestDriver.sub0ne(1))
        );
    }
}