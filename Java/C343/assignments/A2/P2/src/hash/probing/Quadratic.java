package hash.probing;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * The Quadratic class is a subclass of the Probing class.
 * To implement the Quadratic probing strategy, the Quadratic class overrides the hash method
 * to use the square of the probe number as the offset.
 */

public class Quadratic<K,V> extends Probing<K,V> {
    public Quadratic(int capacity) {
        super(capacity);
    }

    public Quadratic (int capacity, @NotNull Function<K,Integer> hashFun) {
        super(capacity, hashFun);
    }

    public int hash(K key, int i) {
        return Math.floorMod(hash(key) + i * i, capacity);
    }
}
