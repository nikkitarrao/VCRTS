import java.util.ArrayList;
import java.util.Queue;

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
	public int computeCompletionTime(Queue<Integer>jobs) {
		int totalDuration = 0;
		while (!jobs.isEmpty()) {
            // Add the head of the queue to the cumulative sum
            totalDuration += jobs.poll();
            System.out.println("Completion time (in order of first to last job) " + totalDuration + " mins");
        }
		return totalDuration;
		
	}


}
