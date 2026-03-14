public class RecursiveBinomial extends AbstractBinomial {
    @Override
    public long binom(int n, int k) {
        validate(n, k);
        /** TODO:
         *      base cases: C(n,0)=1, C(n,n)=1
         *      recurrence: C(n,k)=C(n-1,k-1)+C(n-1,k)
         */
        throw new Error("TODO (A3.P3.src.RecursiveBinomial): binom");
    }
}
