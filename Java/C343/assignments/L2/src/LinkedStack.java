public class LinkedStack<T> {
    static class TODO extends RuntimeException {} // TODO: remove this line when you're done

    private final SinglyLinkedList<T> list = new SinglyLinkedList<>();

    public void push(T val) { throw new TODO(); }
    public T pop() { throw new TODO(); }
    public boolean isEmpty() { return list.isEmpty(); }
}