package com.pixelnx.eacademy.ui.batch;

public class ModelCount {
   int videoid;
    int chapterid;
    int LayoutId;

    public ModelCount(int videoid, int chapterid, int layoutId) {

        this.videoid = videoid;
        this.chapterid = chapterid;
        LayoutId = layoutId;
    }

    public int getVideoid() {
        return videoid;
    }

    public void setVideoid(int videoid) {
        this.videoid = videoid;
    }

    public int getChapterid() {
        return chapterid;
    }

    public void setChapterid(int chapterid) {
        this.chapterid = chapterid;
    }

    public int getLayoutId() {
        return LayoutId;
    }

    public void setLayoutId(int layoutId) {
        LayoutId = layoutId;
    }
}
