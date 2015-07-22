package com.fei.youlu.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Fei on 10/06/2015.
 */
public class CallLog implements Serializable {

    private int id;
    private String number;
    private String name;
    private long date;
    private int photoId;
    private int type;

    public CallLog() {
    }

    public CallLog(int id, String number, String name, long date, int photoId, int type) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.date = date;
        this.photoId = photoId;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CallLog{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", photoId=" + photoId +
                ", type=" + type +
                '}';
    }
}
