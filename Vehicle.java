public class Vehicle {
    private String make;
    private String model;
    private int year;
    private String vin;

    // Constructor
    public Vehicle(String make, String model, int year, String vin) {
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
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
        return "Vehicle [Make=" + make + ", Model=" + model + ", Year=" + year + ", VIN=" + vin + "]";
    }
}
