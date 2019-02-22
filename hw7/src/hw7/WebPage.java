package hw7;

import java.util.LinkedList;

/**
 * The WebPage class represents a hyperlinked document.
 * Each WebPage contains information such as its position in the adjacency matrix, its page rank,
 * the url of the WebPage, and its asociated keywords.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 *     Recitation #: 06
 */
public class WebPage {
	
	private String url;
	private int index;
	private int rank;
	private LinkedList<String> keywords;
	
	/**
	 * Constructs an instance of WebPage with the supplied parameters.
	 * 
	 * @param u
	 *     The url of the web page.
	 * @param i
	 *     The index of the web page in the adjacency matrix.
	 * @param r
	 *     The page rank of the web page.
	 * @param k
	 *     The keywords associated with the web page.
	 */
	public WebPage(String u, int i, int r, LinkedList<String> k) {
		setUrl(u);
		setIndex(i);
		setRank(r);
		setKeywords(k);
	}
	
	/**
	 * Returns the url from the WebPage.
	 * 
	 * @return
	 *     Returns the url of the web page.
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * Sets the url for the WebPage.
	 * 
	 * @param u
	 *     The url of the web page to set.
	 */
	public void setUrl(String u) {
		url = u;
	}
	
	/**
	 * Returns the index from the WebPage.
	 * 
	 * @return
	 *     Returns the index of the web page.
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * Sets the index for the WebPage.
	 * 
	 * @param i
	 *     The index of the web page to set.
	 */
	public void setIndex(int i) {
		index = i;
	}
	
	/**
	 * Returns the rank from the WebPage.
	 * 
	 * @return
	 *     Returns the rank of the web page.
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * Sets the rank for the WebPage.
	 * 
	 * @param r
	 *     The page rank of the web page to set.
	 */
	public void setRank(int r) {
		rank = r;
	}

	/**
	 * Returns the keywords from the WebPage.
	 * 
	 * @return
	 *     Returns the keywords associated with the web page.
	 */
	public LinkedList<String> getKeywords() {
		return keywords;
	}

	/**
	 * Sets the keywords for the WebPage.
	 * 
	 * @param k
	 *     The keywords associated with the web page to set.
	 */
	public void setKeywords(LinkedList<String> k) {
		keywords = k;
	}
	
	/**
	 * Returns a String of the this WebPage's data members in tabular form.
	 */
	public String toString() {
		String result = String.format("%n  %-4d| %-19s|    %-5d| %s| ", index, url, rank, "%-18s");
		result += keywords.toString().replaceAll("\\[|\\]", "");
		return result;
	}

}
