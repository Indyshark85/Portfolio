import heap.BinaryMinHeap;
import heap.HeapNode;
import heap.MinHeap;
import heap.TernaryMinHeap;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeapTest_Student {
    @Test
    void testBinary_InsertAndMin() throws MinHeap.EmptyHeapExc {
        MinHeap<Integer> h = new BinaryMinHeap<>();

        HeapNode<Integer> n10 = new BinaryMinHeap.Heap2Node<>(10);
        HeapNode<Integer> n4  = new BinaryMinHeap.Heap2Node<>(4);
        HeapNode<Integer> n7  = new BinaryMinHeap.Heap2Node<>(7);

        h.insert(n10);
        h.insert(n4);
        h.insert(n7);

        assertEquals(n4, h.getMin());
        assertEquals(3, h.getSize());
    }

    @Test
    void testBinary_RemoveMinOrder() throws Exception {
        MinHeap<Integer> h = new BinaryMinHeap<>();

        h.insert(new BinaryMinHeap.Heap2Node<>(12));
        h.insert(new BinaryMinHeap.Heap2Node<>(5));
        h.insert(new BinaryMinHeap.Heap2Node<>(9));
        h.insert(new BinaryMinHeap.Heap2Node<>(1));

        assertEquals(1,  h.removeMin().getValue());
        assertEquals(5,  h.removeMin().getValue());
        assertEquals(9,  h.removeMin().getValue());
        assertEquals(12, h.removeMin().getValue());
    }

    @Test
    void testBinary_ReduceValue() throws Exception {
        MinHeap<Integer> h = new BinaryMinHeap<>();

        HeapNode<Integer> n20 = new BinaryMinHeap.Heap2Node<>(20);
        HeapNode<Integer> n15 = new BinaryMinHeap.Heap2Node<>(15);
        HeapNode<Integer> n30 = new BinaryMinHeap.Heap2Node<>(30);

        h.insert(n20);
        h.insert(n15);
        h.insert(n30);

        h.reduceValue(n30, 5);

        assertEquals(n30, h.getMin());
    }

    @Test
    void testTernary_ChildrenStructure() throws Exception {
        MinHeap<Integer> h = new TernaryMinHeap<>();

        TernaryMinHeap.Heap3Node<Integer> n6 = new TernaryMinHeap.Heap3Node<>(6);
        TernaryMinHeap.Heap3Node<Integer> n2 = new TernaryMinHeap.Heap3Node<>(2);
        TernaryMinHeap.Heap3Node<Integer> n9 = new TernaryMinHeap.Heap3Node<>(9);
        TernaryMinHeap.Heap3Node<Integer> n1 = new TernaryMinHeap.Heap3Node<>(1);
        TernaryMinHeap.Heap3Node<Integer> n7 = new TernaryMinHeap.Heap3Node<>(7);

        h.insert(n6);
        h.insert(n2);
        h.insert(n9);
        h.insert(n1);
        h.insert(n7);

        assertEquals(n1, h.getMin());

        // children of n1 in ternary heap should be at indices 1,2,3 → n2,n9,n6 (depending on moveUp)
        List<HeapNode<Integer>> kids = h.getChildren(n1);
        assertEquals(3, kids.size());
    }

    @Test
    void testTernary_RemoveMin() throws Exception {
        MinHeap<Integer> h = new TernaryMinHeap<>();

        h.insert(new TernaryMinHeap.Heap3Node<>(4));
        h.insert(new TernaryMinHeap.Heap3Node<>(2));
        h.insert(new TernaryMinHeap.Heap3Node<>(8));
        h.insert(new TernaryMinHeap.Heap3Node<>(3));

        assertEquals(2, h.removeMin().getValue());
        assertEquals(3, h.removeMin().getValue());
        assertEquals(4, h.removeMin().getValue());
        assertEquals(8, h.removeMin().getValue());
    }

}
