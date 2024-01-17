package net.manish.sem05.ui.batch;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelBachDetails implements Serializable {

    String status;
    String msg;
    ArrayList<batchData> batchData;
    ArrayList<batchData> yourBatch;
    ArrayList<batchData> recommendedBatch;

    public ArrayList<ModelBachDetails.batchData> getYourBatch() {
        return yourBatch;
    }

    public void setYourBatch(ArrayList<ModelBachDetails.batchData> yourBatch) {
        this.yourBatch = yourBatch;
    }

    public ArrayList<ModelBachDetails.batchData> getRecommendedBatch() {
        return recommendedBatch;
    }

    public void setRecommendedBatch(ArrayList<ModelBachDetails.batchData> recommendedBatch) {
        this.recommendedBatch = recommendedBatch;
    }

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

    public ArrayList<ModelBachDetails.batchData> getBatchData() {
        return batchData;
    }

    public void setBatchData(ArrayList<ModelBachDetails.batchData> batchData) {
        this.batchData = batchData;
    }


    public static class batchData implements Serializable {
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
        boolean purchase_condition;

        public batchData(String id, String batchName, String startDate, String endDate, String startTime, String endTime, String batchType, String batchPrice, String noOfStudent, String description, String status, String batchImage, String paymentType, String batchOfferPrice, String currencyDecimalCode, String currencyCode, boolean purchase_condition) {
            this.id = id;
            this.batchName = batchName;
            this.startDate = startDate;
            this.endDate = endDate;
            this.startTime = startTime;
            this.endTime = endTime;
            this.batchType = batchType;
            this.batchPrice = batchPrice;
            this.noOfStudent = noOfStudent;
            this.description = description;
            this.status = status;
            this.batchImage = batchImage;
            this.paymentType = paymentType;
            this.batchOfferPrice = batchOfferPrice;
            this.currencyDecimalCode = currencyDecimalCode;
            this.currencyCode = currencyCode;
            this.purchase_condition = purchase_condition;

        }

        public boolean isPurchase_condition() {
            return purchase_condition;
        }

        public void setPurchase_condition(boolean purchase_condition) {
            this.purchase_condition = purchase_condition;
        }

        ArrayList<SubjectList> batchSubject;
        ArrayList<VideoLectures> videoLectures;

        public ArrayList<VideoLectures> getVideoLectures() {
            return videoLectures;
        }

        public void setVideoLectures(ArrayList<VideoLectures> videoLectures) {
            this.videoLectures = videoLectures;
        }

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

        public ArrayList<SubjectList> getBatchSubject() {
            return batchSubject;
        }

        public void setBatchSubject(ArrayList<SubjectList> batchSubject) {
            this.batchSubject = batchSubject;
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

        public ArrayList<ModelBachDetails.batchData.batchFecherd> getBatchFecherd() {
            return batchFecherd;
        }

        public void setBatchFecherd(ArrayList<ModelBachDetails.batchData.batchFecherd> batchFecherd) {
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

        public class VideoLectures implements Serializable{
            String  id="";
            String  adminId="";
            String  title="";
            String  batch="";
            String  topic="";
            String  subject="";
            String  url="";
            String  status="";
            String  addedBy="";
            String  videoType="";
            String  previewType="";
            String  description="";
            String  videoId="";

            public String getVideoId() {
                return videoId;
            }

            public void setVideoId(String videoId) {
                this.videoId = videoId;
            }

            public String getId() {
                return id;
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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getBatch() {
                return batch;
            }

            public void setBatch(String batch) {
                this.batch = batch;
            }

            public String getTopic() {
                return topic;
            }

            public void setTopic(String topic) {
                this.topic = topic;
            }

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getAddedBy() {
                return addedBy;
            }

            public void setAddedBy(String addedBy) {
                this.addedBy = addedBy;
            }

            public String getVideoType() {
                return videoType;
            }

            public void setVideoType(String videoType) {
                this.videoType = videoType;
            }

            public String getPreviewType() {
                return previewType;
            }

            public void setPreviewType(String previewType) {
                this.previewType = previewType;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
        public class SubjectList implements Serializable{
            String id;
            String subjectName;
            ArrayList<ChapterList> chapter;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSubjectName() {
                return subjectName;
            }

            public void setSubjectName(String subjectName) {
                this.subjectName = subjectName;
            }

            public ArrayList<ChapterList> getChapter() {
                return chapter;
            }

            public void setChapter(ArrayList<ChapterList> chapter) {
                this.chapter = chapter;
            }

            public class ChapterList implements Serializable
            {
                String id="";
                String chapterName="";
                ArrayList<VideoLec> videoLectures;

                public ArrayList<VideoLec> getVideoLectures() {
                    return videoLectures;
                }

                public void setVideoLectures(ArrayList<VideoLec> videoLectures) {
                    this.videoLectures = videoLectures;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getChapterName() {
                    return chapterName;
                }

                public void setChapterName(String chapterName) {
                    this.chapterName = chapterName;
                }

                public class VideoLec implements Serializable{
                    String  id="";
                    String  adminId="";
                    String  title="";
                    String  batch="";
                    String  topic="";
                    String  subject="";
                    String  url="";
                    String  status="";
                    String  addedBy="";
                    String  videoType="";
                    String  previewType="";
                    String  description="";
                    String  videoId="";

                    public String getId() {
                        return id;
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

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public String getBatch() {
                        return batch;
                    }

                    public void setBatch(String batch) {
                        this.batch = batch;
                    }

                    public String getTopic() {
                        return topic;
                    }

                    public void setTopic(String topic) {
                        this.topic = topic;
                    }

                    public String getSubject() {
                        return subject;
                    }

                    public void setSubject(String subject) {
                        this.subject = subject;
                    }

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    public String getStatus() {
                        return status;
                    }

                    public void setStatus(String status) {
                        this.status = status;
                    }

                    public String getAddedBy() {
                        return addedBy;
                    }

                    public void setAddedBy(String addedBy) {
                        this.addedBy = addedBy;
                    }

                    public String getVideoType() {
                        return videoType;
                    }

                    public void setVideoType(String videoType) {
                        this.videoType = videoType;
                    }

                    public String getPreviewType() {
                        return previewType;
                    }

                    public void setPreviewType(String previewType) {
                        this.previewType = previewType;
                    }

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public String getVideoId() {
                        return videoId;
                    }

                    public void setVideoId(String videoId) {
                        this.videoId = videoId;
                    }
                }
            }


        }
    }
}
