package com.konglong.test;

import java.sql.Time;
import java.util.Date;

public class Message {
	public static void main(String[] args) {
		long l = 1394761301000l;
		System.out.println(new Date(l).toLocaleString());
		
		String str = "上诉人王秀蓉。sdfds";
		System.out.println(str.split("，|。")[0]);
		System.out.println("被上诉人（一审原告）：孙波，男，1982年4月18日出生".length());
	}
}
