import exceptions.EmptyStackE;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StackITest {
    @Test
    void testSimple () {
        StackPL<Integer> stackPL = new StackPL<>();
        int n = 10;

        for (int i = 0; i < n; i++) {
            stackPL.push(i);
        }

        for (int i = n - 1; i >= 0; i--) {
            try {
                assertEquals(i, stackPL.pop());
            }
            catch (EmptyStackE ex) {
                throw new Error("Internal bug: EmptyStackE should not be thrown in this test");
            }
        }
    }

    @Test
    void testPeek() throws EmptyStackE {
        StackPL<Integer> stack = new StackPL<>();
        stack.push(42);
        assertEquals(42, stack.peek());
        assertEquals(42, stack.peek()); // should not mutate
    }

    @Test
    void testEmptyPop() {
        StackPL<Integer> stack = new StackPL<>();
        assertThrows(EmptyStackE.class, stack::pop);
    }
}
