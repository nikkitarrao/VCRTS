import java.util.ArrayList;

public class VC_Controller extends User {
	private int redundancyLevel;
	private ArrayList<Job> activeJobs; //queue/linked list
	private ArrayList<Job> completedJobs;

	public VC_Controller(String id, String name, String password) {
		super(id, name, password);
		activeJobs = new ArrayList<>();
		completedJobs = new ArrayList<>();
	}
	
	//compute completion time
	//duration length + duration length in the queue


}
