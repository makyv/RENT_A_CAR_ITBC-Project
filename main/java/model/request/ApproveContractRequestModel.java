package model.request;

public class ApproveContractRequestModel {
    private boolean approved;

    public ApproveContractRequestModel(boolean approved) {
        this.approved = approved;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
