package hw7;

import java.util.Comparator;

/**
 * The RankComparator class implements the Comparator class.
 * Used for sorting WebPages based on their page rank.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 *     Recitation #: 06
 */
public class RankComparator implements Comparator{
	
	/**
	 * Compares the page rank of two WebPages. If they are equal, 0 is returned.
	 * If the first WebPage's rank is greater than the second, -1 is returned.
	 * If the first WebPage's rank is less than the second, 1 is returned.
	 */
	public int compare(Object o1, Object o2) {
		WebPage page1 = (WebPage)o1;
		WebPage page2 = (WebPage)o2;
		if (page1.getRank() == page2.getRank()) {
			return 0;
		}
		else if (page1.getRank() < page2.getRank()) {
			return 1;
		}
		else {
			return -1;
		}
	}

}
