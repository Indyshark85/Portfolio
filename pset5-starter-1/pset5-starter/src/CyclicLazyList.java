import java.util.ArrayList;
import java.util.List;

class CyclicLazyList<T> implements ILazyList<T>{

    private final List<T> items;
    private int CIndex;

    /**
     * constructor is variadic and receives any number of value
     * @param values T
     */
    public CyclicLazyList(T...values) {
        this.items = new ArrayList<>();
        for (T value: values){
            this.items.add(value);
        }
        this.CIndex = 0;
    }

    /**
     *The cyclic lazy list should return
     * the first item received from the constructor, then the second, and so forth until reaching the end.
     * After returning all the values, cycle back to the front and continue
     * @return T
     */
    @Override
    public T next(){
        //edge case
        if(items.isEmpty()){
            return null;
        }
        T value = items.get(CIndex);
        CIndex=(CIndex+1)%items.size();
        return value;
    }
}
