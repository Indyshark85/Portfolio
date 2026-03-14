package heap;
import java.util.ArrayList;
import java.util.List;

/**
 * MinHeap is an abstract class that represents a minimum heap data structure.
 * It provides methods to insert elements, remove the minimum element, and
 * manage the heap properties.
 */
public abstract class MinHeap<E extends Comparable<E>> {
    protected List<HeapNode<E>> nodes = new ArrayList<>();
    protected int size = 0;

    /**
     * The two abstract methods getParent and getChildren that need to be
     * implemented by the subclasses of MinHeap.
     */
    public abstract HeapNode<E> getParent(HeapNode<E> node) throws NoParentE;

    public abstract List<HeapNode<E>> getChildren(HeapNode<E> node) throws NoChildE;

    public List<HeapNode<E>> getNodes() { return nodes; }

    //--------------------------------------------------------------------
    // Getters and some utility methods public for testing
    //--------------------------------------------------------------------

    public int getSize() {
        return size;
    }

    /**
     * Returns the minimum child of the given node or throws NoChildE if the node has no children.
     */
    public HeapNode<E> getMinChild(HeapNode<E> elem) throws NoChildE {
        throw new Error("TODO: heap.MinHeap.getMinChild()");
    }

    /**
     * Swaps the two nodes in the heap. It is important to note that this method
     * must update the indices of the nodes as well.
     */
    public void swapNodes (HeapNode<E> a, HeapNode<E> b) {
        throw new Error("TODO: heap.MinHeap.swapNodes()");
    }

    /**
     * Moves the given node up in the heap until it is in the correct position
     * according to the heap property.
     */
    public void moveUp(HeapNode<E> elem) {
        throw new Error("TODO: heap.MinHeap.moveUp()");
    }

    /**
     * Reduces the value of the given node and moves it up in the heap until it is
     * in the correct position according to the heap property.
     */
    public void reduceValue (HeapNode<E> elem, E newValue) {
        throw new Error("TODO: heap.MinHeap.reduceValue()");
    }

    /**
     * Moves the given node down in the heap until it is in the correct position
     * according to the heap property.
     */
    public void moveDown(HeapNode<E> elem) {
        throw new Error("TODO: heap.MinHeap.moveDown()");
    }

    /**
     * Returns the minimum element in the heap without removing it. Throws
     * EmptyHeapExc if the heap is empty.
     */
    public HeapNode<E> getMin() throws EmptyHeapExc {
        throw new Error("TODO: heap.MinHeap.getMin()");
    }

    /**
     * Inserts the given node into the heap. The node must not already be in the
     * heap so its fields 'index' and 'heap' must be properly initialized at this point.
     * The node is added to the end of the heap and then moved up to its
     * correct position.
     */
    public void insert(HeapNode<E> elem) {
        throw new Error("TODO: heap.MinHeap.insert()");
    }

    /**
     * Removes the minimum element from the heap and returns it. But first,
     * the element at index 0 in the array cannot be left empty: it must be updated to
     * contain the new minimum. To do that, the last node in the array is moved to the
     * first position, and then we invoke moveDown on it. This will ensure that the
     * heap property is maintained. The method returns the minimum element that was
     * removed.
     */
    public HeapNode<E> removeMin() throws EmptyHeapExc {
        throw new Error("TODO: heap.MinHeap.removeMin()");
    }

    // --------------------------------------------------------------------
    // Exceptions
    // --------------------------------------------------------------------

    public static class NoParentE extends Exception {}
    public static class NoChildE extends Exception {}
    public static class EmptyHeapExc extends Exception {}
}
