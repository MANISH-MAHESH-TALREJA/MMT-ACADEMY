package com.pixelnx.eacademy.model.modellogin;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public class ModelLogin implements Serializable {

    String status = "";
    String msg = "";
    Data studentData =new Data();


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

    public Data getStudentData() {
        return studentData;
    }

    public void setStudentData(Data studentData) {
        this.studentData = studentData;
    }

    public static class Data {

        String studentId = "";
        String fullName = "";
        String uniqueId = "";
        String enrollmentId = "";
        String password = "";
        String userEmail = "";
        String image = "";
        String mobile = "";
        String versionCode = "";
        String adminId = "";
        String batchId = "";
        String amount = "";
        String transactionId = "";
        String admissionDate = "";
        String loginDate = "";
        String batchName = "";
        String paymentType = "";
        String languageName = "";

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getLanguageName() {
            return languageName;
        }

        public void setLanguageName(String languageName) {
            this.languageName = languageName;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public void setUniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
        }

        public String getEnrollmentId() {
            return enrollmentId;
        }

        public void setEnrollmentId(String enrollmentId) {
            this.enrollmentId = enrollmentId;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
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

        public String getAdmissionDate() {
            return admissionDate;
        }

        public void setAdmissionDate(String admissionDate) {
            this.admissionDate = admissionDate;
        }

        public String getLoginDate() {
            return loginDate;
        }

        public void setLoginDate(String loginDate) {
            this.loginDate = loginDate;
        }

        public String getBatchName() {
            return batchName;
        }

        public void setBatchName(String batchName) {
            this.batchName = batchName;
        }
    }


}






