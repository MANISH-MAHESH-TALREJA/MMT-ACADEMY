package com.pixelnx.eacademy.model.modelextraClass;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelExtraClass implements Serializable {

    String status="";
    String msg="";
ArrayList<ExtraClass> extraClass;

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

    public ArrayList<ExtraClass> getExtraClass() {
        return extraClass;
    }

    public void setExtraClass(ArrayList<ExtraClass> extraClass) {
        this.extraClass = extraClass;
    }

    public class ExtraClass {
        String id="";
        String adminId="";
        String date="";
        String startTime="";
        String endTime="";
        String teacherId="";
        String description="";
        String status="";
        String name="";
        String teachGender="";



        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }



        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getAdminId() {
            return adminId;
        }

        public void setAdminId(String adminId) {
            this.adminId = adminId;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(String teacherId) {
            this.teacherId = teacherId;
        }

        public String getTeachGender() {
            return teachGender;
        }

        public void setTeachGender(String teachGender) {
            this.teachGender = teachGender;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
