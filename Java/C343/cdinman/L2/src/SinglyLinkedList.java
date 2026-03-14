import java.util.NoSuchElementException;

public class SinglyLinkedList<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size = 0;

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    // Add to front
    public void addFirst(T val) {
        Node<T> NewNode = new Node<>(val);
        NewNode.next= head;
        head = NewNode;
        if (tail == null)
            tail = NewNode;
        size++;
    }

    // Add to end
    public void addLast(T val) {
        Node<T> NewNode = new Node<>(val);
        if (tail == null){
            head = NewNode;
            tail = NewNode;
        }else{
            tail.next = NewNode;
            tail = NewNode;
        }
        size++;
    }

    // Remove first element
    public T removeFirst() {
        if (head==null){
            return null;
        }
        T value = head.data;
        head=head.next;
        if (head == null){
            tail=null;
            //just in case
        }
        size--;
        return value;
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