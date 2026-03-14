import exceptions.EmptyListE;
import exceptions.EmptyQueueE;
import exceptions.EmptyStackE;
import interfaces.QueueI;
import org.jetbrains.annotations.NotNull;

/**
 * QueueTwoStacks is a class that implements a queue using two stacks.
 * The queue behavior requires insertions at one end and deletions at the other.
 * Here we use two stacks to simulate the queue behavior. The inbox stack is used
 * to enqueue elements and the outbox stack is used to dequeue elements. When the
 * outbox stack is empty, we pop all elements from the inbox stack and push them
 * onto the outbox stack. This way, the elements are reversed and we can pop them
 * in the correct order.
 * <p>
 * The implementation is correct and quite efficient.
 *
 * @param <E> the type of elements in the queue
 */
public class QueueTwoStacks<E> implements QueueI<E> {
    private final @NotNull StackPL<E> inbox;
    private final @NotNull StackPL<E> outbox;

    public QueueTwoStacks() {
        inbox = new StackPL<>();
        outbox = new StackPL<>();
    }

    /**
     * Check if the queue is empty.
     */
    public boolean isEmpty() {
        return inbox.isEmpty() && outbox.isEmpty();
    }

    /**
     * Get the number of elements in the queue.
     */
    public int size() {
        return inbox.size() + outbox.size();
    }

    /**
     * Enqueue an element into the queue. Just use the inbox stack to push the element.
     */
    public void enqueue(@NotNull E e) {
        inbox.push(e);
    }

    /**
     * Dequeue an element from the queue. The default behavior is to pop from
     * the outbox stack. If the outbox stack is empty, pop all elements
     * from the inbox stack and push them onto the outbox stack. Then pop the
     * top element from the outbox stack.
     */
    public E dequeue() throws EmptyQueueE {
        if (outbox.isEmpty()){
            while(!inbox.isEmpty()){
                try {
                    outbox.push(inbox.pop());
                }catch (EmptyStackE ex){
                    throw new Error("inbox failed to pop :(");
                }
            }
        }
        try {
            return outbox.pop();
        } catch (EmptyStackE ex) {
            throw new EmptyQueueE();
        }
    }

    /**
     * Peek at the element at the front of the queue. The default behavior is to peek
     * at the top element of the outbox stack. If the outbox stack is empty, pop all
     * elements from the inbox stack and push them onto the outbox stack.
     */
    public E peek() throws EmptyQueueE{
        if (outbox.isEmpty()){
            while(!inbox.isEmpty()){
                try {
                    outbox.push(inbox.pop());
                }catch (EmptyStackE ex){
                    throw new Error("inbox failed to pop :(");
                }
            }
        }
        try {
            return outbox.peek();
        } catch (EmptyStackE ex) {
            throw new EmptyQueueE();
        }
    }
}
