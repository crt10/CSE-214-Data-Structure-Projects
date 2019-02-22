package hw1;

public class FullPlannerException extends Exception{
	
	public FullPlannerException () {
        super ("There is no more space in the planner.");
    }
}
