package com.fei.youlu.entity;

import java.io.Serializable;

/**
 * ��������һ������Ϣ�Ự
 */
public class Conversation implements Serializable{
	private int id;
	private int photoId;
	private String number;
	private String name;
	private String body;
	private long time;
	private int read;

	public Conversation() {
		// TODO Auto-generated constructor stub
	}

	public Conversation(int id, int photoId, String number, String name,
			String body, long time, int read) {
		super();
		this.id = id;
		this.photoId = photoId;
		this.number = number;
		this.name = name;
		this.body = body;
		this.time = time;
		this.read = read;
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

	public int getRead() {
		return read;
	}

	public void setRead(int read) {
		this.read = read;
	}

	@Override
	public String toString() {
		return "Conversation [id=" + id + ", photoId=" + photoId + ", number="
				+ number + ", name=" + name + ", body=" + body + ", time="
				+ time + ", read=" + read + "]";
	}
}
