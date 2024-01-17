package com.pixelnx.eacademy.model.modelliveclassdata;

import java.io.Serializable;

public class ModelLiveClassData implements Serializable {

    String status="";
    String msg="";
    String filesUrl="";
    String meeting_number="";
    LiveClass liveClass;

    public String getStatus() {
        return status;
    }

    public String getMeeting_number() {
        return meeting_number;
    }

    public void setMeeting_number(String meeting_number) {
        this.meeting_number = meeting_number;
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

    public String getFilesUrl() {
        return filesUrl;
    }

    public void setFilesUrl(String filesUrl) {
        this.filesUrl = filesUrl;
    }

    public LiveClass getLiveClass() {
        return liveClass;
    }

    public void setLiveClass(LiveClass liveClass) {
        this.liveClass = liveClass;
    }

    public static class LiveClass implements Serializable
    {
        String name="";
        String teachImage="";
        String subjectName="";
        String chapterName="";

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public String getTeachImage() {
            return teachImage;
        }

        public void setTeachImage(String teachImage) {
            this.teachImage = teachImage;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
