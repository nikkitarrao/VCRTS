import java.util.ArrayList;

public class VC_Controller extends User {
	private int redundancyLevel;
	private ArrayList<Job> activeJobs;
	private ArrayList<Job> completedJobs;

	public VC_Controller(String id, String name) {
		super(id, name);
		activeJobs = new ArrayList<>();
		completedJobs = new ArrayList<>();
	}
}
