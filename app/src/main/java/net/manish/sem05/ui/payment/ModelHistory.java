package com.pixelnx.eacademy.ui.payment;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelHistory implements Serializable {

    String status;
    String msg;
    ArrayList<paymentData> paymentData;

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

    public ArrayList<ModelHistory.paymentData> getPaymentData() {
        return paymentData;
    }

    public void setPaymentData(ArrayList<ModelHistory.paymentData> paymentData) {
        this.paymentData = paymentData;
    }

    class paymentData{
        String id;
        String batchId;
        String transactionId;
        String amount;
        String createAt;
        String batchName;
        String startDate;
        String endDate;
        String startTime;
        String endTime;
        String batchType;
        String batchPrice;
        String description;
        String batchOfferPrice;
        String batchPriceConvert;
        String currencyDecimalCode;

        public String getBatchOfferPrice() {
            return batchOfferPrice;
        }

        public void setBatchOfferPrice(String batchOfferPrice) {
            this.batchOfferPrice = batchOfferPrice;
        }

        public String getBatchPriceConvert() {
            return batchPriceConvert;
        }

        public void setBatchPriceConvert(String batchPriceConvert) {
            this.batchPriceConvert = batchPriceConvert;
        }

        public String getCurrencyDecimalCode() {
            return currencyDecimalCode;
        }

        public void setCurrencyDecimalCode(String currencyDecimalCode) {
            this.currencyDecimalCode = currencyDecimalCode;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBatchId() {
            return batchId;
        }

        public void setBatchId(String batchId) {
            this.batchId = batchId;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCreateAt() {
            return createAt;
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
        }

        public String getBatchName() {
            return batchName;
        }

        public void setBatchName(String batchName) {
            this.batchName = batchName;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getBatchType() {
            return batchType;
        }

        public void setBatchType(String batchType) {
            this.batchType = batchType;
        }

        public String getBatchPrice() {
            return batchPrice;
        }

        public void setBatchPrice(String batchPrice) {
            this.batchPrice = batchPrice;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
