package com.pixelnx.eacademy.model.modelhomework;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelHomeWork implements Serializable {
    String status = "";
    String msg = "";
    ArrayList<Homework> homeWork;


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

    public ArrayList<Homework> getHomeWork() {
        return homeWork;
    }

    public void setHomeWork(ArrayList<Homework> homeWork) {
        this.homeWork = homeWork;
    }

    public class Homework {
        String description = "";
        String name = "";
        String teachGender = "";
        String subjectName = "";
        String date = "";


        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTeachGender() {
            return teachGender;
        }

        public void setTeachGender(String teachGender) {
            this.teachGender = teachGender;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

}
