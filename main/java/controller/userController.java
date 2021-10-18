package controller;

import dao.UserDaoSQL;
import model.GetUserModel;
import model.UserModel;
import model.UserRegisterModel;
import model.UserUpdateModel;
import model.request.UserLoginRequestModel;
import model.request.UserRegisterRequestModel;
import model.request.UserUpdateRequestModel;
import model.response.GetUserResponseModel;
import model.response.UserChangeInfoResponseModel;
import model.response.UserLoginResponseModel;
import model.response.UserRegisterResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class userController {
    static UserDaoSQL ud = new UserDaoSQL();

    private String getSHA256(String password) {
        String hashValue = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            byte[] digestedBytes = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digestedBytes){
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private boolean isEmailValid(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    private boolean isPasswordValid(String password){
        String PASSWORD_PATTERN =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,15}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
        }



    @PostMapping("/users/register")
    public UserRegisterResponseModel userRegister
            (@RequestBody UserRegisterRequestModel userReg){
        String username = userReg.getUsername();
        if(username.length() < 3){
            return new UserRegisterResponseModel(false, "Invalid username.");
        }if(!isPasswordValid(userReg.getPassword())){
            return new UserRegisterResponseModel(false, "Invalid password.");
        }if(!isEmailValid(userReg.getEmail())){
            return new UserRegisterResponseModel(false, "Invalid email.");
        }
        String password = getSHA256(userReg.getPassword());
        ud.registerUser(new UserRegisterModel(userReg.getUserId().toString(),username, password, userReg.getEmail()));
        return new UserRegisterResponseModel(true, "Register is successful.");
    }

   @PostMapping("/users/login")
   public UserLoginResponseModel login(@RequestBody UserLoginRequestModel user){
        String identification = user.getIdentification();
        String password = getSHA256(user.getPassword());
       if (ud.emailExists(identification) || ud.userNameExists(identification)) {
           if (password.equals(ud.getPasswordWithIdentification(identification)))
               return new UserLoginResponseModel(true, ud.getIdWithIdentification(identification));
       }
       return new UserLoginResponseModel(false, "Wrong username/email or password");
   }

   @PatchMapping("/users/{id}")
   public UserChangeInfoResponseModel changeUserInfo(@PathVariable("id") String id,
                                                     @RequestBody UserUpdateRequestModel user){
        String password = getSHA256(user.getPassword());
        String newPassword = user.getNewPassword();
        if(!ud.getPasswordWithUUID(id).equals(password)) {
            return new UserChangeInfoResponseModel(false, "Wrong password!!");
        }
            if(!isPasswordValid(newPassword)){
                return new UserChangeInfoResponseModel(false, "New password is invalid");
            }
            if (!newPassword.isEmpty()) {
                if (!isPasswordValid(newPassword)) {
                    return new UserChangeInfoResponseModel(false, "New password is invalid!!");
                }
                newPassword = getSHA256(newPassword);
            }
            UserUpdateModel up = new UserUpdateModel(user.getUsername(), user.getPassword(),
                    user.getFirstName(), user.getLastName(),
                    user.getPhoneNumber(), user.getImage());
            ud.updateUser(up, id);

        return new UserChangeInfoResponseModel(true, "Success!");
   }

    @GetMapping("/users/{id}")
    public GetUserResponseModel getUser(@PathVariable("id") String id) {
        return ud.getUser(id);
    }


    @GetMapping("/users")
    public List<GetUserModel> getAllUsers(@RequestHeader("authorization") String id) {
        if (ud.isAdmin(id)) {
            return ud.getAllUsers();
        }
        return null;
    }



}
