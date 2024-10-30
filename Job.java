
public class Job {
	private String duration;
	private String completionTime;
	private String status;
	private boolean isCompleted;
	
	//Constructor
	public Job () {
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
	
}
