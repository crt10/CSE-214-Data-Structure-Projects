package hw5;

/**
 * The FileNotFoundException class extends the Java API Exception class.
 * It is a throwable exception, indicating that the a file was not found in the tree.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 */
public class FileNotFoundException extends Exception {
	
	/**
	 * Constructs an instance of FileNotFoundException with the message "ERROR: No such file exists.".
	 * 
	 * Postcondition:
	 *     This FileNotFoundException has been initialized.
	 */
	public FileNotFoundException() {
		super("ERROR: No such file exists.");
	}

}
