package com.study.gst.mmpapp.model;

//메인페이지의 이벤트
public class Event {
    private String TITLE;
    private String CONTENT;
    private String IMAGE;

    public Event(String title, String content, String image) {
        this.TITLE = title;
        this.CONTENT = content;
        this.IMAGE = image;
    }
    public String getTITLE(){
        return TITLE;
    }
    public String getCONTENT(){
        return CONTENT;
    }
    public String getIMAGE(){
        return IMAGE;
    }
}
