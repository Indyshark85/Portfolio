class Problem4 {

    /**
     * when given a data point 𝑥, a mean 𝜇, and a standard deviation 𝜎,
     * computes the corresponding z-score of 𝑥 and returns whether it is an “extreme” outlier.
     *
     * @param x data point.
     * @param avg mean .
     * @param stddev standard Deviation.
     * @return True/False.
     */
    static boolean isExtremeOutlier(double x, double avg, double stddev) {
        boolean Rval= false;

        double eq= (x-avg)/stddev;

        if (eq<-3 || eq>3){
            Rval=true;
        }
        return Rval;
    }
}
