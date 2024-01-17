package com.pixelnx.eacademy.model;

import java.io.Serializable;

public class ModelDynamicNotices implements Serializable {
    String name;
    String descp;
    String other;
    String id;
    String videoId;
    String videoType;

    public ModelDynamicNotices(String name, String descp, String other, String id, String videoId,String videoType) {
        this.name = name;
        this.descp = descp;
        this.other = other;
        this.id = id;
        this.videoId = videoId;
        this.videoType=videoType;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
