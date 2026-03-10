class Problem13 {

    /**
     *  method that, when given a number of small bars 𝑠,
     * large bars 𝑙, and maximum weight 𝑤, determines the number of small candy bars he can fit in the
     * box. Large bars weigh five kilograms, and small bars weigh one kilogram. Note that Carlo always
     * tries to fit large candies first before small. Return -
     * criteria.
     * @param s # of small candy bars (1 kg).
     * @param l # of large candy bars (5 kg).
     * @param w weight that box can hold in kg.
     * @return -1 if it is impossible otherwise return how many small candy bars that can fit.
     */
    static int fitCandy(int s, int l, int w) {
        int LBU = Math.min(l,w/5); //calculates this via taking the min value between the amount l bars and the max allowed to by the weight
        int remain = w - LBU * 5;
        if (remain<=s){
            return remain;
        }else{
            return -1;
        }

    }
}
