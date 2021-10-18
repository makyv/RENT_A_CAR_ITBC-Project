package dao;

import model.ContractModel;
import model.request.ContractSignedRequestModel;

import java.time.LocalDate;
import java.util.List;

public interface ContractDao {
    void addNewContract(ContractSignedRequestModel cm);
    List<ContractModel> getAllContracts();
    List<ContractModel> getAllApprovedContracts();
    List<ContractModel> getAllPendingContracts();
    List<LocalDate> getCarOccupiedDates(String carId);
    List<ContractModel> getContractHistory(String userId);
    void delete(String contractId);
    void updateApprove(String contractId, Boolean approved);
    boolean userPendingContract(String userId);

}
