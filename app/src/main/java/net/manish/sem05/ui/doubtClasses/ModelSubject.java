package com.pixelnx.eacademy.ui.doubtClasses;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelSubject implements Serializable {

    String status="";
    String msg="";
    ArrayList<subjectData> subjectData;

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

    public ArrayList<ModelSubject.subjectData> getSubjectData() {
        return subjectData;
    }

    public void setSubjectData(ArrayList<ModelSubject.subjectData> subjectData) {
        this.subjectData = subjectData;
    }

    class subjectData implements Serializable {

        String subjectId="";
        String subjectName="";

        public String getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(String subjectId) {
            this.subjectId = subjectId;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }
    }
}
