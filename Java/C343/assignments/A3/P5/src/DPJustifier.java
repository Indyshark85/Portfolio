import java.util.*;

public class DPJustifier extends AbstractJustifier {

    @Override
    public List<String> justify(List<String> words, int L, double b) {
        /** TODO: Build a DP solution that minimizes total L1 ugliness.
         *   Definitions:
         *    - a_i = word lengths; L = target line length; b = ideal blank.
         *    - For candidate line w_i..w_j (inclusive):
         *        k = j - i           // number of gaps
         *        W = sum_{t=i}^j a_t // total word length
         *        S = L - W           // free space for blanks (sum of gap lengths)
         *   Feasibility:
         *    - If k == 0 (single word):
         *        * non-last line requires S == 0
         *        * last line allows S >= 0
         *    - If k > 0 (multiple words): require S >= k  // strictly positive gaps
         *   Cost (L1 via average blank b' = S/k, with k>0):
         *    - non-last: k * |b' - b|
         *    - last:     k * max(0, b - b')   // only shrinking is charged
         *    - If k == 0, cost is 0 when feasible, +∞ otherwise.
         *   DP:
         *    - DP(i) = min over j>=i of [ lineCost(i..j) + DP(j+1) ], scanning i from n-1 down to 0.
         *  Steps:
         *    1) Precompute a[0..n-1] (and optionally prefix sums).
         *    2) Fill dp[n]=0, then dp[i] for i=n-1..0; keep next[i] to reconstruct.
         *    3) Reconstruct lines with render(words, i, j, L, last) using integer gaps.
         */

        throw new Error("TODO (A3.P5.src.DPJustifier): jsutify");
    }

    /**
     * L1 line cost with strictly positive gaps.
     * - k == 0 (single word):
     *     non-last: feasible iff S == 0 (cost 0); else INF
     *     last:     feasible iff S >= 0 (cost 0); else INF
     * - k > 0:
     *     feasible iff S >= k (each gap >= 1 space)
     *     cost = k*|S/k - b| for non-last
     *     cost = k*max(0, b - S/k) for last
     */
    static double lineCost(int k, int S, double b, boolean last) {
        if (k == 0) {
            if (!last) return (S == 0) ? 0.0 : Double.POSITIVE_INFINITY;
            return (S >= 0) ? 0.0 : Double.POSITIVE_INFINITY;
        }
        if (S < k) return Double.POSITIVE_INFINITY;
        double avg = (double) S / k;
        return last ? k * Math.max(0.0, b - avg) : k * Math.abs(avg - b);
    }
}
