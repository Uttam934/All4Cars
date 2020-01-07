package com.allforcars.all4cars.Model;

public class Getuserdata_Model {

    private String name;
    private String email;
    private String phone_number;
    private String user_image;
    private String status;
    private String created_at;
    private String birthday_day;
    private String user_type;
    private String notification_status;




    public String getnotification_status() {
        return notification_status;
    }

    public void setnotification_status(String notification_status) {
        this.notification_status = notification_status;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getcreated_at() {
        return created_at;
    }

    public void setcreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getbirthday_day() {
        return birthday_day;
    }

    public void setbirthday_day(String birthday_day) {
        this.birthday_day = birthday_day;
    }

    public String getuser_type() {
        return user_type;
    }

    public void setuser_type(String user_type) {
        this.user_type = user_type;
    }


    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }



    public String getphone_number() {
        return phone_number;
    }

    public void setphone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getuser_image() {
        return user_image;
    }

    public void setuser_image(String user_image) {
        this.user_image = user_image;
    }


    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }


}
