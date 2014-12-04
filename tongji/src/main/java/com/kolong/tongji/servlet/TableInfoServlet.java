package com.kolong.tongji.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.util.JSONStringer;

import com.kolong.tongji.dao.TableDao;
import com.kolong.tongji.factory.DaoFactory;
import com.kolong.tongji.util.Constants;

public class TableInfoServlet extends HttpServlet {

	/**
	 * table分页信息
	 */
	private static final long serialVersionUID = -8397232924123972282L;
	private static TableDao tableDao = DaoFactory.getTableDao();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		String dataType = request.getParameter("dataType");
		String userId = request.getParameter("userId");
		
		try {
			if("0".equals(userId) && "domain_1TitleCount".equals(dataType)) {
				getDomain_1TitleCount(request, response);
			}else if("domain_1TitleCount".equals(dataType)) {
				getDomain_1TitleCountFor20(request, response,userId);
			}else if("0".equals(userId) && "domain_1TitleCountArea".equals(dataType)) {
				getDomain_1TitleCountArea(request, response);
			}else if("domain_1TitleCountArea".equals(dataType)) {
				getDomain_1TitleCountAreaFor20(request, response,userId);
			}else if("simiTitle".equals(dataType)) {
				getSimiTile(request, response,userId);
			}else if("0".equals(userId) && "totalCount".equals(dataType)) {
				getTotalCount(request, response);
			}else if("totalCount".equals(dataType)) {
				getTotalCountFor20(request, response,userId);
			}else if("0".equals(userId) && "totalCountArea".equals(dataType)) {
				getTotalCountArea(request, response);
			}else if("totalCountArea".equals(dataType)) {
				getTotalCountAreaFor20(request, response,userId);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void getSimiTile(HttpServletRequest request, HttpServletResponse response, String userId) throws IOException {
		String simId = request.getParameter("simId");
		int totalCount = tableDao.getTitleCount(userId, simId);
		String url = "cpoiTemp.jsp?userId={userId}&simId={simId}&totalCount={totalCount}";
		url = url.replace("{userId}", userId).replace("{simId}", simId).replace("{totalCount}", totalCount+"");
		
		response.sendRedirect(url);
	}

	private void getDomain_1TitleCount(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间

		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		int totalCount = 0;
		String isFilter = request.getParameter("isFilter");
		String domain_1 = request.getParameter("domain_1");
		totalCount = tableDao.getDomain_1TitleCount(c1, c2, domain_1, Boolean.parseBoolean(isFilter));
		
		JSONStringer json = new JSONStringer();
		json.object()
		.key("totalCount")
		.value(totalCount)
		.endObject();
		response.getWriter().write(json.toString());
	}
	private void getDomain_1TitleCountFor20(HttpServletRequest request, HttpServletResponse response,String userId) throws ParseException, IOException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间

		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		int totalCount = 0;
		String isFilter = request.getParameter("isFilter");
		String domain_1 = request.getParameter("domain_1");
		totalCount = tableDao.getDomain_1TitleCountFor20(c1, c2, domain_1, userId, Boolean.parseBoolean(isFilter));
		
		JSONStringer json = new JSONStringer();
		json.object()
		.key("totalCount")
		.value(totalCount)
		.endObject();
		response.getWriter().write(json.toString());
	}
	private void getDomain_1TitleCountArea(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		int totalCount = 0;
		String isFilter = request.getParameter("isFilter");
		String domain_1 = request.getParameter("domain_1");
		totalCount = tableDao.getDomain_1TitleCount(c1, c2, domain_1,Boolean.parseBoolean(isFilter));
		
		JSONStringer json = new JSONStringer();
		json.object()
		.key("totalCount")
		.value(totalCount)
		.endObject();
		response.getWriter().write(json.toString());
	}
	private void getDomain_1TitleCountAreaFor20(HttpServletRequest request, HttpServletResponse response,String userId) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		int totalCount = 0;
		String isFilter = request.getParameter("isFilter");
		String domain_1 = request.getParameter("domain_1");
		totalCount = tableDao.getDomain_1TitleCountFor20(c1, c2, domain_1,userId,Boolean.parseBoolean(isFilter));
		
		JSONStringer json = new JSONStringer();
		json.object()
		.key("totalCount")
		.value(totalCount)
		.endObject();
		response.getWriter().write(json.toString());
	}
	private void getTotalCountArea(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		int totalCount = 0;
		String isFilter = request.getParameter("isFilter");
		if("true".equals(isFilter)) {
			totalCount = tableDao.getCount(c1, c2, true);
		}else {
			totalCount = tableDao.getCount(c1, c2, false);
		}
		
		JSONStringer json = new JSONStringer();
		json.object()
		.key("totalCount")
		.value(totalCount)
		.endObject();
		response.getWriter().write(json.toString());
	}
	private void getTotalCountAreaFor20(HttpServletRequest request, HttpServletResponse response,String userId) throws ParseException, IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(rq1);
		Date endDate = df.parse(rq2);
		long c1 = startDate.getTime() / 1000;
		long interval = 1000 * 60 * 60 * 24;// 一天时间
		long c2 = (endDate.getTime() + interval - 1) / 1000;
		
		int totalCount = 0;
		String isFilter = request.getParameter("isFilter");
		totalCount = tableDao.getCountFor20(c1, c2, userId,Boolean.parseBoolean(isFilter));
		
		JSONStringer json = new JSONStringer();
		json.object()
		.key("totalCount")
		.value(totalCount)
		.endObject();
		response.getWriter().write(json.toString());
	}
	
	private void getTotalCount(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间

		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		int totalCount = 0;
		String isFilter = request.getParameter("isFilter");
		totalCount = tableDao.getCount(c1, c2, Boolean.parseBoolean(isFilter));
		
		JSONStringer json = new JSONStringer();
		json.object()
			.key("totalCount")
			.value(totalCount)
			.endObject();
		response.getWriter().write(json.toString());
	}
	private void getTotalCountFor20(HttpServletRequest request, HttpServletResponse response,String userId) throws ParseException, IOException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = df.parse(rq);
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间

		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1)) / 1000;
		c2 = (c2 + interval - 1) / 1000;
		
		int totalCount = 0;
		String isFilter = request.getParameter("isFilter");
		totalCount = tableDao.getCountFor20(c1, c2, userId,Boolean.parseBoolean(isFilter));
		
		JSONStringer json = new JSONStringer();
		json.object()
			.key("totalCount")
			.value(totalCount)
			.endObject();
		response.getWriter().write(json.toString());
	}

}
