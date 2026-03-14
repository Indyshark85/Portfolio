package hash.probing;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * The Linear class is a subclass of Probing that implements the linear probing strategy.
 * The hash function is defined as h(k,i) = (h(k) + i) mod m, where h(k) is the hash of the key k,
 * i is the probe number, and m is the capacity of the hash table.
 */

public class Linear<K,V> extends Probing<K,V> {
    public Linear(int capacity) {
        super(capacity);
    }

    public Linear (int capacity, @NotNull Function<K,Integer> hashFun) {
        super(capacity, hashFun);
    }

    public int hash(K key, int i) {
        return Math.floorMod(hashFun.apply(key) + i, capacity);
    }
}
