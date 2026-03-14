package heap;

/**
 * HeapNode is an abstract class representing a node in a heap. It can be used to
 * uniformly represent nodes in different types of heaps, such as binary or
 * ternary heaps.
 * <p>
 * A heap node contains a value, a reference to the heap it belongs to, and
 * an index in that heap. The heap instance variable is initialized by calling
 * the setHeap() method. This should be done by heap constructor or by the
 * insert() method of the heap. If the node moves positions in the heap, the
 * setIndex() method should be called to update the index.
 */
public abstract class HeapNode<E extends Comparable<E>> implements Comparable<HeapNode<E>> {
    protected E value;
    protected MinHeap<E> heap;
    protected int index;

    public HeapNode(E value) {
        this.value = value;
    }

    void setHeap(MinHeap<E> heap) {
        this.heap = heap;
    }

    void setValue(E value) {
        this.value = value;
    }

    void setIndex(int index) {
        this.index = index;
    }

    public E getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    public int compareTo(HeapNode<E> other) {
        return value.compareTo(other.value);
    }
}
