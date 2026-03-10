class Problem12 {

    /**
     *  method that, when given a rectangle centered at (𝑟𝑥 , 𝑟𝑦), width 𝑤 and
     * height ℎ as well as a point ( 𝑝𝑥 , 𝑝𝑦), returns whether the point is located strictly inside the rectangle.
     *
     * @param rx rectangle x coord.
     * @param ry rectangle y coord.
     * @param w width.
     * @param h height.
     * @param px point x coord.
     * @param py point y coord.
     * @return boolean determined on whether-or-not the point is within the rectangle.
     */
    static boolean isInsideRectangle(double rx, double ry, double w, double h, double px, double py) {
        double hwidth = w/2;
        double hHeight = h/2;
        double L_edge = rx - hwidth;
        double R_edge = rx + hwidth;
        double T_edge = ry - hHeight;
        double B_edge = ry + hHeight;

        if (px > L_edge && px < R_edge && py > T_edge && py < B_edge){
            return true;
        }
        else{
            return false;
        }

    }
}
