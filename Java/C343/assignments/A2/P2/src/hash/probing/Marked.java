package hash.probing;

import hash.exceptions.KeyNotFoundE;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a marked entry in a probing hash table. A
 * marked entry is used to indicate that a key was previously
 * removed from the table. It is considered "empty" for the
 * purposes of inserting into the table. But when we
 * search for a key, we must treat marked entries as if they
 * were occupied.
 */

public class Marked<K,V> extends AbstractEntry<K,V> {

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
        return "*";
    }
}
