import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AlignmentTest {

    private int scoreOf(String ax, String ay, int match, int mismatch, int gap) {
        int s = 0;
        for (int i = 0; i < ax.length(); i++) {
            char a = ax.charAt(i), b = ay.charAt(i);
            if (a == '-' || b == '-') s += gap;
            else s += (a == b ? match : mismatch);
        }
        return s;
    }

    @Test
    void exampleACCTGA_ACGTGA() {
        AlignmentStrategy nw = new NeedlemanWunsch();
        AlignmentResult r = nw.align("ACCTGA", "ACGTGA");
        assertEquals(r.alignedX.length(), r.alignedY.length(), "Aligned lengths must match");
        assertEquals(r.score, scoreOf(r.alignedX, r.alignedY, 1, -1, -2), "Reported score must match recalculated");
        // This particular scoring should yield a score >= 3 (known optimal >= 3)
        assertTrue(r.score >= 3, "Expected score at least 3 for this pair under +1/-1/-2");
    }

    @Test
    void emptyVsString() {
        AlignmentStrategy nw = new NeedlemanWunsch();
        AlignmentResult r = nw.align("", "AGT");
        assertEquals(3 * -2, r.score);
        assertEquals("---", r.alignedX);
        assertEquals("AGT", r.alignedY);
    }

    @Test
    void identicalStrings() {
        AlignmentStrategy nw = new NeedlemanWunsch();
        AlignmentResult r = nw.align("GATTACA", "GATTACA");
        assertEquals(7, r.score);
        assertEquals("GATTACA", r.alignedX);
        assertEquals("GATTACA", r.alignedY);
    }
}
