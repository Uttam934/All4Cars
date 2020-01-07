package com.allforcars.all4cars.Model;

public class Notification_Model {

    public String notification_id;
    public String message;
    public String title;
    public String date;
    public String status;


    public String getnotification_id() {
        return notification_id;
    }

    public void setnotification_id(String notification_id) {
        this.notification_id = notification_id;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }


    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }


    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }



}
