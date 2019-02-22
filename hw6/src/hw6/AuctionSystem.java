package hw6;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The AuctionSystem class contains a main method will allow the user to interact with the database by
 * listing open auctions, make bids on open auctions, and create new auctions for different items.
 * On startup, the AuctionSystem should check to see if the file auctions.obj exists in the current directory.
 * If it does, then the file should be loaded and deserialized into an AuctionTable for new auctions/bids.
 * If the file does not exist, an empty AuctionTable object should be created and used instead.
 * Next, the user should be prompted to enter a username to access the system.
 * This is the name that will be used to create new auctions and bid on the open auctions available in the table.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 *     Recitation #: 06
 */
public class AuctionSystem implements Serializable{

	public static void main(String[] args) throws IOException{
		System.out.println("Starting...");
		AuctionTable auctions;
		Scanner kb = new Scanner(System.in);
		String input = "";
		try {
			FileInputStream file = new FileInputStream("auction.obj");
			ObjectInputStream inStream = new ObjectInputStream(file);
			auctions = (AuctionTable)inStream.readObject();
			inStream.close();
			System.out.println("Loading previous Auction Table...");
		} catch (Exception e) {
			System.out.println("No previous auction table detected.");
			auctions = new AuctionTable();
			System.out.println("Creating new table...");
		}
		System.out.print("\nPlease enter a username: ");
		String user = kb.nextLine();
		while (!input.equals("q")) {
			String auctionID = "";
			Auction auction = null;
			System.out.println("\nMenu:\n    (D) - Import Data from URL\n"
			  + "    (A) - Create a New Auction\n    (B) - Bid on an Item\n"
			  + "    (I) - Get Info on Auction\n    (P) - Print All Auctions\n"
			  + "    (R) - Remove Expired Auctions\n    (T) - Let Time Pass\n"
			  + "    (Q) - Quit\n");
			System.out.print("Please select an option: ");
			input = kb.nextLine().toLowerCase();
			try {
				switch (input) {
					case "d":
						System.out.print("Please enter a URL: ");
						auctions = AuctionTable.buildFromURL(kb.nextLine());
						System.out.println("\nLoading...");
						System.out.println("Auction data loaded successfully!");
						break;
					case "a":
						System.out.println("\nCreating new Auction as " + user + ".");
						System.out.print("Please enter an Auction ID: ");
						auctionID = kb.nextLine();
						System.out.print("Please enter an Auction time (hours): ");
						int timeRemaining = kb.nextInt();
						kb.nextLine();
						System.out.print("Please enter some Item Info: ");
						String itemInfo = kb.nextLine();
						auctions.putAuction(auctionID, new Auction(timeRemaining, 0, auctionID, user, "", itemInfo));
						System.out.println("\nAuction " + auctionID + " inserted into the table.");
						break;
					case "b":
						System.out.print("Please enter an Auction ID: ");
						auctionID = kb.nextLine();
						auction = auctions.getAuction(auctionID);
						if (auction != null) {
							if (auction.getTimeRemaining() != 0) {
								System.out.println("\nAuction " + auctionID + " is OPEN.");
								String currentBid = String.format("$ %.2f", auction.getCurrentBid());
								if (auction.getCurrentBid() == 0) {
									currentBid = "None";
								}
								System.out.println("    Current Bid: " + currentBid);
								System.out.print("\nWhat would you like to bid?: ");
								Double bid = kb.nextDouble();
								if (bid < 0) {
									throw new IllegalArgumentException("Your bid can not be negative.");
								}
								kb.nextLine();
								auction.newBid(user, bid);
								System.out.println("Bid accepted.");
							}
							else {
								System.out.println("\nAuction " + auctionID + " is CLOSED.");
								String currentBid = String.format("$ %.2f", auction.getCurrentBid());
								if (auction.getCurrentBid() == 0) {
									currentBid = "None";
								}
								System.out.println("    Current Bid: " + currentBid);
								System.out.println("\nYou can no longer bid on this item.");
							}
						}
						else {
							System.out.println("\nAuction " + auctionID + " was not found.");
						}
						break;
					case "i":
						System.out.print("Please enter an Auction ID: ");
						auctionID = kb.nextLine();
						auction = auctions.getAuction(auctionID);
						if (auction != null) {
							System.out.println(String.format("%nAuction %s:%n    "
							  + "Seller: %s%n    Buyer: %s%n    Time: %d hours%n"
							  + "    Info: %s", auctionID, auction.getSellerName(), auction.getBuyerName(), 
							  auction.getTimeRemaining(), auction.getItemInfo()));
						}
						else {
							System.out.println("\nAuction " + auctionID + " was not found.");
						}
						break;
					case "p":
						auctions.printTable();
						break;
					case "r":
						System.out.println("\nRemoving expired auctions...");
						auctions.removeExpiredAuctions();
						System.out.println("All expired auctions removed.");
						break;
					case "t":
						System.out.print("How many hours should pass: ");
						int time = kb.nextInt();
						kb.nextLine();
						auctions.letTimePass(time);
						System.out.println("\nTime passing...");
						System.out.println("Auction times updated.");
						break;
					case "q":
						FileOutputStream file = new FileOutputStream("auction.obj");
						ObjectOutputStream outStream = new ObjectOutputStream(file);
						System.out.println("\nWriting Auction Table to file...");
						outStream.writeObject(auctions);
						System.out.println("Done!");
						outStream.close();
						kb.close();
						break;
					default:
						System.out.println("Please select a valid option");
				}
			} catch (InputMismatchException e) {
				kb.nextLine();
				System.out.println("\nPlease enter in appropriate parameters");
			} catch (ClosedAuctionException e) {
				System.out.println("\n" + e.getMessage());
			}catch (BidTooSmallException e) {
				System.out.println("\n" + e.getMessage());
			} catch (IllegalArgumentException e) {
				System.out.println("\n" + e.getMessage());
			}
		}
		System.out.println("\nGoodbye.");
	}

}
