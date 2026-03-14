package hash.chains;

import hash.AbstractHM;
import hash.exceptions.KeyNotFoundE;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.HashSet;
import java.util.Set;

/**
 * A chained implementation of a hash map. There is a backing array that
 * grows dynamically as needed. Each element in the array is a linked list.
 * <p>
 * The hash function could be provided by the user. If not, the default
 * hash function is the hashCode method of the Object class.
 * </p>
 */

public class Chained<K,V> extends AbstractHM<K,V> {
    private int size;
    private int capacity;
    private LinkedList<Entry<K,V>>[] table;
    private Function<K,Integer> hashFun;

    @SuppressWarnings("unchecked")
    public Chained(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        this.hashFun = Object::hashCode;
        this.table = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) table[i] = new LinkedList<>();
        // For the line above, a common mistake is to instead write something like:
        // LinkedList<Entry<K,V>>[] empty = new LinkedList<>();
        // for (int i = 0; i < capacity; i++) table[i] = empty
        // where empty is a single linked list that is being reused for all the elements of the array.
        // Quiz: What is the problem with this approach?
    }

    public Chained(int capacity, Function<K,Integer> hashFun) {
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
        for (int i = 0; i < capacity; i++) table[i].clear();
        size = 0;
    }

    /**
     * The load factor is the ratio of the number of elements in the hash map
     * to the capacity of the hash map. We will automatically rehash the hash map
     * when the load factor is greater than 0.75.
     */
    public double getLoadFactor() {
        return (double) size / capacity;
    }

    /**
     * The hash function is used to determine the index in the backing array
     * by taking the modulo of the hash code of the key and the capacity of the
     * backing array.
     * <p>
     * Common mistake is to use % instead of Math.floorMod. The % operator in Java
     * is not the modulo operator, it is the remainder operator. The remainder operator
     * can return negative values, while the modulo operator always returns positive values.
     * And the Java hashCode method can return negative values !!!
     */
    public int hash(K key) {
        return Math.floorMod(hashFun.apply(key), capacity);
    }

    /**
     * Iterate over all the elements in the hash map and collect the keys in a set.
     */
    public @NotNull Set<K> keySet() {
        throw new Error("Not implemented yet");
    }

    /**
     * Iterate over all the elements in the hash map and collect the values in a set.
     */
    public @NotNull Set<V> values() {
        Set<V> values = new HashSet<>();
        for (int i = 0; i < capacity; i++) {
            for (Entry<K,V> entry : table[i]) {
                values.add(entry.getValue());
            }
        }
        return values;
    }

    /**
     * Hash the key to find the index in the backing array. Then iterate over
     * the linked list at that index to see if the key is present.
     */
    public boolean containsKey(@NotNull K key) {
        throw new Error("Not implemented yet");
    }

    /**
     * Iterate over all the elements in the hash map to see if the value is present.
     */
    public boolean containsValue(@NotNull V value) {
        throw new Error("Not implemented yet");
    }

    /**
     * Double the capacity of the backing array and compute the next available
     * prime. Then rehash all the elements in the hash map to the new backing array.
     */
    @SuppressWarnings("unchecked")
    public void rehash() {
        throw new Error("Not implemented yet");
    }

    /**
     * First check if the load factor is greater than 0.75 and rehash if needed.
     * Then hash the key to find the index in the backing array. Finally,
     * iterate over the linked list at that index to see if the key is present.
     * If the key is present, update the value. Otherwise, add a new entry.
     * Don't forget to increment the size.
     */
    public void put(@NotNull K key, @NotNull V value) {
        if (getLoadFactor() > 0.75) rehash();
        int index = hash(key);
        for (Entry<K, V> entry : table[index]) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }
        table[index].add(new Entry<>(key, value));
        size++;
    }

    /**
     * Hash the key to find the index in the backing array. Then iterate over
     * the linked list at that index to see if the key is present. If the key
     * is present, return the value. Otherwise, throw a KeyNotFoundE exception.
     */
    public @NotNull V get(@NotNull K key) throws KeyNotFoundE {
        throw new Error("Not implemented yet");
    }

    /**
     * Hash the key to find the index in the backing array. Then iterate over
     * the linked list at that index to see if the key is present. If the key
     * is present, remove the entry and decrement the size. Otherwise, throw
     * a KeyNotFoundE exception.
     */
    public void remove(@NotNull K key) throws KeyNotFoundE {
        throw new Error("Not implemented yet");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < capacity; i++) {
            sb.append(i).append(": ");
            for (Entry<K,V> entry : table[i]) {
                sb.append(entry).append(" -> ");
            }
            sb.append("*\n");
        }
        return sb.toString();
    }
}
