package model;

import java.util.UUID;

public class CarModel {
    private String carId;
    private String licencePlate;
    private String make;
    private String model;
    private Integer year;
    private Integer engineCapacity;
    private String color;
    private Double price;
    private Integer doors;
    private String size;
    private Integer power;
    private Boolean automatic;
    private String fuel;
    private String image;

    public CarModel(String carId, String licencePlate, String make, String model, Integer year, Integer engineCapacity, String color, Double price, Integer doors, String size, Integer power, Boolean automatic, String fuel, String image) {

        this.carId = carId;
        this.licencePlate = licencePlate;
        this.make = make;
        this.model = model;
        this.year = year;
        this.engineCapacity = engineCapacity;
        this.color = color;
        this.price = price;
        this.doors = doors;
        this.size = size;
        this.power = power;
        this.automatic = automatic;
        this.fuel = fuel;
        this.image = image;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(Integer engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDoors() {
        return doors;
    }

    public void setDoors(Integer doors) {
        this.doors = doors;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Boolean getAutomatic() {
        return automatic;
    }

    public void setAutomatic(Boolean automatic) {
        this.automatic = automatic;
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

    @Override
    public String toString() {
        return  " CarId: " + carId + "\n" +
                " Licence Plate: " + licencePlate + "\n" +
                " Make: " + make + "\n" +
                " Model: " + model + "\n" +
                " Year: " + year + "\n" +
                " Engine Capacity: " + engineCapacity + "\n" +
                " Color: " + color + "\n" +
                " Price: " + price + "\n" +
                " Doors: " + doors + "\n" +
                " Size: " + size + "\n" +
                " Power: " + power + "\n" +
                " Automatic: " + automatic + "\n" +
                " Fuel: " + fuel + "\n" +
                " Image: " + image + "\n" +
                '}';
    }
}
