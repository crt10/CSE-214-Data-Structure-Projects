package hw2;
/**
 * The TrainLinkedList class implements a Double-Linked list ADT using TrainCarNode(s).
 * TrainLinkedList contains references to the head, cursor and tail of the list.
 * Methods to perform insertion, deletion and search are implemented.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 */

public class TrainLinkedList {

	public TrainCarNode head;
	public TrainCarNode tail;
	public TrainCarNode cursor;
	public int size;
	public double length;
	public double value;
	public double weight;
	public boolean danger;
	public int dangerCount;
	
	/**
	 * Constructs an instance of the TrainLinkedList with no TrainCar objects in it.
	 * 
	 * Postconditions:
	 *     This TrainLinkedList has been initialized to an empty linked list.
	 *     Head, tail, and cursor are all set to null.
	 */
	/*public TrainLinkedList() {
		head = null;
		tail = null;
		cursor = null;
		size = 0;
		length = 0;
		value = 0;
		weight = 0;
		danger = false;
		dangerCount = 0;
	}*/
	
	/**
	 * Returns a reference to the TrainCar at the node currently referenced by the cursor.
	 * 
	 * Precondition:
	 *     The list is not empty (cursor is not null).
	 *     
	 * @return
	 *     The reference to the TrainCar at the node currently referenced by the cursor.
	 */
	public TrainCar getCursorData() {
		return cursor.getCar();
	}
	
	/**
	 * Places car in the node currently referenced by the cursor.
	 * 
	 * Precondition:
	 *     The list is not empty (cursor is not null)
	 *     
	 * @param car
	 *     The cursor node now contains a reference to car as its data.
	 */
	public void setCursorData(TrainCar car) {
		cursor.setCar(car);
	}
	
	/**
	 * Moves the cursor to point at the next TrainCarNode.
	 * 
	 * Precondition:
	 *     The list is not empty (cursor is not null) and cursor does not currently reference the tail of the list.
	 *     
	 * Postcondition:
	 *     The cursor has been advanced to the next TrainCarNode, or has remained at the tail of the list.
	 */
	public void cursorForward() {
		cursor = cursor.getNext();
		if (cursor != null)
		{
			if (cursor.getNext() == null)
				tail = cursor;
			if (cursor.getPrev() == null)
				head = cursor;
		}
	}
	
	/**
	 * Moves the cursor to point at the previous TrainCarNode.
	 * 
	 * Precondition:
	 *     The list is not empty (cursor is not null) and the cursor does not currently reference the head of the list.
	 *     
	 * Postcondition:
	 *     The cursor has been moved back to the previous TrainCarNode, or has remained at the head of the list.
	 */
	public void cursorBackward() {
		cursor = cursor.getPrev();
		if (cursor != null)
		{
			if (cursor.getPrev() == null)
				head = cursor;
			if (cursor.getNext() == null)
				tail = cursor;
		}
	}
	
	/**
	 * Inserts a car into the train after the cursor position.
	 * 
	 * Precondition:
	 *     This TrainCar object has been instantiated
	 *     
	 * Postconditions:
	 *     The new TrainCar has been inserted into the train after the position of the cursor.
	 *     All TrainCar objects previously on the train are still on the train, and their order has been preserved.
	 *     The cursor now points to the inserted car.
	 *     
	 * @param newCar
	 *     the new TrainCar to be inserted into the train.
	 *     
	 * @exception IllegalArgumentException
	 *     Indicates that newCar is null.
	 */
	public void insertAfterCursor(TrainCar newCar) {
		if (newCar == null)
			throw new IllegalArgumentException("The car does not exist");
		else
		{
			TrainCarNode newNode = new TrainCarNode();
			if (size == 0)
			{
				cursor = newNode;
				head = cursor;
				tail = cursor;
			}
			else
			{
				newNode.setPrev(cursor);
				newNode.setNext(cursor.getNext());
				if (cursor != tail) 
				{
					cursor.getNext().setPrev(newNode);
				}
				cursor.setNext(newNode);
				cursorForward();
			}
			setCursorData(newCar);
			size++;
			length += getCursorData().getCarLength();
			weight += getCursorData().getCarWeight();
		}
	}
	
	/**
	 * Removes the TrainCarNode referenced by the cursor and returns the TrainCar contained within the node.
	 * 
	 * Precondition:
	 *     The cursor is not null.
	 * 
	 * Postconditions:
	 *     The TrainCarNode referenced by the cursor has been removed from the train.
	 *     The cursor now references the next node, or the previous node if no next node exists.
	 * 
	 * @return
	 *     Returns the TrainCar to be removed.
	 */
	public TrainCar removeCursor() {
		TrainCar c = getCursorData();
		length -= c.getCarLength();
		weight -= c.getCarWeight();
		if (!c.isEmpty())
		{
			value -= c.getProductLoad().getValue();
			weight -= c.getProductLoad().getWeight();
			if (getCursorData().getProductLoad().getDanger())
			{
				dangerCount--;
				if (dangerCount == 0)
					danger = false;
			}
		}
		if (cursor != tail && cursor != head)
		{
			cursor.getNext().setPrev(cursor.getPrev());
			cursor.getPrev().setNext(cursor.getNext());
			cursorForward();
		}
		else if (cursor == tail && cursor != head)
		{
			cursor.getPrev().setNext(cursor.getNext());
			cursorBackward();
		}
		else if (cursor == head && cursor != tail)
		{
			cursor.getNext().setPrev(cursor.getPrev());
			cursorForward();
		}
		else
		{
			cursor = null;
			head = null;
			tail = null;
		}
		size--;
		return c;
	}
	
	/**
	 * Determines the number of TrainCar objects currently on the train.
	 * 
	 * @return
	 *     The number of TrainCar objects on this train.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Returns the total length of the train in meters.
	 * 
	 * @return
	 *     The sum of the lengths of each TrainCar in the train.
	 */
	public double getLength() {
		return length;
	}
	
	/**
	 * Returns the total value of product carried by the train.
	 * 
	 * @return
	 *     The sum of the values of each TrainCar in the train.
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * Returns the total weight in tons of the train.
	 * Note that the weight of the train is the sum of the weights of each empty TrainCar,
	 * plus the weight of the ProductLoad carried by that car.
	 * 
	 * @return
	 *     The sum of the weight of each TrainCar plus the sum of the ProductLoad carried by that car.
	 */
	public double getWeight() {
		return weight;
	}
	
	/**
	 * Whether or not there is a dangerous product on one of the TrainCar objects on the train.
	 * 
	 * @return
	 *     Returns true if the train contains at least one TrainCar carrying a dangerous ProductLoad, false otherwise.
	 */
	public boolean isDangerous() {
		return danger;
	}
	
	/**
	 * Searches the list for all ProductLoad objects with the indicated name and sums together their weight and value
	 * (Also keeps track of whether the product is dangerous or not),
	 * then prints a single ProductLoad record to the console.
	 * 
	 * @param name
	 *     the name of the ProductLoad to find on the train.
	 */
	public void findProduct(String name) {
		double weight = 0;
		double value = 0;
		String danger = "NO";
		boolean found = false;
		TrainCarNode tempCursor = cursor;
		for (cursor = head; cursor != null; cursorForward()) {
			if (getCursorData().getProductLoad() != null)
				if (getCursorData().getProductLoad().getName().equals(name))
				{
					found = true;
					weight += getCursorData().getProductLoad().getWeight();
					value += getCursorData().getProductLoad().getValue();
					if (getCursorData().getProductLoad().getDanger())
						danger = "YES";
				}
		}
		cursor = tempCursor;
		if (found)
		{
			System.out.println(String.format("%n%-15s%-15s%-15s%-15s","Name","Weight (t)"
			  ,"Value ($)","Dangerous"));
			System.out.println("============================================================");
			System.out.println(String.format("%-15s%-15.1f%-15.2f%-15s%n",name,weight,value,danger));
		}
		else
			System.out.println("\nNo record of " + name + " on board train.\n");
	}
	
	/**
	 * Prints a neatly formatted table of the car number, car length, car weight,
	 * load name, load weight, load value, and load dangerousness for all of the car on the train.
	 */
	public void printManifest() {
		int num = 0;
		TrainCarNode tempCursor = cursor;
		String pointer = "";
		System.out.println(String.format("%n    %-37s%-60s","Car:","Load:"));
		System.out.println(String.format("    %-7s%-15s%-15s|%-15s%-15s%-15s%-15s"
		  ,"Num","Length (m)","Weight (t)","Name","Weight (t)","Value ($)","Dangerous"));
		System.out.println("    ==============================================="
		  + "==================================================");
		for (cursor = head; cursor != null; cursorForward()) {
			num++;
			String name = "EMPTY";
			String danger = "NO";
			double weight = 0;
			double value = 0;
			if (!getCursorData().isEmpty())
			{
				name = getCursorData().getProductLoad().getName();
				weight = getCursorData().getProductLoad().getWeight();
				value = getCursorData().getProductLoad().getValue();
				if (getCursorData().getProductLoad().getDanger())
				{
					danger = "YES";
				}
			}
			if (tempCursor == cursor)
				pointer = " ->";
			else
				pointer = "";
			System.out.println(String.format("%-4s%-7d%-15.1f%-16.1f%-15s%-15.1f%-15.2f%-15s"
			  ,pointer,num,getCursorData().getCarLength(),getCursorData().getCarWeight()
			  ,name,weight,value,danger));
		}
		cursor = tempCursor;
		System.out.println("\n");
	}
	
	/**
	 * Removes all dangerous cars from the train, maintaining the order of the cars in the train.
	 * 
	 * Postconditions:
	 *     All dangerous cars have been removed from this train.
	 *     The order of all non-dangerous cars must be maintained upon the completion of this method.
	 */
	public void removeDangerousCars() {
		cursor = head;
		while (cursor != null) {
			if (getCursorData().getProductLoad() != null)
			{
				if (getCursorData().getProductLoad().getDanger())
				{
					removeCursor();
					if (cursor == null)
						break;
				}
				else
					cursorForward();
			}
			else
				cursorForward();
		}
		if (cursor == null)
			cursor = tail;
	}
	
	/**
	 * Returns a neatly formatted String representation of the train.
	 * 
	 * @return
	 *     A neatly formatted string containing information about the train,including it's size (number of cars),
	 *     length in meters, weight in tons, value in dollars, and whether it is dangerous or not.
	 */
	public String toString() {
		String danger = "not dangerous";
		if (isDangerous())
			danger = "DANGEROUS";
		return String.format("%nTrain: %d cars, %.1f meters, %.1f tons, %.2f value,"
		  + " %s.%n",size(),getLength(),getWeight(),getValue(),danger);
	}
}
