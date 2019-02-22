package hw7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * The WebGraph class represents a collection of WebPage objects as a directed graph.
 * Each WebPage contains contains a Collection of WebPages member variable called pages
 * and a 2-dimensional array of integers (adjacency matrix) member variable called links. 
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 *     Recitation #: 06
 */
public class WebGraph {
	
	public static final int MAX_PAGES = 40;
	private LinkedList<WebPage> pages = new LinkedList<WebPage>();
	private int[][] links = new int[MAX_PAGES][MAX_PAGES];
	
	/**
	 * Constructs a WebGraph object using the indicated files as the source for pages and edges.
	 * 
	 * Preconditions:
	 *     Both parameters reference text files which exist.
	 *     The files follow proper format as outlined in the "Reading Graph from File" section below.
	 *     
	 * Postconditions:
	 *     A WebGraph has been constructed and initialized based on the text files.
	 *     
	 * @param pagesFile
	 *     String of the relative path to the file containing the page information.
	 * @param linksFile
	 *     String of the relative path to the file containing the link information.
	 *     
	 * @return
	 *     The WebGraph constructed from the text files.
	 *     
	 * @throws IllegalArgumentException
	 *     Thrown if either of the files does not reference a valid text file,
	 *     or if the files are not formatted correctly.
	 */
	public static WebGraph buildFromFiles(String pagesFile, String linksFile) throws IllegalArgumentException {
		try {
			WebGraph graph = new WebGraph();
			Scanner reader = new Scanner(new File(pagesFile));
			while (reader.hasNextLine()) {
				String[] line = reader.nextLine().split(" ");
				if (!line[4].contains(".")) {
					throw new IllegalArgumentException("Page file contains an invalid page");
				}
				LinkedList<String> keywords = new LinkedList<String>();
				for (int i = 5; i < line.length; i++) {
					keywords.add(line[i]);
				}
				graph.addPage(line[4], keywords);
			}
			reader = new Scanner(new File(linksFile));
			while (reader.hasNextLine()) {
				String[] line = reader.nextLine().split(" ");
				if (!line[4].contains(".") || !line[5].contains(".")) {
					throw new IllegalArgumentException("Links file contains an invalid page");
				}
				graph.addLink(line[4], line[5]);
			}
			reader.close();
			return graph;
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Can not find text files.");
		}
	}
	
	/**
	 * Adds a page to the WebGraph.
	 * 
	 * Preconditions:
	 *     url is unique and does not exist as the URL of a WebPage already in the graph. 
	 *     url and keywords are not null.
	 *     
	 * Postconditions:
	 *     The page has been added to pages at index 'i' and links has been
	 *     logically extended to include the new row and column indexed by 'i'.
	 * 
	 * @param url
	 *     The URL of the web page.
	 * @param keywords
	 *     The keywords associated with the WebPage.
	 *     
	 * @throws IllegalArgumentException
	 *      If url is not unique and already exists in the graph, or if either argument is null, or if the graph is full.
	 */
	public void addPage(String url, LinkedList<String> keywords) throws IllegalArgumentException {
		if (url == null || keywords == null) {
			throw new IllegalArgumentException("Can not have null arguments.");
		}
		if (!url.contains(".")) {
			throw new IllegalArgumentException("The URL given is not valid.");
		}
		if (pages.size() == 40) {
			throw new IllegalArgumentException("There is no more space in the graph.");
		}
		for (WebPage w: pages) {
			if (w.getUrl().equals(url)) {
				throw new IllegalArgumentException("Error: " + url + " already exists "
				  + "in the WebGraph. Could not add new WebPage.");
			}
		}
		pages.add(new WebPage(url, pages.size(), 0, keywords));
	}
	
	/**
	 * Adds a link from the WebPage with the URL indicated by source to the WebPage 
	 * with the URL indicated by destination.
	 * 
	 * Preconditions:
	 *     Both parameters reference WebPages which exist in the graph.
	 *     
	 * @param source
	 *     The URL of the page which contains the hyperlink to destination.
	 * @param destination
	 *     The URL of the page which the hyperlink points to.
	 *     
	 * @throws IllegalArgumentException
	 *     If either of the URLs are null or could not be found in pages.
	 */
	public void addLink(String source, String destination) throws IllegalArgumentException {
		if (source == null || destination == null) {
			throw new IllegalArgumentException("Can not have null arguments.");
		}
		try {
			if (findURLIndex(source) == -1) {
				throw new IllegalArgumentException("Error: " + source);
			}
			else if(findURLIndex(destination) == -1) {
				throw new IllegalArgumentException("Error: " + destination);
			}
			links[findURLIndex(source)][findURLIndex(destination)] = 1;
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage() + " not found in the graph.");
		}
		updatePageRanks();
	}
	
	/**
	 * Removes the WebPage from the graph with the given URL.
	 * 
	 * Postconditions:
	 *     The WebPage with the indicated URL has been removed from the graph,
	 *     and it's corresponding row and column has been removed from the adjacency matrix.
	 *     All pages that has an index greater than the index that was removed
	 *     should decrease their index value by 1.
	 *     If url is null or could not be found in pages,
	 *     the method ignores the input and does nothing.
	 *     
	 * @param url
	 *     The URL of the page to remove from the graph.
	 */
	public void removePage(String url) {
		int urlIndex = findURLIndex(url);
		for (int i = 0; i < pages.size(); i++) {
			if (i >= urlIndex) {
				links[i] = links[i+1];
			}
			for (int j = urlIndex; j < pages.size()-1; j++) {
				links[i][j] = links[i][j+1];
			}
		}
		for (int x = urlIndex + 1; x < pages.size(); x++) {
			pages.get(x).setIndex(pages.get(x).getIndex()-1);
		}
		pages.remove(urlIndex);
		updatePageRanks();
	}
	
	/**
	 * Removes the link from WebPage with the URL indicated
	 * by source to the WebPage with the URL indicated by destination.
	 * 
	 * Postconditions:
	 * The entry in links for the specified hyperlink has been set to 0 (no link).
	 * If either of the URLs could not be found, the input is ignored and the method does nothing.
	 * 
	 * @param source
	 *     The URL of the WebPage to remove the link.
	 * @param destination
	 *     The URL of the link to be removed.
	 */
	public void removeLink(String source, String destination) {
		links[findURLIndex(source)][findURLIndex(destination)] = 0;
		updatePageRanks();
	}
	
	/**
	 * Calculates and assigns the PageRank for every page in the WebGraph.
	 * 
	 * Postconditions:
	 *     All WebPages in the graph have been assigned their proper PageRank.
	 */
	public void updatePageRanks() {
		pages.sort(new IndexComparator());
		for (int i = 0; i < pages.size(); i++) {
			int rank = 0;
			for (int j = 0; j < pages.size(); j++) {
				if (links[j][i] == 1) {
					rank++;
				}
			}
			pages.get(i).setRank(rank);
		}
	}
	
	/**
	 * Prints the WebGraph in tabular form.
	 */
	public void printTable() {
		System.out.println(String.format("%n%-10s%-18s%-10s%-20s%s", "Index", "URL"
		  , "PageRank", "Links", "Keywords"));
		for (int i = 0; i < 102; i++) {
			System.out.print("-");
		}
		for (WebPage w: pages) {
			System.out.print(String.format(w.toString(), linksToString(w.getIndex())));
		}
	}
	
	/**
	 * Searches and prints WebPages with the corresponding keyword.
	 * Results are sorted by their page ranks.
	 * 
	 * @param keyword
	 *     The associated keyword to look for in the collection of WebPages.
	 */
	public String searchKeyword(String keyword) {
		String results = "";
		int rank = 1;
		sort(new RankComparator());
		for (WebPage w: pages) {
			if (w.getKeywords().contains(keyword)) {
				results += String.format("%n  %-3d|    %-6d| %s", rank++, w.getRank(), w.getUrl());
			}
		}
		if (results.equals("")) {
			return "\nNo search results found for the keyword " + keyword + ".";
		}
		else {
			String header = "\nRank   PageRank    URL\n";
			for (int i = 0; i < 45; i++) {
				header += "-";
			}
			return header + results;
		}
	}
	
	/**
	 * Returns the index of the specified url. Returns -1 if the specified url is not found.
	 * 
	 * @param url
	 *     The url of the WebPage to search for.
	 *     
	 * @return
	 *     Returns the index of the WebPage with the corresponding url.
	 */
	public int findURLIndex(String url) {
		for (WebPage w: pages) {
			if (w.getUrl().equals(url)) {
				return w.getIndex();
			}
		}
		return -1;
	}
	
	/**
	 * Sorts the collection of WebPages with the supplied Comparator.
	 * 
	 * @param c
	 *     The Comparator to sort with.
	 */
	public void sort(Comparator c) {
		Collections.sort(pages, c);
	}
	
	/**
	 * Returns a string of the links of the WebPage with the supplied index.
	 * 
	 * @param index
	 *     The index of the WebPage
	 *     
	 * @return
	 *     Returns the string of the links.
	 */
	public String linksToString(int index) {
		String edges = "";
		for (int j = 0; j < pages.size(); j++) {
			if (links[index][j] != 0) {
				edges += j + ", ";
			}
		}
		if (edges.lastIndexOf(", ") != -1) {
			edges = edges.substring(0, edges.lastIndexOf(", "));
		}
		return edges;
	}
	
	/**
	 * Extra Credit
	 * Stores and returns the data of WebGraph into a 2D String Array to be used with a JTable.
	 * 
	 * @return
	 *     Returns the data of WebGraph in a 2D String Array
	 */
	public String[][] toTable() {
		String[][] table = new String[pages.size()][MAX_PAGES];
		for (WebPage w: pages) {
			String[] row = table[w.getIndex()];
			row[0] = Integer.toString(w.getIndex());
			row[1] = w.getUrl();
			row[2] = Integer.toString(w.getRank());
			row[3] = linksToString(w.getIndex());
			row[4] = w.getKeywords().toString().replaceAll("\\[|\\]", "");
		}
		return table;
	}

}
