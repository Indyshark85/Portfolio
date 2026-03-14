package traversals;

import util.Progress;

import java.util.Collection;

/**
 * TraversalStrategy interface defines the methods required for
 * a traversal strategy. These are start, hasNext, next, and visit.
 */
public interface TraversalStrategy<V> {
    void start(V start);
    boolean hasNext();
    V next();
    Progress visit(V current, Collection<V> neighbors);
}
