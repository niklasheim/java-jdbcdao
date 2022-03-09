package com.htlimst.mycoursesystem;

import java.sql.Connection;
import java.sql.SQLException;

import com.htlimst.mycoursesystem.dataaccess.MysqlDatabaseConnection;
import com.htlimst.mycoursesystem.ui.Cli;

public class MycoursesystemApplication {

	public static void main(String[] args) {

		Cli myCli = new Cli();
		myCli.start();
		
		try {
			Connection myConnection = MysqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/kurssystem", "root", null);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	

}
