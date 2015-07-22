package com.fei.youlu.entity;

/**
 * Created by Fei on 06/06/2015.
 */
public class Contact {

    private int id;
    private int photoId;
    private String phone;
    private String email;
    private String address;
    private String name;

    public Contact() {
    }

    public Contact(int id, int photoId, String phone, String email, String address, String name) {
        this.id = id;
        this.photoId = photoId;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", photoId=" + photoId +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
