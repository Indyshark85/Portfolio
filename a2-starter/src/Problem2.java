class Problem2 {

    /**
     * Calculates the hyperfactorial of the given number using recursion.
     *
     * @param n The number for which the hyperfactorial is to be calculated.
     * @return The hyperfactorial of the number.
     */
    static long hyperfactorial(long n) {
        if (n<1){
            return 1;
        }else{
            return (long) Math.pow(n,n)*hyperfactorial(n-1);
        }
    }


    /**
     * Calculates the hyperfactorial of the given number using tail recursion.
     *
     * @param n The number for which the hyperfactorial is to be calculated.
     * @return The hyperfactorial of the number.
     */
    static long hyperfactorialTR(long n) {
        return hyperfactorialHelper(n,1);
    }
    /**
     * Helper method for tail recursion hyperfactorial calculation
     * @param n The current number being processed
     * @param acc The accumulated product of hyperfactorials
     * @return The hyperfactorial of the original number
     */
    private static long hyperfactorialHelper(long n,long acc){
        if (n < 1){
            return acc;
        }else{
            return hyperfactorialHelper(n-1,acc*=Math.pow(n,n));
        }
    }
    /**
     * Calculates the hyperfactorial of the given number using a loop.
     *
     * @param n The number for which the hyperfactorial is to be calculated.
     * @return The hyperfactorial of the number.
     */
    static long hyperfactorialLoop(long n) {
        long factorial=1;
        while (n > 1 ){
            factorial*= Math.pow(n,n);
            n--;
        }
        return factorial;
    }
}
