public class VehicleOwner extends User{
    private String email;
    private String phoneNumber;
    private String vehicleResidencyTime;
    private Vehicle vehicle;
    
    // Constructor
    public VehicleOwner(String name, String id, String password,  String email, String phoneNumber, String vehicleResidencyTime, Vehicle vehicle) {
    	super(name, id, password);
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.vehicle = vehicle;
    }

    // Getters and Setters (optional if you need to access or modify the fields later)
    public String getName() {
        return name;
    }

    public String getUserName() {
        return name;
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
