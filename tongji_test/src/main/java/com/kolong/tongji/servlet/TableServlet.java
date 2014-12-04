package com.kolong.tongji.servlet;

import java.io.IOException;
import java.net.URLDecoder;
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

import com.kolong.tongji.dao.TableDao;
import com.kolong.tongji.dao.Wb_TongJiDao;
import com.kolong.tongji.factory.DaoFactory;
import com.kolong.tongji.util.Constants;
import com.kolong.tongji.util.JsonUtil;
import com.kolong.tongji.vo.Entity;
import com.kolong.tongji.vo.Table;
import com.kolong.tongji.vo.WeiBo;

public class TableServlet extends HttpServlet {
	

	private static final long serialVersionUID = -4245527574189192651L;
	private static TableDao tableDao = DaoFactory.getTableDao();//static 与非static的区别
	private Wb_TongJiDao wd = DaoFactory.getWb_TongJiDao();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		String dataType = request.getParameter("dataType");
		String userId = request.getParameter("userId");

		try {

			if ("0".equals(userId) && "userCount".equals(dataType)) {//20个行业的
				getUserCount(request, response);
			}else if("userCount".equals(dataType)) {//细分行业的
				getUserCountFor20(request, response, userId);
			}else if("0".equals(userId) && "title".equals(dataType)) {
				getTitle(request, response);
			}else if ("title".equals(dataType)) {
				getTitleFor20(request, response, userId);
			} else if ("0".equals(userId) && "userCountTop10".equals(dataType)) {
				getUserCountTop10(request, response);
			}else if("userCountTop10".equals(dataType)) {
				getUserCountTop10For20(request, response,userId);
			}else if("0".equals(userId) && "titleArea".equals(dataType)) {
				getTitleArea(request, response);
			}else if("titleArea".equals(dataType)) {
				getTitleAreaFor20(request, response, userId);
			}else if ("0".equals(userId) && "viewCount".equals(dataType)) {
				getViewCountTop5(request, response);
			}else if("viewCount".equals(dataType)) {
				getViewCountTop5For20(request, response ,userId);
			}else if ("0".equals(userId) && "viewCountArea".equals(dataType)) {
				getViewCountAreaTop5(request, response);
			}else if("viewCountArea".equals(dataType)) {
				getViewCountAreaTop5For20(request, response ,userId);
			}else if ("0".equals(userId) && "reprintCount".equals(dataType)) {
				getReprintCountTop5(request, response);
			}else if("reprintCount".equals(dataType)) {
				getReprintCountTop5For20(request, response ,userId);
			}else if ("0".equals(userId) && "reprintCountArea".equals(dataType)) {
				getReprintCountAreaTop5(request, response);
			}else if("reprintCountArea".equals(dataType)) {
				getReprintCountAreaTop5For20(request, response ,userId);
			}else if ("0".equals(userId) && "replyCount".equals(dataType)) {
				getReplyCountTop5(request, response);
			}else if("replyCount".equals(dataType)) {
				getReplyCountTop5For20(request, response ,userId);
			}else if ("0".equals(userId) && "replyCountArea".equals(dataType)) {
				getReplyCountAreaTop5(request, response);
			}else if("replyCountArea".equals(dataType)) {
				getReplyCountAreaTop5For20(request, response ,userId);
			}else if("0".equals(userId) && "countryCount".equals(dataType)) {
				getCountryCount(request, response);
			}else if("countryCount".equals(dataType)) {
				getCountryCountFor20(request, response, userId);
			}else if ("0".equals(userId) && "site_yxl".equals(dataType)) {
				getSite_yxl(request, response);
			}else if("site_yxl".equals(dataType)) {
				getSite_yxlFor20(request, response ,userId);//访问者模式
			}else if ("0".equals(userId) && "sitesTop10".equals(dataType)) {
				getSitesTop10(request, response);
			}else if("sitesTop10".equals(dataType)) {
				getSitesTop10For20(request, response ,userId);
			} else if("0".equals(userId) && "sidesContTop5".equals(dataType)) {
				getSidesContTop5(request, response);
			} else if("sidesContTop5".equals(dataType)) {
				getSidesContTop5For20(request, response ,userId);
			} else if("0".equals(userId) && "sidesContTop5Rq".equals(dataType)) {
				getSidesContTop5Rq(request, response);
			} else if("sidesContTop5Rq".equals(dataType)) {
				getSidesContTop5RqFor20(request, response ,userId);
			}else if("0".equals(userId) && "sidesCountTop5".equals(dataType)) {
				getSidesCountTop5(request, response);
			}else if("sidesCountTop5".equals(dataType)){
				getSidesCountTop5For20(request, response ,userId);
			}else if("0".equals(userId) && "sidesCountForOne".equals(dataType)) {
				getSidesCountForOne(request, response);
			}else if("sidesCountForOne".equals(dataType)) {
				getSidesCountForOneFor20(request, response ,userId);
			}else if("0".equals(userId) && "minCountForOne".equals(dataType)) {
				getMinCountForOne(request, response);
			}else if("minCountForOne".equals(dataType)) {
				getMinCountForOneFor20(request, response ,userId);
			}else if("0".equals(userId) && "rtCountAreaTop10".equals(dataType)) {
				getRtCountAreaTop10(request, response);
			}else if("rtCountAreaTop10".equals(dataType)) {
				getRtCountAreaTop10For20(request, response ,userId);
			}else if("0".equals(userId) && "commCountTop10".equals(dataType)) {
				getCommCountTop10(request, response);
			}else if("commCountTop10".equals(dataType)) {
				getCommCountTop10For20(request, response ,userId);
			}else if("0".equals(userId) && "verifiedCount".equals(dataType)) {
				getVerifiedCount(request, response);
			}else if("verifiedCount".equals(dataType)) {
				getVerifiedCountFor20(request, response ,userId);
			}else if("0".equals(userId) && "site_cls".equals(dataType)) {
				getSite_cls(request, response);
			}else if("site_cls".equals(dataType)) {
				getSite_clsFor20(request, response ,userId);
			}else if("0".equals(userId) && "site_clsArea".equals(dataType)) {
				getSite_clsArea(request, response);
			}else if("site_clsArea".equals(dataType)) {
				getSite_clsAreaFor20(request, response ,userId);
			}else if("0".equals(userId) && "titleWithoutDomain".equals(dataType)) {
				getTitleWithoutDomain(request, response);
			}else if("titleWithoutDomain".equals(dataType)) {
				getTitleWithoutDomainFor20(request, response ,userId);
			}else if("0".equals(userId) && "titleAreaWithoutDomain".equals(dataType)) {
				getTitleAreaWithoutDomain(request, response);
			}else if("titleAreaWithoutDomain".equals(dataType)) {
				getTitleAreaWithoutDomainFor20(request, response ,userId);
			}else if("0".equals(userId) && "viewOfSite_cls".equals(dataType)) {
				getViewOfSite_cls(request, response);
			}else if("viewOfSite_cls".equals(dataType)) {
				getViewOfSite_clsFor20(request, response ,userId);
			}else if("0".equals(userId) && "reprintOfSite_cls".equals(dataType)) {
				getReprintOfSite_cls(request, response);
			}else if("reprintOfSite_cls".equals(dataType)) {
				getReprintOfSite_clsFor20(request, response ,userId);
			}else if("0".equals(userId) && "replyOfSite_cls".equals(dataType)) {
				getReplyOfSite_cls(request, response);
			}else if("replyOfSite_cls".equals(dataType)) {
				getReplyOfSite_clsFor20(request, response ,userId);
			}else if("0".equals(userId) && "sidesTitle".equals(dataType)) {
				getSidesTitle(request, response);
			}else if("sidesTitle".equals(dataType)) {
				getSidesTitleFor20(request, response ,userId);
			}else if("0".equals(userId) && "sidesTitleForRq".equals(dataType)) {
				getSidesTitleForRq(request, response);
			}else if("sidesTitleForRq".equals(dataType)) {
				getSidesTitleForRqFor20(request, response ,userId);
			}else if("0".equals(userId) && "minCountTop5".equals(dataType)) {
				getMinCountTop5(request, response);
			}else if("minCountTop5".equals(dataType)) {
				getMinCountTop5For20(request, response, userId);
			}else if("0".equals(userId) && "minContTop5".equals(dataType)) {
				getMinContTop5(request, response);
			}else if("minContTop5".equals(dataType)) {
				getMinContTop5For20(request, response, userId);
			}else if("0".equals(userId) && "minContTop5Rq".equals(dataType)) {
				getMinContTop5Rq(request, response);
			}else if("minContTop5Rq".equals(dataType)) {
				getMinContTop5RqFor20(request, response, userId);
			}else if("simiTitle".equals(dataType)) {
				getSimiTitle(request, response, userId);
			}

		} catch (ParseException e1) {
			e1.printStackTrace();
		}

	}
	
	private void getSimiTitle(HttpServletRequest request, HttpServletResponse response, String userId) throws IOException {
		String sim_id = request.getParameter("sim_id");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		List<Table> tables = tableDao.getTitle(userId, sim_id, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		
		String json = JsonUtil.<Table>getJsonTestGeneric(tables, "id", "site_name", "site_url", "url","pubdate","title","user_id","uuid");
		
		response.getWriter().write(json);
	}

	private void getSite_clsArea(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		List<Entity> entities = tableDao.getSite_cls(c1, c2);
		
		String json = JsonUtil.<Entity>getJsonTestGeneric(entities, "id", "count");
		
		response.getWriter().write(json);
	}
	private void getSite_clsAreaFor20(HttpServletRequest request, HttpServletResponse response, String userId) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		List<Entity> entities = tableDao.getSite_clsFor20(c1, c2, userId);
		
		String json = JsonUtil.<Entity>getJsonTestGeneric(entities, "id", "count");
		
		response.getWriter().write(json);
	}
	private void getSite_cls(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		
		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		List<Entity> entities = tableDao.getSite_cls(c1, c2);
		
		String json = JsonUtil.<Entity>getJsonTestGeneric(entities, "id", "count");
		
		response.getWriter().write(json);
	}
	private void getSite_clsFor20(HttpServletRequest request, HttpServletResponse response, String userId) throws ParseException, IOException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		
		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		List<Entity> entities = tableDao.getSite_clsFor20(c1, c2, userId);
		
		String json = JsonUtil.<Entity>getJsonTestGeneric(entities, "id", "count");
		
		response.getWriter().write(json);
	}

	private void getCountryCount(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<Entity> entities = tableDao.getCountryCount(rq1, rq2);
		
		String json = JsonUtil.<Entity>getJsonTestGeneric(entities, "name", "count", "count2");
		
		response.getWriter().write(json);
	}
	private void getCountryCountFor20(HttpServletRequest request, HttpServletResponse response,String userId) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<Entity> entities = tableDao.getCountryCountFor20(rq1, rq2, userId);
		
		String json = JsonUtil.<Entity>getJsonTestGeneric(entities, "name", "count", "count2");
		
		response.getWriter().write(json);
	}

	private void getMinContTop5(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		List<String> mins = tableDao.getMins();
		List<Table> tables = tableDao.getArticleList(c1, c2);
		
		List<Entity> entities = new ArrayList<Entity>();
		for(Table table : tables) {
			Entity entity = new Entity();
			int i = 0;//敏感词出现的次数
			for(String min : mins) {
				i += JsonUtil.getWordCount(min, table.getTitle()) + JsonUtil.getWordCount(min, table.getDescription());
			}
			entity.setId(table.getId());
			entity.setUuid(table.getUuid());
			entity.setUser_id(table.getUser_id());
			entity.setName(table.getTitle());
			entity.setUrl(table.getUrl());
			entity.setCount(i);
			entity.setSite_name(table.getSite_name());
			entity.setRq(table.getPubdate());
			entities.add(entity);
		}
		
		Collections.sort(entities);//将获得的对象排序
		
		String json = JsonUtil.getJsonEntity(entities, "name", "count", "user_id","id","uuid","rq", "site_name");
		
		response.getWriter().write(json);
	}
	private void getMinContTop5For20(HttpServletRequest request, HttpServletResponse response,String userId) throws IOException, ParseException {
		
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		List<String> mins = tableDao.getMins();
		List<Table> tables = tableDao.getArticleListFor20(c1, c2, userId);
		
		List<Entity> entities = new ArrayList<Entity>();
		for(Table table : tables) {
			Entity entity = new Entity();
			int i = 0;//敏感词出现的次数
			for(String min : mins) {
				i += JsonUtil.getWordCount(min, table.getTitle()) + JsonUtil.getWordCount(min, table.getDescription());
			}
			entity.setId(table.getId());
			entity.setUuid(table.getUuid());
			entity.setUser_id(table.getUser_id());
			entity.setName(table.getTitle());
			entity.setUrl(table.getUrl());
			entity.setCount(i);
			entity.setSite_name(table.getSite_name());
			entity.setRq(table.getPubdate());
			entities.add(entity);
		}
		
		Collections.sort(entities);//将获得的对象排序
		
		String json = JsonUtil.getJsonEntity(entities, "name", "count", "user_id","id","uuid","rq", "site_name");
		
		response.getWriter().write(json);
	}
	private void getMinContTop5Rq(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		
		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		List<String> mins = tableDao.getMins();
		List<Table> tables = tableDao.getArticleList(c1, c2);
		
		List<Entity> entities = new ArrayList<Entity>();
		for(Table table : tables) {
			Entity entity = new Entity();
			int i = 0;//敏感词出现的次数
			for(String min : mins) {
				i += JsonUtil.getWordCount(min, table.getTitle()) + JsonUtil.getWordCount(min, table.getDescription());
			}
			entity.setId(table.getId());
			entity.setUuid(table.getUuid());
			entity.setUser_id(table.getUser_id());
			entity.setName(table.getTitle());
			entity.setUrl(table.getUrl());
			entity.setCount(i);
			entity.setSite_name(table.getSite_name());
			entity.setRq(table.getPubdate());
			entities.add(entity);
		}
		
		Collections.sort(entities);//将获得的对象排序
		
		String json = JsonUtil.getJsonEntity(entities, "name", "count", "user_id","id","uuid","rq", "site_name");
		
		response.getWriter().write(json);
	}
	private void getMinContTop5RqFor20(HttpServletRequest request, HttpServletResponse response,String userId) throws IOException, ParseException {
		
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		
		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		List<String> mins = tableDao.getMins();
		List<Table> tables = tableDao.getArticleListFor20(c1, c2, userId);
		
		List<Entity> entities = new ArrayList<Entity>();
		for(Table table : tables) {
			Entity entity = new Entity();
			int i = 0;//敏感词出现的次数
			for(String min : mins) {
				i += JsonUtil.getWordCount(min, table.getTitle()) + JsonUtil.getWordCount(min, table.getDescription());
			}
			entity.setId(table.getId());
			entity.setUuid(table.getUuid());
			entity.setUser_id(table.getUser_id());
			entity.setName(table.getTitle());
			entity.setUrl(table.getUrl());
			entity.setCount(i);
			entity.setSite_name(table.getSite_name());
			entity.setRq(table.getPubdate());
			entities.add(entity);
		}
		
		Collections.sort(entities);//将获得的对象排序
		
		String json = JsonUtil.getJsonEntity(entities, "name", "count", "user_id","id","uuid","rq", "site_name");
		
		response.getWriter().write(json);
	}

	private void getMinCountTop5(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<Entity> entities = tableDao.getMinCountTop5(rq1, rq2,"10");
		
		String json = JsonUtil.<Entity>getJsonTestGeneric(entities, "name", "count");
		
		response.getWriter().write(json);
		
	}
	private void getMinCountTop5For20(HttpServletRequest request, HttpServletResponse response,String userId) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<Entity> entities = tableDao.getMinCountTop5For20(rq1, rq2,"10",userId);
		
		String json = JsonUtil.<Entity>getJsonTestGeneric(entities, "name", "count");
		
		response.getWriter().write(json);
		
	}

	private void getVerifiedCount(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<WeiBo> weiBos = tableDao.getVerifiedCount(rq1, rq2);
		
		String json = JsonUtil.<WeiBo>getJsonTestGeneric(weiBos,"user_verified","v_count");
		
		response.getWriter().write(json);
	}
	private void getVerifiedCountFor20(HttpServletRequest request, HttpServletResponse response, String userId) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<WeiBo> weiBos = tableDao.getVerifiedCountFor20(rq1, rq2,userId);
		
		String json = JsonUtil.<WeiBo>getJsonTestGeneric(weiBos, "user_verified","v_count");
		
		response.getWriter().write(json);
	}
	private void getCommCountTop10(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<WeiBo> weiBos = tableDao.getCommCountTop10(rq1, rq2,"10");
		
		String json = JsonUtil.<WeiBo>getJsonTestGeneric(weiBos, "user", "comm","wid","uid","user_verified","sour","text","userId","rq");
		
		response.getWriter().write(json);
	}
	private void getCommCountTop10For20(HttpServletRequest request, HttpServletResponse response, String userId) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<WeiBo> weiBos = tableDao.getCommCountTop10For20(rq1, rq2,"10",userId);
		
		String json = JsonUtil.<WeiBo>getJsonTestGeneric(weiBos, "user", "comm","wid","uid","user_verified","sour","text","userId","rq");
		
		response.getWriter().write(json);
	}
	private void getRtCountAreaTop10(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<WeiBo> weiBos = tableDao.getRtCountTop10(rq1, rq2,"10");
		
		String json = JsonUtil.<WeiBo>getJsonTestGeneric(weiBos, "user", "rt","wid","uid","user_verified","sour","text","userId","rq");
		
		response.getWriter().write(json);
	}
	private void getRtCountAreaTop10For20(HttpServletRequest request, HttpServletResponse response, String userId) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<WeiBo> weiBos = tableDao.getRtCountTop10For20(rq1, rq2,"10",userId);
		
		String json = JsonUtil.<WeiBo>getJsonTestGeneric(weiBos, "user", "rt","wid","uid","user_verified","sour","text","userId","rq");
		
		response.getWriter().write(json);
	}
	private void getMinCountForOne(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		String minWord = URLDecoder.decode(request.getParameter("name"),"utf-8");   
		List<Entity> entities = tableDao.getMinCountForOne(rq1, rq2,minWord);
				
		String json = JsonUtil.getJsonForOne(entities, Constants.MONITORCIRCLE);
		
		response.getWriter().write(json);
	}
	private void getMinCountForOneFor20(HttpServletRequest request, HttpServletResponse response, String userId) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		String minWord = URLDecoder.decode(request.getParameter("name"),"utf-8");   
		List<Entity> entities = tableDao.getMinCountForOneFor20(rq1, rq2,minWord, userId);
		
		String json = JsonUtil.getJsonForOne(entities, Constants.MONITORCIRCLE);
		
		response.getWriter().write(json);
	}
	private void getSidesCountForOne(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		String sideWord = URLDecoder.decode(request.getParameter("name"),"utf-8");   
		List<Entity> entities = tableDao.getSidesCountForOne(rq1, rq2,sideWord);
		
		String json = JsonUtil.getJsonForOne(entities, Constants.MONITORCIRCLE);
		
		response.getWriter().write(json);
	}
	private void getSidesCountForOneFor20(HttpServletRequest request, HttpServletResponse response, String userId) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		String sideWord = URLDecoder.decode(request.getParameter("name"),"utf-8");   
		List<Entity> entities = tableDao.getSidesCountForOneFor20(rq1, rq2,sideWord, userId);
		
		String json = JsonUtil.getJsonForOne(entities, Constants.MONITORCIRCLE);
		
		response.getWriter().write(json);
	}
	private void getSidesCountTop5(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<Entity> entities = tableDao.getSidesCountTop5(rq1, rq2,"10");
		
		String json = JsonUtil.<Entity>getJsonTestGeneric(entities, "name", "count");
		
		response.getWriter().write(json);
	}
	private void getSidesCountTop5For20(HttpServletRequest request, HttpServletResponse response, String userId) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<Entity> entities = tableDao.getSidesCountTop5For20(rq1, rq2,"10", userId);
		
		String json = JsonUtil.<Entity>getJsonTestGeneric(entities, "name", "count");
		
		response.getWriter().write(json);
	}
	private void getSidesContTop5(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		List<Table> tables = tableDao.getSidesContTop5(c1, c2,"10");
		
		String json = JsonUtil.<Table>getJsonTestGeneric(tables, "title", "id", "uuid", "user_id", "side_wrong_count","pubdate","site_name");
		
		response.getWriter().write(json);
	}
	private void getSidesContTop5For20(HttpServletRequest request, HttpServletResponse response, String userId) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		List<Table> tables = tableDao.getSidesContTop5For20(c1, c2,"10", userId);
		
		String json = JsonUtil.<Table>getJsonTestGeneric(tables, "title", "id", "uuid", "user_id","side_wrong_count","pubdate","site_name");
		
		response.getWriter().write(json);
	}
	private void getSidesContTop5Rq(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		
		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		List<Table> tables = tableDao.getSidesContTop5(c1, c2,"10");
		
		String json = JsonUtil.<Table>getJsonTestGeneric(tables, "title", "id", "uuid", "user_id", "side_wrong_count","pubdate","site_name");
		
		response.getWriter().write(json);
	}
	private void getSidesContTop5RqFor20(HttpServletRequest request, HttpServletResponse response, String userId) throws ParseException, IOException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		
		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		List<Table> tables = tableDao.getSidesContTop5For20(c1, c2,"10", userId);
		
		String json = JsonUtil.<Table>getJsonTestGeneric(tables, "title", "id", "uuid", "user_id","side_wrong_count","pubdate","site_name");
		
		response.getWriter().write(json);
	}
	private void getSitesTop10(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		String pr = request.getParameter("pr");
		List<Table> tables = null;
		if(rq1!=null && rq2!=null && pr!=null) {
			tables = tableDao.getSitesTop10(rq1, rq2, pr, "10");
		}
		
		String json = JsonUtil.<Table>getJsonTestGeneric(tables, "site_name", "domain_1","userCount");
		response.getWriter().write(json);
	}
	private void getSitesTop10For20(HttpServletRequest request, HttpServletResponse response, String userId) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		String pr = request.getParameter("pr");
		
		List<Table> tables = null;
		if(rq1!=null && rq2!=null && pr!=null) {
			tables = tableDao.getSitesTop10For20(rq1, rq2, pr,userId,"10");
		}
		
		String json = JsonUtil.<Table>getJsonTestGeneric(tables, "site_name", "domain_1","userCount");
		
		response.getWriter().write(json);
	}
	private void getSite_yxl(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<Table> tables = null;
		if(rq1!=null && rq2!=null) {
			tables = tableDao.getSite_yxl(rq1, rq2);
		}
		
		String json = JsonUtil.<Table>getJsonTestGeneric(tables, "grade", "sCount");
		response.getWriter().write(json);
	}
	private void getSite_yxlFor20(HttpServletRequest request, HttpServletResponse response, String userId) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");

		List<Table> tables = null;
		if(rq1!=null && rq2!=null) {
			tables = tableDao.getSite_yxlFor20(rq1, rq2, userId);
		}

		String json = JsonUtil.<Table>getJsonTestGeneric(tables, "grade", "sCount");
		
		response.getWriter().write(json);
	}

	private void getReplyCountTop5(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		
		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String isFilter = request.getParameter("isFilter");
		
		List<Table> tables = new ArrayList<Table>();
		tables = tableDao.getTitlePTest(c1, c2, Integer.parseInt(pageNo), Integer.parseInt(pageSize), "reply", Boolean.parseBoolean(isFilter));
		
		String json = JsonUtil.getJsonTest(tables, "title", "reply", "id","uuid","user_id","pubdate","site_name");
		response.getWriter().write(json);
	}
	private void getReplyCountTop5For20(HttpServletRequest request, HttpServletResponse response, String userId) throws ParseException, IOException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		
		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String isFilter = request.getParameter("isFilter");
		List<Table> tables = new ArrayList<Table>();
		tables = tableDao.getTitlePFor20Test(c1, c2, Integer.parseInt(pageNo), Integer.parseInt(pageSize), "reply", userId, Boolean.parseBoolean(isFilter));
		
		String json = JsonUtil.getJsonTest(tables, "title", "reply", "id","uuid","user_id","pubdate","site_name");
		response.getWriter().write(json);
	}
	private void getReplyCountAreaTop5(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String isFilter = request.getParameter("isFilter");
		List<Table> tables = new ArrayList<Table>();
		tables = tableDao.getTitlePTest(c1, c2, Integer.parseInt(pageNo), Integer.parseInt(pageSize), "reply", Boolean.parseBoolean(isFilter));
		
		String json = JsonUtil.getJsonTest(tables, "title", "reply", "id","uuid","user_id","pubdate","site_name");
		response.getWriter().write(json);
	}
	private void getReplyCountAreaTop5For20(HttpServletRequest request, HttpServletResponse response, String userId) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;

		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String isFilter = request.getParameter("isFilter");
		List<Table> tables = new ArrayList<Table>();
		tables = tableDao.getTitlePFor20Test(c1, c2, Integer.parseInt(pageNo), Integer.parseInt(pageSize), "reply", userId, Boolean.parseBoolean(isFilter));

		String json = JsonUtil.getJsonTest(tables, "title", "reply", "id","uuid","user_id","pubdate","site_name");
		response.getWriter().write(json);
	}

	private void getReprintCountTop5(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		
		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String isFilter = request.getParameter("isFilter");
		
		List<Table> tables = new ArrayList<Table>();
		tables = tableDao.getTitlePTest(c1, c2, Integer.parseInt(pageNo), Integer.parseInt(pageSize), "similar_count", Boolean.parseBoolean(isFilter));
		
		String json = JsonUtil.getJsonTest(tables, "title", "similar_count", "id","uuid","user_id","pubdate","site_name");
		response.getWriter().write(json);
	}
	private void getReprintCountTop5For20(HttpServletRequest request, HttpServletResponse response, String userId) throws ParseException, IOException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		
		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String isFilter = request.getParameter("isFilter");
		List<Table> tables = new ArrayList<Table>();
		tables = tableDao.getTitlePFor20Test(c1, c2, Integer.parseInt(pageNo), Integer.parseInt(pageSize), "similar_count", userId, Boolean.parseBoolean(isFilter));
		
		String json = JsonUtil.getJsonTest(tables, "title", "similar_count", "id","uuid","user_id","pubdate","site_name");
		response.getWriter().write(json);
	}
	private void getReprintCountAreaTop5(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String isFilter = request.getParameter("isFilter");
		List<Table> tables = new ArrayList<Table>();
		tables = tableDao.getTitlePTest(c1, c2, Integer.parseInt(pageNo), Integer.parseInt(pageSize), "similar_count", Boolean.parseBoolean(isFilter));
		
		String json = JsonUtil.getJsonTest(tables, "title", "similar_count", "id","uuid","user_id","pubdate","site_name");
		response.getWriter().write(json);
	}
	private void getReprintCountAreaTop5For20(HttpServletRequest request, HttpServletResponse response, String userId) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;

		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String isFilter = request.getParameter("isFilter");
		List<Table> tables = new ArrayList<Table>();
		tables = tableDao.getTitlePFor20Test(c1, c2, Integer.parseInt(pageNo), Integer.parseInt(pageSize), "similar_count", userId, Boolean.parseBoolean(isFilter));

		String json = JsonUtil.getJsonTest(tables, "title", "similar_count", "id","uuid","user_id","pubdate","site_name");
		response.getWriter().write(json);
	}

	private void getViewCountTop5(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		
		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String isFilter = request.getParameter("isFilter");
		
		List<Table> tables = new ArrayList<Table>();
		tables = tableDao.getTitlePTest(c1, c2, Integer.parseInt(pageNo), Integer.parseInt(pageSize), "view", Boolean.parseBoolean(isFilter));
		
		String json = JsonUtil.getJsonTest(tables, "title", "view", "id","uuid","user_id","pubdate","site_name");
		response.getWriter().write(json);
		
	}
	
	private void getViewCountTop5For20(HttpServletRequest request, HttpServletResponse response, String userId) throws ParseException, IOException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		
		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String isFilter = request.getParameter("isFilter");
		List<Table> tables = new ArrayList<Table>();
		tables = tableDao.getTitlePFor20Test(c1, c2, Integer.parseInt(pageNo), Integer.parseInt(pageSize), "view", userId, Boolean.parseBoolean(isFilter));
		
		String json = JsonUtil.getJsonTest(tables, "title", "view", "id","uuid","user_id","pubdate","site_name");
		response.getWriter().write(json);
		
	}
	private void getViewCountAreaTop5(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String isFilter = request.getParameter("isFilter");
		List<Table> tables = new ArrayList<Table>();
		tables = tableDao.getTitlePTest(c1, c2, Integer.parseInt(pageNo), Integer.parseInt(pageSize), "view", Boolean.parseBoolean(isFilter));
		
		String json = JsonUtil.getJsonTest(tables, "title", "view", "id","uuid","user_id","pubdate","site_name");
		response.getWriter().write(json);
		
	}
	
	private void getViewCountAreaTop5For20(HttpServletRequest request, HttpServletResponse response, String userId) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;

		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String isFilter = request.getParameter("isFilter");
		List<Table> tables = new ArrayList<Table>();
		tables = tableDao.getTitlePFor20Test(c1, c2, Integer.parseInt(pageNo), Integer.parseInt(pageSize), "view", userId, Boolean.parseBoolean(isFilter));
		
		String json = JsonUtil.getJsonTest(tables, "title", "view", "id","uuid","user_id","pubdate","site_name");
		response.getWriter().write(json);

	}

	private void getUserCountTop10(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		List<Table> tables = tableDao.getUUU(c1, c2, 10);
		
		String json = JsonUtil.getJson(tables, "userCount", "site_name", "domain_1");
		response.getWriter().write(json);
	}
	private void getUserCountTop10For20(HttpServletRequest request, HttpServletResponse response, String userId) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;

		List<Table> tables = tableDao.getUUUFor20(c1, c2, 10,userId);

		String json = JsonUtil.getJson(tables, "userCount", "site_name", "domain_1");
		response.getWriter().write(json);
	}

	/**
	 * 全行业新增发文量对应的文章列表（某一个网站）
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ParseException
	 */
	private void getTitle(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		
		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		String domain_1 = request.getParameter("domain_1");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String isFilter = request.getParameter("isFilter");
		List<Table> tables = new ArrayList<Table>();
		tables = tableDao.getTitleTest(c1, c2, Integer.parseInt(pageNo), Integer.parseInt(pageSize), domain_1, Boolean.parseBoolean(isFilter));
		
		String json = JsonUtil.getJsonTest(tables, "pubdate", "title", "similar_count", "uuid", "id","user_id","site_name","site_cls");
		response.getWriter().write(json);
	}
	private void getTitleFor20(HttpServletRequest request, HttpServletResponse response, String userId) throws IOException, ParseException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		
		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		String domain_1 = request.getParameter("domain_1");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String isFilter = request.getParameter("isFilter");
		List<Table> tables = new ArrayList<Table>();
		tables = tableDao.getTitleTestFor20(c1, c2, Integer.parseInt(pageNo), Integer.parseInt(pageSize), domain_1, userId, Boolean.parseBoolean(isFilter));
		
		String json = JsonUtil.getJsonTest(tables, "pubdate", "title", "similar_count", "uuid", "id","user_id","site_name","site_cls");
		response.getWriter().write(json);
	}
	/**
	 * 全行业新增发文量对应的文章列表(所有网站)
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ParseException
	 */
	private void getTitleWithoutDomain(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		
		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String isFilter = request.getParameter("isFilter");
		List<Table> tables = new ArrayList<Table>();
		tables = tableDao.getTitle(c1, c2, Integer.parseInt(pageNo), Integer.parseInt(pageSize), Boolean.parseBoolean(isFilter));
		
		String json = JsonUtil.getJsonTest(tables, "pubdate", "title", "similar_count", "uuid", "id","user_id","site_name");
		response.getWriter().write(json);
	}
	private void getTitleWithoutDomainFor20(HttpServletRequest request, HttpServletResponse response, String userId) throws IOException, ParseException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		
		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String isFilter = request.getParameter("isFilter");
		List<Table> tables = new ArrayList<Table>();
		tables = tableDao.getTitleFor20(c1, c2, Integer.parseInt(pageNo), Integer.parseInt(pageSize), userId, Boolean.parseBoolean(isFilter));
		
		String json = JsonUtil.getJsonTest(tables, "pubdate", "title", "similar_count", "uuid", "id","user_id","site_name");
		response.getWriter().write(json);
	}
	
	/**
	 * 累积发文量对应的文章列表（某一个网站）
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ParseException
	 */
	private void getTitleArea(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		String domain_1 = request.getParameter("domain_1");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String isFilter = request.getParameter("isFilter");
		List<Table> tables = new ArrayList<Table>();
		tables = tableDao.getTitleTest(c1, c2, Integer.parseInt(pageNo), Integer.parseInt(pageSize), domain_1, Boolean.parseBoolean(isFilter));
		
		String json = JsonUtil.getJsonTest(tables, "pubdate", "title", "similar_count", "uuid", "id","user_id","site_name","site_cls");
		response.getWriter().write(json);
	}
	private void getTitleAreaFor20(HttpServletRequest request, HttpServletResponse response, String userId) throws IOException, ParseException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		String domain_1 = request.getParameter("domain_1");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String isFilter = request.getParameter("isFilter");
		List<Table> tables = new ArrayList<Table>();
		tables = tableDao.getTitleTestFor20(c1, c2, Integer.parseInt(pageNo), Integer.parseInt(pageSize), domain_1, userId, Boolean.parseBoolean(isFilter));
		
		String json = JsonUtil.getJsonTest(tables, "pubdate", "title", "similar_count", "uuid", "id","user_id","site_name","site_cls");
		response.getWriter().write(json);
	}
	/**
	 * 累积发文量对应的文章列表(所有网站)
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ParseException
	 */
	private void getTitleAreaWithoutDomain(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String isFilter = request.getParameter("isFilter");
		List<Table> tables = new ArrayList<Table>();
		tables = tableDao.getTitle(c1, c2, Integer.parseInt(pageNo), Integer.parseInt(pageSize), Boolean.parseBoolean(isFilter));
		
		String json = JsonUtil.getJsonTest(tables, "pubdate", "title", "similar_count", "uuid", "id","user_id","site_name");
		response.getWriter().write(json);
	}
	private void getTitleAreaWithoutDomainFor20(HttpServletRequest request, HttpServletResponse response, String userId) throws IOException, ParseException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		String isFilter = request.getParameter("isFilter");
		List<Table> tables = new ArrayList<Table>();
		tables = tableDao.getTitleFor20(c1, c2, Integer.parseInt(pageNo), Integer.parseInt(pageSize), userId, Boolean.parseBoolean(isFilter));
		
		String json = JsonUtil.getJsonTest(tables, "pubdate", "title", "similar_count", "uuid", "id","user_id","site_name");
		response.getWriter().write(json);
	}
	/**
	 * 累积浏览量下的按媒体类型分类的浏览量
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ParseException
	 */
	private void getViewOfSite_cls(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		Entity wb_entity = new Entity();
		wb_entity.setId(8);
		
		String sql = "select sum(wb_viewCount) from wb_tongji where rq between '{rq1}' and '{rq2}' ";
		sql = sql.replace("{rq1}", rq1).replace("{rq2}", rq2);
		wb_entity.setCount(wd.getWb_cls(sql));
		
		List<Entity> entities = tableDao.getVSROfSite_cls(c1, c2,"view");
		entities.add(wb_entity);
		
		String json = JsonUtil.<Entity>getJsonTestGeneric(entities, "id", "count");
		response.getWriter().write(json);
	}
	private void getViewOfSite_clsFor20(HttpServletRequest request, HttpServletResponse response, String userId) throws IOException, ParseException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		Entity wb_entity = new Entity();
		wb_entity.setId(8);
		
		String sql = "select sum(wb_viewCount) from wb_tongji where rq between '{rq1}' and '{rq2}' and userId = {userId}";
		sql = sql.replace("{rq1}", rq1).replace("{rq2}", rq2).replace("{userId}", userId);
		wb_entity.setCount(wd.getWb_cls(sql));
		
		List<Entity> entities = tableDao.getVSROfSite_clsFor20(c1, c2,"view",userId);
		entities.add(wb_entity);
		
		String json = JsonUtil.<Entity>getJsonTestGeneric(entities, "id", "count");
		response.getWriter().write(json);
	}
	/**
	 * 累积转载量下的按媒体类型分类的转载量
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ParseException
	 */
	private void getReprintOfSite_cls(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		Entity wb_entity = new Entity();
		wb_entity.setId(8);
		
		String sql = "select sum(wb_rtCount) from wb_tongji where rq between '{rq1}' and '{rq2}' ";
		sql = sql.replace("{rq1}", rq1).replace("{rq2}", rq2);
		wb_entity.setCount(wd.getWb_cls(sql));
		
		List<Entity> entities = tableDao.getVSROfSite_cls(c1, c2,"similar_count");
		entities.add(wb_entity);
		
		String json = JsonUtil.<Entity>getJsonTestGeneric(entities, "id", "count");
		response.getWriter().write(json);
	}
	private void getReprintOfSite_clsFor20(HttpServletRequest request, HttpServletResponse response, String userId) throws IOException, ParseException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		Entity wb_entity = new Entity();
		wb_entity.setId(8);
		
		String sql = "select sum(wb_rtCount) from wb_tongji where rq between '{rq1}' and '{rq2}' and userId = {userId}";
		sql = sql.replace("{rq1}", rq1).replace("{rq2}", rq2).replace("{userId}", userId);
		wb_entity.setCount(wd.getWb_cls(sql));
		List<Entity> entities = tableDao.getVSROfSite_clsFor20(c1, c2,"similar_count",userId);
		entities.add(wb_entity);
		
		String json = JsonUtil.<Entity>getJsonTestGeneric(entities, "id", "count");
		response.getWriter().write(json);
	}
	/**
	 * 累积浏览量下的按媒体类型分类的评论量
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ParseException
	 */
	private void getReplyOfSite_cls(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		Entity wb_entity = new Entity();
		wb_entity.setId(8);
		
		String sql = "select sum(wb_commCount) from wb_tongji where rq between '{rq1}' and '{rq2}' ";
		sql = sql.replace("{rq1}", rq1).replace("{rq2}", rq2);
		wb_entity.setCount(wd.getWb_cls(sql));
		
		List<Entity> entities = tableDao.getVSROfSite_cls(c1, c2,"reply");
		entities.add(wb_entity);
		
		String json = JsonUtil.<Entity>getJsonTestGeneric(entities, "id", "count");
		response.getWriter().write(json);
	}
	private void getReplyOfSite_clsFor20(HttpServletRequest request, HttpServletResponse response, String userId) throws IOException, ParseException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		Entity wb_entity = new Entity();
		wb_entity.setId(8);
		
		String sql = "select sum(wb_commCount) from wb_tongji where rq between '{rq1}' and '{rq2}' and userId = {userId}";
		sql = sql.replace("{rq1}", rq1).replace("{rq2}", rq2).replace("{userId}", userId);
		wb_entity.setCount(wd.getWb_cls(sql));
		
		List<Entity> entities = tableDao.getVSROfSite_clsFor20(c1, c2,"reply",userId);
		entities.add(wb_entity);
		
		String json = JsonUtil.<Entity>getJsonTestGeneric(entities, "id", "count");
		response.getWriter().write(json);
	}
	/**
	 * 发布周期内出现负面词的文章列表
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ParseException
	 */
	private void getSidesTitle(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		String sideWord = URLDecoder.decode(request.getParameter("name"),"utf-8"); 
		
		List<Table> tables = tableDao.getSidesTitle(c1, c2,sideWord,"10");
		
		String json = JsonUtil.<Table>getJsonTestGeneric(tables, "id", "uuid","title","similar_count","user_id","pubdate","site_name");
		response.getWriter().write(json);
	}
	private void getSidesTitleFor20(HttpServletRequest request, HttpServletResponse response, String userId) throws IOException, ParseException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		String sideWord = URLDecoder.decode(request.getParameter("name"),"utf-8"); 
		
		List<Table> tables = tableDao.getSidesTitleFor20(c1, c2,sideWord, "10", userId);
		
		String json = JsonUtil.<Table>getJsonTestGeneric(tables, "id", "uuid","title","similar_count","user_id","pubdate","site_name");
		response.getWriter().write(json);
	}
	/**
	 * 监测周期内出现负面词的文章列表
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ParseException
	 */
	private void getSidesTitleForRq(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		
		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		String sideWord = URLDecoder.decode(request.getParameter("name"),"utf-8"); 
		
		List<Table> tables = tableDao.getSidesTitle(c1, c2,sideWord,"10");
		
		String json = JsonUtil.<Table>getJsonTestGeneric(tables, "id", "uuid","title","similar_count","user_id","pubdate","site_name");
		response.getWriter().write(json);
	}
	private void getSidesTitleForRqFor20(HttpServletRequest request, HttpServletResponse response, String userId) throws IOException, ParseException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		
		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		String sideWord = URLDecoder.decode(request.getParameter("name"),"utf-8"); 
		
		List<Table> tables = tableDao.getSidesTitleFor20(c1, c2,sideWord, "10", userId);
		
		String json = JsonUtil.<Table>getJsonTestGeneric(tables, "id", "uuid","title","similar_count","user_id","pubdate","site_name");
		response.getWriter().write(json);
	}

	/**
	 * 
	 * 全行业新增发文量top10
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ParseException
	 */
	private void getUserCount(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		
		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		List<Table> tables = tableDao.getUUU(c1, c2, 10);
		
		String json = JsonUtil.getJson(tables, "userCount", "site_name", "domain_1");
		response.getWriter().write(json);
	}
	/**
	 * 
	 * 细分行业新增发文量top10
	 * @param request
	 * @param response
	 * @param userId
	 * @throws IOException
	 * @throws ParseException
	 */
	private void getUserCountFor20(HttpServletRequest request, HttpServletResponse response,String userId) throws IOException, ParseException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间

		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;

		List<Table> tables = tableDao.getUUUFor20(c1, c2,10,userId);

		String json = JsonUtil.getJson(tables, "userCount", "site_name", "domain_1");
		response.getWriter().write(json);
	}

}
