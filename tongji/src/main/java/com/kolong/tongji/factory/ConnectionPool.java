package com.kolong.tongji.factory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

public class ConnectionPool {
	private static Logger logger = Logger.getLogger(ConnectionPool.class);
	
	private static BasicDataSource dataSource;
	
	static {
		try {
			InputStream is = ConnectionPool.class.getClassLoader().getResourceAsStream("db.properties");
			Properties p = new Properties();
			p.load(is);
			is.close();
			
			dataSource = new BasicDataSource();
			dataSource.setUsername(p.getProperty("c_username"));
			dataSource.setPassword(p.getProperty("c_password"));
			dataSource.setUrl(p.getProperty("c_url"));
			dataSource.setDriverClassName(p.getProperty("c_jdbcDriver"));
			dataSource.setInitialSize(Integer.parseInt(p.getProperty("initialSize")));
			dataSource.setMaxTotal(Integer.parseInt(p.getProperty("maxTotal")));
			dataSource.setMaxIdle(Integer.parseInt(p.getProperty("maxIdle")));
			dataSource.setMinIdle(Integer.parseInt(p.getProperty("minIdle")));
			dataSource.setMaxWaitMillis(Long.parseLong(p.getProperty("maxWaitMillis")));
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	public static Connection getConn() {
		try {
			if(dataSource != null) {
				return dataSource.getConnection();
			} 
		}catch (SQLException e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}

	public static void closeConn(Connection conn) {
		try {
			if(conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
		}finally {
			conn = null;
		}
	}
	
	public static DataSource getDs() {
		return dataSource;
	}
	
	public static void showStatus() {
		if(logger.isDebugEnabled()) {
			logger.debug("InitialSize:" + dataSource.getInitialSize());
			logger.debug("MaxTotal:" + dataSource.getMaxTotal());
			logger.debug("MaxIdle:" + dataSource.getMaxIdle());
			logger.debug("MinIdle:" + dataSource.getMinIdle());
			logger.debug("NumActive:" + dataSource.getNumActive());
		}
	}



	public static void main(String[] args) {
		System.out.println(ConnectionPool.getConn());
		ConnectionPool.showStatus();
	}
	
	
	
}
