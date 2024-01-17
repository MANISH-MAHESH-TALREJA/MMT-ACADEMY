package net.manish.sem05.model.modeltopscore;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelTopScorer implements Serializable {
    String msg="";
    String status="";
    String filesUrl="";

    public String getFilesUrl() {
        return filesUrl;
    }

    public void setFilesUrl(String filesUrl) {
        this.filesUrl = filesUrl;
    }

    ArrayList<TopThree> topThree;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<TopThree> getTopThree() {
        return topThree;
    }

    public void setTopThree(ArrayList<TopThree> topThree) {
        this.topThree = topThree;
    }

        public class TopThree implements Serializable{
            String paperName="";
            String name="";
            String image="";
            String percentage="";

       public String getPaperName() {
           return paperName;
       }

       public void setPaperName(String paperName) {
           this.paperName = paperName;
       }

       public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }
    }
}
