package com.htlimst.mycoursesystem.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlDatabaseConnection {
    
    private static Connection conn = null;
    private MysqlDatabaseConnection(){}

    public static Connection getConnection(String url, String user, String passwd) throws ClassNotFoundException, SQLException{
        if(conn == null){
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, passwd);
        } 
        return conn; 
    }
}
