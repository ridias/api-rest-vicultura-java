package com.springboot.app.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection {
	
	private static Connection conn = null;
	private String driver;
    private String url;
    private String username;
    private String password;
    
    private MysqlConnection() {
    	this.url= "jdbc:mysql://localhost:3306/viculturadb";
    	this.driver = "com.mysql.cj.jdbc.Driver";
    	this.username = "root";
    	this.password = "Gloriaeterna1995?";
    	
    	try {
    		Class.forName(driver);
    		conn = DriverManager.getConnection(url, username, password);
    	}catch(ClassNotFoundException e) {
    		e.printStackTrace();
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public static Connection getConnection() {
    	if(conn == null) {
    		new MysqlConnection();
    	}
    	
    	return conn;
    }
    
    public static void closeConnection() {
    	if(conn != null) {
    		try{
    			conn.close();
    		}catch(Exception ex) {
    			ex.printStackTrace();
    		}
    	}
    	
    	conn = null;
    }
}
