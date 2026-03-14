public class DPBinomial extends AbstractBinomial {
    @Override
    public long binom(int n, int k) {
        validate(n, k);
        /** TODO: bottom-up DP (Pascal triangle):
        *       C[i][0]=C[i][i]=1; else C[i][j]=C[i-1][j-1]+C[i-1][j]
        */
        throw new Error("TODO (A3.P3.src.DPBinomial): binom");
    }
}
