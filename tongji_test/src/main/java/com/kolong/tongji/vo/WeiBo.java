package com.kolong.tongji.vo;

import java.util.Date;

public class WeiBo {
	// Î¢²©×Ö¶Î
	private String wid;
	private String uid;
	private int comm;
	private int rt;
	private String user;
	private int user_verified;
	private String sour;
	private int userId;
	private String text;
	private Date rq;
	
	private int v_count;//Î¢²©ÈÏÖ¤Êý

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getComm() {
		return comm;
	}

	public void setComm(int comm) {
		this.comm = comm;
	}

	public int getRt() {
		return rt;
	}

	public void setRt(int rt) {
		this.rt = rt;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getUser_verified() {
		return user_verified;
	}

	public void setUser_verified(int user_verified) {
		this.user_verified = user_verified;
	}

	public String getSour() {
		return sour;
	}

	public void setSour(String sour) {
		this.sour = sour;
	}

	public int getV_count() {
		return v_count;
	}

	public void setV_count(int v_count) {
		this.v_count = v_count;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getRq() {
		return rq;
	}

	public void setRq(Date rq) {
		this.rq = rq;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
