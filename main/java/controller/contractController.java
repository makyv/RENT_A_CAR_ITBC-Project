package controller;

import dao.CarDaoSQL;
import dao.ContractDaoSQL;
import dao.UserDaoSQL;
import model.ContractModel;
import model.request.ApproveContractRequestModel;
import model.request.ContractSignedRequestModel;
import model.response.ContractInfoResponseModel;
import model.response.ContractResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@RestController
public class contractController {
    private static ContractDaoSQL conDao = new ContractDaoSQL();
    private static UserDaoSQL ud = new UserDaoSQL();
    private static CarDaoSQL cd = new CarDaoSQL();

    private static double getContractPrice(LocalDate startDate, LocalDate endDate, String carId) {
        double price = cd.getPrice(carId);
        int days = (int) DAYS.between(startDate, endDate) + 1;
        return price * days;
    }


    @PostMapping("/contracts/sample")
    public ContractResponseModel getContractSample(
            @RequestBody ContractResponseModel con) {
        double contractPrice = getContractPrice(con.getStartDate(),
                con.getEndDate(), con.getCarId());

        return new ContractResponseModel(con.getUserId(), con.getCarId(),
                con.getStartDate(), con.getEndDate(), contractPrice, false);
    }
//2 /contracts - POST
//Додаје нови уговор (Клијент)

    @PostMapping("/contracts")
    public ContractInfoResponseModel postSingedContract(@RequestBody ContractSignedRequestModel con) {

        if (conDao.userPendingContract(con.getUserId())) {
            return new ContractInfoResponseModel(false, "User already has pending contract!!");
        }
        if (!cd.isCarAvailable(con.getStartDate(), con.getEndDate(), con.getCarId())) {
            return new ContractInfoResponseModel(false, "Car is not available for whole duration of the contract!!");
        }
        conDao.addNewContract(con);
        return new ContractInfoResponseModel(true, "Contract created, waiting for approval!!");
    }

//3 /contracts - GET
//Администратори могу да виде све уговоре

    @GetMapping("/contracts")
    public List<ContractModel> getAllcontracts(@RequestHeader("authorization") String adminId) {
        if (ud.isAdmin(adminId)) {
            return conDao.getAllContracts();
        }
        return null;
    }
//4 /contracts/pending - GET
//Администратори могу да виде све неразрешене уговоре

    @GetMapping("/contracts/pending")
    public List<ContractModel> getAllPendingContracts
            (@RequestHeader("authorization") String adminId) {
        if (ud.isAdmin(adminId)) {
            return conDao.getAllPendingContracts();
        }
        return null;
    }

//5 /contracts/{contractId}/approval - POST
//Администратор одобрава или одбија уговор
//Ако га одбије - уговор се брише из базе
//Ако га одобри - Уговор добија approved = true

    @PostMapping("/contracts/{contractId}/approval")
    public void approveContract(@RequestHeader("authorization") String adminId,
                                @PathVariable("contractId") String contractId,
                                @RequestBody ApproveContractRequestModel adminApproval){

        if (!ud.isAdmin(adminId)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        if (adminApproval.isApproved()){
            conDao.updateApprove(contractId, true);
        }
        else
            conDao.delete(contractId);
    }

//6  /contracts/{userId}/history - GET
//Дохвата се историја уговора за клијента са датим id

    @GetMapping("/contracts/{userId}/history")
    public List<ContractModel> getContractHistory(@PathVariable("userId") String id) {
        return conDao.getContractHistory(id);
    }



}






