package com.kolong.tongji.factory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConnectionFactory {
	private static Logger logger = Logger.getLogger(ConnectionFactory.class);
	private static Properties p = new Properties();
	
	private String url;
	private String username;
	private String password;
	private String dbName;
	
	static {
		try {
			//InputStream is = new FileInputStream("db.properties");
			InputStream is = ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
			p.load(is);
			is.close();
			Class.forName(p.getProperty("jdbcDriver"));
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public ConnectionFactory() {
		url = p.getProperty("url");
		username = p.getProperty("username");
		password = p.getProperty("password");
		dbName = p.getProperty("dbName");
	}
	
	public Connection getConn() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url+dbName,username,password);
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
		}
		return conn;
	}
	
	public Connection getConn(String db) {
		Connection conn = null;
		try {
			if(db != null) {
				conn = DriverManager.getConnection(url+db, username, password);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return conn;
	}
	
	public void close(Connection conn) {
		if(conn!= null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
 	}
	
	
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		ConnectionFactory factory = new ConnectionFactory();
		System.out.println(factory.getConn("data_0008"));
	}
	
}
