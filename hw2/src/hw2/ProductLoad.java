package hw2;
/**
 * The ProductLoad class contains information on the load such as
 * the product name, weight, value, and its danger level.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 */

public class ProductLoad {

	public String name;
	public double weight;
	public double value;
	public boolean danger;
	
	/**
	 * Constructs an instance of ProductLoad with the supplied parameters.
	 * 
	 * Postcondition:
	 *     This ProductLoad has been initialized with the supplied parameters.
	 *     
	 * @param n
	 *     The name of the load.
	 * @param w
	 *     The weight of the load.
	 * @param v
	 *     The value of the load.
	 * @param d
	 *     Whether the load is dangerous or not.
	 */
	public ProductLoad(String n, double w, double v, boolean d) {
		setName(n);
		setWeight(w);
		setValue(v);
		setDanger(d);
	}
	
	/**
	 * Sets the name for the ProductLoad.
	 * 
	 * @param n
	 *     The name to set.
	 */
	public void setName(String n) {
		name = n;
	}
	
	/**
	 * Sets the weight for the ProductLoad.
	 * 
	 * @param w
	 *     The weight to set.
	 *     
	 * @exception IllegalArgumentException
	 *     Indicates that weight can not be zero or negative.
	 */
	public void setWeight(double w) {
		if (w < 0)
			throw new IllegalArgumentException("\nWeight can not be negative or zero.\n");
		else
		{
			weight = w;
		}
	}
	
	/**
	 * Sets the value for the ProductLoad.
	 * 
	 * @param v
	 *     The value to set.
	 * 
	 * @exception IllegalArgumentException
	 *     Indicates that the value can not be zero or negative.
	 */
	public void setValue(double v) {
		if (v < 0)
			throw new IllegalArgumentException("\nValue can not be negative\n");
		else
			value = v;
	}
	
	/**
	 * Sets whether the ProductLoad is dangerous or not.
	 * 
	 * @param d
	 *     The level of danger to set.
	 */
	public void setDanger(boolean d) {
		danger = d;
	}
	
	/**
	 * Returns the current name of the ProductLoad.
	 * 
	 * @return
	 *     returns the name of the ProductLoad.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the current weight of the ProductLoad.
	 * 
	 * @return
	 *     returns the weight of the ProductLoad.
	 */
	public double getWeight() {
		return weight;
	}
	
	/**
	 * Returns the current value of the ProductLoad.
	 * 
	 * @return
	 *     returns the value of the ProductLoad.
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * Returns whether the ProductLoad is dangerous or not.
	 * 
	 * @return
	 *     returns the level of danger of the ProductLoad.
	 */
	public boolean getDanger() {
		return danger;
	}
}
