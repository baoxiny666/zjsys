package org.tried.demo.model.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

public class ConnectionPool {
	private Vector<Connection> pool;

	private String url =Config.getInstance().getJdbcUrl();

	private String username = Config.getInstance().getJdbcUsername();

	private String password =Config.getInstance().getJdbcPassword();

	private String driverClassName = Config.getInstance().getDriverClass();

	private int poolSize = 10;

	private static ConnectionPool instance = null;

	private ConnectionPool() {
		init();
	}

	private void init() {
		pool = new Vector<Connection>(poolSize);
		addConnection();
	}

	public synchronized void release(Connection conn) {
		if(conn!=null){
		pool.add(conn);
		}
	}

	public synchronized void closePool() {
		for (int i = 0; i < pool.size(); i++) {
			try {
				((Connection) pool.get(i)).close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			pool.remove(i);
		}
	}

	public static ConnectionPool getInstance() {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}

	public synchronized Connection getConnection() {
		if (pool.size() > 0) {
			Connection conn = pool.get(0);
			pool.remove(conn);
			return conn;
		} else {
			return null;
		}
	}

	
	private void addConnection() {
		Connection conn = null;
		for (int i = 0; i < poolSize; i++) {

			try {
				Class.forName(driverClassName);
				conn = java.sql.DriverManager.getConnection(url, username, password);
				pool.add(conn);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
}