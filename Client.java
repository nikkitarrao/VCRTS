import java.util.ArrayList;

public class Client extends User{
	private ArrayList<Job> jobs;

	public Client(String id, String name) {
		super(id, name);
		jobs = new ArrayList<>();
	}
	
	public void submitJob(Job job) {
		jobs.add(job);
	}
	
	public String checkJobStatus(Job job) {
		return job.getStatus();
	}
}
