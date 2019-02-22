package hw5;

/**
 * The BashTerminal class contains a single main method
 * which allows a user to interact with a file system implemented by an instance of DirectoryTree.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 */
import java.util.Scanner;

public class BashTerminal {
	
	public static void main(String[] args) {
		DirectoryTree fileSystem = new DirectoryTree();
		Scanner kb = new Scanner(System.in);
		String input = "";
		System.out.println("Starting bash terminal.");
		while (!input.equals("exit")) {
			try {
				System.out.print("[112336491@host]: $ ");
				String[] arguments = kb.nextLine().split(" ");
				input = arguments[0]; //The command given by the user.
				switch (input) {
					case "pwd": //Print the "present working directory" of the cursor node
						if (arguments.length != 1) { //If other arguments other than 'pwd' are provided.
							throw new IllegalArgumentException("ERROR: Invalid parameters.");
						}
						System.out.println(fileSystem.presentWorkingDirectory());
						break;
					case "ls": //List the names of all the child directories or files of the cursor.
						if (arguments.length == 2 && arguments[1].equals("-R")) { //If the argument '-R' is provided.
							fileSystem.printDirectoryTree();
						}
						else if (arguments.length == 1){ //If only 'ls' is provided.
							System.out.println(fileSystem.listDirectory());
						}
						else { //If other arguments other that 'ls' and 'ls -R' are provided.
							throw new IllegalArgumentException("ERROR: Invalid parameters.");
						}
						break;
					case "cd": //Moves the cursor
						if (arguments.length == 2) {
							if (arguments[1].equals("/")) { //If the argument '/' is provided.
								fileSystem.resetCursor(); //Moves the cursor to the root of the tree.
							}
							else if (arguments[1].equals("..")) { //If the argument '..' is provided.
								fileSystem.changeDirectoryParent(); //Moves the cursor up to its parent directory.
							}
							else { //If the argument provided is either a path or directory.
								fileSystem.changeDirectory(arguments[1]);;
							}
						}
						else { //Other arguments other than '/', '..', {path}, or {dir} are provided.
							throw new IllegalArgumentException("ERROR: Invalid parameters.");
						}			
						break;
					case "mkdir": //Creates a new directory with the indicated name as a child of the cursor, as long as there is room.
						if (arguments.length > 2) { //If there are spaces in the node name.
							throw new IllegalArgumentException("ERROR: The name of the directory"
							  + " can not have any spaces or forward slashes.");
						}
						if (arguments.length == 1) { //If no name is provided for the directory.
							throw new IllegalArgumentException("ERROR: Name not provided for directory.");
						}
						fileSystem.makeDirectory(arguments[1]);
						break;
					case "touch": //Creates a new file with the indicated name as a child of the cursor, as long as there is room.
						if (arguments.length > 2) { //If there are spaces in the node name.
							throw new IllegalArgumentException("ERROR: The name of the file"
							  + " can not have any spaces or forward slashes.");
						}
						if (arguments.length == 1) { //If no name is provided for the file.
							throw new IllegalArgumentException("ERROR: Name not provided for file.");
						}
						fileSystem.makeFile(arguments[1]);
						break;
					case "find": //Finds the node in the tree with the indicated name and prints the path.
						if (arguments.length > 2) { //If there are spaces in the node name.
							throw new IllegalArgumentException("ERROR: The name can not "
							  + "contain spaces");
						}
						if (arguments.length == 1) { //If no name is provided for the node.
							throw new IllegalArgumentException("ERROR: Name not provided");
						}
						if (!fileSystem.findChild(arguments[1], fileSystem.root, false)) {
							throw new FileNotFoundException(); //Thrown if the node was not found.
						}
						break;
					case "mv": //Moves a file or directory specified by 'src' to 'dst', including all children.
						if (arguments.length > 3) { //If other arguments other than {src} and {dest} are provided.
							throw new IllegalArgumentException("ERROR: Invalid parameters.");
						}
						if (arguments.length < 3) { //If {src} or {dst} was not provided.
							throw new IllegalArgumentException("ERROR: Not enough parameters provided.");
						}
						fileSystem.move(arguments[1], arguments[2]);
						break;
					case "exit": //Exits the program.
						System.out.println("Program terminating normally");
						break;
					default: //An unknown command was given.
						throw new IllegalArgumentException("ERROR: Command not found.");
				}
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} catch (DirectoryNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (NotADirectoryException e) {
				System.out.println(e.getMessage());
			} catch (FullDirectoryException e) {
				System.out.println(e.getMessage());
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}
		kb.close();
	}

}
