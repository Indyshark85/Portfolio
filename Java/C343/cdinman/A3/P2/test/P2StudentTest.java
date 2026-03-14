import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class P2StudentTest {

    @Test
    public void testSimpleMatch() {
        AlignmentStrategy aligner = new NeedlemanWunsch();
        AlignmentResult result = aligner.align("A", "A");

        assertEquals("A", result.alignedX);
        assertEquals("A", result.alignedY);
        assertEquals(1, result.score); // match = +1
    }

    @Test
    public void testSingleMismatch() {
        AlignmentStrategy aligner = new NeedlemanWunsch();
        AlignmentResult result = aligner.align("A", "G");

        assertEquals("A", result.alignedX);
        assertEquals("G", result.alignedY);
        assertEquals(-1, result.score); // mismatch = -1
    }

    @Test
    public void testGapInsertion() {
        AlignmentStrategy aligner = new NeedlemanWunsch();
        AlignmentResult result = aligner.align("A", "AG");

        // Best alignment: A- vs AG → score = +1 + (-2) = -1
        assertEquals("A-", result.alignedX);
        assertEquals("AG", result.alignedY);
        assertEquals(-1, result.score);
    }

    @Test
    public void testClassicExample() {
        AlignmentStrategy aligner = new NeedlemanWunsch();
        AlignmentResult result = aligner.align("ACCTGA", "ACGTGA");

        System.out.println(result);

        // One optimal alignment:
        // A C C T G A
        // A C - T G A
        assertEquals("ACCTGA".length(), result.alignedX.length());
        assertEquals(result.alignedX.length(), result.alignedY.length());
        assertEquals(4, result.score);
    }

    @Test
    public void testEmptyStrings() {
        AlignmentStrategy aligner = new NeedlemanWunsch();
        AlignmentResult result = aligner.align("", "");

        assertEquals("", result.alignedX);
        assertEquals("", result.alignedY);
        assertEquals(0, result.score);
    }

    @Test
    public void testLeadingGap() {
        AlignmentStrategy aligner = new NeedlemanWunsch();
        AlignmentResult result = aligner.align("GATTACA", "ATTACA");

        System.out.println(result);
        // Expect a gap at start or end, final score should match consistent penalties
        assertTrue(result.alignedX.contains("-") || result.alignedY.contains("-"));
    }
}