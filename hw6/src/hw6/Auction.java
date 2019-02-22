package hw6;

import java.io.Serializable;

/**
 * The Auction class represents an active auction currently in the database.
 * Each Auction contains information such as the seller's name, the current bid, the time remaining,
 * the current bidder's name, information about the item, and the unique auction ID.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 *     Recitation #: 06
 */
public class Auction implements Serializable{
	
	public int timeRemaining;
	public double currentBid;
	public String auctionID, sellerName, buyerName, itemInfo;
	
	/**
	 * Constructs an instance of DirectoryNode with the supplied parameters.
	 * If any of the String parameters contain an empty String, they are set as "N/A".
	 * 
	 * @param t
	 *     The timeRemaining of the Auction.
	 * @param c
	 *     The currentBid of the Auction.
	 * @param a
	 *     The auctionID of the Auction.
	 * @param s
	 *     The sellerName of the Auction.
	 * @param b
	 *     The buyerName of the Auction.
	 * @param i
	 *     The itemInfo of the Auction.
	 */
	public Auction(int t, double c, String a, String s, String b, String i) {
		if (s.equals("")) {
			s = "N/A";
		}
		if (a.equals("")) {
			a = "N/A";
		}
		if (b.equals("")) {
			b = "N/A";
		}
		if (i.equals("")) {
			i = "N/A";
		}
		timeRemaining = t;
		currentBid = c;
		auctionID = a;
		sellerName = s.replaceAll("\n", "");
		buyerName = b;
		itemInfo = i;
	}
	
	/**
	 * Returns the time remaining for the auction.
	 * 
	 * @return
	 *     Returns timeRemaining from the Auction.
	 */
	public int getTimeRemaining() {
		return timeRemaining;
	}
	
	/**
	 * Returns the current bid for the auction.
	 * 
	 * @return
	 *     Returns currentBid from the Auction.
	 */
	public double getCurrentBid() {
		return currentBid;
	}
	
	/**
	 * Returns the auction ID for the auction.
	 * 
	 * @return
	 *     Returns auctionID from the Auction.
	 */
	public String getAuctionID() {
		return auctionID;
	}
	
	/**
	 * Returns the name of the seller for the auction.
	 * 
	 * @return
	 *     Returns sellerName from the Auction.
	 */
	public String getSellerName() {
		return sellerName;
	}
	
	/**
	 * Returns the name of the buyer for the auction.
	 * 
	 * @return
	 *     Returns buyerName from the Auction.
	 */
	public String getBuyerName() {
		return buyerName;
	}
	
	/**
	 * Returns information on the item from the auction.
	 * 
	 * @return
	 *     Returns itemInfo from the Auction.
	 */
	public String getItemInfo() {
		return itemInfo;
	}
	
	/**
	 * Decreases the time remaining for this auction by the specified amount.
	 * If time is greater than the current remaining time for the auction,
	 * then the time remaining is set to 0.
	 * 
	 * Postconditions:
	 *     timeRemaining has been decremented by the indicated amount and is greater than or equal to 0.
	 *     
	 * @param time
	 *     The value to decrease timeRemaining by.
	 */
	public void decrementTimeRemaining(int time) {
		timeRemaining -= time;
		if (timeRemaining < 0) {
			timeRemaining = 0;
		}
	}
	
	/**
	 * Makes a new bid on this auction.
	 * If bidAmt is larger than currentBid,
	 * then the value of currentBid is replaced by bidAmt and buyerName is is replaced by bidderName.
	 * 
	 * Preconditions:
	 *     The auction is not closed.
	 *     
	 * Postconditions:
	 *     currentBid Reflects the largest bid placed on this object.
	 *     If the auction is closed, throw a ClosedAuctionException.
	 *     
	 * @param bidderName
	 *     The name of the bidder.
	 * @param bidAmt
	 *     The bid amount.
	 *     
	 * @throws ClosedAuctionException
	 *     Indicates that the auction is closed and no more bids can be placed.
	 * @throws BidTooSmallException
	 *     Indicates that the bid is smaller than the current bid.
	 */
	public void newBid(String bidderName, double bidAmt) throws ClosedAuctionException, BidTooSmallException {
		if (timeRemaining == 0) {
			throw new ClosedAuctionException(auctionID);
		}
		if (bidAmt > currentBid) {
			currentBid = bidAmt;
			buyerName = bidderName;
		}
		else {
			throw new BidTooSmallException();
		}
	}
	
	/**
	 * Returns a string of the data members in tabular form.
	 * 
	 * @return
	 *     Returns a String representation of all member variables in Auction.
	 */
	public String toString() {
		String bid = String.format("$%9.2f", currentBid);
		if (currentBid == 0) {
			bid = "       N/A";
		}
		return String.format("%11s | %s | %-22s| %-24s| %3d hours | %.42s",
		  auctionID, bid, sellerName, buyerName, timeRemaining, itemInfo);
	}

}
