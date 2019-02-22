package hw1;
/**
 * The Course class contains general information such as course name, course code, course section and instructor.
 * Course objects can be cloned and can see if they are equal to other course objects.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 */

public class Course {

	public String name; //Name of the course.
	public String department; //Department of the course.
	public String instructor; //Instructor of the course.
	public int code; //Course code for the course.
	public byte section; //Section of the course.

	/**
	 * Constructs an instance of Course with the supplied parameters.
	 * 
	 * Postcondition:
	 *     This Course has been initialized with the supplied parameters.
	 *     
	 * @param n
	 *     The name of the course.
	 * @param d
	 *     The department of the course.
	 * @param i
	 *     The instructor of the course.
	 * @param c
	 *     The course code for the course.
	 * @param s
	 *     The section for the course.
	 */
	public Course(String n, String d, String i, int c, byte s) {
		setName(n);
		setDepartment(d);
		setInstructor(i);
		setCode(c);
		setSection(s);
	}
	
	/**
	 * Sets the name for the Course.
	 * 
	 * @param n
	 *     The name to set.
	 */
	public void setName(String n) {
		name = n;
	}
	
	/**
	 * Sets the department for the Course.
	 * 
	 * @param d
	 *     The department to set.
	 */
	public void setDepartment(String d) {
		department = d;
	}
	
	/**
	 * Sets the instructor for the Course.
	 * 
	 * @param n
	 *     The instructor to set.
	 */
	public void setInstructor(String i) {
		instructor = i;
	}
	
	/**
	 * Sets the course code for the Course.
	 * 
	 * @param n
	 *     The course code to set.
	 *     
	 * @throws IllegalArgumentException
	 *     Indicates that the course code can not be a negative number.
	 */
	public void setCode(int c) {
		if (c < 0)
			throw new IllegalArgumentException("Course codes cannot be negative.");
		else
			code = c;
	}
	
	/**
	 * Sets the section for the Course.
	 * 
	 * @param n
	 *     The section to set.
	 *     
	 * @throws IllegalArgumentException
	 *     Indicates that the section can not be a negative number.
	 */
	public void setSection(byte s) {
		if (s < 0)
			throw new IllegalArgumentException("Course sections cannot be negative.");
		else
			section = s;
	}
	
	/**
	 * Returns the current name of the Course.
	 * 
	 * @return
	 *     returns the name of the Course.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the current department of the Course.
	 * 
	 * @return
	 *     returns the department of the Course.
	 */
	public String getDepartment() {
		return department;
	}
	
	/**
	 * Returns the current instructor of the Course.
	 * 
	 * @return
	 *     returns the instructor of the Course.
	 */
	public String getInstructor() {
		return instructor;
	}
	
	/**
	 * Returns the current course code for the Course.
	 * 
	 * @return
	 *     returns the course code for the Course.
	 */
	public int getCode() {
		return code;
	}
	
	/**
	 * Returns the current section of the Course.
	 * 
	 * @return
	 *     returns the section of the Course.
	 */
	public byte getSection() {
		return section;
	}
	
	/**
	 * Creates a copy of this Course.
	 * Subsequent changes to the copy will not affect the original and vice versa.
	 * 
	 * Preconditions:
	 *     This Course object has been instantiated.
	 *     
	 * @returns
	 *     A copy of this Course object.
	 *     
	 * Note:
	 *     The return value must be typecasted to a Course before it can be used.
	 */
	public Object clone() {
		return new Course(name,department,instructor,code,section);
	}
	
	/**
	 * Checks if the Object, obj, is equal to this Course object.
	 * 
	 * @return
	 *     True if obj refers to a Course object with the same attributes as this Course.
	 *     Otherwise, the return value is false.
	 */
	public boolean equals (Object obj) {
		Course objCourse = (Course) obj;
		return (objCourse.getName().equals(name) && 
		  objCourse.getDepartment().equals(department) &&
		  objCourse.getInstructor().equals(instructor) &&
		  objCourse.getCode() == code &&
		  objCourse.getSection() == section);
	}
}
