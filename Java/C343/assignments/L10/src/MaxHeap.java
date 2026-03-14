import java.util.*;

public class MaxHeap {
    private List<String> nodes; // Heap nodes
    private Map<String, Integer> indices; // Node indices
    private Map<String, Integer> weights; // Node weights
    private int size; // Heap size

    public MaxHeap() {
        nodes = new ArrayList<>();
        indices = new HashMap<>();
        weights = new HashMap<>();
        size = 0;
    }

    // Swap two nodes
    public void swap(String n1, String n2) {
        int index1 = indices.get(n1);
        int index2 = indices.get(n2);

        nodes.set(index1, n2);
        nodes.set(index2, n1);

        indices.put(n2, index1);
        indices.put(n1, index2);
    }

    // Heapify up
    public void moveUp(String n) {
        // TODO: Implement the logic for moving the node up in the heap.
        // 1. Retrieve the parent of the current node using the getParent method.
        // 2. Compare the weight of the current node with its parent.
        // 3. If the current node's weight is greater than the parent's weight, swap them.
        // 4. Continue the process recursively or iteratively until the heap property is restored.
    }

    // Heapify down
    public void moveDown(String n) {
        // TODO: Implement the logic for moving the node down in the heap.
        // 1. Start with the current node.
        // 2. Retrieve the left, middle, and right children using the getLeftChild, getMiddleChild, and getRightChild methods.
        // 3. Compare the weights of the children with the current node.
        // 4. If one of the children has a greater weight, swap the current node with the largest child.
        // 5. Repeat the process recursively or iteratively until the heap property is restored.


        String current = n;

        while (true) {
            String largest = current;
            Optional<String> leftChild = getLeftChild(current);
            Optional<String> middleChild = getMiddleChild(current);
            Optional<String> rightChild = getRightChild(current);

            // TODO: Compare the children weights with the current node's weight.

            // If the largest child is not the current node, swap and continue.
            if (!largest.equals(current)) {
                swap(current, largest);
                current = largest;
            } else {
                break;
            }
        }
    }

    // Get parent
    public Optional<String> getParent(String n) {
        int parentIndex = (indices.get(n) - 1) / 3;
        if (parentIndex < 0) return Optional.empty();
        return Optional.of(nodes.get(parentIndex));
    }

    // Get left child
    public Optional<String> getLeftChild(String n) {
        int leftIndex = 3 * indices.get(n) + 1;
        if (leftIndex >= size) return Optional.empty();
        return Optional.of(nodes.get(leftIndex));
    }

    // Get middle child
    public Optional<String> getMiddleChild(String n) {
        int middleIndex = 3 * indices.get(n) + 2;
        if (middleIndex >= size) return Optional.empty();
        return Optional.of(nodes.get(middleIndex));
    }

    // Get right child
    public Optional<String> getRightChild(String n) {
        int rightIndex = 3 * indices.get(n) + 3;
        if (rightIndex >= size) return Optional.empty();
        return Optional.of(nodes.get(rightIndex));
    }

    // Insert a new node
    public void insert(String n, int weight) {
        // TODO: Implement the logic to insert a new node into the heap.
        // 1. Add the node to the nodes list.
        // 2. Set the weight of the node in the weights map.
        // 3. Update the indices map with the node's index.
        // 4. Call moveUp to restore the heap property after insertion.
    }

    // Extract the maximum node (Bonus)
    public String extractMax() {
        // TODO: Implement the logic to extract the maximum node (root) from the heap.
        // 1. Check if the heap is empty. If so, return null.
        // 2. Swap the root node with the last node in the heap.
        // 3. Remove the last node from the heap.
        // 4. Call moveDown on the new root to restore the heap property.
        // Return the maximum value

        return null ; // Can be removed after logic implementation
    }

    // Get a list of all nodes in the heap
    public List<String> getNodes() {
        return new ArrayList<>(nodes);
    }

    // Print heap in level order
    public void printHeap() {
        System.out.println("Heap: " + nodes);
    }
}

