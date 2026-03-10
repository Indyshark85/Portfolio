class FibonacciLazyList implements ILazyList<Integer> {
    private int current=1;
    private int previous =0;
    private boolean isFirst=true;

    /**
     * which implements ILazyList<Integer> and correctly overrides
     * next to produce Fibonacci sequence values
     * @return Int
     */
    @Override
    public Integer next(){
        if(isFirst){
            isFirst=false;
            return previous;
        }
        int nextValue= previous + current;
        previous=current;
        current=nextValue;
        return previous;

    }
}
