import java.util.Objects;

class PQueue<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;


    public PQueue() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    /**
     * method that returns whether the elements of this queue
     * are equal to the provided queue’s elements.
     *
     * @param obj OBJECT OMG YAY.
     * @return boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (this== obj) return true;
        if (!(obj instanceof PQueue<?>))
            return false;
        PQueue<?> other = (PQueue<?>) obj;
        if (this.size != other.size)
            return false;
        Node<T> current = this.first;
        Node<?> otherCurrent = other.first;
        while (current!= null && otherCurrent !=null) {
            if (!Objects.equals(current.value, otherCurrent.value))
                return false;
            current= current.next;
            otherCurrent=otherCurrent.next;
        }
        return true;
    }

    /**
     * method that enqueues a value onto the end of a new
     * queue containing all the old elements, in addition to the new value.
     *
     * @param t T (like the cool one who makes music).
     * @return T.
     */
    PQueue<T> enqueue(T t) {
        PQueue<T> newQueue =this.copy();
        Node<T> newNode = new Node<>(t);
        if (this.first== null) {
            newQueue.first= newQueue.last = newNode;
        } else {
            newQueue.last.next=newNode;
            newQueue.last=newNode;
        }
        newQueue.size++;
        return newQueue;
    }

    /**
     * method that removes the first element of the queue,
     * returning a new queue without this first value.
     *
     * @return T.
     */
    PQueue<T> dequeue() {
        if (this.first == null)
            return new PQueue<>();
        PQueue<T> newQueue = new PQueue<>();
        if (this.first.next == null)
            return newQueue;
        newQueue.first=this.first.next;
        newQueue.last=this.last;
        newQueue.size=this.size-1;
        return newQueue;
    }

    /**
     * method that returns the first element of the queue.
     *
     * @return a value of T.
     */
    T peek() {
        if (this.first != null || this.size!=0)
            return this.first.value;
        else{
            return null;
        }
    }

    /**
     * method that creates a queue with
     * the values passed as vals. Note that this must be a variadic method.
     *
     * @param vals T.
     * @return T.
     * @param <T> T.
     */
    static <T> PQueue<T> of(T... vals) {
        PQueue<T> queue = new PQueue<>();
        Node<T> previous = null;

        for (T val : vals) {
            Node<T> newNode = new Node<>(val);
            if (previous == null) {
                queue.first = newNode;
            } else {
                previous.next = newNode;
            }
            previous = newNode;
        }

        queue.last = previous;
        queue.size = vals.length;
        return queue;

    }

    /**
     * method that returns the number of elements in the queue..
     *
     * @return int.
     */
    int size() {
        return this.size;
    }

    /**
     * method that returns a new queue with the same
     * elements as the current queue.
     *
     * @return T.
     */
    private PQueue<T> copy() {
        PQueue<T> newQueue = new PQueue<>();
        if(this.first ==null){
            return newQueue;
        }
        Node<T> current = this.first;
        newQueue.first=new Node<>(current.value);
        Node<T> newCurrent = newQueue.first;
        while(current.next!= null){
            current=current.next;
            newCurrent.next =new Node<>(current.value);
            newCurrent = newCurrent.next;
        }
        newQueue.last=newCurrent;
        newQueue.size=this.size;
        return newQueue;
    }

    private static class Node<T> {
        T value;
        Node<T> next;
        Node(T value){
            this.value=value;
            this.next=null;
        }

    }
}
