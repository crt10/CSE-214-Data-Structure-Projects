package hw5;

/**
 * The FullDirectoryException class extends the Java API Exception class.
 * It is a throwable exception, indicating that the current DirectoryNode can not hold anymore children.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 */
public class FullDirectoryException extends Exception {
	
	/**
	 * Constructs an instance of FullDirectoryException with the message "ERROR: Present directory is full.".
	 * 
	 * Postcondition:
	 *     This FullDirectoryException has been initialized.
	 */
	public FullDirectoryException() {
		super("ERROR: Present directory is full.");
	}

}
