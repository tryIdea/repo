package com.kolong.tongji.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.util.JSONStringer;

import com.kolong.tongji.dao.TableDao;
import com.kolong.tongji.dao.ThirdDao;
import com.kolong.tongji.factory.ConnectionPool;
import com.kolong.tongji.factory.DaoFactory;
import com.kolong.tongji.util.CompareTime;
import com.kolong.tongji.util.Constants;
import com.kolong.tongji.util.JsonUtil;
import com.kolong.tongji.vo.Entity;
import com.kolong.tongji.vo.FirstLevelIndicator;

public class FirstLevelServlet extends HttpServlet {
	private ThirdDao td = DaoFactory.getThirdDao();
	private TableDao tableDao = DaoFactory.getTableDao();

	private static final long serialVersionUID = -4245527574189192651L;
	private static final Logger logger = Logger.getLogger(FirstLevelServlet.class);

	@Override
	public void init() throws ServletException {
		Connection conn = null;
		try {
			conn = ConnectionPool.getConn();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.closeConn(conn);
			if(logger.isDebugEnabled()) {
				logger.debug("加载firstServlet初始化连接池");
			}
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		String dataType = request.getParameter("dataType");

		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		String userId = request.getParameter("userId");
		
		List<FirstLevelIndicator> firstLevelIndicators = null;
		if (userId != null) {
			firstLevelIndicators = (List<FirstLevelIndicator>) td.findById(userId, "select * from tongji where userId = ? and rq between ? and ? order by rq asc", rq1, rq2).get("firstLevelIndicators");
		}

		if (dataType != null && dataType.trim().length() > 0) {
			// 曝光程度
			if (dataType.equals("exposIndex")) {
				getExposIndex(request, response, firstLevelIndicators);
				// 参与程度
			} else if (dataType.equals("participationIndex")) {
				getParticipationIndex(request, response, firstLevelIndicators);
				// 舆情观点
			} else if (dataType.equals("publicOpinionIndex")) {
				getPublicOpinionIndex(request, response, firstLevelIndicators);
				// 网站特点
			} else if (dataType.equals("siteFeatureIndex")) {
				getSiteFeatureIndex(request, response, firstLevelIndicators);
				// 曝光程度变化率
			} else if (dataType.equals("exposIndexRate")) {
				getExposIndexRate(request, response, firstLevelIndicators);
				// 参与程度变化率
			} else if (dataType.equals("participationIndexRate")) {
				getParticipationIndexRate(request, response, firstLevelIndicators);
				// 舆情观点变化率
			} else if (dataType.equals("publicOpinionIndexRate")) {
				getPublicOpinionIndexRate(request, response, firstLevelIndicators);
				// 网站特点变化率
			} else if (dataType.equals("siteFeatureIndexRate")) {
				getSiteFeatureIndexRate(request, response, firstLevelIndicators);
				// 传播进度
			} else if (dataType.equals("transIndex")) {
				getTransIndex(request, response, firstLevelIndicators);
				// 舆情指数
			} else if ("0".equals(userId) && dataType.equals("cpoi")) {
				getCPOI(request, response, rq1, rq2);
			} else if (dataType.equals("cpoi")) {
				getCPOIFor20(request, response, firstLevelIndicators);
				// 发布周期内舆情指数20
			} else if (dataType.equals("cpoi20")) {
				getCPOI20(request, response, rq1, rq2);
				// 全部监测周期内舆情指数
			} else if (dataType.equals("cpoi20Rq")) {
				getCPOI20Rq(request, response,firstLevelIndicators);

			} else if ("cpoiData".equals(dataType)) {// 指数数据
				getCpoiData(request, response, rq1, rq2);
			}
		}

	}

	/**
	 * 获取指数数据
	 * @param request
	 * @param response
	 * @param rq1
	 * @param rq2
	 * @throws IOException
	 */
	private void getCpoiData(HttpServletRequest request, HttpServletResponse response, String rq1, String rq2) throws IOException {
		String[] userIds = { "0", "790","789","787","784","783","781","780","778","777","776","775","774","773","772","770","769","768","767","766","765" };
		JSONStringer jsonAll = new JSONStringer();
		
		jsonAll.array();
		for (String userId : userIds) {
			@SuppressWarnings("unchecked")
			List<FirstLevelIndicator> firstLevelIndicators = (List<FirstLevelIndicator>) td.findById(userId, "select * from tongji where userId = ? and rq between ? and ? order by rq asc", rq1, rq2).get("firstLevelIndicators");
			
			if(userId.equals("0")) {
				jsonAll.value(JsonUtil.getJsonRq(firstLevelIndicators, Constants.MONITORCIRCLE));
				continue;//跳过全行业的指数
			 }
			
			List<Double> cpois = JsonUtil.getCPOIList(firstLevelIndicators, Constants.MONITORCIRCLE, true);
			JSONStringer json = new JSONStringer();
			json.array();
			for (double cpoi : cpois) {
				cpoi = JsonUtil.convertFromNonfinity(cpoi);
				
				BigDecimal decimal = new BigDecimal(cpoi, new MathContext(5, RoundingMode.HALF_UP));
				decimal = decimal.setScale(2,RoundingMode.HALF_UP);
				cpoi = decimal.doubleValue();
				json.value(cpoi);
			}
			json.endArray();

			jsonAll.value(json);

		}
		
		jsonAll.endArray();
		
		if(logger.isDebugEnabled()) {
			logger.debug(jsonAll);
		}
		
		response.getWriter().write(jsonAll.toString());

	}

/*	*//**
	 * 某一个监测周期内的20个细分行业的舆情指数
	 * @param request
	 * @param response
	 * @throws IOException
	 *//*
	private void getCPOI20Rq(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Connection conn = null;
		JSONStringer json = new JSONStringer();
		JSONStringer arr1 = new JSONStringer();
		JSONStringer arr2 = new JSONStringer();
		
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate;
		try {
			endDate = df.parse(rq);
			long c2 = endDate.getTime();
			long interval = 1000 * 60 * 60 * 24;// 一天时间
			
			long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1));
			c2 = (c2 + interval - 1);
			
			String rq1 = df.format(new Date(c1));
			String rq2 = df.format(new Date(c2));
			
			arr1.array();
			arr2.array();
			
			List<Entity> names = tableDao.queryUserNames();
			List<Entity> entities = new ArrayList<Entity>();
			for (int i = 0; i < names.size(); i++) {
				Entity ent = names.get(i);
				String name = ent.getName();
				String id = ent.getUrl();
				String sql = "select * from tongji where userId = ? and rq between ? and ? order by rq asc";
				@SuppressWarnings("unchecked")
				List<FirstLevelIndicator> firstLevelIndicators = (List<FirstLevelIndicator>) td.findById(id, sql, rq1, rq2).get("firstLevelIndicators");
				
				double cpoi = JsonUtil.getCPOI20(firstLevelIndicators, Constants.MONITORCIRCLE, true);
				
				Entity entity = new Entity();
				entity.setName(name);
				entity.setCount(cpoi);
				entities.add(entity);
			}
			
			Collections.sort(entities);
			for (Entity e : entities) {
				arr1.value(e.getCount());
				arr2.value(e.getName());
			}
			
			arr1.endArray();
			arr2.endArray();
			
			json.object();
			json.key("name");
			json.value("细分行业监测周期内的舆情指数");
			json.key("title");
			json.value("");
			json.key("data");
			json.value(arr1);
			json.key("categories");
			json.value(arr2);
			json.endObject();
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		} finally {
			factory.close(conn);
		}
		response.getWriter().write(json.toString());
	}
*/	/**
	 * 某一个监测周期内的20个细分行业的舆情指数
	 * @param request
	 * @param response
	 * @throws IOException
	 */
/*	private void getCPOI20Rq(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONStringer json = new JSONStringer();
		JSONStringer arr1 = new JSONStringer();
		JSONStringer arr2 = new JSONStringer();
		JSONStringer arr3 = new JSONStringer();
		
		String mRq1 = request.getParameter("mRq1");//监测周期1
		String mRq2 = request.getParameter("mRq2");//监测周期2
		List<Entity> cpoi1 = getCpoiDataByRq(mRq1);
		List<Entity> cpoi2 = getCpoiDataByRq(mRq2);
		for(int i=0; i<cpoi1.size(); i++) {
			Entity e1 = cpoi1.get(i);
			Entity e2 = cpoi2.get(i);
			e1.setCount2(e2.getCount());
			e2.setCount2(e1.getCount());
		}
		
		Collections.sort(cpoi1);
		arr1.array();
		arr2.array();
		arr3.array();
		for (Entity e : cpoi1) {
			arr1.value(e.getName());
			arr2.value(e.getCount());
			arr3.value(e.getCount2());
		}
		
		arr1.endArray();
		arr2.endArray();
		arr3.endArray();
		
		json.object()
		.key("categories").value(arr1)
		.key("name1").value(mRq1)
		.key("data1").value(arr2)
		.key("name2").value(mRq2)
		.key("data2").value(arr3)
		.endObject();
		response.getWriter().write(json.toString());
	}
*/	private void getCPOI20Rq(HttpServletRequest request, HttpServletResponse response,List<FirstLevelIndicator> firstLevelIndicators) throws IOException {
		JSONStringer rqs = JsonUtil.getJsonRq(firstLevelIndicators, Constants.MONITORCIRCLE);
		String[] dates = rqs.toString().replace("[", "").replace("]", "").replace("\"", "").split(",");
		
		JSONStringer json = new JSONStringer();
		json.array();
		for(String date : dates) {
			List<Entity> cpoi = getCpoiDataByRq(date);//求出所有监测周期内的舆情指数(应该：监测周期不能太多也不能太少-->3-12)
			JSONStringer arr = new JSONStringer();
			arr.array();
			for(int i=0; i<cpoi.size(); i++) {
					arr.value(cpoi.get(i).getCount());
			}
			arr.endArray();
			
			JSONStringer SubJson = new JSONStringer();
			SubJson.object()
				.key("rq").value(date)
				.key("data").value(arr)
				.endObject();
			
			json.value(SubJson);
		}
		json.endArray();
		
		JSONStringer JsonNames = new JSONStringer();
		JsonNames.array();
		List<Entity> names = tableDao.queryUserNames();
		for (int i = 0; i < names.size(); i++) {
			Entity ent = names.get(i);
			JsonNames.value(ent.getName());
		}
		JsonNames.endArray();

		JSONStringer jsonCpoi = new JSONStringer();
		jsonCpoi.object()
			.key("categories").value(JsonNames)
			.key("rqAndData").value(json)
			.endObject();
		
		if(logger.isDebugEnabled()) {
			logger.debug(jsonCpoi);
		}
		response.getWriter().write(jsonCpoi.toString());
	}
	
	private List<Entity> getCpoiDataByRq(String rq) {
		List<Entity> entities = new ArrayList<Entity>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate;
		try {
			endDate = df.parse(rq);
			long c2 = endDate.getTime();
			long interval = 1000 * 60 * 60 * 24;// 一天时间
			
			long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1));
			c2 = (c2 + interval - 1);

			String rq1 = df.format(new Date(c1));
			String rq2 = df.format(new Date(c2));

			List<Entity> names = tableDao.queryUserNames();
			for (int i = 0; i < names.size(); i++) {
				Entity ent = names.get(i);
				String name = ent.getName();
				String id = ent.getUrl();
				String sql = "select * from tongji where userId = ? and rq between ? and ? order by rq asc";
				@SuppressWarnings("unchecked")
				List<FirstLevelIndicator> firstLevelIndicators = (List<FirstLevelIndicator>) td.findById(id, sql, rq1, rq2).get("firstLevelIndicators");

				double cpoi = JsonUtil.getCPOI20(firstLevelIndicators, Constants.MONITORCIRCLE, true);

				Entity entity = new Entity();
				entity.setName(name);
				entity.setCount(cpoi);
				entities.add(entity);
			}
			
		}catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return entities;
	}

	/**
	 * 发布周期内20个行业的舆情指数
	 * 
	 * @param rq1
	 * @param rq2
	 * @return
	 * @throws IOException
	 */
	private void getCPOI20(HttpServletRequest request, HttpServletResponse response, String rq1, String rq2) throws IOException {
		JSONStringer json = new JSONStringer();
		JSONStringer arr1 = new JSONStringer();
		JSONStringer arr2 = new JSONStringer();

		try {
			arr1.array();
			arr2.array();

			// 计算发布周期
			final int RELEASECIRCLE = CompareTime.getBetweenDays(rq1, rq2) + 1;

			List<Entity> entities = new ArrayList<Entity>();
			List<Entity> names = tableDao.queryUserNames();
			for (int i = 0; i < names.size(); i++) {
				Entity ent = names.get(i);
				String name = ent.getName();
				String id = ent.getUrl();
				String sql = "select * from tongji where userId = ? and rq between ? and ? order by rq asc";
				@SuppressWarnings("unchecked")
				List<FirstLevelIndicator> firstLevelIndicators = (List<FirstLevelIndicator>) td.findById(id, sql, rq1, rq2).get("firstLevelIndicators");

				double cpoi = JsonUtil.getCPOI20(firstLevelIndicators, RELEASECIRCLE, true);

				Entity entity = new Entity();
				entity.setName(name);
				entity.setCount(cpoi);
				entities.add(entity);
			}

			Collections.sort(entities);
			for (Entity e : entities) {
				arr1.value(e.getCount());
				arr2.value(e.getName());
			}

			arr1.endArray();
			arr2.endArray();

			json.object();
			json.key("name");
			json.value("细分行业发布周期内的舆情指数");
			json.key("title");
			json.value("");
			json.key("data");
			json.value(arr1);
			json.key("categories");
			json.value(arr2);
			json.endObject();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			
		}
		response.getWriter().write(json.toString());

	}

	private void getCPOI(HttpServletRequest request, HttpServletResponse response, String rq1, String rq2) throws IOException {
		JSONStringer json = new JSONStringer();
		JSONStringer rqs = new JSONStringer();
		
		List<String> ids = tableDao.queryUserIds();
		List<Double>  cpoiList = new ArrayList<Double>(); 
		for(int j=0; j<ids.size(); j++) {
			String sql = "select * from tongji where userId = ? and rq between ? and ? order by rq asc";
			@SuppressWarnings("unchecked")
			List<FirstLevelIndicator> firstLevelIndicators = (List<FirstLevelIndicator>) td.findById(ids.get(j), sql, rq1, rq2).get("firstLevelIndicators");
			
			List<Double> cpois = JsonUtil.getCPOIList(firstLevelIndicators, Constants.MONITORCIRCLE, true);
			for(int i=0; i<cpois.size(); i++) {
				if(j == 0) {
					cpoiList.add(cpois.get(i));//初始化数组
					rqs = JsonUtil.getJsonRq(firstLevelIndicators, Constants.MONITORCIRCLE);
				}else {
					cpoiList.set(i, cpois.get(i)+cpoiList.get(i));
				}
			}
		}
		
		for(int j=0; j<cpoiList.size(); j++) {
			cpoiList.set(j, cpoiList.get(j)/20);//取平均值
		}
		
		json.object()
			.key("data")
			.value(cpoiList)
			.key("categoriesArr")
			.value(rqs)
			.endObject();
		
		if (logger.isDebugEnabled()) {
			logger.debug(json);
		}
		response.getWriter().write(json.toString());
	}
	private void getCPOIFor20(HttpServletRequest request, HttpServletResponse response, List<FirstLevelIndicator> firstLevelIndicators) throws IOException {
		String json = JsonUtil.getCPOI(firstLevelIndicators, Constants.MONITORCIRCLE, "舆情指数", "舆情指数变化趋势", "", true);
		if (logger.isDebugEnabled()) {
			logger.debug(json);
		}
		response.getWriter().write(json);
	}

	private void getTransIndex(HttpServletRequest request, HttpServletResponse response, List<FirstLevelIndicator> firstLevelIndicators) throws IOException {
		String json = JsonUtil.getTransIndex(firstLevelIndicators, Constants.MONITORCIRCLE, "传播进度指数", "传播进度指数变化趋势");
		if (logger.isDebugEnabled()) {
			logger.debug(json);
		}
		response.getWriter().write(json);
	}

	private void getSiteFeatureIndexRate(HttpServletRequest request, HttpServletResponse response, List<FirstLevelIndicator> firstLevelIndicators) throws IOException {
		String json = JsonUtil.getJsonStringForRate(firstLevelIndicators, Constants.MONITORCIRCLE, "网站特点变化率", "网站特点变化率趋势", "getSiteFeatureIndex", true);
		if (logger.isDebugEnabled()) {
			logger.debug(json);
		}
		response.getWriter().write(json);

	}

	private void getPublicOpinionIndexRate(HttpServletRequest request, HttpServletResponse response, List<FirstLevelIndicator> firstLevelIndicators) throws IOException {
		String json = JsonUtil.getJsonStringForRate(firstLevelIndicators, Constants.MONITORCIRCLE, "舆情观点变化率", "舆情观点变化率趋势", "getPublicOpinionIndex", true);
		if (logger.isDebugEnabled()) {
			logger.debug(json);
		}
		response.getWriter().write(json);

	}

	private void getParticipationIndexRate(HttpServletRequest request, HttpServletResponse response, List<FirstLevelIndicator> firstLevelIndicators) throws IOException {
		String json = JsonUtil.getJsonStringForRate(firstLevelIndicators, Constants.MONITORCIRCLE, "参与程度变化率", "参与程度变化率趋势", "getParticipationIndex", true);
		if (logger.isDebugEnabled()) {
			logger.debug(json);
		}
		response.getWriter().write(json);

	}

	private void getExposIndexRate(HttpServletRequest request, HttpServletResponse response, List<FirstLevelIndicator> firstLevelIndicators) throws IOException {
		String json = JsonUtil.getJsonStringForRate(firstLevelIndicators, Constants.MONITORCIRCLE, "曝光程度变化率", "曝光程度变化率趋势", "getExposIndex", true);
		if (logger.isDebugEnabled()) {
			logger.debug(json);
		}
		response.getWriter().write(json);
	}

	private void getSiteFeatureIndex(HttpServletRequest request, HttpServletResponse response, List<FirstLevelIndicator> firstLevelIndicators) throws IOException {
		String json = JsonUtil.getJsonStringForFirst(firstLevelIndicators, Constants.MONITORCIRCLE, "网站特点指数", "网站特点指数变化趋势", "getSiteFeatureIndex", true);
		if (logger.isDebugEnabled()) {
			logger.debug(json);
		}
		response.getWriter().write(json);

	}

	private void getPublicOpinionIndex(HttpServletRequest request, HttpServletResponse response, List<FirstLevelIndicator> firstLevelIndicators) throws IOException {
		String json = JsonUtil.getJsonStringForFirst(firstLevelIndicators, Constants.MONITORCIRCLE, "舆情观点指数", "舆情观点指数变化趋势", "getPublicOpinionIndex", true);
		if (logger.isDebugEnabled()) {
			logger.debug(json);
		}
		response.getWriter().write(json);

	}

	private void getParticipationIndex(HttpServletRequest request, HttpServletResponse response, List<FirstLevelIndicator> firstLevelIndicators) throws IOException {
		String json = JsonUtil.getJsonStringForFirst(firstLevelIndicators, Constants.MONITORCIRCLE, "参与程度指数", "参与程度指数变化趋势", "getParticipationIndex", true);
		if (logger.isDebugEnabled()) {
			logger.debug(json);
		}
		response.getWriter().write(json);

	}

	private void getExposIndex(HttpServletRequest request, HttpServletResponse response, List<FirstLevelIndicator> firstLevelIndicators) throws IOException {
		String json = JsonUtil.getJsonStringForFirst(firstLevelIndicators, Constants.MONITORCIRCLE, "曝光程度指数", "曝光程度指数变化趋势", "getExposIndex", true);
		if (logger.isDebugEnabled()) {
			logger.debug(json);
		}
		response.getWriter().write(json);

	}

}
