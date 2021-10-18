package model.response;

public class AdminUpdateUserResponseModel {
    private boolean succesful;
    private String info;

    public AdminUpdateUserResponseModel(boolean succesful, String info) {
        this.succesful = succesful;
        this.info = info;
    }

    public boolean isSuccesful() {
        return succesful;
    }

    public void setSuccesful(boolean succesful) {
        this.succesful = succesful;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
