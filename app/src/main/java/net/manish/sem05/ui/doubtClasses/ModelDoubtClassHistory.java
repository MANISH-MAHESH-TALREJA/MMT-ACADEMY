package com.pixelnx.eacademy.ui.doubtClasses;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelDoubtClassHistory implements Serializable {

    String status;
    String msg;
   ArrayList<doubtsData> doubtsData;

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

    public ArrayList<ModelDoubtClassHistory.doubtsData> getDoubtsData() {
        return doubtsData;
    }

    public void setDoubtsData(ArrayList<ModelDoubtClassHistory.doubtsData> doubtsData) {
        this.doubtsData = doubtsData;
    }

    class doubtsData implements Serializable
    {
        String doubtId;
        String studentId;
        String teacherId;
        String batchId;
        String subjectsId;
        String chaptersId;
        String usersDescription;
        String teacherDescription;
        String appointmentDate;

        String appointmentTime;
        String createAt;
        String status;
        String teacherName;
        String batchName;



        String subjectName;
        String chapterName;

        public String getDoubtId() {
            return doubtId;
        }

        public void setDoubtId(String doubtId) {
            this.doubtId = doubtId;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(String teacherId) {
            this.teacherId = teacherId;
        }

        public String getBatchId() {
            return batchId;
        }

        public void setBatchId(String batchId) {
            this.batchId = batchId;
        }

        public String getSubjectsId() {
            return subjectsId;
        }

        public void setSubjectsId(String subjectsId) {
            this.subjectsId = subjectsId;
        }

        public String getChaptersId() {
            return chaptersId;
        }

        public void setChaptersId(String chaptersId) {
            this.chaptersId = chaptersId;
        }

        public String getUsersDescription() {
            return usersDescription;
        }

        public void setUsersDescription(String usersDescription) {
            this.usersDescription = usersDescription;
        }

        public String getTeacherDescription() {
            return teacherDescription;
        }

        public void setTeacherDescription(String teacherDescription) {
            this.teacherDescription = teacherDescription;
        }

        public String getAppointmentDate() {
            return appointmentDate;
        }

        public void setAppointmentDate(String appointmentDate) {
            this.appointmentDate = appointmentDate;
        }

        public String getAppointmentTime() {
            return appointmentTime;
        }

        public void setAppointmentTime(String appointmentTime) {
            this.appointmentTime = appointmentTime;
        }

        public String getCreateAt() {
            return createAt;
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getBatchName() {
            return batchName;
        }

        public void setBatchName(String batchName) {
            this.batchName = batchName;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }
    }
}
