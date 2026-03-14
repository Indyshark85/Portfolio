class PQueue<T> {

    private static class Node<T> {
        private T data;
        private Node<T> next;

        Node(T t) {
            this.data = t;
            this.next = null;
        }
    }

    private Node<T> first;
    private Node<T> last;
    private int numElements;

    PQueue() {
        this(null);
    }

    PQueue(T data) {
        this(null, null, 0);
    }

    private PQueue(Node<T> first, Node<T> last, int numElements) {
        this.first = first;
        this.last = last;
        this.numElements = numElements;
    }

    /**
     * Deep-copy this queue into a NEW PQueue instance (no node sharing).
     * .
     * How to construct:
     * 1) For an empty queue, return a new empty queue immediately.
     * 2) Otherwise, walk through the elements from the front to the back.
     *    - For each element, create a fresh node holding the same value.
     *    - Link each new node to the end of a newly built list, keeping track of
     *      its first (head) and last (tail) nodes as you go. For the first element,
     *      both head and tail refer to the same new node; thereafter, extend the tail.
     * 3) Keep a running count of how many elements were copied, and return a new
     *    queue built from the newly constructed head and tail together with that count.
     * .
     * Why:
     * - Persistence: do not modify the original queue. Returning a fresh structure
     *   that shares no nodes with the original preserves all previous versions.
     * .
     * Complexity:
     * - Time O(n), Space O(n) new nodes, where n = numElements.
     *
     * @return a new PQueue<> with the same sequence of elements as this queue.
     */
    private PQueue<T> copy() {
        throw new Error("TODO: copy()");
    }

    /**
     * Structural equality between two queues.
     * .
     * How to construct:
     * 1) Because the Java equality contract accepts any Object, first determine
     *    whether the input is a queue (of any element type). If not, the result is false.
     * 2) If the two queues have different numbers of elements, the result is false.
     * 3) Otherwise, walk both queues from front to back in parallel and compare
     *    corresponding elements using their usual notion of equality. If any pair
     *    of elements does not match, the result is false.
     * 4) If every pair matches and both traversals finish at the same time,
     *    the queues are equal and the result is true.
     * .
     * Complexity:
     * - Time O(n), Space O(1) extra.
     *
     * @param obj any object, expected to be a PQueue<?> for equality to be meaningful.
     * @return true iff obj is a PQueue with the same length and pairwise-equal elements.
     * .
     * Note on pattern matching and wildcards:
     * - The form ‘obj instanceof PQueue<?> pq’ both checks the type and binds a local
     *   variable ‘pq’ if the check succeeds (pattern matching for ‘instanceof’).
     * - The ‘<?>’ is the unbounded wildcard, meaning “a PQueue of some unknown element type”.
     *   Because the element type is unknown, you may safely read elements (to compare),
     *   but you should not write values into ‘pq’.
     */
    @Override
    public boolean equals(Object obj) {
        throw new Error("TODO: equals(Object obj)");
    }

    /**
     * Return a NEW queue that contains all current elements followed by t.
     * .
     * How to construct:
     * 1) First create a separate copy of the current queue so the original remains unchanged
     *    (this maintains persistence).
     * 2) Create a fresh node that holds the provided value.
     * 3) If the copied queue has no elements, make this node both the front and the back.
     *    Otherwise, attach the node after the current last element so it becomes the new last.
     * 4) Update the recorded number of elements in the copied queue and return that queue.
     * .
     * Complexity:
     * - Time O(n) due to copy(), Space O(n) for the new nodes in the copy.
     *
     * @param t the value to place at the end of the queue.
     * @return the modified copy with t appended.
     */
    PQueue<T> enqueue(T t) {
        throw new Error("TODO: enqueue(T t)");
    }

    /**
     * Return a NEW queue that is this queue without its first element.
     * .
     * How to construct:
     * 1) Handle small sizes:
     *    - If there are no elements, return an empty queue.
     *    - If there is exactly one element, removing the front leaves an empty queue;
     *      return that (an empty queue).
     * 2) Otherwise:
     *    - Create a single copy of the current queue (to preserve persistence).
     *    - Remove the front element from that copy and update its recorded size.
     *      If this removal makes it empty, ensure its front and back markers reflect emptiness.
     * 3) Return that modified copy.
     * .
     * Complexity:
     * - Time O(n) due to copy(), Space O(n) for the copied nodes.
     *
     * @return the modified copy without the first element (or empty if size <= 1).
     */
    PQueue<T> dequeue() {
        throw new Error("TODO: dequeue()");
    }

    /**
     * Return (without removing) the first element of the queue.
     * .
     * How to construct:
     * 1) If the queue contains at least one element, return the value at the front.
     * 2) If the queue is empty, return null
     * .
     * Complexity:
     * - Time O(1), Space O(1).
     *
     * @return the head element if present, else null.
     */
    T peek() {
        throw new Error("TODO: peek()");
    }

    /**
     * Build a queue from a variable number of input values, preserving their order.
     * This method is generic, static, and accepts a variable-length argument list.
     * .
     * How to construct (conceptually):
     * 1) Create the linked nodes directly in a single pass over the inputs to avoid
     *    forming multiple intermediate queues.
     * 2) Link each new node after the previous one, keeping track of the first
     *    (head) and last (tail) positions as you go.
     * 3) Return a queue initialized with that head, tail, and the total count.
     * .
     * Complexity:
     * - Time O(n), Space O(n) new nodes.
     * .
     * @param vals elements to place into the queue, in order.
     * @return a new PQueue containing vals[0], vals[1], ..., vals[n-1].
     * @param <T> the element type.
     */
    static <T> PQueue<T> of(T... vals) {
        throw new Error("TODO: of(T... vals)");
    }

    /**
     * Return the number of elements currently in the queue.
     * .
     * Complexity:
     * - Time O(1), Space O(1).
     * .
     * @return the size of this queue.
     */
    int size() {
        throw new Error("TODO: size()");
    }
}
