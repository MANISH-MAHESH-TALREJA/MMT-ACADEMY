package com.pixelnx.eacademy.model.modelLeave;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelLeaveHistory implements Serializable {
    String status="";
    String msg="";
    ArrayList<LeaveData> leaveData;

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

    public ArrayList<LeaveData> getLeaveData() {
        return leaveData;
    }

    public void setLeaveData(ArrayList<LeaveData> leaveData) {
        this.leaveData = leaveData;
    }

    public class LeaveData implements Serializable {
        String id="";
        String status="";
        String subject="";
        String leaveMsg="";
        String fromDate="";
        String toDate="";
        String totalDays="";
        String addedAt="";

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getLeaveMsg() {
            return leaveMsg;
        }

        public void setLeaveMsg(String leaveMsg) {
            this.leaveMsg = leaveMsg;
        }

        public String getFromDate() {
            return fromDate;
        }

        public void setFromDate(String fromDate) {
            this.fromDate = fromDate;
        }

        public String getToDate() {
            return toDate;
        }

        public void setToDate(String toDate) {
            this.toDate = toDate;
        }

        public String getTotalDays() {
            return totalDays;
        }

        public void setTotalDays(String totalDays) {
            this.totalDays = totalDays;
        }

        public String getAddedAt() {
            return addedAt;
        }

        public void setAddedAt(String addedAt) {
            this.addedAt = addedAt;
        }
    }
}
