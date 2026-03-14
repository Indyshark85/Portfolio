import java.util.function.Function;

class FunctionalLazyList<T> implements ILazyList<T>{
    private T current;
    private final Function<T,T> function;

    /**
     * Constructor
     * @param function unary function
     * @param initalValue the inital value
     */
    public FunctionalLazyList(Function<T, T> function, T initalValue) {
        this.function = function;
        this.current = initalValue;
    }

    /**
     *the next method to invoke 𝑓 on the current element of the lazy list and return the previous.
     * @return T
     */
    @Override
    public T next(){
        T prev = current;
        current=function.apply(current);
        return prev;
    }
}
