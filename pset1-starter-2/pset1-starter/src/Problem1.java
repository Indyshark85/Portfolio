class Problem1 {

    /**
     * converts a distance
     * in gigameters to light seconds (i.e., the distance that light travels in one second). There are
     * 1, 000, 000, 000 meters in a gigameter, and light travels 299, 792, 458 meters per second.
     *
     * @param gm GigaMeter.
     * @return Conversion to Light Second.
     */
    static double gigameterToLightsecond(double gm) {
        double gigameter= 1000000000;
        double LT= 299792458;
        return ((gm*gigameter)/LT);
    }
}
