public class SevenPercent implements SalesTaxBehavior{
    //Calculates 7% of the sale value
    @Override
    public double compute(double value) {
        return value * 0.07;
    }
}
