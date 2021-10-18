package dao;

import model.*;
import model.response.GetUserResponseModel;

import java.util.List;

public interface UserDao {
    boolean userNameExists(String username);
    boolean emailExists(String email);
    String getPasswordWithIdentification(String identification);
    String getIdWithIdentification(String identification);
    String getPasswordWithUUID(String id);
    boolean isAdmin(String id);
    List<GetUserModel> getAllUsers();
    GetUserResponseModel getUser(String id);
    void registerUser(UserRegisterModel rm);
    void updateUser(UserUpdateModel um, String id);
    void deleteUser(UserModel um);
    void adminUpdateUserInfo(AdminUpdateUserModel user, String id);

}
