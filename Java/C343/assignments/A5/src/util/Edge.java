package util;

public record Edge<V>(V from, V to, Weight weight) {
    public Edge<V> flip() {
        return new Edge<>(to, from, weight);
    }
}
