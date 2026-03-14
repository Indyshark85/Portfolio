package hash.probing;

import hash.exceptions.KeyNotFoundE;
import org.jetbrains.annotations.NotNull;

/**
 * An empty entry in the hash table.
 */
public class EmptyP<K,V> extends AbstractEntry<K,V> {

    public @NotNull K getKey() throws KeyNotFoundE {
        throw new KeyNotFoundE();
    }

    public @NotNull V getValue() throws KeyNotFoundE {
        throw new KeyNotFoundE();
    }

    public void setValue(@NotNull V value) throws KeyNotFoundE {
        throw new KeyNotFoundE();
    }

    public String toString() {
        return "_";
    }
}
