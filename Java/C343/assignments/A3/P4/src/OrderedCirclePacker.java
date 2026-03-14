public class OrderedCirclePacker extends AbstractCirclePacker {
    @Override
    public double packWidth(double[] r) {
        /** TODO:
         *    1) handle n=0 -> 0
         *    2) place x[0]=r[0]
         *    3) for each i>0: x[i] = max( r[i], max_{j<i} ( x[j] + sep(r[i], r[j]) ) )
         *    4) width = max_i (x[i] + r[i])
         */
        throw new Error("TODO (A3.P4.src.OrderedCirclePacker): packWidth");
    }
}
