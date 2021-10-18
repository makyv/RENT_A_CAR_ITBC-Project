package controller;


import dao.CarDao;
import dao.CarDaoSQL;
import dao.ContractDaoSQL;
import dao.UserDaoSQL;
import model.AddCarModel;
import model.CarModel;
import model.SearchCarModel;
import model.request.UpdateCarRequestModel;
import model.response.CarResponseModel;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
public class carController {
    static CarDaoSQL cd = new CarDaoSQL();
    static UserDaoSQL ud = new UserDaoSQL();
    static ContractDaoSQL conDao = new ContractDaoSQL();



    @GetMapping("/cars")
    public List<CarModel> getAllCars(){
        return cd.getAllCars();
    }



    @GetMapping("/cars/search")
    public List<CarModel> searchCars(@RequestParam(required = false) String make,
                                             @RequestParam(required = false) Integer year,
                                             @RequestParam(required = false) String model,
                                             @RequestParam(required = false) Boolean automatic,
                                             @RequestParam(required = false) Double price,
                                             @RequestParam(required = false) Integer power,
                                             @RequestParam(required = false) Integer doors){
        return cd.searchCar(new SearchCarModel(make, year, model, automatic, price, power, doors),
                cd.getAllCars());
    }


    @GetMapping("/cars/{carId}")
    public CarModel getCar(@PathVariable("carId") String carId){
        return cd.getCar(carId);
    }



    @PatchMapping("/cars/{carId}")
    public void updateCar(@PathVariable("carId") String carId,
                          @RequestHeader("authorization") String adminId,
                          @RequestBody UpdateCarRequestModel info){
        if(ud.isAdmin(adminId)){
            cd.updateCar(carId, info);
        }
    }


    @DeleteMapping("/cars/{carId}")
    public void deleteCar(@PathVariable("carId") String carId, @RequestHeader("autorization") String adminId){
            if(ud.isAdmin(adminId))
        cd.deleteCar(carId);
    }


    @PostMapping("/cars")
    public void addCar(@RequestHeader("authorization") String adminId,
                       @RequestBody AddCarModel cm){
        if(ud.isAdmin(adminId))
            cd.addCar(cm);
    }


    @GetMapping("/cars/available")
    public List<CarModel> availableCars(@RequestParam String startDate,
                                                @RequestParam String endDate){
        LocalDate startDateLocal = LocalDate.parse(startDate);
        LocalDate endDateLocal = LocalDate.parse(endDate);
        return cd.availableCars(startDateLocal, endDateLocal);
    }


    @GetMapping("/cars/available/search")
    public List<CarModel> searchCars(@RequestParam String startDate,
                                                @RequestParam String endDate,
                                                @RequestParam(required = false) String make,
                                                @RequestParam(required = false) String model,
                                                @RequestParam(required = false) Integer year,
                                                @RequestParam(required = false) Boolean automatic,
                                                @RequestParam(required = false) Double price,
                                                @RequestParam(required = false) Integer power,
                                                @RequestParam(required = false) Integer doors){
        LocalDate startDateLocal = LocalDate.parse(startDate);
        LocalDate endDateLocal = LocalDate.parse(endDate);
        return cd.searchCar(new SearchCarModel(make, year, model, automatic, price, power, doors),
                cd.availableCars(startDateLocal, endDateLocal));

    }


    @GetMapping("/cars/{carId}/calendar")
    public List<LocalDate> getCarOccupied(@PathVariable("carId") String id){
        return conDao.getCarOccupiedDates(id);
    }




}
