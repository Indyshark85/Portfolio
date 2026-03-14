package hash.probing;

import hash.exceptions.KeyNotFoundE;
import org.jetbrains.annotations.NotNull;

/**
 * In a probing implementation, we need to distinguish between three types of entries:
 * 1. An entry that contains a key-value pair.
 * 2. An entry that is marked as deleted.
 * 3. An entry that is empty.
 * Consequently, we will hav three classes that extend this abstract class.
 */

public abstract class AbstractEntry<K,V> {
    public abstract @NotNull K getKey() throws KeyNotFoundE;
    public abstract @NotNull V getValue() throws KeyNotFoundE;
    public abstract void setValue(@NotNull V value) throws KeyNotFoundE;
}
