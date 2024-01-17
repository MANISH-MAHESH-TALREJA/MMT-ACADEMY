package com.pixelnx.eacademy.ui.syllabus;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelSyllabus implements Serializable  {

    String status;
    String msg;
    Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    class Data {

        String batchId;
        String batchName;
        ArrayList<SubjectData> batchSubject;

        public String getBatchId() {
            return batchId;
        }

        public void setBatchId(String batchId) {
            this.batchId = batchId;
        }

        public String getBatchName() {
            return batchName;
        }

        public void setBatchName(String batchName) {
            this.batchName = batchName;
        }

        public ArrayList<SubjectData> getBatchSubject() {
            return batchSubject;
        }

        public void setBatchSubject(ArrayList<SubjectData> batchSubject) {
            this.batchSubject = batchSubject;
        }

        class SubjectData{

            String id;
            String subjectName;
            ArrayList<ChapterData> chapter;

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

            public ArrayList<ChapterData> getChapter() {
                return chapter;
            }

            public void setChapter(ArrayList<ChapterData> chapter) {
                this.chapter = chapter;
            }

            class ChapterData{
                String id;
                String chapterName;
                String complete;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getChapterName() {
                    return chapterName;
                }

                public void setChapterName(String chapterName) {
                    this.chapterName = chapterName;
                }

                public String getComplete() {
                    return complete;
                }

                public void setComplete(String complete) {
                    this.complete = complete;
                }
            }
        }

    }

}
