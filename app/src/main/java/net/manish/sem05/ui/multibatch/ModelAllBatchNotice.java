package net.manish.sem05.ui.multibatch;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelAllBatchNotice implements Serializable {
    String status="";
    String msg="";
    int noticesCount;
    ArrayList<Data> data;

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

    public int getNoticesCount() {
        return noticesCount;
    }

    public void setNoticesCount(int noticesCount) {
        this.noticesCount = noticesCount;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    class Data{
        String batchName="";
        String batchId="";
        LiveClass liveClass;
        TopThree topThree;

        public LiveClass getLiveClass() {
            return liveClass;
        }

        public void setLiveClass(LiveClass liveClass) {
            this.liveClass = liveClass;
        }

        ArrayList<ExtraClass> extraClass;
    ArrayList<homeWorkClass> homeWork;
    ArrayList<videoLectureClass> videoLecture;
    ArrayList<vacancy> vacancy;
    ArrayList<notices> notices;
    ArrayList<practicePaper> practicePaper;
    ArrayList<mock_paper> mockPaper;
    ArrayList<addNewNotes> addNewNotes;
    ArrayList<addOldPaper> addOldPaper;
    ArrayList<addNewBook> addNewBook;

        public TopThree getTopThree() {
            return topThree;
        }

        public void setTopThree(TopThree topThree) {
            this.topThree = topThree;
        }

        public String getBatchName() {
            return batchName;
        }

        public void setBatchName(String batchName) {
            this.batchName = batchName;
        }

        public String getBatchId() {
            return batchId;
        }

        public void setBatchId(String batchId) {
            this.batchId = batchId;
        }

        public ArrayList<ExtraClass> getExtraClass() {
            return extraClass;
        }

        public void setExtraClass(ArrayList<ExtraClass> extraClass) {
            this.extraClass = extraClass;
        }

        public ArrayList<homeWorkClass> getHomeWork() {
            return homeWork;
        }

        public void setHomeWork(ArrayList<homeWorkClass> homeWork) {
            this.homeWork = homeWork;
        }

        public ArrayList<videoLectureClass> getVideoLecture() {
            return videoLecture;
        }

        public void setVideoLecture(ArrayList<videoLectureClass> videoLecture) {
            this.videoLecture = videoLecture;
        }

        public ArrayList<Data.vacancy> getVacancy() {
            return vacancy;
        }

        public void setVacancy(ArrayList<Data.vacancy> vacancy) {
            this.vacancy = vacancy;
        }

        public ArrayList<Data.notices> getNotices() {
            return notices;
        }

        public void setNotices(ArrayList<Data.notices> notices) {
            this.notices = notices;
        }

        public ArrayList<Data.practicePaper> getPracticePaper() {
            return practicePaper;
        }

        public void setPracticePaper(ArrayList<Data.practicePaper> practicePaper) {
            this.practicePaper = practicePaper;
        }

        public ArrayList<mock_paper> getMockPaper() {
            return mockPaper;
        }

        public void setMockPaper(ArrayList<mock_paper> mockPaper) {
            this.mockPaper = mockPaper;
        }

        public ArrayList<Data.addNewNotes> getAddNewNotes() {
            return addNewNotes;
        }

        public void setAddNewNotes(ArrayList<Data.addNewNotes> addNewNotes) {
            this.addNewNotes = addNewNotes;
        }

        public ArrayList<Data.addOldPaper> getAddOldPaper() {
            return addOldPaper;
        }

        public void setAddOldPaper(ArrayList<Data.addOldPaper> addOldPaper) {
            this.addOldPaper = addOldPaper;
        }

        public ArrayList<Data.addNewBook> getAddNewBook() {
            return addNewBook;
        }

        public void setAddNewBook(ArrayList<Data.addNewBook> addNewBook) {
            this.addNewBook = addNewBook;
        }

        public class TopThree implements Serializable{
            String filesUrl="";
            ArrayList<TopThreeData> topThreeData;

            public String getFilesUrl() {
                return filesUrl;
            }

            public void setFilesUrl(String filesUrl) {
                this.filesUrl = filesUrl;
            }

            public ArrayList<TopThreeData> getTopThreeData() {
                return topThreeData;
            }

            public void setTopThreeData(ArrayList<TopThreeData> topThreeData) {
                this.topThreeData = topThreeData;
            }

            class TopThreeData{
                String paperName="";
                String name="";
                String image="";
                String percentage="";

                public String getPaperName() {
                    return paperName;
                }

                public void setPaperName(String paperName) {
                    this.paperName = paperName;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getPercentage() {
                    return percentage;
                }

                public void setPercentage(String percentage) {
                    this.percentage = percentage;
                }
            }



        }

       class LiveClass implements Serializable
        {



                String name = "";
                String teachImage = "";
                String subjectName = "";
                String chapterName = "";

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getTeachImage() {
                    return teachImage;
                }

                public void setTeachImage(String teachImage) {
                    this.teachImage = teachImage;
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
        public  class ExtraClass implements Serializable{
            String id="";
            String adminId="";
            String date="";
            String startTime="";
            String endTime="";
            String teacherId="";
            String description="";
            String status="";
            String addedAt="";
            String name="";
            String teachGender="";

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

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
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

            public String getTeacherId() {
                return teacherId;
            }

            public void setTeacherId(String teacherId) {
                this.teacherId = teacherId;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getAddedAt() {
                return addedAt;
            }

            public void setAddedAt(String addedAt) {
                this.addedAt = addedAt;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTeachGender() {
                return teachGender;
            }

            public void setTeachGender(String teachGender) {
                this.teachGender = teachGender;
            }
        }
        public  class homeWorkClass implements Serializable{
            String id="";
            String adminId="";
            String teacherId="";
            String date="";
            String subjectId="";
            String batchId="";
            String description="";
            String addedAt="";
            String name="";
            String teachGender="";
            String subjectName="";

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

            public String getTeacherId() {
                return teacherId;
            }

            public void setTeacherId(String teacherId) {
                this.teacherId = teacherId;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getSubjectId() {
                return subjectId;
            }

            public void setSubjectId(String subjectId) {
                this.subjectId = subjectId;
            }

            public String getBatchId() {
                return batchId;
            }

            public void setBatchId(String batchId) {
                this.batchId = batchId;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getAddedAt() {
                return addedAt;
            }

            public void setAddedAt(String addedAt) {
                this.addedAt = addedAt;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTeachGender() {
                return teachGender;
            }

            public void setTeachGender(String teachGender) {
                this.teachGender = teachGender;
            }

            public String getSubjectName() {
                return subjectName;
            }

            public void setSubjectName(String subjectName) {
                this.subjectName = subjectName;
            }
        }
        public  class practicePaper implements Serializable{
            String id="";
            String name="";
            String timeDuration;
            String totalQuestion;

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

            public String getTimeDuration() {
                return timeDuration;
            }

            public void setTimeDuration(String timeDuration) {
                this.timeDuration = timeDuration;
            }

            public String getTotalQuestion() {
                return totalQuestion;
            }

            public void setTotalQuestion(String totalQuestion) {
                this.totalQuestion = totalQuestion;
            }
        }
        public  class notices implements Serializable{
            String id="";
            String title="";
            String description="";
            String noticeFor="";
            String status="";

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getNoticeFor() {
                return noticeFor;
            }

            public void setNoticeFor(String noticeFor) {
                this.noticeFor = noticeFor;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
        public  class videoLectureClass implements Serializable{
            String id="";
            String adminId="";
            String title="";
            String batch="";
            String topic="";
            String subject="";
            String url="";
            String videoId="";
            String videoType="";

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

            public String getVideoId() {
                return videoId;
            }

            public void setVideoId(String videoId) {
                this.videoId = videoId;
            }

            public String getVideoType() {
                return videoType;
            }

            public void setVideoType(String videoType) {
                this.videoType = videoType;
            }
        }
        public  class vacancy implements Serializable{
            String id="";
            String title="";
            String description="";
            String startDate="";
            String lastDate="";
            String mode="";

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }

            public String getLastDate() {
                return lastDate;
            }

            public void setLastDate(String lastDate) {
                this.lastDate = lastDate;
            }

            public String getMode() {
                return mode;
            }

            public void setMode(String mode) {
                this.mode = mode;
            }
        }
        public  class mock_paper implements Serializable{
            String id="";
            String name="";
            String timeDuration="";
            String totalQuestion="";
            String mockSheduledDate ="";
            String mockSheduledTime ="";

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

            public String getTimeDuration() {
                return timeDuration;
            }

            public void setTimeDuration(String timeDuration) {
                this.timeDuration = timeDuration;
            }

            public String getTotalQuestion() {
                return totalQuestion;
            }

            public void setTotalQuestion(String totalQuestion) {
                this.totalQuestion = totalQuestion;
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
        }
        public  class addNewNotes implements Serializable{
            String id="";
            String adminId="";
            String title="";
            String batch="";
            String topic="";
            String subject="";
            String fileName="";
            String status="";
            String addedBy="";
            String addedAt="";
            String url="";

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

            public String getFileName() {
                return fileName;
            }

            public void setFileName(String fileName) {
                this.fileName = fileName;
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

            public String getAddedAt() {
                return addedAt;
            }

            public void setAddedAt(String addedAt) {
                this.addedAt = addedAt;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
        public  class addOldPaper implements Serializable{
            String id="";
            String adminId="";
            String title="";
            String batch="";
            String topic="";
            String subject="";
            String fileName="";
            String status="";
            String addedBy="";
            String addedAt="";

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

            public String getFileName() {
                return fileName;
            }

            public void setFileName(String fileName) {
                this.fileName = fileName;
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

            public String getAddedAt() {
                return addedAt;
            }

            public void setAddedAt(String addedAt) {
                this.addedAt = addedAt;
            }
        }
        public  class addNewBook implements Serializable{
            String id="";
            String adminId="";
            String title="";
            String batch="";
            String topic="";
            String subject="";
            String fileName="";
            String status="";
            String addedBy="";
            String addedAt="";

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

            public String getFileName() {
                return fileName;
            }

            public void setFileName(String fileName) {
                this.fileName = fileName;
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

            public String getAddedAt() {
                return addedAt;
            }

            public void setAddedAt(String addedAt) {
                this.addedAt = addedAt;
            }
        }
    }
}
