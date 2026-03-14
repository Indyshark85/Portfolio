package heap;

import util.Tree2Printer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * BinaryMinHeap is a binary min-heap implementation of the MinHeap abstract class.
 * It provides methods to insert, remove, and manipulate elements in the heap.s
 */
public class BinaryMinHeap<E extends Comparable<E>> extends MinHeap<E> {

    /**
     * Create an empty BinaryMinHeap.
     */
    public BinaryMinHeap() {}

    /**
     * Heapify the given list of elements.
     */
    public BinaryMinHeap(List<HeapNode<E>> elements) {
        nodes = new ArrayList<>(elements);
        size = elements.size();
        for (int i = 0; i < size; i++) {
            HeapNode<E> elem = elements.get(i);
            elem.setHeap(this);
            elem.setIndex(i);
        }
        for (int i = size / 2 - 1; i >= 0; i--) {
            moveDown(nodes.get(i));
        }
    }

    //-------------------------------------------------------------------------
    // Utility methods, public for testing
    //-------------------------------------------------------------------------

    /**
     * Returns the parent of the given element or throws NoParentE if the element is the root.
     */
    public HeapNode<E> getParent(HeapNode<E> elem) throws NoParentE {
        throw new Error("TODO: heap.BinaryMinHeap.getParent()");
    }

    /**
     * Returns the children of the given element or throws NoChildE if the element has no children.
     */
    public List<HeapNode<E>> getChildren(HeapNode<E> elem) throws NoChildE {
        throw new Error("TODO: heap.BinaryMinHeap.getChildren()");
    }

    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BinaryMinHeap<?> that)) return false;
        return size == that.size && nodes.equals(that.nodes);
    }

    public int hashCode() {
        return Objects.hash(nodes, size);
    }

    //-------------------------------------------------------------------------
    // Nested class for binary HeapNode
    // extends the generic HeapNode with the methods needed by Tree2Printer
    //-------------------------------------------------------------------------

    public static class Heap2Node<E extends Comparable<E>>
            extends HeapNode<E>
            implements Tree2Printer.PrintableNode {

        public Heap2Node(E value) {
            super(value);
        }

        public Tree2Printer.PrintableNode getLeft() {
            try {
                List<HeapNode<E>> children = heap.getChildren(this);
                return (Tree2Printer.PrintableNode) children.getFirst();
            } catch (NoChildE e) {
                return null;
            }
        }

        public Tree2Printer.PrintableNode getRight() {
            try {
                List<HeapNode<E>> children = heap.getChildren(this);
                if (children.size() > 1) return (Tree2Printer.PrintableNode) children.get(1);
                else return null;
            } catch (NoChildE e) {
                return null;
            }
        }

        public String getText() {
            return value.toString();
        }
    }

}