class Problem8 {

    /**
     * method that, when given three integers 𝑥, 𝑦, and 𝑧, returns
     * whether or not one of them is less than twenty away from another.
     *
     * @param x int 1.
     * @param y int 2.
     * @param z int 3.
     * @return a boolean value if the difference<=20.
     */
    static boolean lessThan20(int x, int y, int z) {
        int space1 = Math.abs(x-y);
        int space2 = Math.abs(x-z);
        int space3 = Math.abs(y-z);

        if (space1<20 || space2<20 || space3<20){
            return true;
        }else{
            return false;
        }

    }

}
