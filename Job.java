
public class Job {
	private int duration;
	private int completionTime;
	private String status;
	private boolean isCompleted;
	
	//Constructor
	public Job (int duration) {
		this.duration = duration;
		if (status.equals("Completed") || status.equals("In Progress")) {
			this.setStatus(status);
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public void markCompleted() {
        this.isCompleted = true;
    }

	public int getDuration() {
		return duration;
	}
	
	

	
}
