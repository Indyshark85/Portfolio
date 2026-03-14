import java.util.*;

/**
 * Abstract base with common helpers for justification implementations.
 * - Provides: spaces(int), join(words, gaps), and a strict renderer that
 *   enforces positive gaps and produces a line of exact length L.
 */

abstract class AbstractJustifier implements RightJustifier{
    /**
     * Render a single line w_i..w_j to exactly length L, enforcing strictly positive gaps (>=1).
     * Rules:
     *  - If k == 0:
     *      non-last: must return a string of length exactly L (no trailing spaces unless needed to reach L).
     *      last:     may return with trailing spaces (LHS ragged allowed; expansion free).
     *  - If k > 0:
     *      must distribute S (total spaces) with at least 1 per gap. A simple scheme:
     *        base = 1 + (S - k)/k, rem = (S - k) % k, gaps[g] = base (+1 for first rem gaps)
     *      (Any deterministic spread is fine.)
     */
    static String render(List<String> words, int i, int j, int L, boolean last) {
        List<String> ws = words.subList(i - 1, j);
        int k = j - i;
        int total = ws.stream().mapToInt(String::length).sum();
        int S = L - total;

        if (k <= 0) {
            String line = ws.get(0);
            if (!last && line.length() != L)
                throw new IllegalStateException("Single-word non-last line must exactly fill L");
            if (last && line.length() < L)
                return line + " ".repeat(L - line.length());
            return line;
        }

        if (S < k) throw new IllegalStateException("Infeasible: need strictly positive gaps");
        int[] gaps = new int[k];
        int base = 1 + (S - k) / k;
        int extra = (S - k) % k;
        Arrays.fill(gaps, base);
        for (int g = 0; g < extra; g++) gaps[g]++;

        return join(ws, gaps);
    }

    /** Helper to join words with given gap sizes. */
    static String join(List<String> ws, int[] gaps) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ws.size(); i++) {
            sb.append(ws.get(i));
            if (i < gaps.length) sb.append(" ".repeat(gaps[i]));
        }
        return sb.toString();
    }
}
