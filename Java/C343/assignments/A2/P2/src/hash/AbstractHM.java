package hash;

import hash.exceptions.KeyNotFoundE;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Abstract class for Hash Map. We will have 4 implementations of this class.
 */
public abstract class AbstractHM<K,V> {
    // Very simple methods
    public abstract boolean isEmpty();
    public abstract int size();
    public abstract void clear();

    // The method containsKey is expected to be efficient.
    // All the other methods need to iterate over all the data in the hash map
    public abstract @NotNull Set<K> keySet();
    public abstract @NotNull Set<V> values();
    public abstract boolean containsKey(@NotNull K key);
    public abstract boolean containsValue(@NotNull V value);
    public abstract void rehash();

    // These are the main methods of interest in a hash map
    public abstract void put(@NotNull K key, @NotNull V value);
    public abstract @NotNull V get(@NotNull K key) throws KeyNotFoundE;
    public abstract void remove(@NotNull K key) throws KeyNotFoundE;
}
