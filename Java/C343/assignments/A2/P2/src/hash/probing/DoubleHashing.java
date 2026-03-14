package hash.probing;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * The DoubleHashing class is a subclass of the Probing class.
 * To implement the Double Hashing probing strategy, the DoubleHashing class overrides the hash method
 * to use the probe number multiplied by the second hash function as the offset.
 */
public class DoubleHashing<K,V> extends Probing<K,V> {
    private final @NotNull Function<K,Integer> hashFun2;

    public DoubleHashing(int capacity) {
        super(capacity);
        this.hashFun2 = key -> 1 + key.hashCode() % (capacity - 1);
    }

    public DoubleHashing (int capacity, @NotNull Function<K,Integer> hashFun, @NotNull Function<K,Integer> hashFun2) {
        super(capacity, hashFun);
        this.hashFun2 = hashFun2;
    }

    public int hash(K key, int i) {
        return Math.floorMod(hash(key) + i * hashFun2.apply(key), capacity);
    }
}
