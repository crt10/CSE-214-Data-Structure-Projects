package hw6;

import java.io.Serializable;
import java.util.Hashtable;
import big.data.*;

/**
 * The AuctionTable class implements a Hashtable from the Java API.
 * The auctionID will act as the key for the corresponding Auction objects.
 * It represents a table of Auction(s) and provides methods for insertion and removal.
 * A method for importing Auctions from a .xml file is provided.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 *     Recitation #: 06
 */
public class AuctionTable extends Hashtable<String, Auction> implements Serializable{
	
	/**
	 * Uses the BigData library to construct an AuctionTable from a remote data source.
	 * 
	 * Preconditions:
	 *     URL represents a data source which can be connected to using the BigData library.
	 *     The data source has proper syntax.
	 *     
	 * @param URL
	 *     String representing the URL for the remote data source.
	 *     
	 * @return
	 *     Returns an AuctionTable with Auction objects representing the auctions found in the URL.
	 *     
	 * @throws IllegalArgumentException
	 *     Indicates that the URL does not represent a valid datasource.
	 */
	public static AuctionTable buildFromURL(String URL) throws IllegalArgumentException {
		try {
			DataSource ds = DataSource.connect(URL).load();
			String[] sellerName = ds.fetchStringArray("listing/seller_info/seller_name");
			String[] bids = ds.fetchStringArray("listing/auction_info/current_bid");
			Double[] currentBid = new Double[bids.length];
			for (int i = 0; i < bids.length; i++) {
				currentBid[i] = Double.parseDouble(bids[i].replaceAll("[$ ,]", ""));
			}
			String[] timeLeft = ds.fetchStringArray("listing/auction_info/time_left");
			int[] timeRemaining = new int[timeLeft.length];
			for (int i = 0; i < timeLeft.length; i++) {
				int days = 0;
				int hours = 0;
				String[] time = timeLeft[i].split(" ");
				for (int o = 0; o < time.length; o++) {
					if (time[o].contains("day")) {
						days = Integer.parseInt(time[o-1]);
					}
					if (time[o].contains("hour")||time[o].contains("hr")) {
						hours = Integer.parseInt(time[o-1]);
					}
				}
				timeRemaining[i] = days*24 + hours;
			}
			String[] auctionID = ds.fetchStringArray("listing/auction_info/id_num");
			String[] buyerName = ds.fetchStringArray("listing/auction_info/high_bidder/bidder_name");
			String[] memory = ds.fetchStringArray("listing/item_info/memory");
			String[] hardDrive = ds.fetchStringArray("listing/item_info/hard_drive");
			String[] cpu = ds.fetchStringArray("listing/item_info/cpu");
			String[] itemInfo = new String[memory.length];
			for (int i = 0; i < itemInfo.length; i++) {
				itemInfo[i] = String.format("%s %s %s", cpu[i], memory[i], hardDrive[i]);
			}
			AuctionTable auctions = new AuctionTable();
			for (int i = 0; i < auctionID.length; i++) {
				auctions.put(auctionID[i], new Auction(timeRemaining[i], currentBid[i], auctionID[i], sellerName[i], buyerName[i], itemInfo[i]));
			}
			return auctions;
		} catch (Exception e) {
			throw new IllegalArgumentException("The given URL can not be connected to.");
		}
	}
	
	/**
	 * Manually posts an auction, and add it into the table.
	 * 
	 * @param auctionID
	 *     The unique key for this object.
	 * @param auction
	 *     The auction to insert into the table with the corresponding auctionID.
	 *     
	 * @throws IllegalArgumentException
	 *     Indicates that the given auctionID is already stored in the table.
	 */
	public void putAuction(String auctionID, Auction auction) throws IllegalArgumentException {
		if (containsKey(auctionID)) {
			throw new IllegalArgumentException("Auction " + auctionID + " already exists.");
		}
		put(auctionID, auction);
	}
	
	/**
	 * Get the information of an Auction that contains the given ID as key.
	 * 
	 * @param auctionID
	 *     The unique key for this object.
	 *     
	 * @return
	 *     An Auction object with the given key, null otherwise.
	 */
	public Auction getAuction(String auctionID) {
		return get(auctionID);
	}
	
	/**
	 * Simulates the passing of time.
	 * Decrease the timeRemaining of all Auction objects by the amount specified. The value cannot go below 0.
	 * 
	 * Postconditions:
	 *     All Auctions in the table have their timeRemaining timer decreased.
	 *     If the original value is less than the decreased value, set the value to 0.
	 *     
	 * @param numHours
	 *     The number of hours to decrease the timeRemaining value by.
	 * 
	 * @throws IllegalArgumentException
	 *     Indicates that the given numHours is non positive.
	 */
	public void letTimePass(int numHours) throws IllegalArgumentException {
		if (numHours < 0) {
			throw new IllegalArgumentException("The number of hours can not be negative.");
		}
		for (Auction a: values()) {
			a.decrementTimeRemaining(numHours);
		}
	}
	
	/**
	 * Iterates over all Auction objects in the table and removes them if they are expired.
	 * 
	 * Postconditions:
	 *     Only open Auction remain in the table.
	 */
	public void removeExpiredAuctions() {
		Auction[] expiredAuction = new Auction[size()];
		int x = 0;
		for (Auction a: values()) {
			if (a.getTimeRemaining() == 0) {
				expiredAuction[x] = a;
			}
			x++;
		}
		for (x = 0; x < expiredAuction.length; x++) {
			if (expiredAuction[x] != null) {
				remove(expiredAuction[x].getAuctionID());
			}
		}
	}
	
	/**
	 * Prints the AuctionTable in tabular form.
	 * 
	 * Postconditions:
	 *     A tabular form of this AuctionTable has been printed.
	 */
	public void printTable() {
		String table = "\n Auction ID |    Bid     |        Seller         |"
		  + "          Buyer          |    Time   | Item Info\n";
		for (int i = 0; i < 131; i++) {
			table += "=";
		}
		table += "\n";
		for (Auction a: values()) {
			table += a + "\n";
		}
		System.out.println(table.substring(0,table.length()-1));
	}

}
