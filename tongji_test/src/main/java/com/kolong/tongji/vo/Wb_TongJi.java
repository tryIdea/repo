package com.kolong.tongji.vo;

import java.util.Date;

public class Wb_TongJi {
	private int id;
	private int userId;
	private int wb_userCount;
	private int wb_viewCount;
	private int wb_rtCount;
	private int wb_commCount;
	private int wb_sideCount;
	private int wb_positiveCount;
	private int wb_side_wrong_count;
	private int wb_minCount;
	private Date rq;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getWb_userCount() {
		return wb_userCount;
	}
	public void setWb_userCount(int wb_userCount) {
		this.wb_userCount = wb_userCount;
	}
	public int getWb_viewCount() {
		return wb_viewCount;
	}
	public void setWb_viewCount(int wb_viewCount) {
		this.wb_viewCount = wb_viewCount;
	}
	public int getWb_rtCount() {
		return wb_rtCount;
	}
	public void setWb_rtCount(int wb_rtCount) {
		this.wb_rtCount = wb_rtCount;
	}
	public int getWb_commCount() {
		return wb_commCount;
	}
	public void setWb_commCount(int wb_commCount) {
		this.wb_commCount = wb_commCount;
	}
	public int getWb_sideCount() {
		return wb_sideCount;
	}
	public void setWb_sideCount(int wb_sideCount) {
		this.wb_sideCount = wb_sideCount;
	}
	public int getWb_positiveCount() {
		return wb_positiveCount;
	}
	public void setWb_positiveCount(int wb_positiveCount) {
		this.wb_positiveCount = wb_positiveCount;
	}
	public int getWb_side_wrong_count() {
		return wb_side_wrong_count;
	}
	public void setWb_side_wrong_count(int wb_side_wrong_count) {
		this.wb_side_wrong_count = wb_side_wrong_count;
	}
	public Date getRq() {
		return rq;
	}
	public void setRq(Date rq) {
		this.rq = rq;
	}
	public int getWb_minCount() {
		return wb_minCount;
	}
	public void setWb_minCount(int wb_minCount) {
		this.wb_minCount = wb_minCount;
	}
}
