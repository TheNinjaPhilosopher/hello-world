import java.util.Comparator;

public class VIPWaitingArrayImpl<E> implements WaitingArrayInterface<E>, Comparator<E> {
	
	private int entryIndex;
	private E[] loopArray;
	private int initialCapacity;
	
	/**
	 * Special Constructor for VIPWaitingArrayImpl
	 * @param capacity The amount of elements the array can hold
	 */
	@SuppressWarnings("unchecked")
	public VIPWaitingArrayImpl(int capacity) {
		super();
		this.initialCapacity = capacity;
		entryIndex = -1;
		loopArray = (E[])new Object[initialCapacity];
	}
	
	/**
	 * The default constructor for VIPWaitingArrayImpl
	 * Calls the special constructor for simplicity
	 * uses 100 for default capacity
	 */
	public VIPWaitingArrayImpl() {
		this(100);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Adds the element to the array with the help of several helper methods
	 * @param element The element that needs to be inserted
	 */
	public void add(E element) {
		if(isFull())
		{
			System.out.println("Array size has to be increased first");
			resizeCapacity();
		}
		else if(isEmpty())
		{
			loopArray[0]=element;
			return;
		}
		boolean alreadySorted = false;
		for(int i = 0;i<loopArray.length;i++) {
			if(loopArray[i]==null)
			{
				loopArray[i]=element;
				break;
			}
		}
		for(int i = 0;i<loopArray.length-1;i++) {
			if(loopArray[i]==null)
				continue;
			switch(compare(loopArray[i],element)) {
			case -1:
			case 0:
				continue;
			case 1: 
				if(!alreadySorted) {
					entryIndex = i;
					swapAll(element);
					alreadySorted = true;
				}
				break;
			}
		}
	}
	/**
	 * This is a helper method that I used to swap the elements in the array
	 * Creates a new array, passes in some of the elements up to the point where the new element is entered
	 * Then passes in the rest
	 * @param element The element that needs to be inserted
	 */
	@SuppressWarnings("unchecked")
	private void swapAll(E element) {
		E[] tempArray = (E[])new Object[loopArray.length];
		E temp;
		for(int i = 0;i<loopArray.length-1;i++) {
			if(i==entryIndex) {
				temp = loopArray[i];
				tempArray[i] = element;
				tempArray[i+1] = temp;
				break;
			}
			else 
				tempArray[i] = loopArray[i];
		}
		int count = 0;
		for(E e : tempArray) {
			if(e!=null)
				count++;
		}
		for(E e : loopArray) {
			if(!contains(tempArray,e))
			{
				if(count >=tempArray.length-1)
					break;
				tempArray[count] = e;
			}
		}
		loopArray = tempArray;
	}
	
	/**
	 * helper method to see if the passed in element is in the passed in array
	 * @param array the array being checked
	 * @param element the element being checked
	 * @return
	 */
	public boolean contains(E[] array, E element) {
		for(E e : array)
			if(e==element) 
				return true;
		return false;
	}
	
	/**
	 * Doubles the size of the array
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
	 * Removes the first element in the array i.e. element 0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean remove() throws EmptyWaitingArrayException {
		if(isEmpty())
			throw new EmptyWaitingArrayException("The array is already empty.");
		loopArray[0] = null;
		E[] tempArray = (E[])new Object[loopArray.length];
		for(int i = 1,a = 0;i<loopArray.length;i++,a++) {
			if(loopArray[i]==null)
				continue;
			tempArray[a] = loopArray[i];
		}
		loopArray = tempArray;
		return true;			
	}
	
	/**
	 * removes all elements from the array
	 */
	@Override
	public void clear() throws EmptyWaitingArrayException
	{
		if(isEmpty())
			throw new EmptyWaitingArrayException("The array is already empty.");
		for(int i = 0;i<loopArray.length;i++)
			loopArray[i] = null;
		
	}
	
	/**
	 * Retrieves the first element in the array i.e. element 0
	 */
	@Override
	public E getFirst() throws EmptyWaitingArrayException {
		if(isEmpty())
			throw new EmptyWaitingArrayException("The array is already empty.");
		return loopArray[0];
	}
	
	/**
	 * Retrieves the last non-null element in the array
	 */
	@Override
	public E getLast() throws EmptyWaitingArrayException {
		if(isEmpty())
			throw new EmptyWaitingArrayException("The array is already empty.");
		E e = null;
		for(int i = loopArray.length-1;i>=0;i--) {
			if(loopArray[i]==null)
				continue;
			e = loopArray[i];
			break;
		}
		return e;
	}
	
	/**
	 * returns true if all the values are null, false if even one is not null
	 * @return Whether or not the array is empty
	 */
	@Override
	public boolean isEmpty() {
		for(E e : loopArray)
			if(e!=null)
				return false;
		return true;
	}
	/**
	 * returns true if none of the values are null, false if even one is null
	 * @return Whether or not the array is full
	 */
	@Override
	public boolean isFull() {
		for(E e : loopArray)
			if(e==null)
				return false;
		return true;
	}
	
	/**
	 * returns a string containing all non-null elements in the array
	 * @return The string
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
	
	/**
	 * Compares two elements and returns either -1, 0, or 1
	 * @param e1 the first element
	 * @param e2 the second element
	 * @return an integer based on the results of the comparison
	 */
	@Override
	public int compare(E e1, E e2) {
		if(e1 instanceof String && e2 instanceof String)
		{
			String str1 = (String)e1;
			String str2 = (String)e2;
			if(str1.compareTo(str2)<0) 
				return -1;
			else if(str1.compareTo(str2)>0)
				return 1;
			else
				return 0;
		}
		else if(e1 instanceof Integer && e2 instanceof Integer){
			int num1 = (Integer)e1;
			int num2 = (Integer)e2;
			if(num1-num2<0)
				return -1;
			else if(num1-num2>0)
				return 1;
			else
				return 0;
		}
		else if(e1 instanceof Double && e2 instanceof Double){
			double num1 = (Double)e1;
			double num2 = (Double)e2;
			if(num1-num2<0)
				return -1;
			else if(num1-num2>0)
				return 1;
			else
				return 0;
		}
		else if(e1 instanceof Float && e2 instanceof Float){
			float num1 = (Float)e1;
			float num2 = (Float)e2;
			if(num1-num2<0)
				return -1;
			else if(num1-num2>0)
				return 1;
			else
				return 0;
		}
		else if(e1 instanceof Long && e2 instanceof Long){
			long num1 = (Long)e1;
			long num2 = (Long)e2;
			if(num1-num2<0)
				return -1;
			else if(num1-num2>0)
				return 1;
			else
				return 0;
		}
		else
			return 0;
	}
	
}


