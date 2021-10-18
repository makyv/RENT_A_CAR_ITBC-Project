package controller;

import dao.UserDao;
import dao.UserDaoSQL;
import model.AdminUpdateUserModel;
import model.request.AdminUpdateUserRequestModel;
import model.response.AdminUpdateUserResponseModel;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public class adminController {
    UserDaoSQL ud = new UserDaoSQL();


    private boolean isEmailValid(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    @PatchMapping("admin/update/{id}")
    public AdminUpdateUserResponseModel changeUserInfo(@PathVariable("id") String userId,
    @RequestHeader("autorization") String adminId,
        @RequestBody AdminUpdateUserRequestModel info) {
        if (!ud.isAdmin(adminId)) {
            return new AdminUpdateUserResponseModel(false, "Only admin can access this option!");
        }
        String username = info.getUsername();
        String email = info.getEmail();

        if ((!username.isEmpty()) && username.length() < 3){
            username = "";
        }
        if (ud.userNameExists(username)){
            username = "";
        }

        if ((!email.isEmpty()) && !isEmailValid(email)){
            email = "";
        }

        if (ud.userNameExists(email)){
            email = "";
        }
        if (username.isEmpty() && email.isEmpty() && info.getFirstName().isEmpty()
                && info.getLastName().isEmpty() && info.getPersonalNumber().isEmpty()
                && info.getPhoneNumber().isEmpty() && info.getImage().isEmpty()){
            return new AdminUpdateUserResponseModel(false, "Update isn't successful!!");
        }
        AdminUpdateUserModel userInfo = new AdminUpdateUserModel(username, email, info.getFirstName(),
                info.getLastName(), info.getPhoneNumber(), info.getPersonalNumber(), info.getImage());

        ud.adminUpdateUserInfo(userInfo, userId);
        return new AdminUpdateUserResponseModel(true, "Success!!!");



    }
}
