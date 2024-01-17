package com.pixelnx.eacademy.model.modelnotify;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelNotify implements Serializable {

    String status = "";
    String msg = "";
    String baseUrl = "";
    ArrayList<Data> allNotice;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<Data> getAllNotice() {
        return allNotice;
    }

    public void setAllNotice(ArrayList<Data> allNotice) {
        this.allNotice = allNotice;
    }

   public class Data {

        String id = "";
        String  title = "";
        String description = "";
        String noticeFor = "";
        String status = "";
        String date = "";
        String adminId = "";
        String readStatus = "";

       public String getId() {
           return id;
       }

       public void setId(String id) {
           this.id = id;
       }

       public String getTitle() {
           return title;
       }

       public void setTitle(String title) {
           this.title = title;
       }

       public String getDescription() {
           return description;
       }

       public void setDescription(String description) {
           this.description = description;
       }



       public String getStatus() {
           return status;
       }

       public void setStatus(String status) {
           this.status = status;
       }

       public String getDate() {
           return date;
       }

       public void setDate(String date) {
           this.date = date;
       }

       public String getNoticeFor() {
           return noticeFor;
       }

       public void setNoticeFor(String noticeFor) {
           this.noticeFor = noticeFor;
       }

       public String getAdminId() {
           return adminId;
       }

       public void setAdminId(String adminId) {
           this.adminId = adminId;
       }

       public String getReadStatus() {
           return readStatus;
       }

       public void setReadStatus(String readStatus) {
           this.readStatus = readStatus;
       }
   }

}
