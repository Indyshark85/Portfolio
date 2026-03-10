class Problem3 {

    /**
     * Design the double pyramidSurfaceArea(double l, double w, double h) method, which
     * computes the surface area of a pyramid with a given base length 𝑙, base width 𝑤, and height ℎ.
     *
     * @param l Length.
     * @param w Width.
     * @param h height.
     * @return Surface area of a pyramid.
     */
    static double pyramidSurfaceArea(double l, double w, double h) {
        double ans=0;
        if (l<=0 || w<=0 || h<=0){
            ans=0;
        }else{
            double basic = l*w;
            double complicated1= l*Math.sqrt( ((w/2)*(w/2)) + (h*h));
            double complicated2= w*Math.sqrt( ((l/2)*(l/2))+ (h*h) );
            ans=basic+complicated1+complicated2;
        }

        return ans;
    }
}
