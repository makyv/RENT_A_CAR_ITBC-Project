package dao;

import connection.DatabaseConnection;
import model.AddCarModel;
import model.CarModel;
import model.SearchCarModel;
import model.request.UpdateCarRequestModel;
import model.response.CarResponseModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CarDaoSQL implements CarDao{

    static final Connection conn = DatabaseConnection.getConnection();
    private ContractDao cd = new ContractDaoSQL();



    @Override
    public List<CarModel> getAllCars() {
        List<CarModel> allCars = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM cars");
            while(rs.next()) {
                CarModel newCar = new CarModel (
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getDouble(8),
                        rs.getInt(9),
                        rs.getString(10),
                        rs.getInt(11),
                        rs.getBoolean(12),
                        rs.getString(13),
                        rs.getString(14));

                allCars.add(newCar);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCars;
    }

    @Override
    public List<CarModel> searchCar(SearchCarModel sc, List<CarModel> cars) {
        List<CarModel> search = new ArrayList<>();
        if (sc.getAutomatic() == null && sc.getDoors() == null
                && sc.getModel() == null && sc.getMake() == null &&sc.getPower() == null
                && sc.getPrice() == null && sc.getYear() == null){
            return cars;
        }
        for (var car : cars){
            if ((sc.getAutomatic() == null || sc.getAutomatic() == car.getAutomatic())
                    && (sc.getYear() == null || sc.getYear() <= car.getYear())
                    && (sc.getDoors() == null || sc.getDoors() == car.getDoors())
                    && (sc.getModel() == null || car.getModel().toLowerCase().contains(sc.getModel().toLowerCase()))
                    && (sc.getMake() == null || car.getMake().toLowerCase().contains(sc.getMake().toLowerCase()))
                    && (sc.getPower() == null || sc.getPower() >= car.getPower())
                    && (sc.getPrice() == null || sc.getPrice() >= car.getPrice())){
                search.add(car);
            }
        }
        return search;
    }

    @Override
    public CarModel getCar(String carId) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM cars WHERE car_id = " + carId);
            if (rs.next())
                return new CarModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getDouble(8),
                        rs.getInt(9),
                        rs.getString(10),
                        rs.getInt(11),
                        rs.getBoolean(12),
                        rs.getString(13),
                        rs.getString(14));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public CarModel updateCar(String carId, UpdateCarRequestModel info) {
        try {
            PreparedStatement st = conn.prepareStatement("UPDATE cars" +
                    " SET car_id = ?," +
                    "licence_plate = ?," +
                    "make = ?," +
                    "model = ?," +
                    "year = ?," +
                    "engine_capacity = ?," +
                    "color = ?," +
                    "price = ?," +
                    "doors = ?," +
                    "size = ?," +
                    "power = ?," +
                    "automatic = ?," +
                    "fuel = ?," +
                    "image = ?");
            st.setString(1, info.getLicencePlate());
            st.setString(2, info.getMake());
            st.setString(3, info.getModel());
            st.setInt(4, info.getYear());
            st.setInt(5, info.getEngineCapacity());
            st.setString(6, info.getColor());
            st.setDouble(7, info.getPrice());
            st.setInt(8, info.getDoors());
            st.setString(9, info.getSize());
            st.setInt(10, info.getPower());
            st.setBoolean(11, info.isAutomatic());
            st.setString(12, info.getFuel());
            st.setString(13, info.getImage());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteCar(String carId) {
        try {
            PreparedStatement st = conn.prepareStatement("DELETE FROM cars WHERE car_id = " + carId);
            st.setString(1, carId);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public AddCarModel addCar(AddCarModel cm) {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO cars VALUES (" +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?");
            st.setString(1, cm.getCarId().toString());
            st.setString(2, cm.getLicencePlate());
            st.setString(3, cm.getMake());
            st.setString(4, cm.getModel());
            st.setInt(5, cm.getYear());
            st.setInt(6, cm.getEngineCapacity());
            st.setString(7, cm.getColor());
            st.setDouble(8, cm.getPrice());
            st.setInt(9, cm.getDoors());
            st.setString(10, cm.getSize());
            st.setInt(11, cm.getPower());
            st.setBoolean(12, cm.isAutomatic());
            st.setString(13, cm.getFuel());
            st.setString(14, cm.getImage());
            st.executeUpdate();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isCarAvailable(LocalDate startDate, LocalDate endDate, String carId) {
        List<LocalDate> desiredDates = startDate.datesUntil(endDate.plusDays(1)).collect(Collectors.toList());
        List<LocalDate> unavailableDates = cd.getCarOccupiedDates(carId);
        for (var date : desiredDates){
            if (unavailableDates.contains(date)){
                return false;
            }
        }
        return true;

    }

    @Override
    public double getPrice(String id) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT price FROM cars WHERE car_id = " + id);
            rs.next();
            return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<CarResponseModel> searchAvailableCars(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public List<CarModel> availableCars(LocalDate startDate, LocalDate endDate) {
        List<CarModel> allCars = getAllCars();
        List<CarModel> availableCars = new ArrayList<>();
        for (var car : allCars){
            if (isCarAvailable(startDate, endDate, car.getCarId())){
                availableCars.add(car);
            }
        }
        return availableCars;
    }


}
