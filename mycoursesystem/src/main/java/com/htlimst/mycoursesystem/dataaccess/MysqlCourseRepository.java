package com.htlimst.mycoursesystem.dataaccess;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.htlimst.mycoursesystem.domain.Course;
import com.htlimst.mycoursesystem.domain.CourseType;

public class MysqlCourseRepository implements MyCourseRepository {

    private Connection conn;

    public MysqlCourseRepository() throws ClassNotFoundException, SQLException{
        this.conn = MysqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/kurssystem", "root", null);
    }

    public Optional<Course> insert(Course entity) {
        return null;
    }

    public Optional<Course> getById(Long id) {
        String sql = "SELECT * FROM `courses` WHERE `id` = ?";
        try {
            PreparedStatement preparableStatement = conn.prepareStatement(sql);
            preparableStatement.setLong(1, id);
            ResultSet resultSet = preparableStatement.executeQuery();
            resultSet.next();
            Course course = new Course(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("description"), resultSet.getInt("hours"), resultSet.getDate("begindate"), resultSet.getDate("enddate"), CourseType.valueOf(resultSet.getString("courseType")));
            
            return Optional.of(course);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Course> getAll() {
        String sql = "SELECT * FROM `courses`";
        try {
            PreparedStatement preparableStatement;
            preparableStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparableStatement.executeQuery();
            ArrayList<Course> arrayList = new ArrayList<Course>();
            while (resultSet.next()) {
                arrayList.add(new Course(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("description"), resultSet.getInt("hours"), resultSet.getDate("begindate"), resultSet.getDate("enddate"), CourseType.valueOf(resultSet.getString("courseType"))));
            }
            return arrayList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Optional<Course> update(Course entitiy) {
        return null;
    }

    public void deleteById(Long id) {
        
    }

    public List<Course> findAllCoursesByName(String name) {
        return null;
    }

    public List<Course> findAllCoursesByDescription(String description) {
        return null;
    }

    public List<Course> findAllCoursesByNameOrDescription(String searchText) {
        return null;
    }

    public List<Course> findAllCoursesByCourseTyp(CourseType courseType) {
        return null;
    }

    public List<Course> findAllCoursesByStartDate(Date date) {
        return null;
    }

    public List<Course> findAllCoursesByEndDate(Date date) {
        return null;
    }

    public List<Course> findAllRunningCourses() {
        return null;
    }
    

}
