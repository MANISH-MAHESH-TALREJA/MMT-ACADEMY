package com.pixelnx.eacademy.ui.generatepapers;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelGetPaper implements Serializable {

    String status = "";
    String msg = "";
    String currentTime="";
    ArrayList<TotalExamData> totalExamData;

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

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public ArrayList<TotalExamData> getTotalExamData() {
        return totalExamData;
    }

    public void setTotalExamData(ArrayList<TotalExamData> totalExamData) {
        this.totalExamData = totalExamData;
    }

    public class TotalExamData implements Serializable {

        String id = "";
        String adminId = "";
        String name = "";
        String type = "";
        String format = "";
        String batchId = "";
        String totalQuestion = "";
        String timeDuration = "";
        String questionIds = "";
        String mockSheduledDate = "";
        String mockSheduledTime = "";
        String status = "";
        ArrayList<QuestionDetails> questionDetails;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getAdminId() {
            return adminId;
        }

        public void setAdminId(String adminId) {
            this.adminId = adminId;
        }

        public String getBatchId() {
            return batchId;
        }

        public void setBatchId(String batchId) {
            this.batchId = batchId;
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

        public String getQuestionIds() {
            return questionIds;
        }

        public void setQuestionIds(String questionIds) {
            this.questionIds = questionIds;
        }

        public String getMockSheduledDate() {
            return mockSheduledDate;
        }

        public void setMockSheduledDate(String mockSheduledDate) {
            this.mockSheduledDate = mockSheduledDate;
        }

        public String getMockSheduledTime() {
            return mockSheduledTime;
        }

        public void setMockSheduledTime(String mockSheduledTime) {
            this.mockSheduledTime = mockSheduledTime;
        }

        public ArrayList<QuestionDetails> getQuestionDetails() {
            return questionDetails;
        }

        public void setQuestionDetails(ArrayList<QuestionDetails> questionDetails) {
            this.questionDetails = questionDetails;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }



        public class QuestionDetails {
            String id="";
            String question="";
            String options="";

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

            public String getOptions() {
                return options;
            }

            public void setOptions(String options) {
                this.options = options;
            }

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            String answer="";


        }
    }
}
