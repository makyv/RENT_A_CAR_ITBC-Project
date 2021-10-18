package dao;

import connection.DatabaseConnection;
import model.*;
import model.response.GetUserResponseModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoSQL implements UserDao {
    static final Connection conn = DatabaseConnection.getConnection();
    private static PreparedStatement pst;


    @Override
    public boolean userNameExists(String username) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT username FROM users " +
                    "WHERE username = " + username);
            if (rs.next())
                rs.getString(1);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean emailExists(String email) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT email FROM users " +
                    "WHERE email = " + email);
            if (rs.next())
                rs.getString(1);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getPasswordWithIdentification(String identification) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT password FROM users WHERE email = " + identification +
                    "OR username = " + identification);
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getIdWithIdentification(String identification) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT user_id FROM users WHERE email = " + identification +
                    "OR username = " + identification);
            rs.next();
            return rs.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getPasswordWithUUID(String id) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT password FROM users WHERE email = " + id +
                    "OR username = " + id);
            rs.next();
            return rs.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isAdmin(String id) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT admin FROM users WHERE user_id = " + id);

            if (rs.next())
                rs.getBoolean(1);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<GetUserModel> getAllUsers() {
        List<GetUserModel> allUsers = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                GetUserModel um = new GetUserModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getBoolean(10)
                );
                allUsers.add(um);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    @Override
    public GetUserResponseModel getUser(String id) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(" SELECT username, email, " +
                    "password, first_name, last_name," +
                    " phone_number, personal_number, image FROM users WHERE user_id = " + id);
            rs.next();
            GetUserResponseModel user = new GetUserResponseModel(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7));
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void registerUser(UserRegisterModel rm) {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO users VALUES(?, ?, ?, ?, ?");
            st.setString(1, rm.getUserId());
            st.setString(2, rm.getUsername());
            st.setString(3, rm.getEmail());
            st.setString(4, rm.getPassword());
            st.setBoolean(5, rm.isAdmin());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void updateUser(UserUpdateModel um, String id) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT username, first_name," +
                    " last_name, phone_number, personal_number, image FROM users " +
                    " WHERE user_id = '" + id + "'");
            pst = conn.prepareStatement(" UPDATE users " +
                    " SET username = ?," +
                    " first_name = ?," +
                    " last_name = ?," +
                    " phone_number = ?," +
                    " image = ?" +
                    " WHERE user_id = '" + id + "'");
            if (rs.next()) {
                if (um.getUsername().isEmpty())
                    pst.setString(1, rs.getString(1));
                else
                    pst.setString(1, um.getUsername());

                if (um.getFirstName().isEmpty())
                    pst.setString(3, rs.getString(3));
                else
                    pst.setString(3, um.getFirstName());

                if (um.getLastName().isEmpty())
                    pst.setString(4, rs.getString(4));
                else
                    pst.setString(4, um.getLastName());

                if (um.getPhoneNumber().isEmpty())
                    pst.setString(5, rs.getString(5));
                else
                    pst.setString(5, um.getPhoneNumber());

                if (um.getImage().isEmpty())
                    pst.setString(7, rs.getString(7));
                else
                    pst.setString(7, um.getImage());
                pst.executeUpdate();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteUser(UserModel um) {

    }

    @Override
    public void adminUpdateUserInfo(AdminUpdateUserModel user, String id) {

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT username, email, first_name," +
                    " last_name, phone_number, personal_number, image FROM users " +
                    " WHERE user_id = '" + id + "'");
            pst = conn.prepareStatement(" UPDATE users " +
                    " SET username = ?," +
                    " email = ?," +
                    " first_name = ?," +
                    " last_name = ?," +
                    " phone_number = ?," +
                    " personal_number = ?," +
                    " image = ?" +
                    " WHERE user_id = '" + id + "'");
            if (rs.next()) {
                if (user.getUsername().isEmpty())
                    pst.setString(1, rs.getString(1));
                else
                    pst.setString(1, user.getUsername());

                if (user.getEmail().isEmpty())
                    pst.setString(2, rs.getString(2));
                else
                    pst.setString(2, user.getEmail());

                if (user.getFirstName().isEmpty())
                    pst.setString(3, rs.getString(3));
                else
                    pst.setString(3, user.getFirstName());

                if (user.getLastName().isEmpty())
                    pst.setString(4, rs.getString(4));
                else
                    pst.setString(4, user.getLastName());

                if (user.getPhoneNumber().isEmpty())
                    pst.setString(5, rs.getString(5));
                else
                    pst.setString(5, user.getPhoneNumber());

                if (user.getPersonalNumber().isEmpty())
                    pst.setString(6, rs.getString(6));
                else
                    pst.setString(6, user.getPersonalNumber());

                if (user.getImage().isEmpty())
                    pst.setString(7, rs.getString(7));
                else
                    pst.setString(7, user.getImage());
                pst.executeUpdate();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
