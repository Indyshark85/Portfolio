public interface CirclePacker {
    /** Returns minimal box width for ordered radii, with all circles tangent to bottom. */
    double packWidth(double[] radii); // TODO: implement in concrete class
}
