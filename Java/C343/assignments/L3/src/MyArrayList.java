public class MyArrayList<E> implements Iterable<E>
{

    private static final int DEFAULT_CAPACITY = 10;
    private static final int RESIZE_FACTOR = 2;

    private E [ ] items;
    private int size;

    /**
     * Construct an empty ArrayList.
     */
    public MyArrayList( )
    {
        this.items = (E[]) new Object [DEFAULT_CAPACITY];
        this.size = 0;
    }
    
    /**
     * Returns the number of items in this collection.
     * @return the number of items in this collection.
     */
    public int size( )
    {
        return this.size;
    }
    
    /**
     * Returns true if this collection is empty.
     * @return true if this collection is empty.
     */ 
    public boolean isEmpty( )
    {
        return size( ) == 0;
    }
    
    /**
     * Returns the item at position idx.
     * @param idx the index to search in.
     * @throws ArrayIndexOutOfBoundsException if index is out of range.
     */
    public E get( int idx )
    {
        if( idx < 0 || idx >= size( ) )
            throw new ArrayIndexOutOfBoundsException( "Index " + idx + "; size " + size( ) );
        return this.items[ idx ];
    }
        
    /**
     * Changes the item at position idx.
     * @param idx the index to change.
     * @param newVal the new value.
     * @return the old value.
     * @throws ArrayIndexOutOfBoundsException if index is out of range.
     */
    public E set( int idx, E newVal )
    {
        if( idx < 0 || idx >= size( ) )
            throw new ArrayIndexOutOfBoundsException( "Index " + idx + "; size " + size( ) );
        E old = items[ idx ];
        items[ idx ] = newVal;
        
        return old;    
    }

    @SuppressWarnings("unchecked")
    public void resize()
    {
        E[] newItems = (E []) new Object[ items.length * RESIZE_FACTOR ];
        for( int i = 0; i < size( ); i++ )
            newItems[ i ] = items[ i ];
        items = newItems;
    }
    
    /**
     * Adds an item to this collection, at the end.
     * @param x any object.
     * @return true.
     */
    public boolean add( E x )
    {
    add( size( ), x );
        return true;            
    }
    
    /**
     * Adds an item to this collection, at the specified index.
     * @param x any object.
     * @return true.
     */
    public void add( int idx, E x )
    {
        if( items.length == size( ) )
            resize( );

        for( int i = size; i > idx; i-- )
            items[ i ] = items[ i - 1 ];

        items[ idx ] = x;
        size++;
    }
      
    /**
     * Removes an item from this collection.
     * @param idx the index of the object.
     * @return the item was removed from the collection.
     */
    public E remove( int idx )
    {
        E removedItem = items[ idx ];
        
        for( int i = idx; i < size( ) - 1; i++ )
            items[ i ] = items[ i + 1 ];
        size--;
        
        return removedItem;
    }

    
    /**
     * Obtains an Iterator object used to traverse the collection.
     * @return an iterator positioned prior to the first element.
     */
    public java.util.Iterator<E> iterator( )
    {
        return new ArrayListIterator( );
    }

    /**
     * Returns a String representation of this collection.
     */
    public String toString( )
    {
            StringBuilder sb = new StringBuilder( "[ " );

            for( E x : this )
                sb.append( x + " " );
            sb.append( "]" );

            return new String( sb );
    }
    
    /**
     * This is the implementation of the ArrayListIterator.
     * It maintains a notion of a current position and of
     * course the implicit reference to the MyArrayList.
     */
    private class ArrayListIterator implements java.util.Iterator<E>
    {
        private int current = 0;
        private boolean okToRemove = false;

        /**
         * Purpose: Report whether another element exists after the iterator's current position.
         * .
         * What to implement:
         * - Return true if the iterator has not yet visited all elements in the list.
         * - Concretely, compare the iterator's cursor to the list's current size.
         * .
         * @return true if there is at least one more element to visit; false otherwise
         */
        public boolean hasNext( )
        {
            throw new Error("TODO: hasNext()");
        }

        /**
         * Purpose: Return the next element in the iteration and advance the cursor by one.
         * .
         * What to implement:
         * - If there is no next element, throw java.util.NoSuchElementException.
         *   (use hasNext() to your advantage)
         * - Otherwise, mark that a remove is now permitted (set okToRemove to true),
         *   return the element at index 'current', and then increment 'current'.
         * .
         * @return the next element in the iteration
         * @throws java.util.NoSuchElementException if no more elements are available
         */
        public E next( )
        {
            throw new Error("TODO: next()");
        }

        /**
         * Purpose: Remove from the underlying list the last element returned by next().
         * This operation may be called at most once per call to next().
         * .
         * What to implement:
         * - If next() has not yet been called or remove() was already called after the
         *   last next(), throw IllegalStateException.
         * - Otherwise, remove the element at index (current - 1) from the outer list,
         *   decrement 'current' to stay aligned with the shifted elements,
         *   and set okToRemove to false.
         * .
         * @throws IllegalStateException if next() has not been called, or remove() has already
         *         been called after the last next() call
         */
        public void remove( )
        {
            throw new Error("TODO: remove()");
        }
    }
    
}

