package test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Levenshtein {
	private static Properties p = new Properties();
	private static int titles = 0;
	private static List<Long> ids = new ArrayList<Long>();

	public static void main(String[] args) throws IOException {
//		String str1 = "赵作海获释近4年 得到首份工作成环卫工人(图)";
//		String str2 = "赵作海获释4年后当上环卫工 月薪1000多元(图)";
//		String str3 = "蒙冤入狱11年农民赵作海获释4年后 获得首份工作";
//		String str4 = "中央电视台曝光了藏秘双宝是假药，东平想买的人都醒醒吧！\r\n\r\n\r\n[复制链接]";
		String str5 = "赵作海被儿赶出家门后扫大街\\ 称社会比监狱复杂";
////		double d = getSimi2(str2, str1);
////		System.out.println(d);
//		str1 = rmHolder(str1);
//		str2 = rmHolder(str2);
//		str3 = rmHolder(str3);
//		str4 = rmHolder(str4);
		str5 = rmHolder(str5);
		System.out.println(str5);
//		System.out.println(rmChong(str1,str2));
		
		try {
			warning_send_sms("1740");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static String rmHolder(String str) {
		if(str != null) {
			str = str.trim().replaceAll(" ", "").replaceAll("(\\(|（)[\u4E00-\u9FFF、，,×\\w]*(）|\\))", "")
						.replaceAll("\\\\n", "").replaceAll("\\\\r", "")
						.replaceAll("\\n", "").replaceAll("\\r", "")
						.replaceAll("\\\\", "").replaceAll("&\\\\#\\d*;", "")
						.replaceAll("“", "").replaceAll("\\“", "");
		}
		return str==null ? "" : str;
	}
	
	private static double rmChong(String str1, String str2) {
		double s1 = 0;
		for(int i=0; i<str1.length(); i++) {
			if(str2.contains(str1.charAt(i) + "")) {
				s1++;
			}
			
		}
		double d1 = s1/str2.length();
		//System.out.println(s1/str2.length());
		
		double s2 = 0;
		for(int i=0; i<str2.length(); i++) {
			if(str1.contains(str2.charAt(i)+"")) {
				s2++;
			}
		}
		double d2= s2/str1.length();
		//System.out.println(s2/str1.length());
		
		return (d1+d2)/2;
 	}

	public static List<String> warning_send_sms(String user_id) throws SQLException, IOException {
		String fileName = System.getProperty("user.dir") + "/logs/max_id_"+ user_id +".txt";//日志文件名
		
		String words = "";
		String urls = "";
		String filter = "on";
//		String related = "";
		String filter_similar = "";
		String weight = "10"; //默认权重为10
		
		try {
System.out.println(fileName);			
			p.load(new FileReader(fileName));//加载日志文件信息
		} catch (FileNotFoundException e) {
			System.out.println("初始化max_id日志文件！");
		}
		
		 
		Object[] obj1 = {1l,"赵作海获释近4年 得到首份工作成环卫工人(图)","赵作海获释近4年 得到首份工作成环卫工人(图)","赵作海获释近4年 得到首份工作成环卫工人(图)"};
		Object[] obj2 = {2l,"赵作海获释近4年 得到首份工作成环卫工人(图)","赵作海获释近4年 得到首份工作成环卫工人(图)","从“心情烦躁”到“干活不孬”被人认出总是嘿嘿一笑"};
		Object[] obj3 = {3l,"赵作海获释近4年 得到首份工作成环卫工人(图)","赵作海获释近4年 得到首份工作成环卫工人(图)","赵作海被儿赶出家门后扫大街\\ 称社会比监狱复杂"};
		
		List<Object[]> datas = new ArrayList<Object[]>();
		datas.add(obj1);
		datas.add(obj2);
		datas.add(obj3);
		
		List<String> content = new ArrayList<String>();
		int m =0;//记录循环次数
		for (Object[] objects : datas) {
			
			try {
				p.load(new FileReader(fileName));//每次循环都重新加载日志文件信息
			} catch (FileNotFoundException e) {
				System.out.println("初始化max_id日志文件！");
			}
			
			
			long id = (Long) objects[0];
			//int warning_send = (Integer) objects[1];
			String word = (String) objects[1];
			// 过滤关键词
			String title = (String) objects[3];
			String pubdate = "";
			
			//标题过滤
			title = title.trim().replaceAll(" {2,}", " ").replaceAll("\\\\n", "").replaceAll("\\\\r", "").replaceAll("&nbsp;", "").replaceAll("\\n", "").replaceAll("\\r", "").replace("[复制链接]", "");
			if(title.contains("????")) {
				continue;
			}
			
			if("on".equals(filter)) {
				Set<String> set =  p.stringPropertyNames();
				boolean simi = false;
				for(String s : set) {
					if(s.contains("title")) {
						double v = rmChong(rmHolder(title), rmHolder(s.replace("title：", "")));
						if(v > 0.5) {//相似度为0.5
							simi = true;
							System.out.println("标题过滤一条:" + title+ "相似度：" + v);
						}
					}
				}
				
				if(simi) {
					continue;
				}
				
				ids.add(id);
			}
			
			
			//保存标题
			if(p.size() > 21) {//超过20个title清空重来
				String userId = p.getProperty(user_id);
				p.clear();
				p.setProperty(user_id, userId);
			}
			p.setProperty("title：" + title, user_id);
			
		}

		// 保存最大id
		if (ids.size() > 0) {
			p.setProperty(user_id, Collections.max(ids) + "");
			p.store(new FileWriter(fileName), "user_id 为" + user_id
					+ "的最大记录的id");
		}
		
		p.clear();

		return content;
	}
	
}