package com.pixelnx.eacademy.model.modelviewresult;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelViewResult implements Serializable {

    String status = "";
    String msg = "";
    AllData resultData;


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

    public AllData getResultData() {
        return resultData;
    }

    public void setResultData(AllData resultData) {
        this.resultData = resultData;
    }

    public class AllData implements Serializable {

        String id = "";
        String adminId = "";
        String studentId = "";
        String paperId = "";
        String paperName = "";
        String date = "";
        String startTime = "";
        String submitTime = "";
        String totalQuestion = "";
        String timeDuration = "";
        String addedOn = "";
        String questionIds = "";
        ArrayList<AllQuestion> allQuestions;

        public String getId() {
            return id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAdminId() {
            return adminId;
        }

        public void setAdminId(String adminId) {
            this.adminId = adminId;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getPaperId() {
            return paperId;
        }

        public void setPaperId(String paperId) {
            this.paperId = paperId;
        }

        public String getPaperName() {
            return paperName;
        }

        public void setPaperName(String paperName) {
            this.paperName = paperName;
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

        public String getTotalQuestion() {
            return totalQuestion;
        }

        public void setTotalQuestion(String totalQuestion) {
            this.totalQuestion = totalQuestion;
        }

        public String getTimeDuration() {
            return timeDuration;
        }

        public void setTimeDuration(String timeDuration) {
            this.timeDuration = timeDuration;
        }

        public String getAddedOn() {
            return addedOn;
        }

        public void setAddedOn(String addedOn) {
            this.addedOn = addedOn;
        }

        public String getQuestionIds() {
            return questionIds;
        }

        public void setQuestionIds(String questionIds) {
            this.questionIds = questionIds;
        }

        public ArrayList<AllQuestion> getAllQuestions() {
            return allQuestions;
        }

        public void setAllQuestions(ArrayList<AllQuestion> allQuestions) {
            this.allQuestions = allQuestions;
        }


        public class AllQuestion implements Serializable {


            String id = "";
            String question = "";
            String answer = "";
            String options = "";
            String studentAnswer = "";
            String rightAnswer = "";

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getQuestion() {
                return question;
            }

            public void setQuestion(String question) {
                this.question = question;
            }

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public String getOptions() {
                return options;
            }

            public void setOptions(String options) {
                this.options = options;
            }

            public String getStudentAnswer() {
                return studentAnswer;
            }

            public void setStudentAnswer(String studentAnswer) {
                this.studentAnswer = studentAnswer;
            }

            public String getRightAnswer() {
                return rightAnswer;
            }

            public void setRightAnswer(String rightAnswer) {
                this.rightAnswer = rightAnswer;
            }

        }


    }
}
