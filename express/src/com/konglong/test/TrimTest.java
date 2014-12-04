package com.konglong.test;

public class TrimTest {
	public static void main(String[] args) {
		String value = "　　俞子东 ";
		char[] val = new char[value.length()];
		value.getChars(0, value.length(), val, 0);//字符串转换成字符数组

		System.out.println(val.length);
		
		System.out.println(value.replaceAll("　| ", ""));
		
		System.out.println(myTrim(value, " 　"));
	}

	static String myTrim(String source, String toTrim) {//将字符串两边的半角空格、全角空格去掉，其他也可以
		StringBuffer sb = new StringBuffer(source);
		while (toTrim.indexOf(new Character(sb.charAt(0)).toString()) != -1) {
			sb.deleteCharAt(0);
		}
		while (toTrim.indexOf(new Character(sb.charAt(sb.length() - 1))
				.toString()) != -1) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
}
