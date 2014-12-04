package com.kolong.tongji.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.log4j.Logger;

import com.kolong.tongji.factory.ConnectionPool;
import com.kolong.tongji.vo.Lt_TongJi;

public class Lt_TongJiDao {
	private static final Logger logger = Logger.getLogger(Lt_TongJiDao.class);
	
	/**
	 * 获得论坛相关数据
	 * 
	 * @return
	 */
	public List<Lt_TongJi> getLt(String sql) {
		if(logger.isDebugEnabled()) {
			logger.debug("List<Lt_TongJi> getLt:" + sql);
		}
		
		Connection conn = ConnectionPool.getConn();
		
		List<Lt_TongJi> Lts = new ArrayList<Lt_TongJi>();
		try {
			QueryRunner runner = new QueryRunner();
			Lts = runner.query(conn, sql, new ResultSetHandler<List<Lt_TongJi>>() {
				
				@Override
				public List<Lt_TongJi> handle(ResultSet rs) throws SQLException {
					List<Lt_TongJi> Lts = new ArrayList<Lt_TongJi>();
					while (rs.next()) {
						Lt_TongJi Lt = new Lt_TongJi();
						Lt.setLt_userCount(rs.getInt("lt_userCount"));
						Lt.setLt_viewCount(rs.getInt("lt_viewCount"));
						Lt.setLt_rtCount(rs.getInt("lt_rtCount"));
						Lt.setLt_commCount(rs.getInt("lt_commCount"));
						Lt.setLt_sideCount(rs.getInt("lt_sideCount"));
						Lt.setLt_positiveCount(rs.getInt("lt_positiveCount"));
						Lt.setLt_side_wrong_count(rs.getInt("lt_side_wrong_count"));
						Lt.setLt_minCount(rs.getInt("lt_minCount"));
						Lt.setRq(rs.getDate("rq"));
						Lts.add(Lt);
					}
					
					return Lts;
				}
				
			});
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ConnectionPool.closeConn(conn);
		}
		
		return Lts;
	}
	
}
