class Problem2 {

    /**
     * receives five integers representing the number of apples, bananas, oranges, bunches of grapes,
     * and pineapple purchased at a store.
     *
     * @param a # of Apple.
     * @param b # of Bananas.
     * @param o # of Oranges.
     * @param g # of Grapes.
     * @param p # of Pineapples.
     * @return total price of the trip.
     */
    static double grocery(int a, int b, int o, int g, int p) {
        double PriceA = 0.59 * a;
        double PriceB = 0.99 * b;
        double PriceO = 0.45 * o;
        double PriceG = 1.39 * g;
        double PriceP = 2.24 * p;
        return (PriceA+PriceB+PriceO+PriceG+PriceP);
    }
}
