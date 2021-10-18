package dao;

import connection.DatabaseConnection;
import model.ContractModel;
import model.request.ContractSignedRequestModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContractDaoSQL implements ContractDao{
    static final Connection conn = DatabaseConnection.getConnection();

    @Override
    public void addNewContract(ContractSignedRequestModel cm) {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO contracts VALUES (" +
                    "?, ?, ?, ?, ?, ?, ?, false");
            st.setString(1, cm.getContract_id().toString());
            st.setString(2, cm.getUserId());
            st.setString(3, cm.getCarId());
            st.setDate(4, Date.valueOf(cm.getStartDate()));
            st.setDate(5, Date.valueOf(cm.getEndDate()));
            st.setDouble(6, cm.getTotalPrice());
            st.setBoolean(7, cm.isSigned());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<ContractModel> getAllContracts() {
        List<ContractModel> allContracts = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM contracts");
            while(rs.next()){
                ContractModel newContract = new ContractModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4).toLocalDate(),
                        rs.getDate(5).toLocalDate(),
                        rs.getDouble(6),
                        rs.getBoolean(7),
                        rs.getBoolean(8)
                );
                allContracts.add(newContract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allContracts;
    }

    @Override
    public List<ContractModel> getAllApprovedContracts() {
        List<ContractModel> allApContracts = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM contracts WHERE approved = true");
            while(rs.next()){
                ContractModel cm = new ContractModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4).toLocalDate(),
                        rs.getDate(5).toLocalDate(),
                        rs.getDouble(6),
                        rs.getBoolean(7),
                        rs.getBoolean(8));
                allApContracts.add(cm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allApContracts;
    }

    public List<ContractModel> getAllPendingContracts() {
        List<ContractModel> allPenContracts = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM contracts WHERE approved = false");
            while(rs.next()){
                ContractModel cm = new ContractModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4).toLocalDate(),
                        rs.getDate(5).toLocalDate(),
                        rs.getDouble(6),
                        rs.getBoolean(7),
                        rs.getBoolean(8));
                allPenContracts.add(cm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allPenContracts;
    }

    @Override
    public List<LocalDate> getCarOccupiedDates(String carId) {
        List<LocalDate> dates = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT start_date, end_date " +
                    "FROM contracts " +
                    "WHERE car_id = " + carId);
            while(rs.next()){
                dates.addAll(LocalDate.parse(rs.getString(1))
                        .datesUntil(LocalDate.parse(rs.getString(2))
                                .plusDays(1)).collect(Collectors.toList()));
            }
            return dates;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ContractModel> getContractHistory(String userId) {

        List<ContractModel> contracts = new ArrayList<>();
        try {
            Statement st= conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM contracts" +
                    " WHERE user_id = " + userId);
            while (rs.next()){
                        ContractModel cm = new ContractModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4).toLocalDate(),
                        rs.getDate(5).toLocalDate(),
                        rs.getDouble(6),
                        rs.getBoolean(7),
                        rs.getBoolean(8));
                contracts.add(cm);
            }
            return contracts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contracts;
    }



    @Override
    public void delete(String contractId) {
        try {
            PreparedStatement st = conn.prepareStatement("DELETE FROM contracts WHERE contract_id = " + contractId);
            st.setString(1, contractId);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateApprove(String contractId, Boolean approved) {
        try {
            PreparedStatement st = conn.prepareStatement("UPDATE contracts SET approved = ? WHERE contract_id = ?");
            st.setBoolean(1, approved);
            st.setString(2, contractId);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean userPendingContract(String userId) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM contracts WHERE user_id = " + userId +
                    "AND approved = false");
            if(rs.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
