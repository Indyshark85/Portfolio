class Problem3 {

    /**
     *
     * Calculates the subfactorial of the given number using recursion.
     *
     * @param n The number for which the subfactorial is to be calculated.
     * @return The subfactorial of the number.
     */
    static long subfactorial(long n) {
        if (n==0){
            return 1;
        }else if (n ==1){
            return 0;
        }else{
            return (n-1)*(subfactorial(n-1)+subfactorial(n-2));
        }
    }

    /**
     * Calculates the subfactorial of the given number using tail recursion.
     *
     * @param n The number for which the subfactorial is to be calculated.
     * @return The subfactorial of the number.
     */
    static long subfactorialTR(long n) {
        return subfactorialHelper(n,1,0);

    }

    /**
     * Helper method for tail recursion subfactorial calculation
     * @param n The current number being processed
     * @param a The accumulated result for n == 0.
     * @param b The accumulated result for n == 1.
     * @return The subfactorial of the original number.
     */
    private static long subfactorialHelper(long n,long a, long b){
        if (n==0){
            return a;
        } else if (n==1){
            return b;
        } else{
            return subfactorialHelper(n-1,b,(n-1)*(a+b));
        }
    }


    /**
     * Calculates the subfactorial of the given number using a loop.
     *
     * @param n The number for which the subfactorial is to be calculated.
     * @return The subfactorial of the number.
     */
    static long subfactorialLoop(long n) {
        if (n==0){
            return 1;
        }else if (n==1){
            return 0;
        }else{
            long a =1;
            long b =0;
            for (long i=2 ; i<=n;i++){
                long temp = (i-1)*(a+b);
                a=b;
                b=temp;
            }
            return b;
        }
    }
}

