package dao;

import model.AddCarModel;
import model.CarModel;
import model.SearchCarModel;
import model.request.UpdateCarRequestModel;
import model.response.CarResponseModel;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface CarDao {
    List<CarModel> getAllCars();

    List<CarModel> searchCar(SearchCarModel sc, List<CarModel> cars);

    CarModel getCar(String carId);

    CarModel updateCar(String carId, UpdateCarRequestModel info);

    void deleteCar(String carId);

    AddCarModel addCar(AddCarModel cm);

    List<CarModel> availableCars(LocalDate startDate, LocalDate endDate);

    boolean isCarAvailable(LocalDate startDate, LocalDate endDate, String carId);

    double getPrice(String id);

    List<CarResponseModel> searchAvailableCars(LocalDate startDate, LocalDate endDate);
 //   List<LocalDate> listOfDates



}
