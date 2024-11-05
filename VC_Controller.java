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
	
	public void collectJobs() {
		
	}
	
	//compute completion time
	//duration length + duration length in the queue
	public ArrayList<String> computeCompletionTime(Queue<Integer>jobs) {
		int totalDuration = 0;
		ArrayList<String> computedTimes = new ArrayList<String>();
		while (!jobs.isEmpty()) {
            // Add the head of the queue to the cumulative sum
            totalDuration += jobs.poll(); //.poll() deletes the value in the queue that it has already read through
            computedTimes.add(totalDuration + " mins");
            System.out.println("Completion time (in order of first to last job): " + totalDuration + " mins");
            System.out.println("times: " + computedTimes);
            
        }
		return computedTimes ;
		
	}


}
