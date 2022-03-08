package com.htlimst.jdbcdemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Jdbcdemo {

	public static void main(String[] args) {

		selectAllDemo();
	}

	public static void selectAllDemo(){

		String selectAllStudents = "SELECT * FROM `student`";
		String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
		String connectionUser = "root";
		String connectionPass = null;

		try {
			Connection conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPass);
			System.out.println("Connection successful");
		} catch (SQLException e) {
			System.out.println("Connection error: " + e.getMessage());
		}
	}

}
