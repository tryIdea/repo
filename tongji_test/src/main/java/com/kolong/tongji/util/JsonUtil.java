package com.kolong.tongji.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;

import net.sf.json.util.JSONStringer;

import com.kolong.tongji.vo.Entity;
import com.kolong.tongji.vo.FirstLevelIndicator;
import com.kolong.tongji.vo.Lt_TongJi;
import com.kolong.tongji.vo.Table;
import com.kolong.tongji.vo.ThirdLevelIndicator;
import com.kolong.tongji.vo.Wb_TongJi;

public class JsonUtil {
	private static final Logger logger = Logger.getLogger(JsonUtil.class);
	
	
	
	/**
	 * 提取相应的json字符串
	 * @param thirdLevelIndicators 源数据
	 * @param MONITORCIRCLE 监测周期
	 * @param method 对应的方法名
	 * @param isNew  isNew=true对应新增发文量...否则对应累积发文量...
	 * @return json字符串
	 */
	public static String getJsonString(List<ThirdLevelIndicator> thirdLevelIndicators,List<Wb_TongJi> wbs, List<Lt_TongJi> lts, final int MONITORCIRCLE,String method,String wb_method,String lt_method, boolean isNew) {
		JSONStringer json = new JSONStringer();
		JSONStringer data = new JSONStringer();//全部数据=普通+微博数据
		JSONStringer wb_data = new JSONStringer();//微博数据
		JSONStringer lt_data = new JSONStringer();//论坛数据
		JSONStringer categoriesArr = new JSONStringer();
		
		try {
			data.array();
			categoriesArr.array();
			int sumT = 0;
			for(int i=0; i<thirdLevelIndicators.size(); i++) {
				ThirdLevelIndicator third = thirdLevelIndicators.get(i);
				Method m = ThirdLevelIndicator.class.getMethod(method);
				sumT += (Integer) m.invoke(third);
				
				if((i+1)%MONITORCIRCLE == 0) {
					data.value(sumT);
					categoriesArr.value(third.getRq());
					if(isNew) {
						sumT = 0;
					}
				}
			}
			data.endArray();
			categoriesArr.endArray();
			
			//微博数据
			wb_data.array();
			int wb_sumT = 0;
			for(int i=0; i<wbs.size(); i++) {
				Wb_TongJi wb = wbs.get(i);
				Method m = Wb_TongJi.class.getMethod(wb_method);
				wb_sumT += (Integer) m.invoke(wb);
				
				if((i+1)%MONITORCIRCLE == 0) {
					wb_data.value(wb_sumT);
					if(isNew) {
						wb_sumT = 0;
					}
				}
			}
			wb_data.endArray();
			
			//论坛数据
			lt_data.array();
			int lt_sumT = 0;
			for(int i=0; i<lts.size(); i++) {
				Lt_TongJi lt = lts.get(i);
				Method m = Lt_TongJi.class.getMethod(lt_method);
				lt_sumT += (Integer) m.invoke(lt);
				
				if((i+1)%MONITORCIRCLE == 0) {
					lt_data.value(lt_sumT);
					if(isNew) {
						lt_sumT = 0;
					}
				}
			}
			lt_data.endArray();
			
			json.object()
				.key("data")
				.value(data)
				.key("wb_data")
				.value(wb_data)
				.key("lt_data")
				.value(lt_data)
				.key("categories")
				.value(categoriesArr)
				.endObject();
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);		
		}
		return json.toString();
	}
	/**
	 * 提取相应的json字符串(备份)
	 * @param thirdLevelIndicators 源数据
	 * @param MONITORCIRCLE 监测周期
	 * @param name  监测指标
	 * @param method 对应的方法名
	 * @param isNew  是否是新增的。。
	 * @return json字符串
	 */
	public static String getJsonStringForSites(List<ThirdLevelIndicator> thirdLevelIndicators, final int MONITORCIRCLE, String name,String title,String method,boolean isNew) {
		JSONStringer json = new JSONStringer();
		JSONStringer arr1 = new JSONStringer();
		JSONStringer arr2 = new JSONStringer();
		try {
			arr1.array();
			arr2.array();
			
			int sumT = 0;
			for(int i=0; i<thirdLevelIndicators.size(); i++) {
				ThirdLevelIndicator third = thirdLevelIndicators.get(i);
				Class<ThirdLevelIndicator> clazz = ThirdLevelIndicator.class;
				Method m = clazz.getMethod(method);
				sumT += (Integer) m.invoke(third);
				
				if((i+1)%MONITORCIRCLE == 0) {
					arr1.value(sumT);
					arr2.value(third.getRq());
					if(isNew) {
						sumT = 0;
					}
				}
			}
			arr1.endArray();
			arr2.endArray();
			
			json.object();
			json.key("name");
			json.value(name);
			json.key("title");
			json.value(title);
			json.key("data");
			json.value(arr1);
			json.key("categories");
			json.value(arr2);
			json.endObject();
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);		
		}
		return json.toString();
	}
	/**
	 * 提取相应的json字符串
	 * @param firstLevelIndicator 源数据
	 * @param MONITORCIRCLE 监测周期
	 * @param name  监测指标
	 * @param method 对应的方法名
	 * @param isNew  是否是新增的。。
	 * @return json字符串（double类型）
	 */
	public static String getJsonStringForFirst(List<FirstLevelIndicator> firstLevelIndicators, final int MONITORCIRCLE, String name,String title,String method,boolean isNew) {
		JSONStringer json = new JSONStringer();
		JSONStringer arr1 = new JSONStringer();
		JSONStringer arr2 = new JSONStringer();
		try {
			arr1.array();
			arr2.array();
			
			double sumT = 0;
			for(int i=0; i<firstLevelIndicators.size(); i++) {
				FirstLevelIndicator first = firstLevelIndicators.get(i);
				Class<FirstLevelIndicator> clazz = FirstLevelIndicator.class;
				Method m = clazz.getMethod(method);
				double temp = (Double) m.invoke(first);
				
				temp = convertFromNonfinity(temp);
				
				sumT += temp;
				
				if((i+1)%MONITORCIRCLE == 0) {
					arr1.value(sumT);
					arr2.value(first.second.third.getRq());
					if(isNew) {
						sumT = 0;
					}
				}
			}
			arr1.endArray();
			arr2.endArray();
			
			json.object();
			json.key("name");
			json.value(name);
			json.key("title");
			json.value(title);
			json.key("data");
			json.value(arr1);
			json.key("categories");
			json.value(arr2);
			json.endObject();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);		
		}
		return json.toString();
	}
	
	/**
	 * 在发布周期内某个细分行业的舆情指数
	 * @param firstLevelIndicators
	 * @param MONITORCIRCLE
	 * @param name
	 * @param title
	 * @param method
	 * @param isNew
	 * @return
	 */
	public static double getCPOI20(List<FirstLevelIndicator> firstLevelIndicators, final int MONITORCIRCLE,boolean isNew) {
		double CPOI = 0;//舆情指数
		
		try {
			
			List<Double> transList = getTransList(firstLevelIndicators, MONITORCIRCLE);
			
			
			double sumT1 = 0;
			double sumT2 = 0;
			double sumT3 = 0;
			double sumT4 = 0;
			for(int i=0; i<firstLevelIndicators.size(); i++) {
				FirstLevelIndicator first = firstLevelIndicators.get(i);
				
				double temp1 = getTemp(first,"getExposIndex");
				double temp2 = getTemp(first,"getParticipationIndex");
				double temp3 = getTemp(first,"getPublicOpinionIndex");
				double temp4 = getTemp(first,"getSiteFeatureIndex");
				
				sumT1 += temp1;
				sumT2 += temp2;
				sumT3 += temp3;
				sumT4 += temp4;
				
				int j = 0;
				if((i+1)%MONITORCIRCLE == 0) {
					double sumT5 = transList.get(j);
					
					CPOI = sumT1*Params.get("exposIndexWeight") + sumT2*Params.get("participationIndexWeight") + sumT3*Params.get("publicOpinionIndexWeight") + sumT4*Params.get("siteFeatureIndexWeight") + sumT5*Params.get("transIndexWeight");
					
					CPOI = convertFromNonfinity(CPOI);
					
					if(isNew) {
						sumT1 = 0;
						sumT2 = 0;
						sumT3 = 0;
						sumT4 = 0;
					}
					
					j++;
				}
			}
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);		
		}
		return CPOI;
	}
	
	public static String getCPOI(List<FirstLevelIndicator> firstLevelIndicators, final int MONITORCIRCLE, String name,String title,String method,boolean isNew) {
		JSONStringer json = new JSONStringer();
		JSONStringer arr1 = new JSONStringer();
		
		try {
			arr1.array();
			
			
			List<Double> transList = getTransList(firstLevelIndicators, MONITORCIRCLE);
			
			double sumT1 = 0;
			double sumT2 = 0;
			double sumT3 = 0;
			double sumT4 = 0;
			for(int i=0; i<firstLevelIndicators.size(); i++) {
				FirstLevelIndicator first = firstLevelIndicators.get(i);
				
				double temp1 = getTemp(first,"getExposIndex");
				double temp2 = getTemp(first,"getParticipationIndex");
				double temp3 = getTemp(first,"getPublicOpinionIndex");
				double temp4 = getTemp(first,"getSiteFeatureIndex");
				
				sumT1 += temp1;
				sumT2 += temp2;
				sumT3 += temp3;
				sumT4 += temp4;
				
				int j = 0;
				if((i+1)%MONITORCIRCLE == 0) {
					double sumT5 = transList.get(j);
					
					double CPOI = sumT1*Params.get("exposIndexWeight") + sumT2*Params.get("participationIndexWeight") + sumT3*Params.get("publicOpinionIndexWeight") + sumT4*Params.get("siteFeatureIndexWeight") + sumT5*Params.get("transIndexWeight");
					JSONStringer arr2 = new JSONStringer();
					arr2.array()
						.value(first.second.third.getRq())
						.value(convertFromNonfinity(CPOI))
						.endArray();
					if(isNew) {
						sumT1 = 0;
						sumT2 = 0;
						sumT3 = 0;
						sumT4 = 0;
					}
					
					j++;
					
					arr1.value(arr2);
				}
			}
			arr1.endArray();
			
			
			json.object();
			json.key("name");
			json.value(name);
			json.key("title");
			json.value(title);
			json.key("data");
			json.value(arr1);
			json.endObject();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);		
		}
		return json.toString();
	}
	
	public static double convertFromNonfinity(double v) {
		//处理意外情况，基本不可能发生的事
		if(Double.isInfinite(v)) {
			v = 0.01;
		}
		if(Double.isNaN(v)) {
			v = 0;
		}
		return v;
	}
	
	/**
	 * 获取全行业及细分行业的舆情指数列表
	 * @param firstLevelIndicators
	 * @param MONITORCIRCLE
	 * @param isNew
	 * @return
	 */
	public static List<Double> getCPOIList(List<FirstLevelIndicator> firstLevelIndicators, final int MONITORCIRCLE, boolean isNew) {
		List<Double> cpois = new ArrayList<Double>();
		try {
			
			List<Double> transList = getTransList(firstLevelIndicators, MONITORCIRCLE);
			
			double sumT1 = 0;
			double sumT2 = 0;
			double sumT3 = 0;
			double sumT4 = 0;
			for(int i=0; i<firstLevelIndicators.size(); i++) {
				FirstLevelIndicator first = firstLevelIndicators.get(i);
				
				double temp1 = getTemp(first,"getExposIndex");
				double temp2 = getTemp(first,"getParticipationIndex");
				double temp3 = getTemp(first,"getPublicOpinionIndex");
				double temp4 = getTemp(first,"getSiteFeatureIndex");
				
				sumT1 += temp1;
				sumT2 += temp2;
				sumT3 += temp3;
				sumT4 += temp4;
				
				int j = 0;
				if((i+1)%MONITORCIRCLE == 0) {
					double sumT5 = transList.get(j);
					
					double CPOI = sumT1*Params.get("exposIndexWeight") + sumT2*Params.get("participationIndexWeight") + sumT3*Params.get("publicOpinionIndexWeight") + sumT4*Params.get("siteFeatureIndexWeight") + sumT5*Params.get("transIndexWeight");
					
					cpois.add(CPOI);
					if(isNew) {
						sumT1 = 0;
						sumT2 = 0;
						sumT3 = 0;
						sumT4 = 0;
					}
					
					j++;
				}
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);		
		}
		return cpois;
	}
	
	/**
	 * 
	 * 根据方法名调用对应的方法，返回对应的值
	 * @param first
	 * @param method
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	private static double getTemp(FirstLevelIndicator first,String method) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Class<FirstLevelIndicator> clazz = FirstLevelIndicator.class;
		Method m = clazz.getMethod(method);
		double temp = (Double) m.invoke(first);
		
		return temp;
	}
	
	public static String getJsonStringForRate(List<FirstLevelIndicator> firstLevelIndicators, final int MONITORCIRCLE, String name,String title,String method,boolean isNew) {
		JSONStringer json = new JSONStringer();
		JSONStringer arr1 = new JSONStringer();
		JSONStringer arr2 = new JSONStringer();
		try {
			arr1.array();
			
			double sumT = 0;
			
			List<Double> doubles = getExposIndexForRate(firstLevelIndicators, MONITORCIRCLE, method);
			if(doubles.size()>0) {
				arr1.value(0.0);
			}
			for(int i=0; i<doubles.size()-1; i++) {
				sumT = (doubles.get(i+1) - doubles.get(i))/doubles.get(i);//曝光程度变化率计算公式
				arr1.value(sumT);
			}
			
			arr2 = getJsonRq(firstLevelIndicators,MONITORCIRCLE);
			
			arr1.endArray();
			
			json.object();
			json.key("name");
			json.value(name);
			json.key("title");
			json.value(title);
			json.key("data");
			json.value(arr1);
			json.key("categories");
			json.value(arr2);
			json.endObject();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);		
		}
		return json.toString();
	}
	
	/**
	 * 获取传播进度列表
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	private static List<Double> getTransList(List<FirstLevelIndicator> firstLevelIndicators, final int MONITORCIRCLE) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<Double> transList = new ArrayList<Double>();
		
		List<Double> doubles1 = getExposIndexForRate(firstLevelIndicators, MONITORCIRCLE, "getExposIndex");
		List<Double> doubles2 = getExposIndexForRate(firstLevelIndicators, MONITORCIRCLE, "getParticipationIndex");
		List<Double> doubles3 = getExposIndexForRate(firstLevelIndicators, MONITORCIRCLE, "getPublicOpinionIndex");
		List<Double> doubles4 = getExposIndexForRate(firstLevelIndicators, MONITORCIRCLE, "getSiteFeatureIndex");
		if(doubles1.size()>0) {
			double index1 = getIndex(doubles1.get(0),doubles1.get(0),"ExposIndexRate");
			
			double index2 = getIndex(doubles2.get(0),doubles2.get(0),"ParticipationIndexRate");

			double index3 = getIndex(doubles3.get(0),doubles3.get(0),"PublicOpinionIndexRate");

			double index4 = getIndex(doubles4.get(0),doubles4.get(0),"SiteFeatureIndexRate");
			
			transList.add(getTransIndex(index1, index2, index3, index4));
		}
		for(int i=0; i<doubles1.size()-1; i++) {
			double index1 = getIndex(doubles1.get(i),doubles1.get(i+1),"ExposIndexRate");
			
			double index2 = getIndex(doubles2.get(i),doubles2.get(i+1),"ParticipationIndexRate");

			double index3 = getIndex(doubles3.get(i),doubles3.get(i+1),"PublicOpinionIndexRate");

			double index4 = getIndex(doubles4.get(i),doubles4.get(i+1),"SiteFeatureIndexRate");
			
			transList.add(getTransIndex(index1, index2, index3, index4));
		}
		
		return transList;
	}
	
	/**
	 * 传播进度
	 * @param firstLevelIndicators
	 * @param MONITORCIRCLE
	 * @param name
	 * @param title
	 * @param method
	 * @param isNew
	 * @return
	 */
	public static String getTransIndex(List<FirstLevelIndicator> firstLevelIndicators, final int MONITORCIRCLE, String name,String title) {
		JSONStringer json = new JSONStringer();
		JSONStringer arr1 = new JSONStringer();
		JSONStringer arr2 = new JSONStringer();
		try {
			arr1.array();
			
			//double sumT = 0;
			
			List<Double> doubles1 = getExposIndexForRate(firstLevelIndicators, MONITORCIRCLE, "getExposIndex");
			List<Double> doubles2 = getExposIndexForRate(firstLevelIndicators, MONITORCIRCLE, "getParticipationIndex");
			List<Double> doubles3 = getExposIndexForRate(firstLevelIndicators, MONITORCIRCLE, "getPublicOpinionIndex");
			List<Double> doubles4 = getExposIndexForRate(firstLevelIndicators, MONITORCIRCLE, "getSiteFeatureIndex");
			if(doubles1.size()>0) {
				double index1 = getIndex(doubles1.get(0),doubles1.get(0),"ExposIndexRate");
				
				double index2 = getIndex(doubles2.get(0),doubles2.get(0),"ParticipationIndexRate");

				double index3 = getIndex(doubles3.get(0),doubles3.get(0),"PublicOpinionIndexRate");

				double index4 = getIndex(doubles4.get(0),doubles4.get(0),"SiteFeatureIndexRate");
				
				if(logger.isDebugEnabled()) {
					logger.debug("cpoi" + convertFromNonfinity(getTransIndex(index1, index2, index3, index4)));
				}
				arr1.value(convertFromNonfinity(getTransIndex(index1, index2, index3, index4)));
			}
			for(int i=0; i<doubles1.size()-1; i++) {
				double index1 = getIndex(doubles1.get(i),doubles1.get(i+1),"ExposIndexRate");
				
				double index2 = getIndex(doubles2.get(i),doubles2.get(i+1),"ParticipationIndexRate");

				double index3 = getIndex(doubles3.get(i),doubles3.get(i+1),"PublicOpinionIndexRate");

				double index4 = getIndex(doubles4.get(i),doubles4.get(i+1),"SiteFeatureIndexRate");
				
				if(logger.isDebugEnabled()) {
					logger.debug("cpoi" + convertFromNonfinity(getTransIndex(index1, index2, index3, index4)));
				}
				arr1.value(convertFromNonfinity(getTransIndex(index1, index2, index3, index4)));
			}
			
			arr2 = getJsonRq(firstLevelIndicators,MONITORCIRCLE);
			
			arr1.endArray();
			
			json.object();
			json.key("name");
			json.value(name);
			json.key("title");
			json.value(title);
			json.key("data");
			json.value(arr1);
			json.key("categories");
			json.value(arr2);
			json.endObject();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);		
		}
		return json.toString();
	}
	
	/**
	 * 获取传播进度
	 * @param index1
	 * @param index2
	 * @param index3
	 * @param index4
	 * @return
	 */
	private static double getTransIndex(double index1, double index2, double index3, double index4) {
		return index1*Params.get("exposIndexRateWeight") + index2*Params.get("participationIndexRateWeight") + index3*Params.get("publicOpinionIndexRateWeight") + index4*Params.get("siteFeatureIndexRateWeight");
	}
	
	/**
	 * 获得变化率指数
	 * @param d1
	 * @param d2
	 * @param name
	 * @return
	 */
	private static double getIndex(double d1, double d2, String name) {
		double index = ((d2 - d1)/d1-Params.get("min" + name))*100.0/(Params.get("max" + name)-Params.get("min" + name));
		return index;
	}
	
	
	/**
	 * 获得发布周期内曝光程度数组
	 * @param firstLevelIndicators
	 * @param MONITORCIRCLE
	 * @param method
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private static List<Double> getExposIndexForRate(List<FirstLevelIndicator> firstLevelIndicators, final int MONITORCIRCLE,String method) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		List<Double> doubles = new ArrayList<Double>();
		double sum = 0;
		for(int i=0; i<firstLevelIndicators.size(); i++) {
			FirstLevelIndicator first = firstLevelIndicators.get(i);
			Class<FirstLevelIndicator> clazz = FirstLevelIndicator.class;
			Method m = clazz.getMethod(method);
			sum += (Double)m.invoke(first);
			if((i+1)%MONITORCIRCLE == 0) {
				doubles.add(sum);
				sum = 0;
			}
		}
		
		return doubles;
	}
	
	/**
	 * 新增发文量下的文章来源类型数据
	 * @param thirdLevelIndicators 源数据
	 * @param MONITORCIRCLE 监测周期
	 * @param methods 对应的方法名
	 * @return 对象数组
	 */
	public static String getJsonArrayPie(List<ThirdLevelIndicator> thirdLevelIndicators, final int MONITORCIRCLE) {
		JSONStringer json = new JSONStringer();
		try {
			json.array();
			
			int sumT1 = 0;
			int sumT2 = 0;
			int sumT3 = 0;
			int sumT4 = 0;
			int sumT5 = 0;
			int sumT6 = 0;
			int sumT7 = 0;
			int sumT8 = 0;
			int sumT9 = 0;
			for(int i=0; i<thirdLevelIndicators.size(); i++) {
				ThirdLevelIndicator third = thirdLevelIndicators.get(i);
				
				sumT1 += third.getOfficialInfoCount();
				sumT2 += third.getNon_officialInfoCount();
				sumT3 += third.getCountryInfoCount();
				sumT4 += third.getProvinceInfoCount();
				sumT5 += third.getCityInfoCount();
				sumT6 += third.getXianjiInfoCount();
				sumT7 += third.getTraditionInfoCount();
				sumT8 += third.getGeneralInfoCount();
				sumT9 += third.getSocialInfoCount();
					if((i+1)%MONITORCIRCLE == 0) {
						JSONStringer obj = new JSONStringer();
						obj.object()
						.key("official").value(sumT1)
						.key("non_official").value(sumT2)
						.key("country").value(sumT3)
						.key("province").value(sumT4)
						.key("city").value(sumT5)
						.key("xianji").value(sumT6)
						.key("tradition").value(sumT7)
						.key("general").value(sumT8)
						.key("social").value(sumT9)
						.key("rq").value(third.getRq())
						.endObject();
						
						json.value(obj);
					}
					
			}
			json.endArray();
			
			if(logger.isDebugEnabled()) {
				logger.debug(json);
			}
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);		
		}
		return json.toString();
	}
	/**
	 * 网站权威性，区域全面性，类型全面性的数据
	 * @param thirdLevelIndicators 源数据
	 * @param MONITORCIRCLE 监测周期
	 * @param methods 对应的方法名
	 * @return 对象数组
	 */
	public static String getJsonArray(List<ThirdLevelIndicator> thirdLevelIndicators, final int MONITORCIRCLE, String ... methods) {
		JSONStringer json = new JSONStringer();
		try {
			json.array();
			
			int sumT1 = 0;
			int sumT2 = 0;
			int sumT3 = 0;
			int sumT4 = 0;
			Class<ThirdLevelIndicator> clazz = ThirdLevelIndicator.class;
			for(int i=0; i<thirdLevelIndicators.size(); i++) {
				ThirdLevelIndicator third = thirdLevelIndicators.get(i);
				
				if(methods.length == 2) {//官方与非官方(按此顺序)
					sumT1 += (Integer) clazz.getMethod(methods[0]).invoke(third);
					sumT2 += (Integer) clazz.getMethod(methods[1]).invoke(third);
				
					if((i+1)%MONITORCIRCLE == 0) {
						JSONStringer obj = new JSONStringer();
						obj.object()
							.key("official").value(sumT1)
							.key("non_official").value(sumT2)
							.key("rq").value(third.getRq())
							.endObject();
						
						json.value(obj);
						
						sumT1 = 0;
						sumT2 = 0;
					}
				}else if(methods.length == 4) {//区域全面性
					sumT1 += (Integer) clazz.getMethod(methods[0]).invoke(third);
					sumT2 += (Integer) clazz.getMethod(methods[1]).invoke(third);
					sumT3 += (Integer) clazz.getMethod(methods[2]).invoke(third);
					sumT4 += (Integer) clazz.getMethod(methods[3]).invoke(third);
				
				
					if((i+1)%MONITORCIRCLE == 0) {
						JSONStringer obj = new JSONStringer();
						obj.object()
							.key("country").value(sumT1)
							.key("province").value(sumT2)
							.key("city").value(sumT3)
							.key("xianji").value(sumT4)
							.key("rq").value(third.getRq())
							.endObject();
						
						json.value(obj);
						
						sumT1 = 0;
						sumT2 = 0;
						sumT3 = 0;
						sumT4 = 0;
					}
				} else if(methods.length == 3) {//类型全面性
					sumT1 += (Integer) clazz.getMethod(methods[0]).invoke(third);
					sumT2 += (Integer) clazz.getMethod(methods[1]).invoke(third);
					sumT3 += (Integer) clazz.getMethod(methods[2]).invoke(third);
				
				
					if((i+1)%MONITORCIRCLE == 0) {
						JSONStringer obj = new JSONStringer();
						obj.object()
							.key("tradition").value(sumT1)
							.key("general").value(sumT2)
							.key("social").value(sumT3)
							.key("rq").value(third.getRq())
							.endObject();
						
						json.value(obj);
						
						sumT1 = 0;
						sumT2 = 0;
						sumT3 = 0;
					}
				}
			}
			json.endArray();
			
			if(logger.isDebugEnabled()) {
				logger.debug(json);
			}
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);		
		}
		return json.toString();
	}
	/**
	 * 提取相应的json字符串(多系列)
	 * @param thirdLevelIndicators 源数据
	 * @param MONITORCIRCLE 监测周期
	 * @param method 对应的方法名
	 * @param isNew  是否是新增的。。
	 * @return json字符串
	 */
	public static String getJsonArray(List<ThirdLevelIndicator> thirdLevelIndicators, final int MONITORCIRCLE, String method,boolean isNew) {
		JSONStringer arr1 = new JSONStringer();
		try {
			arr1.array();
			
			int sumT1 = 0;
			for(int i=0; i<thirdLevelIndicators.size(); i++) {
				ThirdLevelIndicator third = thirdLevelIndicators.get(i);
				Class<ThirdLevelIndicator> clazz = ThirdLevelIndicator.class;
				Method m1 = clazz.getMethod(method);
				sumT1 += (Integer) m1.invoke(third);
				
				if((i+1)%MONITORCIRCLE == 0) {
					arr1.value(sumT1);
					if(isNew) {
						sumT1 = 0;
					}
				}
			}
			arr1.endArray();
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);		
		}
		return arr1.toString();
	}
	
	public static int getJsonArrayPie(List<ThirdLevelIndicator> thirdLevelIndicators, String method) {
		int sumT1 = 0;
		try {
			
			for(int i=0; i<thirdLevelIndicators.size(); i++) {
				ThirdLevelIndicator third = thirdLevelIndicators.get(i);
				Class<ThirdLevelIndicator> clazz = ThirdLevelIndicator.class;
				Method m1 = clazz.getMethod(method);
				sumT1 += (Integer) m1.invoke(third);
				
			}
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);		
		}
		return sumT1;
	}
	
	
	/**获取日期
	 * @param thirdLevelIndicators
	 * @param MONITORCIRCLE
	 * @param method
	 * @param isNew
	 * @return
	 */
	public static String getJsonRq(List<ThirdLevelIndicator> thirdLevelIndicators, final int MONITORCIRCLE,boolean isNew) {
		JSONStringer arr1 = new JSONStringer();
		try {
			arr1.array();
			
			//int sumT1 = 0;
			for(int i=0; i<thirdLevelIndicators.size(); i++) {
				ThirdLevelIndicator third = thirdLevelIndicators.get(i);
				
				if((i+1)%MONITORCIRCLE == 0) {
					arr1.value(third.getRq());
					if(isNew) {
						//sumT1 = 0;
						//sumT1++;
					}
				}
			}
			arr1.endArray();
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);		
		}
		return arr1.toString();
	}
	/**
	 * 获取日期数组
	 * @param firstLevelIndicators
	 * @param MONITORCIRCLE
	 * @return
	 */
	public static JSONStringer getJsonRq(List<FirstLevelIndicator> firstLevelIndicators, final int MONITORCIRCLE) {
		JSONStringer arr1 = new JSONStringer();
		try {
			arr1.array();
			
			for(int i=0; i<firstLevelIndicators.size(); i++) {
				FirstLevelIndicator first = firstLevelIndicators.get(i);
				
				if((i+1)%MONITORCIRCLE == 0) {
					arr1.value(first.second.third.getRq());
				}
			}
			arr1.endArray();
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);		
		}
		return arr1;
	}
	/**
	 * (版本1)
	 * 获取json字符串
	 * List<Table> tables
	 */
	public static String getJson(List<Table> tables,String method1,String method2,String method3) {
		
		JSONStringer arr = new JSONStringer();
		
		arr.array();
		try {
			Class<Table> clazz = Table.class;
			Method m1 = clazz.getMethod(getMName(method1));
			Method m2 = clazz.getMethod(getMName(method2));
			Method m3 = clazz.getMethod(getMName(method3));
			
			
			for(int i=0; i<tables.size(); i++){
				JSONStringer obj = new JSONStringer();
				Table table = tables.get(i);
				obj.object()
				.key(method1).value(m1.invoke(table))
				.key(method2).value(m2.invoke(table))
				.key(method3).value(m3.invoke(table))
				.endObject();
				arr.value(obj);
			}
			arr.endArray();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);		
		}
		return arr.toString();
	}
	
	
	/**
	 * (版本2)
	 * @param tables
	 * @param methods
	 * @return
	 */
	public static String getJsonTest(List<Table> tables,String ... methods) {
		
		JSONStringer arr = new JSONStringer();
		
		arr.array();
		try {
			Class<Table> clazz = Table.class;
			
			for(int i=0; i<tables.size(); i++){
				JSONStringer obj = new JSONStringer();
				Table table = tables.get(i);
				obj.object();
				for(String method : methods) {
					Method m = clazz.getMethod(getMName(method));
					obj.key(method).value(m.invoke(table));
				}
				obj.endObject();
				
				arr.value(obj);
			}
			arr.endArray();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);		
		}
		return arr.toString();
	}
	/**
	 * 泛型方法(版本3)
	 * @param tables
	 * @param methods
	 * @return
	 */
	public static <T> String getJsonTestGeneric(List<T> tables,String ... methods) {
		
		JSONStringer arr = new JSONStringer();
		
		arr.array();
		try {
			
			for(int i=0; i<tables.size(); i++){
				JSONStringer obj = new JSONStringer();
				T table = tables.get(i);
				
				Class<? extends Object> clazz = null;
				if(clazz == null) {
					clazz = table.getClass(); 
				}
				obj.object();
				for(String method : methods) {
					Method m = clazz.getMethod(getMName(method));
					obj.key(method).value(m.invoke(table));
				}
				obj.endObject();
				
				arr.value(obj);
			}
			arr.endArray();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);		
		}
		return arr.toString();
	}
	/**
	 *单个负面词统计对应的json数据
	 * @param tables
	 * @param methods
	 * @return
	 */
	public static String getJsonForOne(List<Entity> entities,final int MONITORCIRCLE) {
		
		JSONStringer arr = new JSONStringer();
		
		arr.array();
		try {
			int mTotalCount = 0;
			for(int i=0; i<entities.size(); i++){
				
				Entity entity = entities.get(i);
				mTotalCount += entity.getCount();
				if((i+1)%MONITORCIRCLE == 0) {
					JSONStringer obj = new JSONStringer();
					obj.object().key("rq").value(entity.getRq())
						.key("count").value(mTotalCount)
						.endObject();
					
					arr.value(obj);
					mTotalCount = 0;
				}
				
			}
			arr.endArray();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);		
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug(arr);
		}
		
		return arr.toString();
	}
	
	public static String getJsonEntity(List<Entity> entities,String ... methods) {
		
		JSONStringer arr = new JSONStringer();
		
		arr.array();
		try {
			Class<Entity> clazz = Entity.class;
			
			for(int i=0; i<entities.size(); i++){
				JSONStringer obj = new JSONStringer();
				Entity entity = entities.get(i);
				obj.object();
				for(String method : methods) {
					Method m = clazz.getMethod(getMName(method));
					obj.key(method).value(m.invoke(entity));
				}
				obj.endObject();
				
				arr.value(obj);
				
				if(i == 9) {
					break;//前10个
				}
			}
			arr.endArray();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);		
		}
		return arr.toString();
	}
	
	
	/**
	 * 获取方法名
	 * @param method
	 * @return
	 */
	private static String getMName(String method) {
		method = "get" + method.substring(0,1).toUpperCase()+ method.substring(1, method.length());
		return method;
	}
	
	public static String format(Object obj) {
		return new SimpleDateFormat("yyyy-MM-dd").format(obj);
	}
	
	public static Date convertToDate(int created) {
		long rq = created * 1000l;
		return new Date(rq);
	}
	
	/**
	 * 
	 * 判断word在src里出现几次
	 * @param word
	 * @param src
	 * @return
	 */
	public static int getWordCount(String word, String src) {
		if(src == null) return 0;
		if(src.trim().length() == 0) return 0;
		
		String[] str = src.split(word);
		
		return str.length - 1;
	}
	
}
