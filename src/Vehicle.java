public class Vehicle {
    private String make;
    private String model;
    private String year;
    private String vin;

    // Constructor
    public Vehicle(String make, String model,String year, String vin) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.vin = vin;
    }

    // Getters and Setters (optional if you need to access or modify the fields later)
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    // Optional: A toString method for easy printing of the vehicle information
    @Override
    public String toString() {
        return "Make=" + make + ", Model=" + model + ", Year=" + year + ", VIN=" + vin;
    }
}
