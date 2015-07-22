package com.fei.mymyy.entity;

import java.io.Serializable;

/**
 * Created by Fei on 25/06/15.
 */
public class PageModule implements Serializable{

    private String content;
    private int id;
    private String name;
    private String snapShot;

    public PageModule() {
    }

    public PageModule(String content, int id, String name, String snapShot) {
        this.content = content;
        this.id = id;
        this.name = name;
        this.snapShot = snapShot;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSnapShot() {
        return snapShot;
    }

    public void setSnapShot(String snapShot) {
        this.snapShot = snapShot;
    }

    @Override
    public String toString() {
        return "PageModule{" +
                "content='" + content + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", snapShot='" + snapShot + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageModule that = (PageModule) o;

        if (id != that.id) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return !(snapShot != null ? !snapShot.equals(that.snapShot) : that.snapShot != null);

    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (snapShot != null ? snapShot.hashCode() : 0);
        return result;
    }
}
