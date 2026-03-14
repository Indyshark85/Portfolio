public abstract class AbstractCirclePacker implements CirclePacker {
    protected static double sep(double a, double b) {
        // horizontal separation needed for circles of radius a and b to be mutually tangent
        return 2.0 * Math.sqrt(a * b);
    }
}
