package hw5;

/**
 * The NotADirectoryException class extends the Java API Exception class.
 * It is a throwable exception, indicating that a specific node is a file.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 */
public class NotADirectoryException extends Exception {
	
	/**
	 * Constructs an instance of NotADirectoryException with the message "ERROR: Can not add a child to a file.".
	 * 
	 * Postcondition:
	 *     This NotADirectoryException has been initialized.
	 */
	public NotADirectoryException() {
		super("ERROR: Can not add a child to a file.");
	}
	
	/**
	 * Constructs an instance of NotADirectoryException with the String 's'.
	 * 
	 * Postcondition:
	 *     This NotADirectoryException has been initialized with the supplied parameters.
	 */
	public NotADirectoryException(String s) {
		super(s);
	}

}
