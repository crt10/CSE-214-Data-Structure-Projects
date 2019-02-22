package hw6;

/**
 * The BidTooSmallException class extends the Java API Exception class.
 * It is a throwable exception, indicating that the user's bid is less than the current bid for an Auction object.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 *     Reception #: 06
 */
public class BidTooSmallException extends Exception {
	
	/**
	 * Constructs an instance of BidTooSmallException with the message "Your bid was less than or equal to the current bid. Bid was not accepted."
	 * 
	 * Postcondition:
	 *     This BidTooSmallException has been initialized with the supplied parameters.
	 */
	public BidTooSmallException() {
		super("Your bid was less than or equal to the current bid.\nBid was not accepted.");
	}

}
