package com.pixelnx.eacademy.model.modelgallery;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelVideoTopics implements Serializable {
    String status = "";
    String msg = "";

ArrayList<Data> chapter;

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

    public ArrayList<Data> getChapter() {
        return chapter;
    }

    public void setChapter(ArrayList<Data> chapter) {
        this.chapter = chapter;
    }

    public static class Data implements Serializable {

        String chapterName;

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }
    }

}
