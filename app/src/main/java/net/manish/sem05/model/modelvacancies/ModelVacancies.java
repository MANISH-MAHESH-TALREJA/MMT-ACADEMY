package com.pixelnx.eacademy.model.modelvacancies;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelVacancies implements Serializable {
    String status = "";
    String msg = "";
    String filesUrl= "";

    ArrayList<TutorialDetails>  vacancy;

    public String getFilesUrl() {
        return filesUrl;
    }

    public void setFilesUrl(String filesUrl) {
        this.filesUrl = filesUrl;
    }

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

    public ArrayList<TutorialDetails> getVacancy() {
        return vacancy;
    }

    public void setVacancy(ArrayList<TutorialDetails> vacancy) {
        this.vacancy = vacancy;
    }

    public class TutorialDetails implements Serializable {
        String id = "";
        String title = "";
        String vacancyDate = "";
        String description = "";
        String startDate = "";
        String lastDate = "";
        String mode = "";
        String files= "";
        String type = "";

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public String getFiles() {
            return files;
        }

        public void setFiles(String files) {
            this.files = files;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVacancyDate() {
            return vacancyDate;
        }

        public void setVacancyDate(String vacancyDate) {
            this.vacancyDate = vacancyDate;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getLastDate() {
            return lastDate;
        }

        public void setLastDate(String lastDate) {
            this.lastDate = lastDate;
        }


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
