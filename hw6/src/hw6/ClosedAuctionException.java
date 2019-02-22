package hw6;

/**
 * The ClosedAuctionException class extends the Java API Exception class.
 * It is a throwable exception, indicating that the Auction the user is trying to perform an operation on, is full.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 *     Reception #: 06
 */
public class ClosedAuctionException extends Exception {
	
	/**
	 * Constructs an instance of ClosedAuctionException with the message "Auction 'auctionID' is CLOSED."
	 * 
	 * Postcondition:
	 *     This CongestedNetworkException has been initialized with the supplied parameters.
	 *     
	 * @param auctionID
	 *     The ID of the Auction.
	 */
	public ClosedAuctionException(String auctionID) {
		super("Auction " + auctionID + " is CLOSED.");
	}

}
