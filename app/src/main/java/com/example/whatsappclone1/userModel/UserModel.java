package com.example.whatsappclone1.userModel;

public class UserModel {
    String userNickname;
    String userProfilepic;
    String userPhonenumber;
    String Status;

    public UserModel() {
    }

    public UserModel(String userNickname, String userProfilepic, String userPhonenumber, String status) {
        this.userNickname = userNickname;
        this.userProfilepic = userProfilepic;
        this.userPhonenumber = userPhonenumber;
        Status = status;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserProfilepic() {
        return userProfilepic;
    }

    public void setUserProfilepic(String userProfilepic) {
        this.userProfilepic = userProfilepic;
    }

    public String getUserPhonenumber() {
        return userPhonenumber;
    }

    public void setUserPhonenumber(String userPhonenumber) {
        this.userPhonenumber = userPhonenumber;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
