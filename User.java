public class User {
    private String name;
    private String email;
    private String phoneNumber;
    private String userName;
    private String password;
    private Vehicle vehicle;
    
    // Constructor
    public User(String name, String email, String phoneNumber, String userName, String password, Vehicle vehicle) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.password = password;
        this.vehicle = vehicle;
    }

    // Getters and Setters (optional if you need to access or modify the fields later)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public void setUserName(String userName) {
    	this.userName = userName;
    }
    public void setPassword(String password) {
    	this.password=password;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    // Optional: A toString method for easy printing of the user information
    @Override
    public String toString() {
        return "User [Name=" + name + ", Email=" + email + ", Phone=" + phoneNumber + ", Vehicle=" + vehicle + "]";
    }
}
