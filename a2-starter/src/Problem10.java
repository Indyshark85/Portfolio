class Problem10 {

    /**
     * method, which receives a
     * radius 𝑟 and a delta Δ. It computes (and returns) a left-Riemann approximation of the area of a
     * circle.
     *
     * @param r radius double.
     * @param delta Δ double.
     * @return The approximate area of the circle.
     */
    static double circleArea(double r, double delta) {
        double area=0.0;
        for (double x =0; x<r;x+= delta){
            double y = Math.sqrt(r*r-x*x);
            area+=y* delta;
        }
        return 4*area;
    }
}
