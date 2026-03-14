import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class P5StudentTest {
    @Test
    public void testSingleWordExactFit() {
        DPJustifier just = new DPJustifier();
        List<String> words = List.of("Hello");
        List<String> result = just.justify(words, 5, 2);
        assertEquals(List.of("Hello"), result);
    }

    @Test
    public void testSingleWordWithTrailingSpaces() {
        DPJustifier just = new DPJustifier();
        List<String> words = List.of("Hi");
        List<String> result = just.justify(words, 5, 2);
        assertEquals(List.of("Hi   "), result); // last line can be ragged (trailing ok)
    }


    @Test
    public void testMultipleLinesBasic() {
        DPJustifier just = new DPJustifier();
        List<String> words = List.of("This", "is", "a", "test", "paragraph");
        List<String> result = just.justify(words, 10, 2);
        // Example output (exact spacing may vary slightly but lines must meet length L)
        for (String line : result) {
            assertEquals(10, line.length(), "Line not length 10: " + line);
        }
    }

    @Test
    public void testLongWordInfeasible() {
        DPJustifier just = new DPJustifier();
        List<String> words = List.of("supercalifragilisticexpialidocious");
        assertThrows(IllegalStateException.class, () -> just.justify(words, 10, 2));
    }

    @Test
    public void testMinimalSpacingFeasible() {
        DPJustifier just = new DPJustifier();
        List<String> words = List.of("A", "B", "C");
        List<String> result = just.justify(words, 5, 1); // 3 words → 2 gaps
        assertEquals(1, result.size());
        assertEquals(5, result.get(0).length());
    }
}
