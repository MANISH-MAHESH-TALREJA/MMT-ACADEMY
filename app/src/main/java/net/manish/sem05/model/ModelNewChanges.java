package com.pixelnx.eacademy.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelNewChanges implements Serializable {

    String status="";
    ArrayList<ExtraClass> extraClass;
    ArrayList<homeWorkClass> homeWork;
    ArrayList<videoLectureClass> videoLecture;
    ArrayList<ModelNewChanges.vacancy> vacancy;
    ArrayList<ModelNewChanges.notices> notices;
    ArrayList<ModelNewChanges.practicePaper> practicePaper;
    ArrayList<mock_paper> mockPaper;
    ArrayList<ModelNewChanges.addNewNotes> addNewNotes;
    ArrayList<ModelNewChanges.addOldPaper> addOldPaper;
    ArrayList<ModelNewChanges.addNewBook> addNewBook;

    public ArrayList<ModelNewChanges.addNewNotes> getAddNewNotes() {
        return addNewNotes;
    }

    public void setAddNewNotes(ArrayList<ModelNewChanges.addNewNotes> addNewNotes) {
        this.addNewNotes = addNewNotes;
    }

    public ArrayList<ModelNewChanges.addOldPaper> getAddOldPaper() {
        return addOldPaper;
    }

    public void setAddOldPaper(ArrayList<ModelNewChanges.addOldPaper> addOldPaper) {
        this.addOldPaper = addOldPaper;
    }

    public ArrayList<ModelNewChanges.addNewBook> getAddNewBook() {
        return addNewBook;
    }

    public void setAddNewBook(ArrayList<ModelNewChanges.addNewBook> addNewBook) {
        this.addNewBook = addNewBook;
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

    public ArrayList<ModelNewChanges.practicePaper> getPracticePaper() {
        return practicePaper;
    }

    public void setPracticePaper(ArrayList<ModelNewChanges.practicePaper> practicePaper) {
        this.practicePaper = practicePaper;
    }

    public ArrayList<mock_paper> getMockPaper() {
        return mockPaper;
    }

    public void setMockPaper(ArrayList<mock_paper> mockPaper) {
        this.mockPaper = mockPaper;
    }



    public ArrayList<ModelNewChanges.vacancy> getVacancy() {
        return vacancy;
    }

    public void setVacancy(ArrayList<ModelNewChanges.vacancy> vacancy) {
        this.vacancy = vacancy;
    }

    public ArrayList<ModelNewChanges.notices> getNotices() {
        return notices;
    }

    public void setNotices(ArrayList<ModelNewChanges.notices> notices) {
        this.notices = notices;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getAddedAt() {
            return addedAt;
        }

        public void setAddedAt(String addedAt) {
            this.addedAt = addedAt;
        }

        public String getTeachGender() {
            return teachGender;
        }

        public void setTeachGender(String teachGender) {
            this.teachGender = teachGender;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
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

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
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

        public String getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(String teacherId) {
            this.teacherId = teacherId;
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

        public String getAddedAt() {
            return addedAt;
        }

        public void setAddedAt(String addedAt) {
            this.addedAt = addedAt;
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



        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getVideoType() {
            return videoType;
        }

        public void setVideoType(String videoType) {
            this.videoType = videoType;
        }

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

}
