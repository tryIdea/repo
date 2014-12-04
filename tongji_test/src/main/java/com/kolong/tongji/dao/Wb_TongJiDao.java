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
import com.kolong.tongji.vo.Wb_TongJi;

public class Wb_TongJiDao {
	private static final Logger logger = Logger.getLogger(Wb_TongJiDao.class);
	
	/**
	 * 获得微博相关数据
	 * 
	 * @return
	 */
	public List<Wb_TongJi> getWb(String sql) {
		if(logger.isDebugEnabled()) {
			logger.debug("List<Wb_TongJi> getWb" + sql);
		}
		
		Connection conn = ConnectionPool.getConn();
		
		List<Wb_TongJi> wbs = new ArrayList<Wb_TongJi>();
		try {
			QueryRunner runner = new QueryRunner();
			wbs = runner.query(conn, sql, new ResultSetHandler<List<Wb_TongJi>>() {
				
				@Override
				public List<Wb_TongJi> handle(ResultSet rs) throws SQLException {
					List<Wb_TongJi> wbs = new ArrayList<Wb_TongJi>();
					while (rs.next()) {
						Wb_TongJi wb = new Wb_TongJi();
						wb.setWb_userCount(rs.getInt("wb_userCount"));
						wb.setWb_viewCount(rs.getInt("wb_viewCount"));
						wb.setWb_rtCount(rs.getInt("wb_rtCount"));
						wb.setWb_commCount(rs.getInt("wb_commCount"));
						wb.setWb_sideCount(rs.getInt("wb_sideCount"));
						wb.setWb_positiveCount(rs.getInt("wb_positiveCount"));
						wb.setWb_side_wrong_count(rs.getInt("wb_side_wrong_count"));
						wb.setWb_minCount(rs.getInt("wb_minCount"));
						wb.setRq(rs.getDate("rq"));
						wbs.add(wb);
					}
					
					return wbs;
				}
				
			});
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ConnectionPool.closeConn(conn);
		}
		
		return wbs;
	}
	/**
	 * 获得微博浏览量，转载量，评论量
	 * 
	 * @return
	 */
	public int getWb_cls(String sql) {

		Connection conn = ConnectionPool.getConn();
		int wb_cls = 0;
		try {
			QueryRunner runner = new QueryRunner();
			wb_cls = runner.query(conn, sql, new ResultSetHandler<Integer>() {

				@Override
				public Integer handle(ResultSet rs) throws SQLException {
					rs.next();
					return rs.getInt(1);
				}

			});
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			ConnectionPool.closeConn(conn);
		}

		return wb_cls;
	}
	
}
