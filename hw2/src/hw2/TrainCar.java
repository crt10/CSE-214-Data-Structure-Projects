package hw2;
/**
 * The TrainCar class contains general information such as its length and weight.
 * An instance of TrainCar can contain a ProductLoad
 * TrainCar objects can check if they are carrying a load or not.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 */

public class TrainCar {

	public double carLength;
	public double carWeight;
	public ProductLoad load;
	
	/**
	 * Constructs an instance of TrainCar with the supplied parameters
	 * 
	 * Postcondition:
	 *     This TrainCar has been initialized with the supplied parameters.
	 * 
	 * @param l
	 *     The length of the car.
	 * @param w
	 *     The weight of the car.
	 *     
	 * @exception IllegalArgumentException
	 *     Indicates that the length and the weight of the car can not be zero or negative.
	 */
	public TrainCar(double l, double w) {
		if (l <= 0)
			throw new IllegalArgumentException("\nLength can not be negative or zero.\n");
		carLength = l;
		if (w <= 0)
			throw new IllegalArgumentException("\nWeight can not be negative or zero.\n");
		carWeight = w;
		load = null;
	}
	
	/**
	 * Sets the load for the TrainCar.
	 * 
	 * @param p
	 *     The ProductLoad to set.
	 */
	public void setProductLoad(ProductLoad p) {
		load = p;
	}
	
	/**
	 * Returns the current load for the TrainCar.
	 * 
	 * @return
	 *     Returns the ProductLoad for the TrainCar.
	 */
	public ProductLoad getProductLoad() {
		return load;
	}
	
	/**
	 * Returns the current length for the TrainCar.
	 * 
	 * @return
	 *     Returns the carLength of the TrainCar.
	 */
	public double getCarLength() {
		return carLength;
	}
	
	/**
	 * Returns the current weight for the TrainCar.
	 * 
	 * @return
	 *     Returns the carWeight of the TrainCar.
	 */
	public double getCarWeight() {
		return carWeight;
	}
	
	/**
	 * Returns whether the TrainCar contains an instance of ProductLoad.
	 * 
	 * @return
	 *    Returns a boolean indicating if TrainCar contains a ProductLoad object.
	 */
	public boolean isEmpty() {
		return (load == null);
	}
}
