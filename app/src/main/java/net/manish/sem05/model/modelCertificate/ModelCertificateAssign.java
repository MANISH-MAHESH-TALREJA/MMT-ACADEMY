package com.pixelnx.eacademy.model.modelCertificate;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelCertificateAssign implements Serializable {
    String status="";
    String msg="";


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

    ArrayList<Data> data;

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public static class Data implements Serializable
    {

        String batch_name="";
        boolean assign;
        String filename="";
        String filesUrl="";

        public boolean isAssign() {
            return assign;
        }

        public void setAssign(boolean assign) {
            this.assign = assign;
        }

        public String getBatch_name() {
            return batch_name;
        }

        public void setBatch_name(String batch_name) {
            this.batch_name = batch_name;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getFilesUrl() {
            return filesUrl;
        }

        public void setFilesUrl(String filesUrl) {
            this.filesUrl = filesUrl;
        }
    }
}
