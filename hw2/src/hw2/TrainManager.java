package hw2;
/**
 * TrainManager runs a menu driven application which first creates an empty TrainLinkedList object.
 * The program prompts the user for a command to execute an operation.
 * Once a command has been chosen, the program may ask the user for additional information if necessary,
 * and performs the operation.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class TrainManager {

	public static void main(String[] args) {
		TrainLinkedList train = new TrainLinkedList(); //The train to manage
		double length = 0;
		double weight = 0;
		ProductLoad load = null;
		String danger = "";
		boolean dangerous = false;
		String name = "";
		double value = 0;
		Scanner kb = new Scanner(System.in);
		String input = ""; //User input
		 while (!input.equalsIgnoreCase("Q")) {
			System.out.println("(F) Cursor Forward\n(B) Cursor Backward\n"
			  + "(I) Insert Car After Cursor\n(R) Remove Car At Cursor\n"
			  + "(L) Set Product Load\n(S) Search for Product\n"
			  + "(T) Display Train\n(M) Display Manifest\n(D) Remove Dangerous Cars\n"
			  + "(Q) Quit");
			System.out.print("\nEnter a selection: ");
			input = kb.nextLine(); //Takes in the next user selection
			switch (input.toLowerCase()) {
			case "f": //Moves the cursor forward
				if (train.cursor == train.tail)
					System.out.println("\nNo next car, cannot move forward.\n");
				else
				{
					train.cursorForward();
					System.out.println("\nCursor moved forward\n");
				}
				break;
			case "b": //Moves the cursor backwards
				if (train.cursor == train.head)
					System.out.println("\nNo previous car, cannot move backwards.\n");
				else
				{
					train.cursorBackward();
					System.out.println("\nCursor moved backward\n");
				}
				break;
			case "i": //Inserts a car after the cursor
				try {
					System.out.print("\nEnter car length in meters: ");
					length = kb.nextDouble();
					System.out.print("Enter car weight in tons: ");
					weight = kb.nextDouble();
					kb.nextLine();
					train.insertAfterCursor(new TrainCar(length,weight));
					System.out.println(String.format("%nNew Train car %.1f meters"
					  + " %.1f tons inserted into train.%n",length,weight));
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				} catch (InputMismatchException e) {
					System.out.println("\nPlease enter in a proper value.\n");
					kb.nextLine();
				}
				break;
			case "r": //Removes the car at the cursor
				if (train.cursor == null)
					System.out.println("\nThere is no car in the train to remove.\n");
				else
				{
					danger = "NO";
					name = "EMPTY";
					weight = 0;
					value = 0;
					load = train.removeCursor().getProductLoad();
					System.out.println("\nCar successfully unlinked. The following"
					  + " load has been removed from the train: ");
					System.out.println(String.format("%-15s%-15s%-15s%-15s","Name","Weight (t)"
					  ,"Value ($)","Dangerous"));
					System.out.println("============================================================");
					if (load != null)
					{
						if (load.getDanger())
							danger = "YES";
						name = load.getName();
						weight = load.getWeight();
						value = load.getValue();
					}
					System.out.println(String.format("%-15s%-15.1f%-15.2f%-15s%n"
					  ,name,weight,value,danger));
				}
				break;
			case "l": //Sets a load for the car at the cursor
				try {
					if (train.cursor == null)
						throw new IllegalArgumentException("\nThere are no cars in the train.\n");
					System.out.print("\nEnter product name: ");
					name = kb.nextLine();
					System.out.print("Enter product weight in tons: ");
					weight = kb.nextDouble();
					System.out.print("Enter product value in dollars: ");
					value = kb.nextDouble();
					kb.nextLine();
					System.out.print("Is the product dangerous? (y/n): ");
					danger = kb.nextLine();
					if (danger.equalsIgnoreCase("y"))
						dangerous = true;
					else if (danger.equalsIgnoreCase("n"))
						dangerous = false;
					else
						throw new InputMismatchException("");
					train.weight += weight;
					train.value += value;
					if (dangerous)
					{
						train.danger = dangerous;
						train.dangerCount++;
					}
					train.getCursorData().setProductLoad(new ProductLoad(name,weight,value,dangerous));
					System.out.println(String.format("%n%.1f tons of %s added to the current car.%n"
					  ,weight,name));
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				} catch (InputMismatchException e) {
					System.out.println("\nPlease enter in a proper value.\n");
					kb.nextLine();
				}
				break;
			case "s": //Searches for a product using its name
				try {
					if (train.cursor == null)
						throw new IllegalArgumentException("\nThere are no cars in the train.\n");
					System.out.print("\nEnter product name: ");
					name = kb.nextLine();
					train.findProduct(name);
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "t": //Prints the trains information
				System.out.println(train.toString());
				break;
			case "m": //Prints the manifest of the train
				train.printManifest();
				break;
			case "d": //Removes all the dangerous cars from the train
				try {
					if (train.cursor == null)
						throw new IllegalArgumentException("\nThere are no cars in the train.\n");
					train.removeDangerousCars();
					System.out.println("\nDangerous cars successfully removed from the train.\n");
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
				break;
			case "q": //Terminates the program
				System.out.println("\nProgram terminating successfully...");
				break;
			default:
				System.out.println("\nPlease select one of the options.\n");
			}
		}
	}
}
