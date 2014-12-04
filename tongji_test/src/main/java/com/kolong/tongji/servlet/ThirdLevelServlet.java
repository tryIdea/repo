package com.kolong.tongji.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.util.JSONStringer;

import com.kolong.tongji.dao.Lt_TongJiDao;
import com.kolong.tongji.dao.ThirdDao;
import com.kolong.tongji.dao.Wb_TongJiDao;
import com.kolong.tongji.factory.DaoFactory;
import com.kolong.tongji.util.Constants;
import com.kolong.tongji.util.JsonUtil;
import com.kolong.tongji.vo.Lt_TongJi;
import com.kolong.tongji.vo.ThirdLevelIndicator;
import com.kolong.tongji.vo.Wb_TongJi;

public class ThirdLevelServlet extends HttpServlet {
	private ThirdDao td = DaoFactory.getThirdDao();
	private Wb_TongJiDao wd = DaoFactory.getWb_TongJiDao();
	private Lt_TongJiDao ld = DaoFactory.getLt_TongJiDao();

	private static final long serialVersionUID = -4245527574189192651L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		String dataType = request.getParameter("dataType");
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		String userId = request.getParameter("userId");

		@SuppressWarnings("unchecked")
		List<ThirdLevelIndicator> thirdLevelIndicators = (List<ThirdLevelIndicator>) td.findById(userId, "select * from tongji where userId = ? and rq between ? and ? order by rq asc", rq1, rq2).get(
				"thirdLevelIndicators");

		if (dataType != null && dataType.trim().length() > 0) {
			// 发文量
			if (dataType.equals("userCount")) {
				getUserCount(request, response, thirdLevelIndicators, userId);
			} else if (dataType.equals("userCountArea")) {
				getUserCountArea(request, response, thirdLevelIndicators, userId);
			} else if (dataType.equals("userCountPie")) {// 发布周期的类型饼图
				getUserCountPie(request, response, thirdLevelIndicators);
			} else if (dataType.equals("userCountPieRq")) {// 监测周期的类型饼图
				getUserCountPieRq(request, response, userId);

				// 浏览量
			} else if (dataType.equals("viewCount")) {
				getViewCount(request, response, thirdLevelIndicators, userId);
			} else if (dataType.equals("viewCountArea")) {
				getViewCountArea(request, response, thirdLevelIndicators, userId);
				// 转载量
			} else if (dataType.equals("reprintCount")) {
				getReprintCount(request, response, thirdLevelIndicators, userId);
			} else if (dataType.equals("reprintCountArea")) {
				getReprintCountArea(request, response, thirdLevelIndicators, userId);

				// 评论量
			} else if (dataType.equals("replyCount")) {
				getReplyCount(request, response, thirdLevelIndicators, userId);
			} else if (dataType.equals("replyCountArea")) {
				getReplyCountArea(request, response, thirdLevelIndicators, userId);

				// 敏感词
			} else if (dataType.equals("minCount")) {
				getMinCount(request, response, thirdLevelIndicators, userId);

				// 负面观点
			} else if (dataType.equals("sidesCount")) {
				getSidesCount(request, response, thirdLevelIndicators, userId);
			} else if (dataType.equals("side_wrong_count")) {
				getSide_wrong_count(request, response, thirdLevelIndicators, userId);
			} else if (dataType.equals("sidesCountColumn")) {
				getSidesCountColumn(request, response, thirdLevelIndicators);
				// 网站权威性
			} else if (dataType.equals("officialCount")) {
				getOfficialCount(request, response, thirdLevelIndicators);
				// 区域全面性
			} else if (dataType.equals("countryCount")) {
				getCountryCount(request, response, thirdLevelIndicators);
				// 类型全面性
			} else if (dataType.equals("traditionCount")) {
				getTraditionCount(request, response, thirdLevelIndicators);
				// 网站数量
			} else if (dataType.equals("sitesCount")) {
				getSitesCount(request, response, thirdLevelIndicators);
			}
		}

	}

	private void getSitesCount(HttpServletRequest request, HttpServletResponse response, List<ThirdLevelIndicator> thirdLevelIndicators) throws IOException {
		String json = JsonUtil.getJsonStringForSites(thirdLevelIndicators, Constants.MONITORCIRCLE, "网站数量", "网站数量变化趋势", "getSitesCount", true);

		response.getWriter().write(json);

	}

	private void getTraditionCount(HttpServletRequest request, HttpServletResponse response, List<ThirdLevelIndicator> thirdLevelIndicators) throws IOException {

		String data = JsonUtil.getJsonArray(thirdLevelIndicators, Constants.MONITORCIRCLE, "getTraditionCount", "getGeneralCount", "getSocialCount");

		response.getWriter().write(data);

	}

	private void getCountryCount(HttpServletRequest request, HttpServletResponse response, List<ThirdLevelIndicator> thirdLevelIndicators) throws IOException {

		String data = JsonUtil.getJsonArray(thirdLevelIndicators, Constants.MONITORCIRCLE, "getCountryCount", "getProvinceCount", "getCityCount", "getXianjiCount");

		response.getWriter().write(data);

	}

	private void getOfficialCount(HttpServletRequest request, HttpServletResponse response, List<ThirdLevelIndicator> thirdLevelIndicators) throws IOException {
		String data = JsonUtil.getJsonArray(thirdLevelIndicators, Constants.MONITORCIRCLE, "getOfficialCount", "getNon_officialCount");

		response.getWriter().write(data);

	}

	private void getSidesCount(HttpServletRequest request, HttpServletResponse response, List<ThirdLevelIndicator> thirdLevelIndicators, String userId) throws IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<Wb_TongJi> wbs = wd.getWb(buildSql(rq1, rq2, userId));
		List<Lt_TongJi> lts = ld.getLt(buildLtSql(rq1, rq2, userId));
		String json = JsonUtil.getJsonString(thirdLevelIndicators, wbs, lts, Constants.MONITORCIRCLE, "getSidesCount", "getWb_sideCount" ,"getLt_sideCount", true);
		
		response.getWriter().write(json);
	}
	private void getSide_wrong_count(HttpServletRequest request, HttpServletResponse response, List<ThirdLevelIndicator> thirdLevelIndicators, String userId) throws IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<Wb_TongJi> wbs = wd.getWb(buildSql(rq1, rq2, userId));
		List<Lt_TongJi> lts = ld.getLt(buildLtSql(rq1, rq2, userId));
		String json = JsonUtil.getJsonString(thirdLevelIndicators, wbs, lts, Constants.MONITORCIRCLE, "getSide_wrong_count", "getWb_side_wrong_count", "getLt_side_wrong_count", true);

		response.getWriter().write(json);
	}

	private void getSidesCountColumn(HttpServletRequest request, HttpServletResponse response, List<ThirdLevelIndicator> thirdLevelIndicators) throws IOException {

		int sidesCount = JsonUtil.getJsonArrayPie(thirdLevelIndicators, "getSidesCount");
		int positiveCount = JsonUtil.getJsonArrayPie(thirdLevelIndicators, "getPositiveCount");
		int userCount = JsonUtil.getJsonArrayPie(thirdLevelIndicators, "getUserCount");

		int neutral = userCount - sidesCount - positiveCount;

		JSONStringer json = new JSONStringer();
		json.array();

		JSONStringer jsonP = new JSONStringer();
		jsonP.object().key("name").value("正面").key("count").value(positiveCount).endObject();
		JSONStringer jsonS = new JSONStringer();
		jsonS.object().key("name").value("负面").key("count").value(sidesCount).endObject();
		JSONStringer jsonN = new JSONStringer();
		jsonN.object().key("name").value("中立").key("count").value(neutral).endObject();
		
		json.value(jsonP)
			.value(jsonS)
			.value(jsonN);
		json.endArray();

		response.getWriter().write(json.toString());
	}

	private void getMinCount(HttpServletRequest request, HttpServletResponse response, List<ThirdLevelIndicator> thirdLevelIndicators, String userId) throws IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<Wb_TongJi> wbs = wd.getWb(buildSql(rq1, rq2, userId));
		List<Lt_TongJi> lts = ld.getLt(buildLtSql(rq1, rq2, userId));
		String json = JsonUtil.getJsonString(thirdLevelIndicators, wbs ,lts, Constants.MONITORCIRCLE, "getMinCount", "getWb_minCount", "getLt_minCount", true);

		response.getWriter().write(json);
	}

	private void getReplyCount(HttpServletRequest request, HttpServletResponse response, List<ThirdLevelIndicator> thirdLevelIndicators, String userId) throws IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<Wb_TongJi> wbs = wd.getWb(buildSql(rq1, rq2, userId));
		List<Lt_TongJi> lts = ld.getLt(buildLtSql(rq1, rq2, userId));
		String json = JsonUtil.getJsonString(thirdLevelIndicators, wbs, lts, Constants.MONITORCIRCLE, "getReplyCount", "getWb_commCount", "getLt_commCount", true);

		response.getWriter().write(json);
	}

	private void getReplyCountArea(HttpServletRequest request, HttpServletResponse response, List<ThirdLevelIndicator> thirdLevelIndicators , String userId) throws IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<Wb_TongJi> wbs = wd.getWb(buildSql(rq1, rq2, userId));
		List<Lt_TongJi> lts = ld.getLt(buildLtSql(rq1, rq2, userId));
		String json = JsonUtil.getJsonString(thirdLevelIndicators, wbs, lts, Constants.MONITORCIRCLE, "getReplyCount", "getWb_commCount", "getLt_commCount", false);

		response.getWriter().write(json);
	}

	private void getReprintCount(HttpServletRequest request, HttpServletResponse response, List<ThirdLevelIndicator> thirdLevelIndicators, String userId) throws IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<Wb_TongJi> wbs = wd.getWb(buildSql(rq1, rq2, userId));
		List<Lt_TongJi> lts = ld.getLt(buildLtSql(rq1, rq2, userId));
		String json = JsonUtil.getJsonString(thirdLevelIndicators, wbs, lts, Constants.MONITORCIRCLE, "getReprintCount", "getWb_rtCount", "getLt_rtCount", true);

		response.getWriter().write(json);
	}

	private void getReprintCountArea(HttpServletRequest request, HttpServletResponse response, List<ThirdLevelIndicator> thirdLevelIndicators, String userId) throws IOException {
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<Wb_TongJi> wbs = wd.getWb(buildSql(rq1, rq2, userId));
		List<Lt_TongJi> lts = ld.getLt(buildLtSql(rq1, rq2, userId));
		String json = JsonUtil.getJsonString(thirdLevelIndicators, wbs, lts, Constants.MONITORCIRCLE, "getReprintCount","getWb_rtCount", "getLt_rtCount", false);

		response.getWriter().write(json);
	}

	private void getViewCount(HttpServletRequest request, HttpServletResponse response, List<ThirdLevelIndicator> thirdLevelIndicators, String userId) throws IOException {

		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<Wb_TongJi> wbs = wd.getWb(buildSql(rq1, rq2, userId));
		List<Lt_TongJi> lts = ld.getLt(buildLtSql(rq1, rq2, userId));
		String json = JsonUtil.getJsonString(thirdLevelIndicators, wbs, lts, Constants.MONITORCIRCLE, "getViewCount", "getWb_viewCount", "getLt_viewCount",true);

		response.getWriter().write(json);

	}

	private void getViewCountArea(HttpServletRequest request, HttpServletResponse response, List<ThirdLevelIndicator> thirdLevelIndicators, String userId) throws IOException {

		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<Wb_TongJi> wbs = wd.getWb(buildSql(rq1, rq2, userId));
		List<Lt_TongJi> lts = ld.getLt(buildLtSql(rq1, rq2, userId));
		String json = JsonUtil.getJsonString(thirdLevelIndicators, wbs, lts,Constants.MONITORCIRCLE, "getViewCount", "getWb_viewCount", "getLt_viewCount",false);

		response.getWriter().write(json);
	}

	private void getUserCount(HttpServletRequest request, HttpServletResponse response, List<ThirdLevelIndicator> thirdLevelIndicators,String userId) throws IOException {

		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<Wb_TongJi> wbs = wd.getWb(buildSql(rq1, rq2, userId));
		List<Lt_TongJi> lts = ld.getLt(buildLtSql(rq1, rq2, userId));
		
		String json = JsonUtil.getJsonString(thirdLevelIndicators,wbs,lts,Constants.MONITORCIRCLE, "getUserCount", "getWb_userCount", "getLt_userCount", true);

		response.getWriter().write(json);

	}

	private void getUserCountArea(HttpServletRequest request, HttpServletResponse response, List<ThirdLevelIndicator> thirdLevelIndicators, String userId) throws IOException {
		
		String rq1 = request.getParameter("rq1");
		String rq2 = request.getParameter("rq2");
		
		List<Wb_TongJi> wbs = wd.getWb(buildSql(rq1, rq2, userId));
		List<Lt_TongJi> lts = ld.getLt(buildLtSql(rq1, rq2, userId));
		String json = JsonUtil.getJsonString(thirdLevelIndicators, wbs, lts, Constants.MONITORCIRCLE, "getUserCount", "getWb_userCount", "getLt_userCount", false);

		response.getWriter().write(json);

	}

	private void getUserCountPie(HttpServletRequest request, HttpServletResponse response, List<ThirdLevelIndicator> thirdLevelIndicators) throws IOException {
		String json = JsonUtil.getJsonArrayPie(thirdLevelIndicators, Constants.MONITORCIRCLE);

		response.getWriter().write(json);

	}

	private void getUserCountPieRq(HttpServletRequest request, HttpServletResponse response, String userId) throws IOException {
		String rq = request.getParameter("rq");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = null;
		try {
			endDate = df.parse(rq);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long c2 = endDate.getTime();
		long interval = 1000 * 60 * 60 * 24;// 一天时间

		long c1 = (c2 - interval * (Constants.MONITORCIRCLE - 1));
		c2 = (c2 + interval - 1);
		@SuppressWarnings("unchecked")
		List<ThirdLevelIndicator> thirdLevelIndicators_Rq = (List<ThirdLevelIndicator>) td.findById(userId, "select * from tongji where userId = ? and rq between ? and ? order by rq asc", format(new Date(c1)), format(new Date(c2))).get(
				"thirdLevelIndicators");
		String json = JsonUtil.getJsonArrayPie(thirdLevelIndicators_Rq, Constants.MONITORCIRCLE);

		response.getWriter().write(json);

	}
	
	public static String format(Object obj) {
		return new SimpleDateFormat("yyyy-MM-dd").format(obj);
	}
	
	/**
	 * 构建微博sql
	 * @param rq1
	 * @param rq2
	 * @param userId
	 * @return
	 */
	public static String buildSql(String rq1, String rq2, String userId) {
		String sql = "";
		if(userId.equals("0")) {
			sql = "select sum(wb_userCount) wb_userCount,sum(wb_viewCount) wb_viewCount, sum(wb_rtCount) wb_rtCount,sum(wb_commCount) wb_commCount, sum(wb_sideCount) wb_sideCount,sum(wb_positiveCount) wb_positiveCount,sum(wb_side_wrong_count) wb_side_wrong_count, sum(wb_minCount) wb_minCount,rq from wb_tongji where rq between '{rq1}' and '{rq2}' GROUP BY rq order by rq asc";
		}else {
			sql = "select wb_userCount, wb_viewCount, wb_rtCount, wb_commCount, wb_sideCount, wb_positiveCount, wb_side_wrong_count, wb_minCount,rq from wb_tongji where rq between '{rq1}' and '{rq2}' and userId = {userId} order by rq asc";
		}
		return sql = sql.replace("{rq1}", rq1).replace("{rq2}", rq2).replace("{userId}", userId);
	}
	/**
	 * 构建论坛sql
	 * @param rq1
	 * @param rq2
	 * @param userId
	 * @return
	 */
	public static String buildLtSql(String rq1, String rq2, String userId) {
		String sql = "";
		if(userId.equals("0")) {
			sql = "select sum(lt_userCount) lt_userCount,sum(lt_viewCount) lt_viewCount, sum(lt_rtCount) lt_rtCount,sum(lt_commCount) lt_commCount, sum(lt_sideCount) lt_sideCount,sum(lt_positiveCount) lt_positiveCount,sum(lt_side_wrong_count) lt_side_wrong_count, sum(lt_minCount) lt_minCount,rq from lt_tongji where rq between '{rq1}' and '{rq2}' GROUP BY rq order by rq asc";
		}else {
			sql = "select lt_userCount, lt_viewCount, lt_rtCount, lt_commCount, lt_sideCount, lt_positiveCount, lt_side_wrong_count, lt_minCount,rq from lt_tongji where rq between '{rq1}' and '{rq2}' and userId = {userId} order by rq asc";
		}
		return sql = sql.replace("{rq1}", rq1).replace("{rq2}", rq2).replace("{userId}", userId);
	}

}
