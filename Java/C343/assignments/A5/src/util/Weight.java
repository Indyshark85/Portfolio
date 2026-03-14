package util;
import org.jetbrains.annotations.NotNull;

/**
 * Weight class represents a weight (distance) value that can be either finite or infinite.
 */
public sealed abstract class Weight implements Comparable<Weight>
        permits Weight.FiniteWeight, Weight.InfiniteWeight {
    public static final Weight ZERO = Weight.of(0);
    public static Weight of(int value) {
        return new FiniteWeight(value);
    }
    public static Weight infinity() { return new InfiniteWeight(); }
    public abstract int asInt();
    public abstract boolean isInfinite();
    public abstract Weight negate();
    public abstract Weight plus(Weight weight);
    public abstract Weight minus(Weight weight);
    public boolean isNonPositive() { return !isInfinite() && asInt() <= 0; }
    public int compareTo(@NotNull Weight other) {
        if (this instanceof InfiniteWeight) return other instanceof InfiniteWeight ? 0 : 1;
        if (other instanceof InfiniteWeight) return -1;
        return Integer.compare(this.asInt(), other.asInt());
    }

    public static final class InfiniteWeight extends Weight {
        public int asInt() { throw new Error("Cannot convert infinite distance to int"); }
        public boolean isInfinite() { return true; }
        public Weight negate() { return this; }
        public Weight plus(Weight weight) { return this; }
        public Weight minus(Weight weight) { return this; }
        public boolean equals(Object o) { return o instanceof InfiniteWeight; }
        public int hashCode() { return 0; }
    }

    public static final class FiniteWeight extends Weight {
        private final int value;
        public FiniteWeight(int value) {
            this.value = value;
        }
        public int asInt() { return value; }
        public boolean isInfinite() {
            return false;
        }
        public Weight negate() {
            return new FiniteWeight(-value);
        }
        public Weight plus(Weight weight) {
            if (weight instanceof InfiniteWeight) return weight;
            else {
                long result = (long) value + weight.asInt();
                return result > Integer.MAX_VALUE ? new InfiniteWeight() : Weight.of((int) result);
            }
        }
        public Weight minus(Weight weight) {
            return plus(weight.negate());
        }
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof FiniteWeight that)) return false;
            return value == that.value;
        }
        public int hashCode() {
            return Integer.hashCode(value);
        }
    }
}
