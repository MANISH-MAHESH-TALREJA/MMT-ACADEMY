package com.pixelnx.eacademy.model.modelpracticepaper;

import java.util.ArrayList;

public class ModelPracticePaper {
    String status = "";
    String msg = "";
    String mockInfo = "";
    String serverTime = "";
    ArrayList<ExamPaper> examPaper;

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



    public ArrayList<ExamPaper> getExamPaper() {
        return examPaper;
    }

    public void setExamPaper(ArrayList<ExamPaper> examPaper) {
        this.examPaper = examPaper;
    }

    public String getMockInfo() {
        return mockInfo;
    }

    public void setMockInfo(String mockInfo) {
        this.mockInfo = mockInfo;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public class ExamPaper {


        String id = "";
        String examType = "";
        String name = "";
        String totalQuestion = "";
        String timeDuration = "";
        String mockSheduledTime = "";
        String mockSheduledDate = "";
        String currentTime = "";
        String Total = "";
        String negetive = "";


        public String getTotal() {
            return Total;
        }

        public void setTotal(String total) {
            Total = total;
        }

        public String getNegetive() {
            return negetive;
        }

        public void setNegetive(String negetive) {
            this.negetive = negetive;
        }

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

  

       

        public String getExamType() {
            return examType;
        }

        public void setExamType(String examType) {
            this.examType = examType;
        }

        public String getTotalQuestion() {
            return totalQuestion;
        }

        public void setTotalQuestion(String totalQuestion) {
            this.totalQuestion = totalQuestion;
        }

        public String getMockSheduledTime() {
            return mockSheduledTime;
        }

        public void setMockSheduledTime(String mockSheduledTime) {
            this.mockSheduledTime = mockSheduledTime;
        }

        public String getMockSheduledDate() {
            return mockSheduledDate;
        }

        public void setMockSheduledDate(String mockSheduledDate) {
            this.mockSheduledDate = mockSheduledDate;
        }

        public String getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(String currentTime) {
            this.currentTime = currentTime;
        }

        public String getTimeDuration() {
            return timeDuration;
        }

        public void setTimeDuration(String timeDuration) {
            this.timeDuration = timeDuration;
        }
    }
}
