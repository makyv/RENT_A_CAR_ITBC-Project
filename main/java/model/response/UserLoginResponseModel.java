package model.response;

public class UserLoginResponseModel {
    private boolean successful;
    private String info;

    public UserLoginResponseModel(boolean successful, String userId) {
        this.successful = successful;
        this.info = userId;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
