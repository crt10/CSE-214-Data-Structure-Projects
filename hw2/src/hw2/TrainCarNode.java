package hw2;
/**
 * The TrainCarNode class wraps an instance of TrainCar,
 * providing methods for navigating between other TrainCar objects.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 */

public class TrainCarNode {

	public TrainCarNode prev;
	public TrainCarNode next;
	public TrainCar car;
	
	/**
	 * Constructs an instance of TrainCarNode with no parameters.
	 * 
	 * Postcondition:
	 *     This TrainCarNode has been initialized with its variables set to null.
	 */
	public TrainCarNode() {
		prev = null;
		next = null;
		car = null;
	}
	
	/**
	 * Constructs and instance of TrainCarNode with the supplied parameters.
	 * 
	 * Postcondition:
	 *     This TrainCarNode has been initialized with the supplied parameters.
	 *     
	 * @param c
	 *     The TrainCar of the TrainCarNode.
	 */
	public TrainCarNode(TrainCar c) {
		prev = null;
		next = null;
		setCar(c);
	}
	
	/**
	 * Sets the previous node for the current TrainCarNode.
	 * 
	 * @param c
	 *     The TrainCarNode to set.
	 */
	public void setPrev(TrainCarNode c) {
		prev = c;
	}
	
	/**
	 * Sets the next node for the current TrainCarNode.
	 * 
	 * @param c
	 *     The TrainCarNode to set.
	 */
	public void setNext(TrainCarNode c) {
		next = c;
	}
	
	/**
	 * Sets the car to wrap for the current TrainCarNode.
	 * 
	 * @param c
	 *     The TrainCar to set.
	 */
	public void setCar(TrainCar c) {
		car = c;
	}
	
	/**
	 * Returns the previous node for the TrainCarNode.
	 * 
	 * @return
	 *     Returns the previous TrainCarNode for the current TrainCarNode.
	 */
	public TrainCarNode getPrev() {
		return prev;
	}
	
	/**
	 * Returns the next node for the TrainCarNode.
	 * 
	 * @return
	 *     Returns the next TrainCarNode for the current TrainCarNode.
	 */
	public TrainCarNode getNext() {
		return next;
	}
	
	/**
	 * Returns the current car for the TrainCarNode.
	 * 
	 * @return
	 *     Returns the TrainCar of the TrainCarNode.
	 */
	public TrainCar getCar() {
		return car;
	}
}
