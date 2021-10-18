package model.request;

public class UpdateCarRequestModel {
    private String licencePlate, make, model, color, size, fuel, image;
    private int year, engineCapacity, doors, power;
    private double price;
    private boolean automatic;

    public UpdateCarRequestModel(String licencePlate, String make, String model, String color, String size, String fuel, String image, int year, int engineCapacity, int doors, int power, double price, boolean automatic) {
        this.licencePlate = licencePlate;
        this.make = make;
        this.model = model;
        this.color = color;
        this.size = size;
        this.fuel = fuel;
        this.image = image;
        this.year = year;
        this.engineCapacity = engineCapacity;
        this.doors = doors;
        this.power = power;
        this.price = price;
        this.automatic = automatic;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }
}
