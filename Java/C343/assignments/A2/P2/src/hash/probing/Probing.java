package hash.probing;

import hash.AbstractHM;
import hash.exceptions.KeyNotFoundE;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Probing is an abstract class that implements the basic methods of a hash map using probing.
 * It is missing one method, hash (k,i) that determines the probing strategy and that
 * must be implemented by the subclasses.
 */

public abstract class Probing<K,V> extends AbstractHM<K,V> {
    private int size;
    protected int capacity;
    private AbstractEntry<K,V>[] table;
    protected Function<K,Integer> hashFun;

    @SuppressWarnings("unchecked")
    public Probing(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        this.table = (AbstractEntry<K, V>[]) Array.newInstance(AbstractEntry.class, capacity);
        for (int i = 0; i < capacity; i++) table[i] = new EmptyP<>();
        this.hashFun = Object::hashCode;
    }

    public Probing(int capacity, Function<K,Integer> hashFun) {
        this(capacity);
        this.hashFun = hashFun;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        size = 0;
        for (int i = 0; i < capacity; i++) table[i] = new EmptyP<>();
    }

    /**
     * We will automatically rehash when the load factor is greater than 0.75.
     */
    public double getLoadFactor() {
        return (double) size / capacity;
    }

    /**
     * This is the basic hash function that we will use to determine the index of the key.
     * The result of this function will be used as the initial index. The hash function
     * hash(k,i) will determine the probing strategy where k is the key and i is the
     * number of times we have probed.
     */
    public int hash(K key) {
        return Math.floorMod(hashFun.apply(key), capacity);
    }

    /**
     * This is the probing strategy that we will use to determine the next index to probe.
     * We will have three different probing strategies: linear, quadratic, and double hashing
     * that will be implemented by the subclasses.
     */
    public abstract int hash(K key, int i);

    /**
     * Iterate through the table to collect the keys.
     */
    public @NotNull Set<K> keySet() {
        throw new Error("Not implemented yet");
    }

    /**
     * Iterate through the table to collect the values.
     */
    public @NotNull Set<V> values() {
        throw new Error("Not implemented yet");
    }

    /**
     * Start a probing loop that might iterate through the whole table.
     * For each iteration, we will hash the key and the iteration index
     * to give us an index in the array. If the index is empty, we know
     * that the key is not in the table. If the index is an entry, we
     * will compare the key with the entry key. If they are equal, we
     * have found the key. If we iterate through the whole table and
     * we have not found the key, we know that the key is not in the table.
     */
    public boolean containsKey(@NotNull K key) {
        throw new Error("Not implemented yet");
    }

    /**
     * Iterate through the table to find the value.
     */
    public boolean containsValue(@NotNull V value) {
        throw new Error("Not implemented yet");
    }

    /**
     * Allocate a new table with size as the next prime number after 2*oldCapacity.
     * Rehash all the entries in the old table to the new table.
     */
    @SuppressWarnings("unchecked")
    public void rehash() {
        throw new Error("Not implemented yet");
    }

    /**
     * If the load factor is greater than 0.75, we rehash the table. We
     * then start a probing loop that might iterate through the whole table.
     * For each iteration, we will hash the key and the iteration index
     * to give us an index in the array. If the index is empty or marked
     * as deleted, we will insert the key and value in the table. If the
     * index is an entry, we will compare the key with the entry key. If
     * they are equal, we will update the value. If we iterate through the
     * whole table and we have not found an empty or marked index, we know
     * that our probing strategy is not finding an empty index. We will
     * rehash the table and try again.
     */
    public void put(@NotNull K key, @NotNull V value) {
        if (getLoadFactor() > 0.75) rehash();
        for (int i = 0; i < capacity; i++) {
            int j = hash(key, i);
            if (table[j] instanceof Entry<K, V> entry) {
                if (entry.getKey().equals(key)) {
                    entry.setValue(value);
                    return;
                }
            }
            else {
                table[j] = new Entry<>(key, value);
                size++;
                return;
            }
        }
        rehash();
        put(key, value);
    }

    /**
     * Start a probing loop that might iterate through the whole table.
     * For each iteration, we will hash the key and the iteration index
     * to give us an index in the array. If the index is empty, we know
     * that the key is not in the table. If the index is an entry, we
     * will compare the key with the entry key. If they are equal, we
     * have found the key. If we iterate through the whole table and
     * we have not found the key, we know that the key is not in the table.
     */
    public @NotNull V get(@NotNull K key) throws KeyNotFoundE {
        throw new Error("Not implemented yet");
    }

    /**
     * Start a probing loop that might iterate through the whole table.
     * For each iteration, we will hash the key and the iteration index
     * to give us an index in the array. If the index is empty, we know
     * that the key is not in the table and throw an exception. If the
     * index is an entry, we will compare the key with the entry key. If
     * they are equal, we have found the key. In that case, we mark the
     * index as deleted and decrease the size of the table.
     * If we iterate through the whole table and we have not found the key,
     * we know that the key is not in the table and throw an exception.
     */
    public void remove(@NotNull K key) throws KeyNotFoundE {
        throw new Error("Not implemented yet");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < capacity; i++) {
            sb.append(table[i].toString());
            if (i < capacity-1) sb.append(" ");
        }
        return sb.toString();
    }
}
