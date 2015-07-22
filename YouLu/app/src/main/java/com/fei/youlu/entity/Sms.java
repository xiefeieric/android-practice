package com.fei.youlu.entity;

import java.io.Serializable;

/**
 * ����һ������Ϣ
 */
public class Sms implements Serializable{
	private int id;
	private int photoId;
	private String body;
	private long time;
	private int type;
	private String name;

	public Sms() {
		// TODO Auto-generated constructor stub
	}

	public Sms(int id, int photoId, String body, long time, int type, String name) {
		super();
		this.id = id;
		this.photoId = photoId;
		this.body = body;
		this.time = time;
		this.type = type;
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

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return this.body;
	}
	
}
