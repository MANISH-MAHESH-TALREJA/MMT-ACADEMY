package com.pixelnx.eacademy.model.attendanceModel;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelAttendance implements Serializable {

    String status = "";
    String msg = "";
    ArrayList<Attendance> attendance;


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

    public ArrayList<Attendance> getAttendance() {
        return attendance;
    }

    public void setAttendance(ArrayList<Attendance> attendance) {
        this.attendance = attendance;
    }

    public static class Attendance implements Serializable {
        String id = "";
        String studentId = "";
        String addedId = "";
        String date = "";
        String time = "";

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getAddedId() {
            return addedId;
        }

        public void setAddedId(String addedId) {
            this.addedId = addedId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
