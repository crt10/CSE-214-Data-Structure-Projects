package hw5;

/**
 * The DirectoryNode class represents a node in a file tree.
 * One node will be able to have a maximum of 10 children.
 * Each node contains info such as its name, its parent, and whether it is a file.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 */
public class DirectoryNode {
	
	public String name;
	public DirectoryNode[] children;
	public DirectoryNode parent;
	public boolean isFile;
	
	/**
	 * Constructs an instance of DirectoryNode with the supplied parameters.
	 * The maximum number of children will be set to 10.
	 * 
	 * Postcondition: 
	 *     This DirectoryNode has been initialized with the supplied parameters.
	 * 
	 * @param n
	 *     The name of the node.
	 * @param p
	 *     The parent of the node.
	 * @param f
	 *     Whether the node is a file or directory.
	 */
	public DirectoryNode(String n, DirectoryNode p, boolean f) {
		setName(n);
		setParent(p);
		children = new DirectoryNode[10];
		setIsFile(f);
	}
	
	/**
	 * Sets the name of the DirectoryNode.
	 * 
	 * @param n
	 *     The name of the node to set.
	 */
	public void setName(String n){
		name = n;
	}
	
	/**
	 * Sets the parent of the DirectoryNode.
	 * 
	 * @param p
	 *     The parent of the node to set.
	 */
	public void setParent(DirectoryNode p) {
		parent = p;
	}
	
	/**
	 * Sets isFile of the DirectoryNode.
	 * 
	 * @param f
	 *     The type of node to set.
	 */
	public void setIsFile(boolean f) {
		isFile = f;
	}
	
	/**
	 * Returns the name of the DirectoryNode.
	 * 
	 * @return
	 *     Returns the name of the node.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the parent of the DirectoryNode.
	 * 
	 * @return
	 *     Returns the parent of the node.
	 */
	public DirectoryNode getParent() {
		return parent;
	}
	
	/**
	 * Returns isFile from the DirectoryNode.
	 * 
	 * @return
	 *     Returns whether the node is a file or not.
	 */
	public boolean getIsFile() {
		return isFile;
	}
	
	/**
	 * Returns children from the DirectoryNode.
	 * 
	 * @return
	 *     Returns the array of children of the node.
	 */
	public DirectoryNode[] getChildren() {
		return children;
	}
	
	/**
	 * Adds newChild to any of the open child positions of this node.
	 * Children will be added in a left-to-right order,
	 * starting from index 0 to index 9.
	 * 
	 * Preconditions:
	 *     This node is not a file.
	 *     There is at least one empty position in the children of this node.
	 *     
	 * Postcondition:
	 *     newChild has been added as a child of this node.
	 *     If there is no room for a new node, a FullDirectoryException is thrown.
	 * 
	 * @param newChild
	 *     The new child to add to this node.
	 *     
	 * @throws NotADirectoryException
	 *     Indicates if the current node is a file, as files cannot contain DirectoryNode references.
	 * @throws FullDirectoryException
	 *     Indicates if all child references of this directory are occupied.
	 */
	public void addChild(DirectoryNode newChild) throws NotADirectoryException, FullDirectoryException {
		if (isFile) {
			throw new NotADirectoryException();
		}
		if (children[children.length-1] != null) {
			throw new FullDirectoryException();
		}
		for (int i = 0; i < children.length; i++) {
			if (children[i] == null) {
				children[i] = newChild;
				newChild.setParent(this);
				break;
			}
			else {
				if (children[i].getName().equals(newChild.getName())) {
					throw new IllegalArgumentException("ERROR: Can not have duplicate names in the directory.");
				}
			}
		}
	}
	
	/**
	 * Returns the child from this DirectoryNode based off of its name.
	 * 
	 * @param n
	 *     The name of the child to return
	 *     
	 * @return
	 *     Returns the child of the node.
	 *     
	 * @throws DirectoryNotFoundException
	 *     Indicates that the child with the name supplied doesn't exist.
	 */
	public DirectoryNode getChild(String n) throws DirectoryNotFoundException {
		int index = -1;
		for (int i = 0; i < children.length; i++) {
			if (children[i] != null && children[i].getName().equals(n)) {
				index = i;
			}
		}
		if (index == -1) {
			throw new DirectoryNotFoundException(n);
		}
		return children[index];
	}
	
	/**
	 * Removes the child from the node corresponding to the given parameter.
	 * Once removed, all children afterwards are shifted down an index.
	 * 
	 * @param child
	 *     The node to remove from the children of this node.
	 */
	public void removeChild(DirectoryNode child) {
		boolean removed = false;
		for (int i = 0; i < children.length; i++) {
			if (removed) {
				children[i-1] = children[i];
			}
			if (children[i] == child) {
				children[i] = null;
				removed = true;
			}
		}
	}

}
