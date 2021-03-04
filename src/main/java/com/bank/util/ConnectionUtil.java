package com.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;


import org.apache.log4j.Logger;
import org.postgresql.Driver;

import com.bank.exceptions.UserNotFoundException;

public class ConnectionUtil {
	private static Logger log = Logger.getLogger(ConnectionUtil.class);

	public static Connection getConnection() throws UserNotFoundException {	
		try {
			Driver postgresDriver = new Driver();
			
			DriverManager.registerDriver(postgresDriver);
			
			String url = System.getenv("db_url");
			String username = System.getenv("db_username");
			String password = System.getenv("db_password");
			
			Connection con = DriverManager.getConnection(url, username, password);
			
			return con;
		}catch(Exception e) {
			log.error("Unable to establish connection! Exception message is: " + e.getMessage());
			throw new UserNotFoundException("Account does not exist!!!");
			
		}
	}
	
}
