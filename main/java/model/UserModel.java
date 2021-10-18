package model;

import java.util.UUID;

public class UserModel {
    private UUID userId;
    private String username, email, password, firstName, lastName, phoneNumber,
            personalNumber, image;
    private boolean admin;

    public UserModel(String username,
                     String email,
                     String password,
                     String firstName,
                     String lastName,
                     String phoneNumber,
                     String personalNumber,
                     String image,
                     boolean admin) {
        this.userId = UUID.randomUUID();
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.personalNumber = personalNumber;
        this.image = image;
        this.admin = admin;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return  " UserId: " + userId + "\n" +
                " Username: " + username + "\n" +
                " Email: " + email + "\n" +
                " Password: " + password + "\n" +
                " First Name: " + firstName + "\n" +
                " Last Name: " + lastName + "\n" +
                " Phone Number:" + phoneNumber + "\n" +
                " Personal Number: " + personalNumber + "\n" +
                " Image: " + image + "\n" +
                " Admin: " + admin +
                '}';
    }
}
