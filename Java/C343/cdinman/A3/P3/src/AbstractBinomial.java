public abstract class AbstractBinomial implements BinomialCoefficient {
    protected void validate(int n, int k) {
        if (n < 0 || k < 0 || k > n) {
            throw new IllegalArgumentException("Require 0 <= k <= n");
        }
    }
}
