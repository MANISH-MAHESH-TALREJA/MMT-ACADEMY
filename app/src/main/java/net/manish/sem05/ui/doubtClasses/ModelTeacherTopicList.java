package com.pixelnx.eacademy.ui.doubtClasses;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelTeacherTopicList implements Serializable {
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

    public ArrayList<ModelTeacherTopicList.subjectData> getSubjectData() {
        return subjectData;
    }

    public void setSubjectData(ArrayList<ModelTeacherTopicList.subjectData> subjectData) {
        this.subjectData = subjectData;
    }

    class subjectData implements Serializable {
        ArrayList<teacherData>  teacherData;

    class teacherData implements Serializable{
        String id="";
        String name="";

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    ArrayList<chapterData>  chapterData;
    class chapterData implements Serializable{
        String id="";
        String chapter_name="";

        public String getChapter_name() {
            return chapter_name;
        }

        public void setChapter_name(String chapter_name) {
            this.chapter_name = chapter_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

        public ArrayList<ModelTeacherTopicList.subjectData.teacherData> getTeacherData() {
            return teacherData;
        }

        public void setTeacherData(ArrayList<ModelTeacherTopicList.subjectData.teacherData> teacherData) {
            this.teacherData = teacherData;
        }

        public ArrayList<ModelTeacherTopicList.subjectData.chapterData> getChapterData() {
            return chapterData;
        }

        public void setChapterData(ArrayList<ModelTeacherTopicList.subjectData.chapterData> chapterData) {
            this.chapterData = chapterData;
        }
    }
}
