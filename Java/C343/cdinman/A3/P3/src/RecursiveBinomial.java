public class RecursiveBinomial extends AbstractBinomial {
    @Override
    public long binom(int n, int k) {
        validate(n, k);
        /** TODO:
         *      base cases: C(n,0)=1, C(n,n)=1
         *      recurrence: C(n,k)=C(n-1,k-1)+C(n-1,k)
         */
        if (k==0 || k == n) return 1;

        return binom(n-1,k-1)+ binom(n-1,k);
    }
}
