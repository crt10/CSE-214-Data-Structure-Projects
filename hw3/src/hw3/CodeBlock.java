package hw3;
/**
 * The CodeBlock class represents a nested block of code and contains information such as
 * the complexity of the block, the highest sub complexity, its nested name, and its loop variable if the block
 * is a while loop.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 */

public class CodeBlock {

	public static final String[] BLOCK_TYPES = {"def","for","while","if","else","elif"};
	public final int DEF = 0;
	public final int FOR = 1;
	public final int WHILE = 2;
	public final int IF = 3;
	public final int ELIF = 4;
	public final int ELSE = 5;
	private Complexity blockComplexity;
	private Complexity highestSubComplexity;
	private String name;
	private String loopVariable;
	
	/**
	 * Constructs an instance of CodeBlock with the supplied parameters.
	 * 
	 * Postcondition:
	 *     This Complexity has been initialized with the supplied parameters.
	 *     highestSubComplexity has been initialized to a complexity of O(1)
	 *     and loopVariable initialized to null;
	 *
	 * @param b
	 *     The complexity of this block.
	 * @param n
	 *     The name of the block in its nested structure.
	 */
	public CodeBlock(Complexity b, String n) {
		setBlockComplexity(b);
		highestSubComplexity = new Complexity(0,0);
		setName(n);
		loopVariable = null;
	}
	
	/**
	 * Returns the complexity of the block.
	 * 
	 * @return
	 *     Returns the current blockComplexity of this CodeBlock.
	 */
	public Complexity getBlockComplexity() {
		return blockComplexity;
	}
	
	/**
	 * Returns the highest sub-complexity of the block.
	 * 
	 * @return
	 *     Returns the current highestSubComplexity of this CodeBlock.
	 */
	public Complexity getHighestSubComplexity() {
		return highestSubComplexity;
	}
	
	/**
	 * Returns the name of the block.
	 * 
	 * @return
	 *     Returns the name of this CodeBlock.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the loop variable of the block.
	 * 
	 * @return
	 *     Returns the loopVariable of this CodeBlock.
	 */
	public String getLoopVariable() {
		return loopVariable;
	}
	
	/**
	 * Sets the blockComplexity of this CodeBlock.
	 * 
	 * @param b
	 *     The Complexity object to set as the blockComplexity.
	 */
	public void setBlockComplexity(Complexity b) {
		blockComplexity = b;
	}
	
	/**
	 * Sets the highestSubComplexity of this CodeBlock.
	 * 
	 * @param h
	 *     The Complexity object to set as the highestSubComplexity.
	 */
	public void setHighestSubComplexity(Complexity h) {
		highestSubComplexity = h;
	}
	
	/**
	 * Sets the name of this CodeBlock.
	 * 
	 * @param n
	 *     The String to set as name.
	 */
	public void setName(String n) {
		name = n;
	}
	
	/**
	 * Sets the loopVariable of this CodeBlock.
	 * 
	 * @param l
	 *     The String to set as the loopVariable.
	 */
	public void setLoopVariable(String l) {
		loopVariable = l;
	}
	
	/**
	 * Calculates and returns the total complexity of this CodeBlock.
	 * 
	 * @return
	 *     Returns a new Complexity object with this CodeBlock's total complexity.
	 */
	public Complexity getTotalComplexity() {
		int totalNPower = blockComplexity.getNPower() + highestSubComplexity.getNPower();
		int totalLogPower = blockComplexity.getLogPower() + highestSubComplexity.getLogPower();
		return new Complexity(totalNPower, totalLogPower);
	}
}
