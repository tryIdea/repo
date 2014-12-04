package com.kolong.tongji.vo;

import java.sql.Date;

public class Table {
	private int id;//文章id
	private int user_id;//行业id
	private int userCount;//针对网站的发文量
	private int similar_count;//针对网站的发文量
	private String site_name; //网站名
	private int site_cls; //网站类型
	private String site_url; //网站url
	private String domain_1; //网站一级域名
	private Date pubdate; //文章发布时间
	private int view;//浏览量
	private String title; //标题
	private int reply;//回复量
	private int reprint;//转载量
	private String description; //正文
	private String url; //url
	private String uuid;
	
	private String grade;//网站影响力pr值等级
	private int sCount;//网站数量
	
	private int side_wrong_count;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public String getDomain_1() {
		return domain_1;
	}
	public void setDomain_1(String domain_1) {
		this.domain_1 = domain_1;
	}
	public Date getPubdate() {
		return pubdate;
	}
	public void setPubdate(Date pubdate) {
		this.pubdate = pubdate;
	}
	public int getView() {
		return view;
	}
	public void setView(int view) {
		this.view = view;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getReply() {
		return reply;
	}
	public void setReply(int reply) {
		this.reply = reply;
	}
	public int getReprint() {
		return reprint;
	}
	public void setReprint(int reprint) {
		this.reprint = reprint;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public int getSCount() {
		return sCount;
	}
	public void setSCount(int sCount) {
		this.sCount = sCount;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getSide_wrong_count() {
		return side_wrong_count;
	}
	public void setSide_wrong_count(int side_wrong_count) {
		this.side_wrong_count = side_wrong_count;
	}
	public int getSimilar_count() {
		return similar_count;
	}
	public void setSimilar_count(int similar_count) {
		this.similar_count = similar_count;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getSite_url() {
		return site_url;
	}
	public void setSite_url(String site_url) {
		this.site_url = site_url;
	}
	public int getSite_cls() {
		return site_cls;
	}
	public void setSite_cls(int site_cls) {
		this.site_cls = site_cls;
	}
}
