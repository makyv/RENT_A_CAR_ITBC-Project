package model;

public class SearchCarModel {
    private String make, model;
    private Integer year, power, doors;
    private Double price;
    private Boolean automatic;

    public SearchCarModel(String make, Integer year, String model, Boolean automatic, Double price, Integer power, Integer doors) {
        this.make = make;
        this.year = year;
        this.model = model;
        this.automatic = automatic;
        this.price = price;
        this.power = power;
        this.doors = doors;
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

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getDoors() {
        return doors;
    }

    public void setDoors(Integer doors) {
        this.doors = doors;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAutomatic() {
        return automatic;
    }

    public void setAutomatic(Boolean automatic) {
        this.automatic = automatic;
    }
}
