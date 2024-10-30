import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Client extends User{
	public Queue<Job> jobs;

	public Client(String id, String name, String password) {
		super(id, name, password);
		jobs = new LinkedList<>();
	}
	
	public void submitJob(Job job) throws FileNotFoundException {
		// adds element to the end of the list
		jobs.offer(job); 
		
		//store to file
    	PrintStream output = new PrintStream(new File("Jobs.txt"));
    	//prints info gathered to printstream output folder
      	output.println("Job Information: " + job);
      	output.println("");
	}
	
	public String checkJobStatus(Job job) {
		return job.getStatus();
	}
}
