package net.manish.sem05.ui.batch;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelBatchDetailsOld implements Serializable {

    String status;
    String msg;
    ArrayList<batchData> batchData;


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

    public ArrayList<ModelBatchDetailsOld.batchData> getBatchData() {
        return batchData;
    }

    public void setBatchData(ArrayList<ModelBatchDetailsOld.batchData> batchData) {
        this.batchData = batchData;
    }


    public class batchData implements Serializable {
        String id;
        String batchName;
        String startDate;
        String endDate;
        String startTime;
        String endTime;
        String batchType;
        String batchPrice;
        String noOfStudent;
        String description;
        String status;
        String batchImage;
        String paymentType;
        String batchOfferPrice;

        String currencyDecimalCode;
        String currencyCode;

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        public String getBatchOfferPrice() {
            return batchOfferPrice;
        }

        public void setBatchOfferPrice(String batchOfferPrice) {
            this.batchOfferPrice = batchOfferPrice;
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

        ArrayList<batchFecherd> batchFecherd;

        public ArrayList<ModelBatchDetailsOld.batchData.batchFecherd> getBatchFecherd() {
            return batchFecherd;
        }

        public void setBatchFecherd(ArrayList<ModelBatchDetailsOld.batchData.batchFecherd> batchFecherd) {
            this.batchFecherd = batchFecherd;
        }

        public String getBatchImage() {
            return batchImage;
        }

        public void setBatchImage(String batchImage) {
            this.batchImage = batchImage;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getNoOfStudent() {
            return noOfStudent;
        }

        public void setNoOfStudent(String noOfStudent) {
            this.noOfStudent = noOfStudent;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public class batchFecherd implements Serializable {

            String batchSpecification;
            String fecherd;

            public String getFecherd() {
                return fecherd;
            }

            public void setFecherd(String fecherd) {
                this.fecherd = fecherd;
            }

            public String getBatchSpecification() {
                return batchSpecification;
            }

            public void setBatchSpecification(String batchSpecification) {
                this.batchSpecification = batchSpecification;
            }


        }
    }
}
