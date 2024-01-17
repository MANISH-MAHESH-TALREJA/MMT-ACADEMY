package com.pixelnx.eacademy.model.modelpractiesresult;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelPractiesResult implements Serializable {

    String status = "";
    String msg = "";
    ArrayList<PracticeResult> resultData;
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

    public ArrayList<PracticeResult> getResultData() {
        return resultData;
    }

    public void setResultData(ArrayList<PracticeResult> resultData) {
        this.resultData = resultData;
    }

    public static class PracticeResult  implements Serializable{



        String paperId = "";
        String resultId = "";
        String paperName = "";
        String studentId = "";
        String totalQuestion = "";
        String totalAttemptQuestion = "";
        String rightAns = "";
        String date = "";
        String totalTime = "";
        String attemptedQuestion = "";
        String startTime = "";
        String submitTime = "";
        String timeTaken = "";
        String totalScore = "";
        String  percentage= "";
        String scheduleTime= "";
        String paperTotalMarks = "";
        String paperNegetiveMarks = "";

        public String getPaperTotalMarks() {
            return paperTotalMarks;
        }

        public void setPaperTotalMarks(String paperTotalMarks) {
            this.paperTotalMarks = paperTotalMarks;
        }


        public String getPaperNegetiveMarks() {
            return paperNegetiveMarks;
        }

        public void setPaperNegetiveMarks(String paperNegetiveMarks) {
            this.paperNegetiveMarks = paperNegetiveMarks;
        }

        public String getScheduleTime() {
            return scheduleTime;
        }

        public void setScheduleTime(String scheduleTime) {
            this.scheduleTime = scheduleTime;
        }

        public String getPaperId() {
            return paperId;
        }

        public void setPaperId(String paperId) {
            this.paperId = paperId;
        }

        public String getResultId() {
            return resultId;
        }

        public void setResultId(String resultId) {
            this.resultId = resultId;
        }

        public String getPaperName() {
            return paperName;
        }

        public void setPaperName(String paperName) {
            this.paperName = paperName;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getTotalQuestion() {
            return totalQuestion;
        }

        public void setTotalQuestion(String totalQuestion) {
            this.totalQuestion = totalQuestion;
        }

        public String getTotalAttemptQuestion() {
            return totalAttemptQuestion;
        }

        public void setTotalAttemptQuestion(String totalAttemptQuestion) {
            this.totalAttemptQuestion = totalAttemptQuestion;
        }

        public String getRightAns() {
            return rightAns;
        }

        public void setRightAns(String rightAns) {
            this.rightAns = rightAns;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(String totalTime) {
            this.totalTime = totalTime;
        }

        public String getAttemptedQuestion() {
            return attemptedQuestion;
        }

        public void setAttemptedQuestion(String attemptedQuestion) {
            this.attemptedQuestion = attemptedQuestion;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getSubmitTime() {
            return submitTime;
        }

        public void setSubmitTime(String submitTime) {
            this.submitTime = submitTime;
        }

        public String getTimeTaken() {
            return timeTaken;
        }

        public void setTimeTaken(String timeTaken) {
            this.timeTaken = timeTaken;
        }

        public String getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(String totalScore) {
            this.totalScore = totalScore;
        }

        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }
    }


}
