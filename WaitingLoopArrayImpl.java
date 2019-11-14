/**
 * Class for the waiting loop array
 * @author logan
 *
 * @param <E> Generic Object
 */
public class WaitingLoopArrayImpl<E> implements WaitingArrayInterface<E> {
	private int entryIndex;
	private int exitIndex;
	private E[] loopArray;
	private int initialCapacity;
	
	/**
	 * 
	 * @param initialCapacity
	 */
	@SuppressWarnings("unchecked")
	public WaitingLoopArrayImpl(int initialCapacity) {
		super();
		this.initialCapacity = initialCapacity;
		exitIndex = 0;
		entryIndex = this.initialCapacity-1;
		loopArray = (E[])new Object[initialCapacity];
	}
	/**
	 * 
	 */
	public WaitingLoopArrayImpl() {
		this(100);
	}
	
	/**
	 * doubles the size of the array by creating a new array and passing in the new objects
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void resizeCapacity() {
		int newCapacity = initialCapacity*2;
		E[] newArray = (E[])new Object[newCapacity];
		for(int i = 0;i<loopArray.length;i++) 
		{
			if(loopArray[i]==null)
				continue;
			newArray[i] = loopArray[i];
		}
		loopArray = newArray;
	}
	
	/**
	 * Adds an element to the array at the entryIndex
	 * if the array is full, calls the resizeCapacity method
	 */
	@Override
	public void add(E element) {
		if(isFull())
		{
			resizeCapacity();
		}
		incEntryIndex();
		loopArray[entryIndex] = element;
	}
	
	/**
	 * Removes the element at the exitIndex from the array
	 * if the array is empty, throws an EmptyWaitingArrayException
	 * @return Whether or not the item could be removed
	 */
	@Override
	public boolean remove() throws EmptyWaitingArrayException
	{
		if(isEmpty())
			throw new EmptyWaitingArrayException("The array is already empty.");
		loopArray[exitIndex] = null;
		incExitIndex();
		return true;
	}
	
	/**
	 * Clears the array
	 * if the array is empty, throws an EmptyWaitingArrayException
	 */
	@Override
	public void clear() throws EmptyWaitingArrayException
	{
		if(isEmpty())
			throw new EmptyWaitingArrayException("The array is already empty.");
		for(E e : loopArray)
		{
			if(e!=null) {
				remove();
			}
		}
		
	}
	/**
	 * Gets the first element to be added
	 * if the array is empty, throws an EmptyWaitingArrayException
	 * @return the first element to be added
	 */
	@Override
	public E getFirst() throws EmptyWaitingArrayException {
		if(isEmpty())
			throw new EmptyWaitingArrayException("The array empty. There is nothing to return.");
		return loopArray[exitIndex];
	}
	/**
	 * Gets the last element to be added
	 * if the array is empty, throws an EmptyWaitingArrayException
	 * @return the last element to be added
	 */
	@Override
	public E getLast() throws EmptyWaitingArrayException {
		if(isEmpty())
			throw new EmptyWaitingArrayException("The array empty. There is nothing to return.");
		return loopArray[entryIndex];
	}
	/**
	 * Checks to see if the array is empty
	 * If the array contains no elements (every spot in the array equals null)
	 * then the array is empty
	 * @return Whether or not the array is full
	 */
	@Override
	public boolean isEmpty() {
		for(E e : loopArray)
			if(e!=null)
				return false;
		return true;
	}
	/**
	 * Checks to see if the array is full
	 * If the number of elements in the array equals the array's length minus 1
	 * then the array is full
	 * @return Whether or not the array is full
	 */
	@Override
	public boolean isFull() {
		int count = 0;
		for(E e : loopArray)
			if(e!=null)
				count++;
		return count==loopArray.length-1;
	}
	
	/**
	 * helper index for increasing the exit index and making it loop
	 */
	public void incExitIndex() {
		exitIndex++;
		if(exitIndex > loopArray.length-1) 
		{
			exitIndex = 0;
		}
	}
	
	/**
	 * helper index for increasing the entry index and making it loop
	 */
	public void incEntryIndex() {
		entryIndex++;
		if(entryIndex > loopArray.length-1) 
		{
			entryIndex = 0;
		}
	}
	
	/**
	 * Displays the non-null elements of the array
	 */
	@Override
	public String toString() {
		String ans="[";
		for(int i = 0;i<loopArray.length;i++) {
			if(loopArray[i]==null)
				continue;
			if(i==loopArray.length-1)
				ans+=loopArray[i];
			else
				ans+=loopArray[i]+",";
		}
		if(ans.charAt(ans.length()-1)==',')
			ans= ans.substring(0,ans.length()-1);
		return ans+"]";
	}
}





