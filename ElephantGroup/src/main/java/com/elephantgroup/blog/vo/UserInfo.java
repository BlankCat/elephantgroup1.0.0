package com.elephantgroup.blog.vo;

/**
 * Created on 2016/11/10.
 */
public class UserInfo {

    private String _id;
    private String phonenumber;
    private String username;
    private String password;
    private String headImage;

    public String get_id() {
        return _id;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "_id='" + _id + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", headImage='" + headImage + '\'' +
                '}';
    }
}
