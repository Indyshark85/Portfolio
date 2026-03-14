public class SinglyLinkedList<T> {
    static class TODO extends RuntimeException {} // TODO: remove this line when you're done

    private Node<T> head;
    private int size = 0;

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    // Add to front
    public void addFirst(T val) {
        throw new TODO();
    }

    // Add to end
    public void addLast(T val) {
        throw new TODO();
    }

    // Remove first element
    public T removeFirst() {
        throw new TODO();
    }

    // Get value at index
    public T get(int index) {
        Node<T> cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.data;
    }
}