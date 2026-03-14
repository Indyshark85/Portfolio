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
        List<HeapNode<E>> children = getChildren(elem);
        HeapNode<E> min = children.get(0);

        for(HeapNode<E> c : children){
            if(c.getValue().compareTo(min.getValue())<0){
                min = c;
            }
        }
        return min;
    }

    /**
     * Swaps the two nodes in the heap. It is important to note that this method
     * must update the indices of the nodes as well.
     */
    public void swapNodes (HeapNode<E> a, HeapNode<E> b) {
        int ai = a.getIndex();
        int bi = b.getIndex();

        nodes.set(ai,b);
        nodes.set(bi,a);

        a.setIndex(bi);
        b.setIndex(ai);
    }

    /**
     * Moves the given node up in the heap until it is in the correct position
     * according to the heap property.
     */
    public void moveUp(HeapNode<E> elem) {
        while(true){
            try{
                HeapNode<E> parent = getParent(elem);

                if(elem.getValue().compareTo(parent.getValue()) >= 0) return;

                swapNodes(elem,parent);
            } catch (NoParentE e){
                return;
            }
        }
    }

    /**
     * Reduces the value of the given node and moves it up in the heap until it is
     * in the correct position according to the heap property.
     */
    public void reduceValue (HeapNode<E> elem, E newValue) {
        elem.setValue(newValue);
        moveUp(elem);
    }

    /**
     * Moves the given node down in the heap until it is in the correct position
     * according to the heap property.
     */
    public void moveDown(HeapNode<E> elem) {
        while (true){
            HeapNode<E> minChild;
            try {
                minChild=getMinChild(elem);
            }catch (NoChildE e){
                return;
            }

            if(elem.getValue().compareTo(minChild.getValue()) <= 0) return;

            swapNodes(elem,minChild);
        }
    }

    /**
     * Returns the minimum element in the heap without removing it. Throws
     * EmptyHeapExc if the heap is empty.
     */
    public HeapNode<E> getMin() throws EmptyHeapExc {
        if(size == 0) throw new EmptyHeapExc();
        return nodes.get(0);
    }

    /**
     * Inserts the given node into the heap. The node must not already be in the
     * heap so its fields 'index' and 'heap' must be properly initialized at this point.
     * The node is added to the end of the heap and then moved up to its
     * correct position.
     */
    public void insert(HeapNode<E> elem) {
        elem.setHeap(this);
        elem.setIndex(size);
        nodes.add(elem);
        size++;
        moveUp(elem);
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
        if(size == 0) throw new EmptyHeapExc();
        HeapNode<E> min = nodes.get(0);

        if(size == 1) {
            nodes.remove(0);
            size = 0;
            return min;
        }
        HeapNode<E> last = nodes.get(size-1);
        nodes.set(0,last);
        last.setIndex(0);

        nodes.remove(size-1);
        size--;

        moveDown(last);
        return min;
    }

    // --------------------------------------------------------------------
    // Exceptions
    // --------------------------------------------------------------------

    public static class NoParentE extends Exception {}
    public static class NoChildE extends Exception {}
    public static class EmptyHeapExc extends Exception {}
}
