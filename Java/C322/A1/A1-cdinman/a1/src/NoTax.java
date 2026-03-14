public class NoTax implements SalesTaxBehavior {
    //Always returns 0 tax
    @Override
    public double compute(double value) {
        return 0.0;
    }
}
