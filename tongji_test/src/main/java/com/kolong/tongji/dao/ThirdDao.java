package com.kolong.tongji.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.log4j.Logger;

import com.kolong.tongji.factory.ConnectionPool;
import com.kolong.tongji.vo.FirstLevelIndicator;
import com.kolong.tongji.vo.SecondLevelIndicator;
import com.kolong.tongji.vo.ThirdLevelIndicator;

public class ThirdDao {
	private static Logger logger = Logger.getLogger(ThirdDao.class);
	
	public Map<String,List<?>> findById(String userId,String sql, String rq1, String rq2) {
		
		Connection conn = ConnectionPool.getConn();
		
		Map<String,List<?>> obj = null;
		try {
			QueryRunner runner = new QueryRunner();
			obj =  runner.query(conn, sql, new ResultSetHandler<Map<String,List<?>>>() {

				@Override
				public Map<String,List<?>> handle(ResultSet rs) throws SQLException {
					Map<String,List<?>> map = new HashMap<String, List<?>>();
					List<ThirdLevelIndicator> thirdLevelIndicators = new ArrayList<ThirdLevelIndicator>();
					List<SecondLevelIndicator> secondLevelIndicators = new ArrayList<SecondLevelIndicator>();
					List<FirstLevelIndicator> firstLevelIndicators = new ArrayList<FirstLevelIndicator>();
					while(rs.next()) {
						ThirdLevelIndicator third = new ThirdLevelIndicator();
						
						third.setId(rs.getInt("id"));
						third.setUserCount(rs.getInt("userCount"));
						third.setViewCount(rs.getInt("viewCount"));
						third.setReprintCount(rs.getInt("reprintCount"));
						third.setReplyCount(rs.getInt("replyCount"));
						third.setTitleMinCount(rs.getInt("titleMinCount"));
						third.setTitleMinWeight(rs.getFloat("titleMinWeight"));
						third.setDescMinCount(rs.getInt("descMinCount"));
						third.setDescMinWeight(rs.getFloat("descMinWeight"));
						third.setMinCount(rs.getInt("minCount"));
						third.setSidesCount(rs.getInt("sidesCount"));
						third.setPositiveCount(rs.getInt("positiveCount"));
						third.setSide_wrong_count(rs.getInt("side_wrong_count"));
						third.setS_sitesCount(rs.getInt("s_sitesCount"));
						third.setOfficialInfoCount(rs.getInt("officialInfoCount"));
						third.setOfficialWeight(rs.getFloat("officialWeight"));
						third.setOfficialCount(rs.getInt("officialCount"));
						third.setNon_officialInfoCount(rs.getInt("non_officialInfoCount"));
						third.setNon_officialWeight(rs.getFloat("non_officialWeight"));
						third.setNon_officialCount(rs.getInt("non_officialCount"));
						third.setCountryInfoCount(rs.getInt("countryInfoCount"));
						third.setCountryWeight(rs.getFloat("countryWeight"));
						third.setCountryCount(rs.getInt("countryCount"));
						third.setProvinceInfoCount(rs.getInt("provinceInfoCount"));
						third.setProvinceWeight(rs.getFloat("provinceWeight"));
						third.setProvinceCount(rs.getInt("provinceCount"));
						third.setCityInfoCount(rs.getInt("cityInfoCount"));
						third.setCityWeight(rs.getFloat("cityWeight"));
						third.setCityCount(rs.getInt("cityCount"));
						third.setXianjiInfoCount(rs.getInt("xianjiInfoCount"));
						third.setXianjiWeight(rs.getFloat("xianjiWeight"));
						third.setXianjiCount(rs.getInt("xianjiCount"));
						third.setTraditionInfoCount(rs.getInt("traditionInfoCount"));
						third.setTraditionWeight(rs.getFloat("traditionWeight"));
						third.setTraditionCount(rs.getInt("traditionCount"));
						third.setGeneralInfoCount(rs.getInt("generalInfoCount"));
						third.setGeneralWeight(rs.getFloat("generalWeight"));
						third.setGeneralCount(rs.getInt("generalCount"));
						third.setSocialInfoCount(rs.getInt("socialInfoCount"));
						third.setSocialWeight(rs.getFloat("socialWeight"));
						third.setSocialCount(rs.getInt("socialCount"));
						third.setSite_yxl(rs.getInt("site_yxl"));
						third.setSitesCount(rs.getInt("sitesCount"));
						third.setRq(rs.getDate("rq"));
						third.setUserId(rs.getInt("userId"));
						
						if(rs.getInt("viewCount")==0 && rs.getInt("reprintCount")==0 && rs.getInt("replyCount")==0){
							third.setViewCount(1);
						}
						
						if(rs.getInt("titleMinCount")==0 && rs.getInt("descMinCount")==0 && rs.getInt("minCount")==0 && rs.getInt("sidesCount")==0) {
							third.setSidesCount(1);
						}
						
						if(rs.getInt("userCount")==0 && rs.getInt("viewCount")==0 && rs.getInt("sidesCount")==0
								&& rs.getInt("non_officialInfoCount")==0 && rs.getInt("non_officialCount")==0
								&& rs.getInt("xianjiInfoCount")==0 && rs.getInt("xianjiCount")==0
								&& rs.getInt("generalInfoCount")==0 && rs.getInt("generalCount")==0
								&& rs.getInt("sitesCount")==0) {
							third.setUserCount(1);
							third.setViewCount(1);
							third.setSidesCount(1);
							third.setNon_officialInfoCount(1);
							third.setNon_officialCount(1);
							third.setXianjiInfoCount(1);
							third.setXianjiCount(1);
							third.setGeneralInfoCount(1);
							third.setGeneralCount(1);
							third.setSitesCount(1);
						}
						
						//3
						thirdLevelIndicators.add(third);
						//2
						SecondLevelIndicator socond = new SecondLevelIndicator(third);
						secondLevelIndicators.add(socond);
						//1
						FirstLevelIndicator first = new FirstLevelIndicator(socond);
						firstLevelIndicators.add(first);
					}
					
					map.put("firstLevelIndicators", firstLevelIndicators);
					map.put("secondLevelIndicators", secondLevelIndicators);
					map.put("thirdLevelIndicators", thirdLevelIndicators);
					
					return map;
				}
				
			}, userId, rq1, rq2);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}finally {
			ConnectionPool.closeConn(conn);
		}
		
		return obj;
	}
	
	public void test(String sql,String userId) {
		DataSource ds = ConnectionPool.getDs();
		QueryRunner runner = new QueryRunner(ds);
		try {
			runner.query(sql, new ResultSetHandler<Object>() {

					@Override
					public Object handle(ResultSet rs) throws SQLException {
						while(rs.next()) {
							System.out.println(rs.getObject(2));
						}
						
						return null;
					}
					
				}, userId);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
}
