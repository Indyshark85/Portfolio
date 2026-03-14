public class LinkedQueue<T> {
    static class TODO extends RuntimeException {
    } // TODO: remove this line when you're done

    private final SinglyLinkedList<T> list = new SinglyLinkedList<>();

    public void enqueue(T val) {
        throw new TODO();
    }

    public T dequeue() {
        throw new TODO();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}