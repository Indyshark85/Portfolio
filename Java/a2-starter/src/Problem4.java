class Problem4 {
    /**
     *Generates the Collatz sequence for the given number using recursion
     * @param n The starting number of the Collatz sequence
     * @return string representing the Collatz sequence
     */
    static String collatz(int n) {
        if (n==1){
            return "1";
        }else if (n%2==0){
            return n+","+collatz(n/2);
        }else{
            return n+","+collatz(3*n+1);
        }
    }

    /**
     * Generates the Collatz sequence for the given number using tail recursion
     * @param n The starting number of the Collatz sequence
     * @return A string representing the Collatz sequence.
     */
    static String collatzTR(int n) {
        return collatzHelpler(n,"");
    }

    /**
     * Helper method for tail recursion Collatz sequence generation
     * @param n The current number being processed
     * @param acc The accumulated sequence string
     * @return A string representing the Collatz sequence
     */
    private static String collatzHelpler(int n, String acc){
        if (n==1){
            return acc+1;
        }else if (n%2==0){
            return collatzHelpler(n/2, acc + n + ",");
        }else{
            return collatzHelpler(3*n+1, acc + n + ",");
        }
    }

    /**
     * Generates the Collatz sequence for the given number using a loop.
     * @param n The starting number of the Collatz sequence.
     * @return A string representing the Collatz sequence.
     */
    static String collatzLoop(int n) {
        String acc=""+n;
        while (!(n==1)){
            if (n%2==0){
                n=n/2;
            }else{
                n=3*n+1;
            }
            acc+=","+n;
        }
        return acc;
    }
}
