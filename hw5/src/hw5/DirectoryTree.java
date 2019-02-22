package hw5;

/**
 * The DirectoryTree class represents a tree of DirectoryNodes.
 * The class contains references to the root of the tree,
 * a cursor for the present working directory,
 * and various methods for insertion and deletion. 
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 */
public class DirectoryTree {
	
	DirectoryNode root;
	DirectoryNode cursor;
	
	/**
	 * Initializes a DirectoryTree object with a single DirectoryNode named "root".
	 * 
	 * PostCondition:
	 *     The tree contains a single DirectoryNode named "root", and both cursor and root reference this node.
	 */
	public DirectoryTree() {
		root = new DirectoryNode("root", null, false);
		cursor = root;
	}
	
	/**
	 * Moves the cursor to the root node of the tree.
	 * 
	 * Postcondition:
	 *     The cursor now references the root node of the tree.
	 */
	public void resetCursor() {
		cursor = root;
	}
	
	/**
	 * Moves the cursor to the directory with the name indicated by 'name'.
	 * 
	 * Preconditions:
	 *     'name' references a valid directory.
	 *     'name' cannot reference a file.
	 *     
	 * Postconditions:
	 *     The cursor now references the directory with the name indicated by name.
	 *     If a child could not be found with that name, a DirectoryNotFoundException is thrown.
	 *     If the name was not a directory, a NotADirectoryException is thrown.
	 * 
	 * @param name
	 *     The name of the directory to change to.
	 *     
	 * @throws NotADirectoryException
	 *     Indicates if the node with the indicated name is a file,
	 *     as files cannot be selected by the cursor.
	 * @throws DirectoryNotFoundException
	 *     Indicates if the node with the indicated name is not found.
	 */
	public void changeDirectory(String name) throws NotADirectoryException, DirectoryNotFoundException {
		DirectoryNode tempCursor = cursor;
		int i = 0;
		String[] directories = name.split("/");
		if (directories.length == 0) {
			throw new IllegalArgumentException("ERROR: Invalid parameters.");
		}
		if (directories[0].equals(root.getName())) {
			tempCursor = root;
			i++;
		}
		while (i < directories.length) {
			if (!directories[i].isEmpty()) {
				if (tempCursor.getChild(directories[i]).getIsFile()) {
					throw new NotADirectoryException("ERROR: Can not change directory into a file.");
				}
				tempCursor = tempCursor.getChild(directories[i]);
			}
			i++;
		}
		cursor = tempCursor;
	}
	
	/**
	 * Returns a String containing the path of directory names from the root node of the tree to the cursor,
	 * with each name separated by a forward slash "/".
	 * 
	 * Postcondition:
	 *     The cursor remains at the same DirectoryNode.
	 *     
	 * @return
	 *     Returns a string of the current present working directory.
	 */
	public String presentWorkingDirectory() {
		DirectoryNode tempCursor = cursor;
		String pwd = tempCursor.getName();
		while (tempCursor.getParent() != null) {
			tempCursor = tempCursor.getParent();
			pwd = tempCursor.getName() + "/" + pwd;
		}
		return pwd;
	}
	
	/**
	 * Returns a String containing a space-separated list of names
	 * of all the child directories or files of the cursor.
	 * 
	 * PostCondition:
	 *     The cursor remains at the same DirectoryNode.
	 *     
	 * @return
	 *     A formatted String of DirectoryNode names.
	 */
	public String listDirectory() {
		DirectoryNode tempCursor = cursor;
		DirectoryNode[] children = tempCursor.getChildren();
		String directory = "";
		for (int i = 0; i < children.length; i++) {
			if (children[i] != null) {
				directory += children[i].getName() + " ";
			}
		}
		return directory;
	}
	
	/**
	 * Prints a formatted nested list of names of all the nodes in the directory tree, starting from the cursor.
	 * 
	 * Postcondition:
	 *     The cursor remains at the same DirectoryNode.
	 */
	public void printDirectoryTree() {
		System.out.println("|- " + cursor.getName());
		DirectoryNode[] children = cursor.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i] != null) {
				printDirectoryTree(children[i], "");
			}
		}
	}
	
	/**
	 * Helper method for printDirectoryTree().
	 * Prints a formatted nested list of names of all the nodes in the directory tree, starting from the cursor.
	 * 
	 * Postcondition:
	 *     The cursor remains at the same DirectoryNode.
	 *     
	 * @param node
	 *     The child passed to print.
	 * @param tabs
	 *     The number of tabs to print.
	 */
	public void printDirectoryTree(DirectoryNode node, String tabs) {
		tabs += "    ";
		if (node.getIsFile()) {
			System.out.println(tabs + "- " + node.getName());
		}
		else {
			System.out.println(tabs + "|- " + node.getName());
		}
		DirectoryNode[] children = node.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i] != null) {
				printDirectoryTree(children[i], tabs);
			}
		}
	}
	
	/**
	 * Creates a directory with the indicated name and adds it to the children of the cursor node. 
	 * 
	 * Precondition: 
	 *     'name' is a legal argument (does not contain spaces " " or forward slashes "/").
	 *     
	 * Postcondition:
	 *     A new DirectoryNode has been added to the children of the cursor,
	 *     or an exception has been thrown.
	 *     
	 * @param name
	 *     The name of the directory to add.
	 *     
	 * @throws IllegalArgumentException
	 *     Thrown if the 'name' argument is invalid.
	 * @throws FullDirectoryException
	 *     Thrown if all child references of this directory are occupied.
	 * @throws NotADirectoryException
	 *     Thrown if trying to add a child to a file node.
	 */
	public void makeDirectory(String name) throws IllegalArgumentException, FullDirectoryException, NotADirectoryException {
		if (name.contains(" ") || name.contains("/")) {
			throw new IllegalArgumentException("ERROR: The name of the directory"
			  + " can not have any spaces or forward slashes.");
		}
		cursor.addChild(new DirectoryNode(name, cursor, false));
	}
	
	/**
	 * Creates a file with the indicated name and adds it to the children of the cursor node. 
	 * 
	 * Precondition:
	 *     'name' is a legal argument (does not contain spaces " " or forward slashes "/").
	 * 
	 * @param name
	 *     The name of the file to add.
	 *     
	 * @throws IllegalArgumentException
	 *     Thrown if the 'name' argument is invalid.
	 * @throws FullDirectoryException
	 *     Thrown if all child references of this directory are occupied.
	 * @throws NotADirectoryException
	 *     Thrown if trying to add a child to a file node.
	 */
	public void makeFile(String name) throws IllegalArgumentException, FullDirectoryException, NotADirectoryException {
		if (name.contains(" ") || name.contains("/")) {
			throw new IllegalArgumentException("ERROR: The name of the file"
			  + " can not have any spaces or forward slashes.");
		}
		cursor.addChild(new DirectoryNode(name, cursor, true));
	}
	
	//Extra Credit
	
	/**
	 * Finds the node in the tree with the indicated name and prints the path.
	 * 
	 * @param name
	 *     The name of the child to find.
	 * @param node
	 *     The current node the recursive method is searching from.
	 * @param found
	 *     Whether the child with 'name' was found.
	 *     
	 * @return
	 *     Returns whether the child was found.
	 */
	public boolean findChild(String name, DirectoryNode node, boolean found) {
		if (node.getName().equals(name))
		{
			DirectoryNode tempCursor = cursor;
			cursor = node;
			System.out.println(presentWorkingDirectory());
			cursor = tempCursor;
			found = true;
		}
		DirectoryNode[] children = node.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i] != null) {
				if (!found) {
					found = findChild(name, children[i], found);
				}
				else {
					findChild(name, children[i], found);
				}
			}
		}
		return found;
	}
	
	/**
	 * Moves the cursor up to its parent directory.
	 * Nothing is done if the cursor references the root.
	 * 
	 * Postcondition:
	 *     The cursor currently references its parent.
	 *     
	 * @throws IllegalArgumentException
	 *     Indicates that the cursor is at the root.
	 */
	public void changeDirectoryParent() {
		if (cursor != root) {
			cursor = cursor.getParent();
		}
		else {
			throw new IllegalArgumentException("ERROR: Already at root directory.");
		}
	}
	
	/**
	 * Moves a file or directory specified by src to dst, including all children.
	 * 
	 * @param src
	 *     The file or directory to move.
	 * @param dst
	 *     The directory to move 'src' to.
	 *     
	 * @throws DirectoryNotFoundException
	 *     Indicates the either the 'src' or 'dst' was not found in the tree.
	 * @throws NotADirectoryException
	 *     Indicates that 'dst' is a file and not a directory.
	 * @throws FullDirectoryException
	 *     Indicates that all child references of 'dst' are occupied.
	 * @throws IllegalArgumentException
	 *     Indicates that there is already a node with the same name as 'src' in directory 'dst'.
	 */
	public void move(String src, String dst) throws DirectoryNotFoundException, NotADirectoryException, FullDirectoryException {
		DirectoryNode tempCursor = cursor;
		DirectoryNode source = cursor;
		DirectoryNode destination = cursor;
		try {
			changeDirectory(src);
			source = cursor;
		} catch (NotADirectoryException e) {
			String path = src.substring(0, src.lastIndexOf("/"));
			String file = src.substring(src.lastIndexOf("/")+1, src.length());
			changeDirectory(path);
			source = cursor.getChild(file);
		}
		try {
			changeDirectory(dst);
			destination = cursor;
			cursor = tempCursor;
		} catch (NotADirectoryException e) {
			cursor = tempCursor;
			throw new NotADirectoryException("ERROR: Can not move file/folder into a file.");
		}
		if (dst.contains(src)) {
			throw new IllegalArgumentException("ERROR: Can not move a directory into its children.");
		}
		try {
			source.getParent().removeChild(source);
			destination.addChild(source);
		} catch (IllegalArgumentException e) {
			source.getParent().addChild(source);
			throw new IllegalArgumentException("ERROR: Can not have duplicate names in the directory.");
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("ERROR: Can not move the root directory.");
		}
	}

}
