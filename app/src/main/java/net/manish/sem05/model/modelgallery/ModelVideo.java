package com.pixelnx.eacademy.model.modelgallery;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelVideo implements Serializable {
    String status = "";
    String msg = "";

ArrayList<Data> subject;

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

    public ArrayList<Data> getSubject() {
        return subject;
    }

    public void setSubject(ArrayList<Data> subject) {
        this.subject = subject;
    }

    public class Data implements Serializable {
        String id;
        String subjectName;




        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }
    }

}
