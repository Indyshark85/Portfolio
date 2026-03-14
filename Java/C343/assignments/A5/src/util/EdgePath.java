package util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public record EdgePath<V>(List<Edge<V>> edges) implements Iterable<Edge<V>> {

    public List<V> nodes() {
        var list = new ArrayList<V>();
        for (Edge<V> e : edges) list.add(e.from());
        if (!edges.isEmpty()) list.add(edges.getLast().to());
        return list;
    }

    public EdgePath<V> add(Edge<V> edge) {
        var newEdges = new ArrayList<>(edges);
        newEdges.add(edge);
        return new EdgePath<>(newEdges);
    }

    public Weight getMinimumWeight() {
        return edges.stream()
                .map(Edge::weight)
                .min(Weight::compareTo)
                .orElse(Weight.infinity());
    }

    public @NotNull Iterator<Edge<V>> iterator() {
        return edges.iterator();
    }
}
