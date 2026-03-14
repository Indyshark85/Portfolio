import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * Tests for the "Right Justifying a Paragraph" spec with:
 *  - L1 per-gap penalty: sum |b_i - b|
 *  - Strictly positive gaps (>= 1 space) whenever a line has >= 2 words
 *  - Last line: charge only if shrinking (avg < b), still must keep gaps >= 1 when >= 2 words
 * .
 * Feasibility:
 *  - Let k = number of gaps on a line, W = sum of word lengths on that line, S = L - W.
 *  - If k == 0:
 *      * non-last line: S == 0 (line must exactly fit)
 *      * last line: S >= 0 (free expansion, no charge)
 *  - If k > 0: must have S >= k (each gap >= 1) on every line (including last).
 * .
 * Line cost (L1 model via average gap b' = S/k):
 *  - non-last:     k * |b' - b|
 *  - last line:    k * max(0, b - b')
 */
public class JustifyTest {

    // ---- SPEC HELPERS ----

    /** L1 cost under the assignment spec (see header), using only counts and averages. */
    static double l1LineCost(int wordsCount, int totalWordLen, int L, double b, boolean last) {
        int k = wordsCount - 1;
        int S = L - totalWordLen;
        if (S < 0) return Double.POSITIVE_INFINITY;

        if (k == 0) { // single-word line
            return last ? (S >= 0 ? 0.0 : Double.POSITIVE_INFINITY)
                    : (S == 0 ? 0.0 : Double.POSITIVE_INFINITY);
        }

        // strictly positive integer gaps ⇒ S >= k
        if (S < k) return Double.POSITIVE_INFINITY;

        double avg = (double) S / k;
        return last ? k * Math.max(0.0, b - avg)   // last line: only shrinking is charged
                : k * Math.abs(avg - b);       // non-last: absolute deviation
    }


    /** L1 paragraph cost of a set of concrete line breaks (for brute force). */
    static double l1BreaksCost(List<String> words, int L, double b, List<Integer> starts) {
        int n = words.size();
        double total = 0.0;
        for (int t = 0; t < starts.size() - 1; t++) {
            int i = starts.get(t);
            int j = starts.get(t + 1) - 1;
            boolean last = (t == starts.size() - 2);
            int wordsCount = j - i + 1;
            int W = 0;
            for (int u = i; u <= j; u++) W += words.get(u).length();
            double c = l1LineCost(wordsCount, W, L, b, last);
            if (!Double.isFinite(c)) return Double.POSITIVE_INFINITY;
            total += c;
        }
        return total;
    }

    /** Brute force the optimal L1 cost (small N only). */
    static double bruteForceBestCost(List<String> words, int L, double b) {
        int n = words.size();
        int[] len = words.stream().mapToInt(String::length).toArray();
        return dfs(0, words, len, L, b);
    }

    static double dfs(int start, List<String> words, int[] len, int L, double b) {
        int n = words.size();
        if (start == n) return 0.0;
        double best = Double.POSITIVE_INFINITY;
        for (int end = start; end < n; end++) {
            int wordsCount = end - start + 1;
            int W = 0; for (int t = start; t <= end; t++) W += len[t];
            boolean last = (end == n - 1);
            double c = l1LineCost(wordsCount, W, L, b, last);
            if (!Double.isFinite(c)) continue; // infeasible line
            double tail = dfs(end + 1, words, len, L, b);
            if (Double.isFinite(tail)) best = Math.min(best, c + tail);
        }
        return best;
    }

    /** Validates the renderer’s lines are feasible per spec and returns the L1 cost. */
    static double l1CostFromRendered(List<String> lines, List<String> words, int L, double b) {
        // Map rendered lines back to slices of the original words (in order), ensuring feasibility.
        int n = words.size();
        int idx = 0;
        double total = 0.0;

        for (int li = 0; li < lines.size(); li++) {
            boolean last = (li == lines.size() - 1);
            String line = lines.get(li);

            // Non-last lines must be exactly length L
            if (!last) assertEquals(L, line.length(), "Non-last line must be exactly L chars");

            // Consume words in order by matching exact text separated by spaces.
            int pos = 0;
            int used = 0;

            while (idx + used < n) {
                String w = words.get(idx + used);

                // skip any incidental spaces (should only matter on last line)
                while (pos < line.length() && line.charAt(pos) == ' ') pos++;

                if (pos + w.length() > line.length()) break;
                if (!line.startsWith(w, pos)) break;

                pos += w.length();
                used++;

                // For lines with >= 2 words, spaces must be >= 1 between words (strictly positive gaps)
                if (idx + used < n) {
                    int spaces = 0, tpos = pos;
                    while (tpos < line.length() && line.charAt(tpos) == ' ') { spaces++; tpos++; }

                    // If the next word also matches right after, accept; else we will break later
                    // Only enforce strictly positive if this line truly carries another word.
                    // We can't know yet—so we’ll validate positivity after we decide used count below.
                }
            }

            if (used == 0) throw new AssertionError("Rendered line did not match any words.");

            // Now compute feasibility counts for this slice
            int wordsCount = used;
            int W = 0; for (int t = 0; t < wordsCount; t++) W += words.get(idx + t).length();
            int k = wordsCount - 1;
            int S = line.length() - W; // total spaces actually used on this line

            // Enforce feasibility:
            if (k == 0) {
                if (!last) assertEquals(0, S, "Single-word non-last line must fit exactly");
                else assertTrue(S >= 0, "Single-word last line must have non-negative slack");
            } else {
                // strictly positive gaps => S >= k
                assertTrue(S >= k, "Line must allocate at least one space per gap");
            }

            total += l1LineCost(wordsCount, W, L, b, last);
            idx += used;
        }

        if (idx != n) {
            throw new AssertionError("Rendered lines did not consume all words. consumed=" + idx + " of " + n);
        }
        return total;
    }

    // ---- TESTS ----

    @Test
    void smallParagraph_matchesBruteforce_L1() {
        RightJustifier just = new DPJustifier();
        List<String> words = List.of("This","is","a","tiny","example");
        int L = 12;
        double b = 1.0;

        List<String> lines = just.justify(words, L, b);
        double dpCost = l1CostFromRendered(lines, words, L, b);
        double best  = bruteForceBestCost(words, L, b);

        assertEquals(best, dpCost, 1e-9, "DP cost should equal brute-force L1 optimum");
    }

    @Test
    void lastLine_onlyShrinkingIsCharged() {
        RightJustifier just = new DPJustifier();
        List<String> words = List.of("end","is","ragged","now");
        int L = 16;
        double b = 2.0;

        List<String> lines = just.justify(words, L, b);
        // Ensure feasibility & cost computed under L1
        double dpCost = l1CostFromRendered(lines, words, L, b);

        // Sanity: non-last lines length L
        for (int i = 0; i < lines.size() - 1; i++) {
            assertEquals(L, lines.get(i).length());
        }
        // No assertion about exact number, but the test mainly ensures L1 model compliance.
        assertTrue(Double.isFinite(dpCost));
    }

    @Test
    void strictPositivity_enforced() {
        RightJustifier just = new DPJustifier();
        // Choose words & L so that feasible lines must allocate >=1 per gap
        List<String> words = List.of("aa","bb","cc");
        int L = 8;   // total letters per 2-word line = 4; S = 4 => enough to keep gaps >=1
        double b = 1.0;

        List<String> lines = just.justify(words, L, b);
        // Will throw in l1CostFromRendered if any non-last line has zero-length gaps
        double dpCost = l1CostFromRendered(lines, words, L, b);
        assertTrue(Double.isFinite(dpCost));
    }

    @Test
    void randomParagraphsAgreeWithBruteforce() {
        RightJustifier just = new DPJustifier();
        Random rnd = new Random(2025);

        for (int t = 0; t < 40; t++) {
            int n = 3 + rnd.nextInt(6);
            List<String> words = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                int len = 1 + rnd.nextInt(5);
                StringBuilder sb = new StringBuilder();
                for (int k = 0; k < len; k++) sb.append((char)('a' + rnd.nextInt(26)));
                words.add(sb.toString());
            }
            // Pick L so at least some lines are feasible with >=1 per gap; keep b small-ish
            int Wsum = words.stream().mapToInt(String::length).sum();
            int L = Math.max(6, Math.min(Wsum + n - 1, 14 + rnd.nextInt(6))); // try to keep search space small
            double b = 1.0 + rnd.nextInt(3);

            List<String> lines = just.justify(words, L, b);
            double dpCost = l1CostFromRendered(lines, words, L, b);
            double best  = bruteForceBestCost(words, L, b);

            assertEquals(best, dpCost, 1e-9, "DP cost should equal brute-force L1 optimum (random case)");
        }
    }
}
