package heap;

import util.Tree3Printer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * TernaryMinHeap is a ternary min-heap implementation of the MinHeap abstract class.
 */
public class TernaryMinHeap<E extends Comparable<E>> extends MinHeap<E> {

    /**
     * Create an empty TernaryMinHeap.
     */
    public TernaryMinHeap() {}

    /**
     * Heapify the given list of elements.
     */
    public TernaryMinHeap(List<HeapNode<E>> elements) {
        nodes = new ArrayList<>(elements);
        this.size = elements.size();
        for (int i = 0; i < size; i++) {
            HeapNode<E> elem = elements.get(i);
            elem.setHeap(this);
            elem.setIndex(i);
        }
        for (int i = size / 3 - 1; i >= 0; i--) {
            moveDown(nodes.get(i));
        }
    }

    //------------------------------------------------------------------------
    // Utility methods, public for testing
    //------------------------------------------------------------------------

    /**
     * Returns the parent of the given element or throws NoParentE if the element is the root.
     */
    public HeapNode<E> getParent(HeapNode<E> node) throws NoParentE {
        int i = node.getIndex();
        if (i==0) throw new NoParentE();
        int parentIndex = (i-1)/3;
        return nodes.get(parentIndex);
    }

    /**
     * Returns the children of the given element or throws NoChildE if the element has no children.
     */
    public List<HeapNode<E>> getChildren(HeapNode<E> node) throws NoChildE {
        List<HeapNode<E>> children = new ArrayList<>();
        int i = node.getIndex();

        int c1 = 3*i+1;
        int c2 = 3*i+2;
        int c3 = 3*i+3;

        if(c1<size) children.add(nodes.get(c1));
        if(c2<size) children.add(nodes.get(c2));
        if(c3<size) children.add(nodes.get(c3));

        if(children.isEmpty()) throw new NoChildE();
        return children;
    }

    //------------------------------------------------------------------------
    //------------------------------------------------------------------------

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TernaryMinHeap<?> that)) return false;
        return size == that.size && nodes.equals(that.nodes);
    }

    public int hashCode() {
        return Objects.hash(nodes, size);
    }

    //-------------------------------------------------------------------------
    // Nested class for ternary HeapNode
    // extends the generic HeapNode with the methods needed by Tree3Printer
    //-------------------------------------------------------------------------

    public static class Heap3Node<E extends Comparable<E>>
            extends HeapNode<E>
            implements Tree3Printer.PrintableNode {

        public Heap3Node(E value) {
            super(value);
        }

        public Tree3Printer.PrintableNode getLeft() {
            try {
                List<HeapNode<E>> children = heap.getChildren(this);
                return (Tree3Printer.PrintableNode) children.getFirst();
            } catch (NoChildE e) {
                return null;
            }
        }

        public Tree3Printer.PrintableNode getMiddle() {
            try {
                List<HeapNode<E>> heapChildren = heap.getChildren(this);
                if (heapChildren.size() > 1) return (Tree3Printer.PrintableNode) heapChildren.get(1);
                else return null;
            } catch (NoChildE e) {
                return null;
            }
        }

        public Tree3Printer.PrintableNode getRight() {
            try {
                List<HeapNode<E>> heapChildren = heap.getChildren(this);
                if (heapChildren.size() > 2) return (Tree3Printer.PrintableNode) heapChildren.get(2);
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
