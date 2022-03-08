package com.htlimst.jdbcdemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Jdbcdemo {

	public static void main(String[] args) {

		// selectAllDemo();
		// insertStudent();
		// updateStudent();
		deleteStudent();
	}

	public static void deleteStudent(){
		String deleteStudent = "DELETE FROM `student`WHERE `student`.`id` = 3";
		String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
		String connectionUser = "root";
		String connectionPass = null;

		try {
			Connection conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPass);
			System.out.println("Connection successful");
			PreparedStatement preparedStatement = conn.prepareStatement(deleteStudent);

			try {
				int rowAffected = preparedStatement.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Fehler im SQL-Update Statement: " + e.getMessage());
			}



		} catch (SQLException e) {
			System.out.println("Connection error: " + e.getMessage());
		}
	}

	public static void updateStudent(){
		String updateStudent = "UPDATE `student` SET `name` = ? WHERE `student`.`id` = 2";
		String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
		String connectionUser = "root";
		String connectionPass = null;

		try {
			Connection conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPass);
			System.out.println("Connection successful");
			PreparedStatement preparedStatement = conn.prepareStatement(updateStudent);

			try {
				preparedStatement.setString(1, "Meili");
				int rowAffected = preparedStatement.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Fehler im SQL-Update Statement: " + e.getMessage());
			}



		} catch (SQLException e) {
			System.out.println("Connection error: " + e.getMessage());
		}
	}

	public static void insertStudent(){

		String insertStudent = "INSERT INTO `student`  (`id`, `name`, `email`) VALUES (null, ?, ?)";
		String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
		String connectionUser = "root";
		String connectionPass = null;

		try {
			Connection conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPass);
			System.out.println("Connection successful");
			PreparedStatement preparedStatement = conn.prepareStatement(insertStudent);

			try {
				preparedStatement.setString(1, "Michael Bogensberger");
				preparedStatement.setString(2, "mibog@tsn.at");
				int rowAffected = preparedStatement.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Fehler im SQL-Insert Statement: " + e.getMessage());
			}



		} catch (SQLException e) {
			System.out.println("Connection error: " + e.getMessage());
		}
	}

	public static void selectAllDemo(){

		String selectAllStudents = "SELECT * FROM `student`";
		String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
		String connectionUser = "root";
		String connectionPass = null;

		try {
			Connection conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPass);
			System.out.println("Connection successful");

			PreparedStatement preparedStatement = conn.prepareStatement(selectAllStudents);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");

				System.out.printf("Student aus der DB: [ID] %d, [Name] %s, [E-Mail] %s.\n", id, name, email);
			}

		} catch (SQLException e) {
			System.out.println("Connection error: " + e.getMessage());
		}
	}

}
