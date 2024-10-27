
public class Job {
	private String status;
	
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
	
}
