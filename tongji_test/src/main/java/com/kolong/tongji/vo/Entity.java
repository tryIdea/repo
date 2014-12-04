package com.kolong.tongji.vo;

import java.util.Date;

/**
 * 
 * 通用类，用来处理敏感词。。。
 * @author dfg
 *
 */
public class Entity implements Comparable<Entity>{
	private int id;
	private String name;
	private String site_name;
	private double count;
	private double count2;
	private String url;
	private String uuid;
	private int user_id;
	private Date rq;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getCount() {
		return count;
	}
	public void setCount(double count) {
		this.count = count;
	}
	@Override
	public int compareTo(Entity o) {
		if(this.count > o.getCount()) {
			return -1;
		}else if(this.count == o.getCount()) {
			return 0;
		}else {
			return 1;
		}
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getRq() {
		return rq;
	}
	public void setRq(Date rq) {
		this.rq = rq;
	}
	public double getCount2() {
		return count2;
	}
	public void setCount2(double count2) {
		this.count2 = count2;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
}
