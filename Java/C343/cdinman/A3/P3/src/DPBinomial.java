public class DPBinomial extends AbstractBinomial {
    @Override
    public long binom(int n, int k) {
        validate(n, k);
        /** TODO: bottom-up DP (Pascal triangle):
        *       C[i][0]=C[i][i]=1; else C[i][j]=C[i-1][j-1]+C[i-1][j]
        */
        long[][] C = new long[n + 1][k + 1];

        for(int i =0; i<=n;i++){
            for (int j=0;j<= Math.min(i,k);j++){
                if(j==0||j==i){
                    C[i][j]=1;
                }else{
                    C[i][j]=C[i-1][j-1]+C[i-1][j];
                }
            }
        }
        return C[n][k];
    }
}
