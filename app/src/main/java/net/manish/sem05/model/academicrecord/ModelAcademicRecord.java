package com.pixelnx.eacademy.model.academicrecord;



import java.io.Serializable;


public class ModelAcademicRecord implements Serializable {
    String status="";
    String msg="";
    AcademicRecord academicRecord;

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

    public AcademicRecord getAcademicRecord() {
        return academicRecord;
    }

    public void setAcademicRecord(AcademicRecord academicRecord) {
        this.academicRecord = academicRecord;
    }

    public static class AcademicRecord implements Serializable{

       String extraClass="";
       String totalExtraClass="";
       String totalPracticeTest="";
       String totalMockTest="";
       String practiceResult="";
       String mockResult="";

        public String getTotalExtraClass() {
            return totalExtraClass;
        }

        public void setTotalExtraClass(String totalExtraClass) {
            this.totalExtraClass = totalExtraClass;
        }

        public String getTotalPracticeTest() {
            return totalPracticeTest;
        }

        public void setTotalPracticeTest(String totalPracticeTest) {
            this.totalPracticeTest = totalPracticeTest;
        }

        public String getTotalMockTest() {
            return totalMockTest;
        }

        public void setTotalMockTest(String totalMockTest) {
            this.totalMockTest = totalMockTest;
        }

        public String getExtraClass() {
            return extraClass;
        }

        public void setExtraClass(String extraClass) {
            this.extraClass = extraClass;
        }

        public String getPracticeResult() {
            return practiceResult;
        }

        public void setPracticeResult(String practiceResult) {
            this.practiceResult = practiceResult;
        }

        public String getMockResult() {
            return mockResult;
        }

        public void setMockResult(String mockResult) {
            this.mockResult = mockResult;
        }
    }
}
