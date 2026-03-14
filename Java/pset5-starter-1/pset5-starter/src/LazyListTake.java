import java.util.ArrayList;
import java.util.List;
class LazyListTake<T> {
    private final ILazyList<T> lazyList;
    private final int n;

    /**
     * Constructor should receive an ILazyList and an integer 𝑛
     * denoting how many elements to take, as parameters
     * @param lazyList List
     * @param n int
     */
    public LazyListTake(ILazyList<T> lazyList, int n) {
        this.lazyList = lazyList;
        this.n = n;
    }

    /**
     *method,which returns a List<T> of 𝑛 elements from the given lazy list
     * @return the list
     */
    public List<T> getList(){
        List<T> resultList = new ArrayList<>();
        for(int i=0; i<n; i++){
            resultList.add(lazyList.next());
        }
        return resultList;
    }
}
