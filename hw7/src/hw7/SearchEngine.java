package hw7;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * The SearchEngine class will implement a main method which will provide methods for
 * adding a new page to the graph, removing a page from the graph, add a link between pages,
 * removing links between pages, print the graph in varying orders, and searching for pages
 * containing a specific keyword. The SearchEngine should construct a new WebGraph using the files
 * indicated by the static constant Strings PAGES_FILE and LINKS_FILE. 
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 *     Recitation #: 06
 */
public class SearchEngine {
	
	public static final String PAGES_FILE = "pages.txt";
	public static final String LINKS_FILE = "links.txt";

	public static void main(String[] args) {
		try {
			System.out.println("Loading WebGraph data...");
			WebGraph web = WebGraph.buildFromFiles(PAGES_FILE, LINKS_FILE);
			System.out.print("Success!");
			Gui table = new Gui(web); //Extra Credit
			Scanner kb = new Scanner(System.in);
			String input = "";
			String url1 = "";
			String url2 = "";
			String keyword = "";
			while (!input.equals("q")) {
				try {
					System.out.print("\n\nMenu:\n    (AP) - Add a new page to the graph.\n"
					  + "    (RP) - Remove a page from the graph.\n"
					  + "    (AL) - Add a link betweenpages in the graph.\n"
					  + "    (RL) - Remove a link between pages in the graph.\n"
					  + "    (P)  - Print the graph.\n    (S)  - Search for pages with a keyword.\n"
					  + "    (Q)  - Quit.\n\nPlease select an option: ");
					input = kb.nextLine().toLowerCase();
					switch(input) {
						case "ap":
							System.out.print("Enter a URL: ");
							url1 = kb.nextLine();
							System.out.print("Enter keywords (space-seperated): ");
							keyword = kb.nextLine();
							LinkedList<String> keywords = new LinkedList<String>(Arrays.asList(keyword.split(" ")));
							web.addPage(url1, keywords);
							System.out.print("\n" + url1 + " successfully added to the WebGraph!");
							break;
						case "rp":
							System.out.print("Enter a URL: ");
							url1 = kb.nextLine();
							web.removePage(url1);
							System.out.print("\n" + url1 + " has been removed from the graph!");
							break;
						case "al":
							System.out.print("Enter a source URL: ");
							url1 = kb.nextLine();
							System.out.print("Enter a destination URL: ");
							url2 = kb.nextLine();
							web.addLink(url1, url2);
							System.out.print("\nLink successfully added from " + url1 + " to " + url2 + "!");
							break;
						case "rl":
							System.out.print("Enter a source URL: ");
							url1 = kb.nextLine();
							System.out.print("Enter a destination URL: ");
							url2 = kb.nextLine();
							web.removeLink(url1, url2);
							System.out.print("\nLink removed from " + url1 + " to " + url2 + "!");
							break;
						case "p":
							System.out.print("\n    (I) Sort based on index (ASC)"
							  + "\n    (U) Sort based on URL (ASC)"
							  + "\n    (R) Sort based on rank (DSC)\n"
							  + "\nPlease select an option: ");
							input = kb.nextLine().toLowerCase();
							switch(input) {
								case "i":
									web.sort(new IndexComparator());
									break;
								case "u":
									web.sort(new URLComparator());
									break;
								case "r":
									web.sort(new RankComparator());
									break;
							}
							web.printTable();
							break;
						case "s":
							System.out.print("Search keyword: ");
							keyword = kb.nextLine();
							System.out.print(web.searchKeyword(keyword));
							break;
						case "q":
							System.out.println("\nGoodbye.");
							break;
						default:
							System.out.print("\nPlease select a valid menu option.");
					}
					table.update();
				} catch (IllegalArgumentException e) {
					System.out.print("\n" + e.getMessage());
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Failure.");
			System.out.println(e.getMessage());
			System.exit(0);
			return;
		}
	}

}