package com.pixelnx.eacademy.model.modelnotice;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelNotice implements Serializable {

    String status = "";
    String msg = "";
    ArrayList<Notice> getNotice;

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

    public ArrayList<Notice> getGetNotice() {
        return getNotice;
    }

    public void setGetNotice(ArrayList<Notice> getNotice) {
        this.getNotice = getNotice;
    }

    public class Notice {

        String   noticeId ="";
        String   noticeTitle ="";
        String   noticeFile ="";
        String   noticeText ="";
        String   noticeAccess ="";
        String   noticeDate ="";
        String   status ="";

        public String getNoticeId() {
            return noticeId;
        }

        public void setNoticeId(String noticeId) {
            this.noticeId = noticeId;
        }

        public String getNoticeTitle() {
            return noticeTitle;
        }

        public void setNoticeTitle(String noticeTitle) {
            this.noticeTitle = noticeTitle;
        }

        public String getNoticeFile() {
            return noticeFile;
        }

        public void setNoticeFile(String noticeFile) {
            this.noticeFile = noticeFile;
        }

        public String getNoticeText() {
            return noticeText;
        }

        public void setNoticeText(String noticeText) {
            this.noticeText = noticeText;
        }

        public String getNoticeAccess() {
            return noticeAccess;
        }

        public void setNoticeAccess(String noticeAccess) {
            this.noticeAccess = noticeAccess;
        }

        public String getNoticeDate() {
            return noticeDate;
        }

        public void setNoticeDate(String noticeDate) {
            this.noticeDate = noticeDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
