public class NeedlemanWunsch extends AbstractAlignment {
    public NeedlemanWunsch() {
        super(+1, -1, -2); // default scoring: match=+1, mismatch=-1, gap=-2
    }

    @Override
    public AlignmentResult align(String X, String Y) {
        /** TODO:
         *      1) allocate dp[n+1][m+1] and backtrack tables
         *      2) initialize first row/col with gap penalties
         *      3) fill DP using match/mismatch and gapPenalty
         *      4) traceback to build aligned strings
         *      5) return new AlignmentResult(alignedX, alignedY, dp[n][m])
         */
        throw new Error("TODO (A3.P2.src.NeedlemanWunsch): align");
    }
}
