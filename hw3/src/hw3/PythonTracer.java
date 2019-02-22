package hw3;
/**
 * PythonTracer calculates the Big-Oh notation for a specified python program.
 * The program will prompt the user for the file name of the python program
 * to calculate the Big-Oh notation for.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class PythonTracer {
	
	public static final int SPACE_COUNT = 4;

	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		System.out.println("Please enter a file name (or 'quit' to quit):");
		String input = kb.nextLine();
		while (!input.equalsIgnoreCase("quit")) {
			try {
				System.out.println("Overall complexity of " + input
				  + ": " + traceFile(input) + "\n");
			} catch (FileNotFoundException e) {
				System.out.println("That file cannot be found.");
			}
			System.out.print("Please enter a file name (or 'quit' to quit):");
			input = kb.nextLine();
		}
		System.out.println("Program terminating successfully...");
		kb.close();
	}
	
	/**
	 * Opens the indicated file and traces through the code of the Python function contained within the file,
	 * returning the Big-Oh order of complexity of the function.
	 * During operation, the stack trace should be printed to the console as code blocks are pushed to/popped from the stack.
	 * 
	 * Preconditions:
	 *     fileName is not null and the file it names contains a single Python function with valid syntax.
	 *     
	 * @param fileName
	 *     The name of the python file to calculate the Big-Oh notation for.
	 *     
	 * @return
	 *     A Complexity object representing the total order of complexity of the Python code contained within the file.
	 *     
	 * @throws FileNotFoundException
	 *     Indicates that a file with the specified 'filename' was not found.
	 */
	public static Complexity traceFile(String fileName) throws FileNotFoundException {
		Stack<CodeBlock> blocks = new Stack<>(); //Initialize blocks to an empty stack of CodeBlocks.
		Scanner reader = new Scanner(new File(fileName)); //Open file using fileName.
		String prevName = "1";
		while (reader.hasNextLine()) { //while file has lines
			String line = reader.nextLine(); // line = next line in file.
			if (!line.trim().isEmpty() && line.trim().charAt(0) != '#') { //if line is not empty and line does not start with '#'
				int indents = line.indexOf(line.trim())/SPACE_COUNT; //indents = number of spaces in line / SPACES_COUNT.
				while (indents < blocks.size()) { //while indents is less than size of blocks
					if (indents == 0) { //if indents is 0
						reader.close(); //Close the file
						return blocks.peek().getHighestSubComplexity(); //Return the total complexity of blocks.top.
					}
					else {
						CodeBlock oldTop = blocks.pop();
						prevName = oldTop.getName();
						Complexity oldTopComplexity = oldTop.getTotalComplexity();
						if (oldTopComplexity.compareComplexity(blocks.peek().getHighestSubComplexity())) { //if oldTopComplexity is higher order than blocks.top's highest sub-complexity
							blocks.peek().setHighestSubComplexity(oldTopComplexity); //blocks.top's highest sub-complexity = oldTopComplexity
							System.out.println("Leaving block " + oldTop.getName()
							  + ", updating block " + blocks.peek().getName() + ": ");
						}
						else //if oldTopComplexity is not higher order than blocks.top's highest sub-complexity
							System.out.println("Leaving block " + oldTop.getName()
							  + ", nothing to update.");
						System.out.println(String.format("    %-20s%-30s%-40s%n","BLOCK "+ blocks.peek().getName()+":" //Print block info
						  ,"block complexity = "+blocks.peek().getBlockComplexity().toString()
						  ,"highest sub-complexity = "+blocks.peek().getHighestSubComplexity().toString()));
					}
				}
				if (findKeyWord(line) != null) { //if line contains a keyword
					String keyWord = findKeyWord(line); //keyword = keyword in line.
					String name = null; //name of block is initialized to null;
					if (indents == 0) //if the block is the first block
						name = "1"; //the name of the block is "1"
					else { //if the block is not the first block
						if (indents == prevName.split("\\.").length-1) { //if the block is nested under a block that already has a nested block
							int n = Integer.parseInt(prevName.substring(prevName.lastIndexOf('.')+1, prevName.length()
									));
							name = prevName.substring(0, prevName.lastIndexOf(".")+1) + (n+1); //the name of the block is that of the previous block, but incremented by 1
						}
						else //if the block is the only block nested under another block
							name = blocks.peek().getName() + ".1"; //append .1 to the previous block's name
					}
					if (keyWord.equals("for")) { //if keyword is 'for'
						Complexity x = null;
						if (line.indexOf("N:") == -1 && line.indexOf("log_N:") == -1) //if the complexity of the block is O(1)
							x = new Complexity(0,0);
						else if (line.indexOf("N:") != -1 && line.indexOf("log_N:") == -1) //if the complexity of the block is O(n)
							x = new Complexity(1,0);
						else //if the complexity of the block is O(log(n))
							x = new Complexity (0,1);
						blocks.push(new CodeBlock(x, name)); //push the new 'for' block to blocks
					}
					else if (keyWord.equals("while")) { //if the keyword is 'while'
						String l = line.trim().split(" ")[1]; //initializes l to the loop variable of the block
						blocks.push(new CodeBlock(new Complexity(0,0), name)); //pushes the new 'while' block to blocks with complexity: O(1)
						blocks.peek().setLoopVariable(l); //sets the loop variable for the new 'while' block to l
					}
					else //if the keyword is not 'for' or 'while'
						blocks.push(new CodeBlock(new Complexity(0,0), name)); //Create new O(1) CodeBlock and push onto the stack.
					System.out.println("Entering block " + name + " '" + keyWord + "':");
					System.out.println(String.format("    %-20s%-30s%-40s%n","BLOCK "+name+":" //Print block info
					    ,"block complexity = "+blocks.peek().getBlockComplexity().toString()
					    ,"highest sub-complexity = "+blocks.peek().getHighestSubComplexity().toString()));
				}
				else if (blocks.peek().getLoopVariable() != null && line.trim().split(" ")[0].equals(blocks.peek().getLoopVariable())) { //else if blocks.top is a 'while' block and line updates blocks.top's loopVariable 
					if (line.indexOf("/= 2") != -1) //if the block is of complexity: O(log(n))
						blocks.peek().setBlockComplexity(new Complexity(0,1)); //set the block's complexity to O(log(n))
					else //if the block is of complexity: O(n)
						blocks.peek().setBlockComplexity(new Complexity(1,0)); //set the block's complexity to O(n)
					System.out.println("Found update statement, updating block " + blocks.peek().getName() + ":");
					System.out.println(String.format("    %-20s%-30s%-40s%n","BLOCK "+blocks.peek().getName()+":" //Print block info
					  ,"block complexity = "+blocks.peek().getBlockComplexity().toString()
					  ,"highest sub-complexity = "+blocks.peek().getHighestSubComplexity().toString()));
				}
			}
		}
		while (blocks.size() > 1) {
			CodeBlock oldTop = blocks.pop();
			Complexity oldTopComplexity = oldTop.getBlockComplexity();
			if (oldTopComplexity.compareComplexity(blocks.peek().getHighestSubComplexity())) { //if oldTopComplexity is higher order than blocks.top's highest sub-complexity
				blocks.peek().setHighestSubComplexity(oldTopComplexity); //blocks.top's highest sub-complexity = oldTopComplexity
				blocks.peek().setHighestSubComplexity(oldTop.getHighestSubComplexity());
				System.out.println("Leaving block " + oldTop.getName()
				  + ", updating block " + blocks.peek().getName() + ": ");
			}
			else //if oldTopComplexity is not higher order than blocks.top's highest sub-complexity
				System.out.println("Leaving block " + oldTop.getName()
				  + ", nothing to update.");
			System.out.println(String.format("    %-20s%-30s%-40s%n","BLOCK "+ blocks.peek().getName()+":" //Print block info
			  ,"block complexity = "+blocks.peek().getBlockComplexity().toString()
			  ,"highest sub-complexity = "+blocks.peek().getHighestSubComplexity().toString()));
		}
		reader.close(); //close the file reader
		return blocks.pop().getHighestSubComplexity(); //Return the total complexity of blocks.top.
	}
	
	/**
	 * Finds a key word within a specified String.
	 * 
	 * @param line
	 *     The String to look for a key word in.
	 *     
	 * @return
	 *     Returns the key word if found, otherwise returns null.
	 */
	public static String findKeyWord(String line) {
		String[] words = line.trim().split(" ");
		for (int i = 0; i < CodeBlock.BLOCK_TYPES.length; i++) {
			if (words[0].equals(CodeBlock.BLOCK_TYPES[i]))
				return words[0];
		}
		return null;
	}
}
