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
        int n = X.length();
        int m = Y.length();

        int[][] dp= new int[n+1][m+1];
        int[][] backtrack= new int[n + 1][m + 1];

        final int DIAG= 0;
        final int UP= 1;
        final int LEFT= 2;

        //initialize the first row and column
        for (int i =0; i <= n; i++) {
            dp[i][0] = i * gapPenalty;
            backtrack[i][0]= UP;
        }
        for (int j = 0; j <= m; j++) {
            dp[0][j] = j * gapPenalty;
            backtrack[0][j]= LEFT;
        }
        backtrack[0][0]= -1;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int match = dp[i - 1][j - 1] + score(X.charAt(i - 1), Y.charAt(j - 1));
                int delete = dp[i - 1][j] + gapPenalty;
                int insert = dp[i][j - 1] + gapPenalty;

                int max = Math.max(match, Math.max(delete, insert));
                dp[i][j] = max;

                if (max == match) backtrack[i][j] = DIAG;
                else if (max == delete) backtrack[i][j] = UP;
                else backtrack[i][j] = LEFT;
            }
        }
        //Traceback
        StringBuilder alignedX = new StringBuilder();
        StringBuilder alignedY = new StringBuilder();

        int i = n, j = m;
        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && backtrack[i][j] == DIAG) {
                alignedX.append(X.charAt(i - 1));
                alignedY.append(Y.charAt(j - 1));
                i--; j--;
            } else if (i > 0 && backtrack[i][j] == UP) {
                alignedX.append(X.charAt(i - 1));
                alignedY.append('-');
                i--;
            } else {
                alignedX.append('-');
                alignedY.append(Y.charAt(j - 1));
                j--;
            }
        }

        alignedX.reverse();
        alignedY.reverse();

        return new AlignmentResult(alignedX.toString(), alignedY.toString(), dp[n][m]);

    }
}
