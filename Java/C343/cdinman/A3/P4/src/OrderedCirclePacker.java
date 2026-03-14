public class OrderedCirclePacker extends AbstractCirclePacker {
    @Override
    public double packWidth(double[] r) {
        /** TODO:
         *    1) handle n=0 -> 0
         *    2) place x[0]=r[0]
         *    3) for each i>0: x[i] = max( r[i], max_{j<i} ( x[j] + sep(r[i], r[j]) ) )
         *    4) width = max_i (x[i] + r[i])
         */
        if(r == null || r.length==0) return 0;

        int n = r.length;
        double[] x = new double[n];

        x[0] = r[0];
        double width = x[0] + r[0];

        for (int i =1; i<n;i++){
            double xi =r[i];
            for(int j=0;j<i;j++){
                xi= Math.max(xi,x[j]+sep(r[i],r[j]));
            }
            x[i] = xi;
            width = Math.max(width,x[i]+r[i]);
        }
        return width;
    }
}
