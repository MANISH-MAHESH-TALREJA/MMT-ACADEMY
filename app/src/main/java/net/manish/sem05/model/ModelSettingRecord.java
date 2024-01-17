package com.pixelnx.eacademy.model;



import java.io.Serializable;


public class ModelSettingRecord implements Serializable {
    String status="";

    Data data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data implements Serializable{

       String languageName="";
       String currencyCode="";
       String currencyDecimalCode="";
       String paymentType="";
       String razorpayKeyId="";
       String razorpaySecretKey="";
       String paypalClientId="";
       String paypalSecretKey="";

        public String getLanguageName() {
            return languageName;
        }

        public void setLanguageName(String languageName) {
            this.languageName = languageName;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        public String getCurrencyDecimalCode() {
            return currencyDecimalCode;
        }

        public void setCurrencyDecimalCode(String currencyDecimalCode) {
            this.currencyDecimalCode = currencyDecimalCode;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public String getRazorpayKeyId() {
            return razorpayKeyId;
        }

        public void setRazorpayKeyId(String razorpayKeyId) {
            this.razorpayKeyId = razorpayKeyId;
        }

        public String getRazorpaySecretKey() {
            return razorpaySecretKey;
        }

        public void setRazorpaySecretKey(String razorpaySecretKey) {
            this.razorpaySecretKey = razorpaySecretKey;
        }

        public String getPaypalClientId() {
            return paypalClientId;
        }

        public void setPaypalClientId(String paypalClientId) {
            this.paypalClientId = paypalClientId;
        }

        public String getPaypalSecretKey() {
            return paypalSecretKey;
        }

        public void setPaypalSecretKey(String paypalSecretKey) {
            this.paypalSecretKey = paypalSecretKey;
        }
    }
}
