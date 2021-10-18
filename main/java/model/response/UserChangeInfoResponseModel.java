package model.response;

public class UserChangeInfoResponseModel {
    private boolean successful;
    private String info;

    public UserChangeInfoResponseModel(boolean successful,
                                       String info) {
        this.successful = successful;
        this.info = info;
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
