package hw4;

/**
 * The CongestedNetworkExeption class extends the Java API Exception class.
 * It is a throwable exception, indicating that there is no more space for another packet in a collection of Router(s).
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 */
public class CongestedNetworkException extends Exception {
	
	/**
	 * Constructs an instance of CongestedNetworkException with the message "Network is congested. "
	 * 
	 * Postcondition:
	 *     This CongestedNetworkException has been initialized.
	 */
	public CongestedNetworkException() {
        super ("Network is congested. ");
    }
}
