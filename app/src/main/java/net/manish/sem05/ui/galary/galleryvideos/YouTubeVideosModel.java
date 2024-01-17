package com.pixelnx.eacademy.ui.galary.galleryvideos;

import java.util.ArrayList;

public class YouTubeVideosModel {

    String status;
    String msg;
    ArrayList<Data> videoLecture;
    String encCode = "";

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

    public ArrayList<Data> getVideoLecture() {
        return videoLecture;
    }

    public void setVideoLecture(ArrayList<Data> videoLecture) {
        this.videoLecture = videoLecture;
    }

    public String getEncCode() {
        return encCode;
    }

    public void setEncCode(String encCode) {
        this.encCode = encCode;
    }

    public class Data {
        String id = "";
        String adminId = "";
        String topic = "";
        String title = "";
        String url = "";
        String videoType = "";
        String subject = "";
        String demoShow  = "";
        String batchKey = "";
        String videoId = "";
        String previewType = "";
        String description = "";

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPreviewType() {
            return previewType;
        }

        public void setPreviewType(String previewType) {
            this.previewType = previewType;
        }

        public String getVideoType() {
            return videoType;
        }

        public void setVideoType(String videoType) {
            this.videoType = videoType;
        }

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getAdminId() {
            return adminId;
        }

        public void setAdminId(String adminId) {
            this.adminId = adminId;
        }

        public String getDemoShow() {
            return demoShow;
        }

        public void setDemoShow(String demoShow) {
            this.demoShow = demoShow;
        }

        public String getBatchKey() {
            return batchKey;
        }

        public void setBatchKey(String batchKey) {
            this.batchKey = batchKey;
        }
    }

}
