package hw3;
/**
 * The Complexity class represents the Big-Oh complexity of some block of code.
 * The Big-Oh notation will be limited to the powers of two base types: n, and log(n).
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 */

public class Complexity {

	public int nPower;
	public int logPower;
	
	/**
	 * Constructs an instance of Complexity with the supplied parameters.
	 * 
	 * Postcondition:
	 *     This Complexity has been initialized with the supplied parameters.
	 *
	 * @param n
	 *     The power of n of the complexity.
	 * @param l
	 *     The power of log(n) of the complexity.
	 */
	public Complexity(int n, int l) {
		setNPower(n);
		setLogPower(l);
	}
	
	/**
	 * Returns the current power of n of the complexity.
	 * 
	 * @return
	 *     Returns the nPower for this Complexity.
	 */
	public int getNPower() {
		return nPower;
	}
	
	/**
	 * Returns the current power of log(n) of the complexity.
	 * 
	 * @return
	 *     Returns the logPower for this Complexity.
	 */
	public int getLogPower() {
		return logPower;
	}
	
	/**
	 * Sets the nPower of the Complexity.
	 * 
	 * @param n
	 *     The power of n to set.
	 */
	public void setNPower(int n) {
		nPower = n;
	}
	
	/**
	 * Sets the logPower of the Complexity.
	 * 
	 * @param l
	 *     The power of log(n) to set.
	 */
	public void setLogPower(int l) {
		logPower = l;
	}
	
	/**
	 * Returns a human-readable Big-Oh notation of the Complexity.
	 * 
	 * @return
	 *     Returns a string of the Big-Oh notation for this Complexity.
	 */
	public String toString() {
		String nString = "n^" + nPower;
		String logString = "log(n)^" + logPower;
		if (nPower == 1)
			nString = "n";
		if (logPower == 1)
			logString = "log(n)";
		if (nPower == 0 && logPower == 0)
			return "O(1)";
		else if (nPower != 0 && logPower == 0)
			return "O(" + nString + ")";
		else if (nPower == 0 && logPower != 0)
			return "O(" + logString + ")";
		else
			return "O(" + nString + "*" + logString + ")";
	}
	
	/**
	 * Compares this Complexity with another Complexity object.
	 * If this Complexity object is more complex, 'true' will be returned, otherwise 'false'.
	 * 
	 * @param x
	 *     The Complexity object to be compared to.
	 * @return
	 *     Returns a boolean indicating if this Complexity is more complex than a specified Complexity object.
	 */
	public boolean compareComplexity(Complexity x) {
		if (nPower > x.getNPower())
			return true;
		else if (nPower == x.getNPower() && logPower >= x.getLogPower())
			return true;
		else
			return false;
	}
}
