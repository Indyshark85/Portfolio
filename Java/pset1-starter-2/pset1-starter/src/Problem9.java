class Problem9 {

    /**
     * method, which receives three
     * integers 𝑥, 𝑦, and 𝑧, and returns whether they are evenly spaced. Evenly spaced means that the
     * difference between the smallest and medium number is the same as the difference between the
     * medium and largest number.
     *
     * @param a int 1.
     * @param b int 2.
     * @param c int 3.
     * @return bool value that is determined by the difference between the various ints.
     */
    static boolean isEvenlySpaced(int a, int b, int c) {
        int small = Math.min(a,Math.min(b,c));
        int large = Math.max(a,Math.max(b,c));
        int medium = a+b+c-small-large;
        return (medium-small)==(large-medium);
    }
}
