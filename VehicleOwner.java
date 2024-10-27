public class VehicleOwner extends User{
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private String vehicleResidencyTime;
    private Vehicle vehicle;
    
    // Constructor
    public VehicleOwner(String id, String name, String username, String email, String phoneNumber, String password, String vehicleResidencyTime, Vehicle vehicle) {
    	super(name, id);
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.vehicle = vehicle;
    }

    // Getters and Setters (optional if you need to access or modify the fields later)
    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.username = username;
    }
    public String getUserName() {
        return name;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
   
    public void setPassword(String password) {
    	this.password=password;
    }
    public void setResidencyTime(String vehicleResidencyTime) {
    	this.vehicleResidencyTime = vehicleResidencyTime;
    }
    public String vehicleResidencyTime() {
		return vehicleResidencyTime;
    	
    }
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
    public void rentVehicle(Vehicle vehicle) {
    	
    }

    // Optional: A toString method for easy printing of the user information
    @Override
    public String toString() {
        return "Owner [Name=" + name + ", Email=" + email + ", Phone=" + phoneNumber + ", Vehicle=" + vehicle + "]";
    }
}
