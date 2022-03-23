package com.htlimst.mycoursesystem;

import java.sql.SQLException;

import com.htlimst.mycoursesystem.dataaccess.MysqlCourseRepository;
import com.htlimst.mycoursesystem.dataaccess.MysqlStudentRepository;
import com.htlimst.mycoursesystem.ui.Cli;

public class MycoursesystemApplication {

	public static void main(String[] args) {

		Cli myCli;
		try {
			myCli = new Cli(new MysqlCourseRepository(), new MysqlStudentRepository());
			myCli.start();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}

}
