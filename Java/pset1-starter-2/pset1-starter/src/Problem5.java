class Problem5 {


    /**
     * when
     * given two side lengths of a triangle 𝑎, 𝑏 and the angle between those two sides 𝜃 in degrees, returns
     * the length of the third side 𝑐.
     *
     * @param a side 1 of Triangle.
     * @param b side 2 of Triangle.
     * @param th angle between both sides.
     * @return length of the third side of the triangle.
     */
    static double lawOfCosines(double a, double b, double th) {
        double c=0;
        if (a==0 || b==0 || th==0){
            c=0;
        }else{
            c = Math.sqrt((a*a)+(b*b)-(2*a*b*Math.cos(Math.toRadians(th))));
        }
        return c;
    }
}
