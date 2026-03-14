import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

// For extra credits uncomment the below commented testcases and pass

public class MaxHeapTest {

    @Test
    public void testInsertions() {
        MaxHeap heap = new MaxHeap();
        heap.insert("A", 10);
        heap.insert("B", 20);
        heap.insert("C", 5);
        heap.insert("D", 25);
        heap.insert("E", 15);
        assertEquals("[D, E, C, B, A]", heap.getNodes().toString(), "Heap structure after insertions is incorrect.");
    }

    @Test
    public void testInsertDescendingOrder() {
        MaxHeap heap = new MaxHeap();
        heap.insert("A", 50);
        heap.insert("B", 40);
        heap.insert("C", 30);
        heap.insert("D", 20);
        heap.insert("E", 10);
        assertEquals("[A, B, C, D, E]", heap.getNodes().toString(), "Heap structure for descending order insertion is incorrect.");
    }

    // @Test
    // public void testInsertDuplicateWeights() {
    //     MaxHeap heap = new MaxHeap();
    //     heap.insert("X", 30);
    //     heap.insert("Y", 30);
    //     heap.insert("Z", 30);
    //     assertEquals("[X, Y, Z]", heap.getNodes().toString(), "Heap structure with duplicate weights is incorrect.");
    // }

    // @Test
    // public void testExtractMaxSingleElement() {
    //     MaxHeap heap = new MaxHeap();
    //     heap.insert("A", 10);
    //     assertEquals("A", heap.extractMax(), "Extracting max from a single-element heap is incorrect.");
    //     assertTrue(heap.getNodes().isEmpty(), "Heap should be empty after extracting the only element.");
    // }

    @Test
    public void testExtractUntilEmpty() {
        MaxHeap heap = new MaxHeap();
        heap.insert("A", 10);
        heap.insert("B", 20);
        heap.insert("C", 5);
        heap.insert("D", 25);
        heap.insert("E", 15);

        assertEquals("D", heap.extractMax(), "First extracted max is incorrect.");
        assertEquals("B", heap.extractMax(), "Second extracted max is incorrect.");
        assertEquals("E", heap.extractMax(), "Third extracted max is incorrect.");
        assertEquals("A", heap.extractMax(), "Fourth extracted max is incorrect.");
        assertEquals("C", heap.extractMax(), "Fifth extracted max is incorrect.");
        assertTrue(heap.getNodes().isEmpty(), "Heap should be empty after extracting all elements.");
    }

    @Test
    public void testExtractFromEmptyHeap() {
        MaxHeap heap = new MaxHeap();
        assertNull(heap.extractMax(), "Extracting from an empty heap should return null.");
    }

    // @Test
    // public void testHeapifyAfterRemoval() {
    //     MaxHeap heap = new MaxHeap();
    //     heap.insert("A", 10);
    //     heap.insert("B", 40);
    //     heap.insert("C", 30);
    //     heap.insert("D", 50);
    //     heap.insert("E", 20);
    //     heap.extractMax(); // Removes D
    //     assertEquals("[B, E, C, A]", heap.getNodes().toString(), "Heap structure after removing max is incorrect.");
    // }
}

