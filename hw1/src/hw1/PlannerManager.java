package hw1;

import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * PlannerManager runs a menu driven application which first creates an empty Planner object.
 * The program prompts the user for a command to execute an operation.
 * Once a command has been chosen, the program may ask the user for additional information if necessary,
 * and performs the operation.
 * 
 * @author Tennyson Cheng
 *     email: tennyson.cheng@stonybrook.edu
 *     Stony Brook ID: 112336491
 */

public class PlannerManager {

	public static void main(String[] args) throws FullPlannerException {
		Planner planner1 = new Planner(); //The primary planner
		Planner planner2 = new Planner(); //The backup planner
		Scanner kb = new Scanner(System.in);
		String input = ""; //User input
		 while (!input.equalsIgnoreCase("Q")) {
			System.out.println("(A) Add Course\n(G) Get Course\n(R) Remove Course\n"
			  + "(P) Print Courses in Planner\n(F) Filter by Department Code\n"
			  + "(L) Look For Course\n(S) Size\n"
			  + "(B) Backup\n(PB) Print Courses in Backup\n(RB) Revert to Backup\n"
			  + "(Q) Quit");
			System.out.print("\nEnter a selection: ");
			input = kb.nextLine(); //Takes in the next user selection
			String name = null;
			String department = null;
			String instructor = null;
			int code = 0;
			byte section = 0;
			int position = 0;
			Course copy = null;
			switch (input) {
			case "A": //Adds a new course into the list.
			case "a":
				try {
					System.out.print("\nEnter a course name: ");
					name = kb.nextLine();
					System.out.print("Enter department: ");
					department = kb.next();
					System.out.print("Enter course code: ");
					code = kb.nextInt();
					System.out.print("Enter course section: ");
					section = kb.nextByte();
					kb.nextLine();
					System.out.print("Enter instructor: ");
					instructor = kb.nextLine();
					System.out.print("Enter position: ");
					position = kb.nextInt();
					kb.nextLine();
					copy = new Course(name,department,instructor,code,section);
					if (planner1.exists(copy))
						throw new IllegalArgumentException("The course already exists in the planner.");
					else
					{
						planner1.addCourse(copy, position);
						System.out.println("\n" + department + " " + code + "." + section +
					  " successfully added to planner.\n");
					}
				} catch (InputMismatchException e) {
					System.out.print("\nPlease enter a proper value\n\n");
					kb.nextLine();
				} catch (IllegalArgumentException e) {
					System.out.println("\n" + e.getMessage() + "\n");
				} catch (FullPlannerException e) {
					System.out.println("\n" + e.getMessage() + "\n");
				}
				break;
			case "G": //Displays information of a Course at the given position.
			case "g":
				try {
					System.out.print("\nEnter a position: ");
					position = kb.nextInt();
					kb.nextLine();
					copy = planner1.getCourse(position);
					System.out.print("\n");
					Planner.table();
					System.out.println(String.format("%-4d%-22s%-11s%-5d%-8d%s%n", 
					  position,copy.getName(),copy.getDepartment(),copy.getCode(),
					  copy.getSection(),copy.getInstructor()));
				} catch (InputMismatchException e) {
					System.out.print("\nPlease enter a proper integer\n\n");
					kb.nextLine();
				} catch (IllegalArgumentException e) {
					System.out.println("\n" + e.getMessage() + "\n");
				}
				break;
			case "R": //Removes the Course at the given position.
			case "r":
				try {
					System.out.print("\nEnter a position: ");
					position = kb.nextInt();
					kb.nextLine();
					copy = planner1.getCourse(position);
					planner1.removeCourse(position);
					System.out.println("\n" + copy.getDepartment() +
					  " " + copy.getCode() + "." + copy.getSection() +
					  " has been successfully removed from the planner.\n");
				} catch (InputMismatchException e) {
					System.out.print("\nPlease enter a proper integer\n\n");
					kb.nextLine();
				} catch (IllegalArgumentException e) {
					System.out.println("\n" + e.getMessage() + "\n");
				}
				break;
			case "P": //Displays all courses in the list.
			case "p":
				System.out.println("\nPlanner:");
				planner1.printAllCourses();
				break;
			case "F": //Displays courses that match the given department code.
			case "f":
				System.out.print("\nEnter department code: ");
				department = kb.next();
				kb.nextLine();
				System.out.print("\n");
				Planner.filter(planner1, department);
				System.out.print("\n");
				break;
			case "L": //Determines whether the course with the given attributes is in the list.
			case "l":
				try {
					System.out.print("\nEnter a course name: ");
					name = kb.nextLine();
					System.out.print("Enter department: ");
					department = kb.next();
					System.out.print("Enter course code: ");
					code = kb.nextInt();
					System.out.print("Enter course section: ");
					section = kb.nextByte();
					kb.nextLine();
					System.out.print("Enter instructor: ");
					instructor = kb.nextLine();
					copy = new Course(name,department,instructor,code,section);
					boolean found = false;
					for (int i = 1; i < planner1.size()+1; i++) {
						if (planner1.getCourse(i).equals(copy))
						{
							System.out.println("\n" + copy.getDepartment() +
							  " " + copy.getCode() + "." + copy.getSection() +
							  " is found in the planner at position " + i + ".\n");
							found = true;
						}
					}
					if (!found)
						System.out.println("\nThe specified course is not in the planner.\n");
				} catch (InputMismatchException e) {
					System.out.print("\nPlease enter a proper value\n\n");
					kb.nextLine();
				} catch (IllegalArgumentException e) {
					System.out.println("\n" + e.getMessage() + "\n");
				}
				break;
			case "S": //Determines the number of courses in the Planner.
			case "s":
				System.out.println("\nThere are " + planner1.size() + " courses in the planner.\n");
				break;
			case "B": //Creates a copy of the given Planner. Changes to the copy will not affect the original and vice versa.
			case "b":
				planner2 = (Planner) planner1.clone();
				System.out.println("\nCreated a backup of the current planner.\n");
				break;
			case "PB": //Displays all the courses from the backed-up list.
			case "pb":
				System.out.println("\nPlanner (Backup):");
				planner2.printAllCourses();
				break;
			case "RB": //Reverts the current Planner to the backed up copy.
			case "rb":
				planner1 = (Planner) planner2.clone();
				System.out.println("\nPlanner successfully reverted to the backup copy.\n");
				break;
			case "Q": //Terminates the program.
			case "q":
				System.out.println("\nProgram terminating successfully...");
				break;
			default:
				System.out.println("\nThat is not a proper selection\n");
				break;
			}
		}
	}
}
