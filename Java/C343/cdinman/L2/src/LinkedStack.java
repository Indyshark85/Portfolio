
public class LinkedStack<T> {
    private final SinglyLinkedList<T> list = new SinglyLinkedList<>();

    public void push(T val) {
        list.addFirst(val); }
    public T pop() {
        if(list.isEmpty()){
            return null;
        }
        return list.removeFirst();
    }
    public boolean isEmpty() {
        return list.isEmpty(); }
}