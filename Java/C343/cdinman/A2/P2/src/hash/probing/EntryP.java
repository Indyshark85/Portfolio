package hash.probing;

import org.jetbrains.annotations.NotNull;

/**
 * A regular entry with a key and a value.
 */

class Entry<K, V> extends AbstractEntry<K, V> {
    private final @NotNull K key;
    private @NotNull V value;

    public Entry(@NotNull K key, @NotNull V value) {
        this.key = key;
        this.value = value;
    }

    public @NotNull K getKey() {
        return key;
    }

    public @NotNull V getValue() {
        return value;
    }

    public void setValue(@NotNull V value) {
        this.value = value;
    }

    public String toString() {
        return String.format("(%s, %s)", key, value);
    }
}
