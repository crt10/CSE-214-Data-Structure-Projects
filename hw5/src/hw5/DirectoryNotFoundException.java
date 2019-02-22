package hw5;

/**
 * The DirectoryNotFoundException class extends the Java API Exception class.
 * It is a throwable exception, indicating that the directory with name 'n' was not found.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 */
public class DirectoryNotFoundException extends Exception {
	
	/**
	 * Constructs an instance of DirectoryNotFoundException with the message "ERROR: No such directory named 'n'."
	 * 
	 * Postcondition:
	 *     This CongestedNetworkException has been initialized with the supplied parameters.
	 */
	public DirectoryNotFoundException(String n) {
		super("ERROR: No such directory named '" + n + "'.");
	}

}
