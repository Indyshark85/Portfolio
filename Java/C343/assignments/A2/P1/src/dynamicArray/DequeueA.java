package dynamicArray;

import exceptions.EmptyDequeueE;
import interfaces.DequeueI;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.Arrays;

/*
[ _ , _ , _ , _ , _ ]
  F               B

enqueueFront("A")

[ A , _ , _ , _ , _ ]
      F           B

enqueueFront("B")

[ A , B , _ , _ , _ ]
          F       B

enqueueBack("C")

[ A , B , _ , _ , C ]
          F   B

doubleCapacity

[ C , A , B , _ , _ , _ , _ , _ , _ , _ ]
              F                       B
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

    public void enqueueFront(@NotNull E e) {
        if (size == capacity) doubleCapacity();
        elems[front] = new Elem<>(e);
        front = Math.floorMod(front + 1, capacity);
        size++;
    }

    public void enqueueBack(@NotNull E e) {
        if (size == capacity) doubleCapacity();
        elems[back] = new Elem<>(e);
        back = Math.floorMod(back - 1, capacity);
        size++;
    }

    public @NotNull E dequeueFront() throws EmptyDequeueE {
        int index = Math.floorMod(front - 1, capacity);
        E result = elems[index].getValue();
        elems[index] = new Empty<>();
        front = index;
        size--;
        return result;
    }

    public @NotNull E dequeueBack() throws EmptyDequeueE {
        int index = Math.floorMod(back + 1, capacity);
        E result = elems[index].getValue();
        elems[index] = new Empty<>();
        back = index;
        size--;
        return result;
    }

    public @NotNull E peekFront() throws EmptyDequeueE {
        int index = Math.floorMod(front - 1, capacity);
        return elems[index].getValue();
    }

    public @NotNull E peekBack() throws EmptyDequeueE {
        int index = Math.floorMod(back + 1, capacity);
        return elems[index].getValue();
    }


    @SuppressWarnings("unchecked")
    public void doubleCapacity () {
        int newCapacity = capacity * 2;
        Node<E>[] newElems = (Node<E>[]) Array.newInstance(Node.class, newCapacity);
        Arrays.fill(newElems, new Empty<>());
        for (int i = 0; i < size; i++) {
            newElems[i] = elems[Math.floorMod(back + 1 + i, capacity)];
        }
        capacity = newCapacity;
        elems = newElems;
        front = size;
        back = capacity - 1;
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

    /**
     * Below we implement the equals and hashCode methods for the DequeueA class.
     * The main contract that needs to be satisfied is that if two dequeues are equal
     * according to the equals method, then calling hashCode on each of the two objects
     * must produce the same integer result.
     * <p>
     * Below we will make sure that two DequeueA objects of the same size and the same
     * elements are equal even if the arrays might have a different capacity and the
     * elements are stored in different positions.
     */

    public boolean equals(Object o) {
        throw new Error("TODO: implement");
    }

    public int hashCode() {
        throw new Error("TODO: implement");
    }

}
