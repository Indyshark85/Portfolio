package dynamicArray;

import exceptions.EmptyDequeueE;
import interfaces.DequeueI;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * DequeueA is a double-ended queue implemented with an array.
 * The array is circular, so the front and back pointers wrap around.
 * The array is resized when it is full by doubling its capacity.
 * <p>
 * The convention is that the front pointer generally points to an
 * empty slot where the next enqueueFront operation will store a value.
 * Similarly, the back pointer generally points to an empty slot where the
 * next enqueueBack operation will store a value.
 * <p>
 * When the array is full, the doubleCapacity method is called to create a new
 * array with double the capacity, and the elements are copied over. The
 * front pointer is set to the size of the original array, and the back pointer is set
 * to capacity - 1.
 * <p>
 * Example:
 * [ _ , _ , _ , _ , _ ]
 * F                 B
 * <p>
 * enqueueFront("A")
 * <p>
 * [ A , _ , _ , _ , _ ]
 *       F           B
 * <p>
 * enqueueFront("B")
 * <p>
 * [ A , B , _ , _ , _ ]
 *           F       B
 * <p>
 * enqueueBack("C")
 * <p>
 * [ A , B , _ , _ , C ]
 *           F   B
 * <p>
 * doubleCapacity
 * [ C , A , B , _ , _ , _ , _ , _ , _ , _ ]
 *               F                       B
 */

public class DequeueA<E> implements DequeueI<E> {
    private @NotNull Node<E>[] elems;
    private int capacity;
    private int front, back;
    private int size;

    @SuppressWarnings("unchecked")
    public DequeueA (int capacity) {
        this.elems = (Node<E>[]) Array.newInstance(Node.class, capacity);
        this.capacity = capacity;
        Arrays.fill(elems, new Empty<>());
        front = 0;
        back = capacity - 1;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    /**
     * Enqueues an element at the front of the dequeue. If the
     * dequeue is full, the capacity is doubled. Remember to increment
     * the front pointer and the size. The front pointer wraps around
     * back to zero when it reaches the end of the array.
     */
    public void enqueueFront(@NotNull E e) {
        if (size == capacity) doubleCapacity();
        elems[front] = new Elem<>(e);
        size++;
        front = Math.floorMod(front+1, capacity);
    }

    /**
     * Enqueues an element at the back of the dequeue. If the
     * dequeue is full, the capacity is doubled. Remember to decrement
     * the back pointer and increment the size. The back pointer wraps around
     * to the end of the array when it reaches zero.
     */
    public void enqueueBack(@NotNull E e) {
        if (size == capacity) doubleCapacity();
        elems[back] = new Elem<>(e);
        size++;
        back = Math.floorMod(back-1, capacity);
    }

    /**
     * Dequeues an element from the front of the dequeue. Make sure
     * to store an empty value in the slot that was just dequeued.
     * If the dequeue is empty, throw an EmptyDequeueE exception.
     */
    public @NotNull E dequeueFront() throws EmptyDequeueE {
        if (isEmpty()) throw new EmptyDequeueE();

        front = Math.floorMod(front-1, capacity);
        Node<E> node = elems[front];
        elems[front] = new Empty<>();
        size--;

        return node.getValue();
    }

    /**
     * Dequeues an element from the back of the dequeue. Make sure
     * to store an empty value in the slot that was just dequeued.
     * If the dequeue is empty, throw an EmptyDequeueE exception.
     */
    public @NotNull E dequeueBack() throws EmptyDequeueE {
        if (isEmpty()) throw new EmptyDequeueE();

        back = Math.floorMod(back+1, capacity);
        Node<E> node = elems[back];
        elems[back] = new Empty<>();
        size--;

        return node.getValue();
    }

    /**
     * Returns the element at the front of the dequeue without removing it.
     * If the dequeue is empty, throw an EmptyDequeueE exception.
     */
    public @NotNull E peekFront() throws EmptyDequeueE {
        if (isEmpty()) throw new EmptyDequeueE();

        int INDX= Math.floorMod(front-1,capacity);
        return elems[INDX].getValue();
    }

    /**
     * Returns the element at the back of the dequeue without removing it.
     * If the dequeue is empty, throw an EmptyDequeueE exception.
     */
    public @NotNull E peekBack() throws EmptyDequeueE {
        if (isEmpty()) throw new EmptyDequeueE();

        int INDX= Math.floorMod(back+1,capacity);
        return elems[INDX].getValue();
    }

    /**
     * Doubles the capacity of the array and copies the elements over.
     * Follow the following convention when copying the elements: the
     * element at position back+1 (modulo the capacity) in the old array
     * should be copied to position 0 in the new array, the element at
     * position back+2 (modulo the capacity) in the old array should be
     * copied to position 1 in the new array, and so on. Make sure to
     * update the front and back pointers accordingly.
     */
    @SuppressWarnings("unchecked")
    public void doubleCapacity () {
        int newCapacity = capacity*2;
        Node<E>[] newElems = (Node<E>[]) Array.newInstance(Node.class, newCapacity);

        int start = Math.floorMod(back+1, capacity);
        for (int i=0; i < size; i++) {
            newElems[i] = elems[(start+i) % capacity];
        }

        for (int i= size; i < newCapacity; i++) {
            newElems[i] = new Empty<>();
        }

        elems = newElems;
        capacity = newCapacity;

        front = size;
        back = newCapacity-1;
    }

    public String toString() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("[ ");
            for (int i = 0; i < capacity; i++) {
                if (elems[i].isEmpty()) sb.append("_");
                else sb.append(elems[i].getValue());
                if (i < capacity - 1) sb.append(" , ");
            }
            sb.append(" ]");
            return sb.toString();
        }
        catch (EmptyDequeueE e) {
            throw new Error("Internal bug in printing dequeue: This should never happen");
        }
    }

}
