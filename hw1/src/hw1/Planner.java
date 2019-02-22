package hw1;
/**
 * The Planner class contains an array of Course objects.
 * Course objects can be added and removed.
 * The Planner can filter and look for specific courses.
 * The Planner can also be cloned.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 */

public class Planner {

	public final int MAX_COURSES = 50;
	private Course[] planner;
	public int size;
	
	/**
	 * Constructs an instance of the Planner with no Course objects in it.
	 * 
	 * Postcondition:
	 *     This planner has been initialized to an empty list of Courses.
	 */
	public Planner() {
		planner = new Course[MAX_COURSES];
		size = 0;
	}
	
	/**
	 * Determines the number of courses currently in the list.
	 * 
	 * Preconditions:
	 *     This planner has been instantiated.
	 *     
	 * @return
	 *     The number of Courses in this Planner.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Adds a Course object to the Planner at a specified position.
	 * 
	 * Preconditions:
     *     This Course object has been instantiated and 1 <= position <= size + 1.
     *     The number of Course objects in this Planner is less than MAX_COURSES.
     *     
     * Postconditions:
     *     The new Course is now listed in the correct preference on the list.
     *     All Courses that were originally greater than or equal to position are moved back one position.
     *     
	 * @param newCourse
	 *     The new course to add to the list.
	 * @param position
	 *     The position (preference) of this course on the list.
	 *     
	 * @throws FullPlannerException
	 *     Indicates that there is no more room in the Planner to record an additional Course.
	 * @throws IllegalArgumentException
	 *     Indicates that position is not within the valid range.
	 */
	public void addCourse(Course newCourse, int position) throws FullPlannerException{
		if (size == 50)
			throw new FullPlannerException();
		else if (position >= 1 && position <= size + 1)
		{
			for (int i = size-1; i >= position-1; i--) {
				planner[i+1] = planner[i];
			}
			planner[position-1] = newCourse;
			size++;
		}
		else
			throw new IllegalArgumentException("That is not a valid position.");
	}
	
	/**
	 * Works just like public void addCourse(Course newCourse, int position),except adds to the end of the list.
	 */
	public void addCourse(Course newCourse) throws FullPlannerException {
		addCourse(newCourse, size+1);
	}
	
	/**
	 * Removes a Course object from the Planner at a specified position.
	 * 
	 * Precondition:
	 *     This planner has been instantiated and 1 <= position <= size.
	 * 
	 * Postconditions:
	 *     The Course at the desired position has been removed.
	 *     All Courses that were originally greater than or equal to position are moved backward one position.
	 * 
	 * @param position
	 *     The position in the Planner where the Course will be removed from.
	 *     
	 * @throws IllegalArgumentException
	 *     Indicates that position is not within valid range.
	 *     
	 * Note:
	 *     position refers to the position in the Planner and not the position in the array.
	 */
	public void removeCourse(int position) {
		if (position >= 1 && position <= size)
		{
			for (int i = position-1; i < size; i++)
				planner[i] = planner[i+1];
			size--;
		}
		else
			throw new IllegalArgumentException("That is not a valid position.");
	}
	
	/**
	 * Returns the Course object at a specified position in the Planner
	 * 
	 * Precondition:
	 *     The Planner object has been instantiated and 1 <= position <= size.
	 *     
	 * @param position
	 *     Position of the Course to retrieve
	 *     
	 * @return
	 *     The Course at the specified position in this Planner object.
	 *     
	 * @throws IllegalArgumentException
	 *     Indicates that the position is not within the valid range.
	 *     
	 * Note:
	 *     position refers to the position in the Planner and not the position in the array.
	 */
	public Course getCourse(int position) {
		if (!(position >= 1 && position <= size))
			throw new IllegalArgumentException("That is not a valid position.");
		else
			return planner[position-1];
	}
	
	/**
	 * Prints the table used for displaying Courses in the Planner.
	 */
	public static void table() {
		System.out.println(String.format("%-4s%-7s%-15s%-11s%-5s%-8s%-25s",
		  "No.","Course","Name","Department","Code","Section","Instructor"));
		System.out.println("_________________________________________________"
		  + "__________________________");
	}
	
	/**
	 * Prints all Courses that are within the specified department.
	 * 
	 * Precondition:
	 *     The Planner object has been instantiated and 1 <= position <= size.
	 *     
	 * Postconditions:
	 *     Displays a neatly formatted table of each course filtered from the Planner.
	 *     Keeps the preference numbers the same.
	 * 
	 * @param planner
	 *     The list of courses to search in.
	 * @param department
	 *     The 3 letter department code for a Course.
	 */
	public static void filter(Planner planner, String department) {
		table();
		for (int i = 1; i < planner.size()+1; i++) {
			Course copy = planner.getCourse(i);
			if (copy.getDepartment().equals(department))
			{
				System.out.println(String.format("%-4d%-22s%-11s%-5d%-8d%s", 
				  i,copy.getName(),copy.getDepartment(),copy.getCode(),
				  copy.getSection(),copy.getInstructor()));
			}
		}
	}
	
	/**
	 * Checks whether a certain Course is already in the list.
	 * 
	 * Precondition:
	 *     This Planner and Course has both been instantiated.
	 * 
	 * @param course
	 *     The Course we are looking for.
	 *     
	 * @return
	 *     True if the Planner contains this Course, false otherwise
	 */
	public boolean exists(Course course) {
		for (int i = 0; i < size(); i++) {
			if (planner[i].equals(course))
				return true;
		}
		return false;
	}
	
	/**
	 * Creates a copy of this Planner.
	 * Subsequent changes to the copy will not affect the original and vice versa.
	 * 
	 * Preconditions:
	 *     This Planner object has been instantiated.
	 *     
	 * @returns
	 *     A copy(backup) of this Planner object.
	 */
	public Object clone(){
		Planner backup = new Planner();
		for (int i = 0; i < size(); i++) {
			try {
				backup.addCourse((Course) planner[i].clone());
			} catch (FullPlannerException e) {
				System.out.print(e.getMessage());;
			}
		}
		return backup;
	}
	
	/**
	 * Prints a neatly formatted table of each item in the list with its position number as shown in the sample output.
	 * 
	 * Preconditions:
	 *     This Planner has been instantiated.
	 *     
	 * Postconditions: Displays a neatly formatted table of each course from the Planner.
	 */
	public void printAllCourses() {
		table();
		System.out.println(toString());
	}
	
	/**
	 * Gets the String representation of this Planner object,
	 * which is a neatly formatted table of each Course in the Planner on its own line with its position number as shown in the sample output.
	 * 
	 * @return
	 *     The String representation of this Planner object.
	 */
	public String toString() {
		String x = "";
		for (int i = 0; i < size(); i++) {
			x += String.format("%-4d%-22s%-11s%-5d%-8d%s%n", 
			  i+1,planner[i].getName(),planner[i].getDepartment(),planner[i].getCode(),
			  planner[i].getSection(),planner[i].getInstructor());
		}
		return x;
	}
}
