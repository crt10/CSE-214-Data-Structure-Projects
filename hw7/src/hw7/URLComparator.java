package hw7;

import java.util.Comparator;

/**
 * The URLComparator class implements the Comparator class.
 * Used for sorting WebPages alphabetically based on their URL.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 *     Recitation #: 06
 */
public class URLComparator implements Comparator{
	
	/**
	 * Compares the URL of two WebPages alphabetically.
	 */
	public int compare(Object o1, Object o2) {
		WebPage page1 = (WebPage)o1;
		WebPage page2 = (WebPage)o2;
		return(page1.getUrl().compareTo(page2.getUrl()));
	}

}
