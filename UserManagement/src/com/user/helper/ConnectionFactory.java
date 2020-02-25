package com.user.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionFactory{

	/*
	 * //Database credentials static final String USER="system"; static final
	 * String PASS="faith";
	 */

	static Connection connection = null;

	public static Connection getConnection() throws Exception {

		Properties prop = loadPropertiesFile();
		// storing properties into variables
		String driverClass = prop.getProperty("ORACLEJDBC.JDBC_DRIVER");
		String url = prop.getProperty("ORACLEJDBC.DB_URL");
		String username = prop.getProperty("ORACLEJDBC.USER");
		String password = prop.getProperty("ORACLEJDBC.PASS");

		// register jdbc driver
		Class.forName(driverClass);

		return DriverManager.getConnection(url, username, password);

	}

	private static Properties loadPropertiesFile() throws Exception {

		Properties prop = new Properties();
		prop.load(Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("jdbc.properties"));
		return prop;
	}

	public static void main(String[] args) throws Exception {

		connection = ConnectionFactory.getConnection();
		System.out.println("Connected to database");

	}

}
