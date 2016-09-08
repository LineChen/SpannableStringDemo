package com.beiing.spannablestringdemo.bean;

/**
 * Created by chenliu on 2016/9/8.<br/>
 * 描述：话题
 * </br>
 */
public class Topic {
    private int id;

    private String title;

    public  Topic(){

    }

    public Topic(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
