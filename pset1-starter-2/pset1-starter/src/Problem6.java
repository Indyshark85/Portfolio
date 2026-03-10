class Problem6 {

    /**
     * A method that will calculate the distance traveled by an object.
     *
     * @param vi initial velocity.
     * @param a acceleration (m/s^2).
     * @param t time (in sec).
     * @return distance traveled in meters.
     */
    static double distanceTraveled(double vi, double a, double t) {
        double distance = t*vi+(0.5)*(a*(t*t));
        return (distance);
    }
}
