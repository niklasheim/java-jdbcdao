package com.htlimst.mycoursesystem.dataaccess;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        try {
            String sql = "INSERT INTO `courses` ( `name`, `description`, `hours`, `begindate`, `enddate`, `coursetype`) VALUES ( ?,?,?,?,?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setInt(3, entity.getHours());
            preparedStatement.setDate(4, entity.getBeginDate());
            preparedStatement.setDate(5, entity.getEndDate());
            preparedStatement.setString(6, entity.getCourseType().toString());

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0) {
                return Optional.empty();
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                return this.getById(generatedKeys.getLong(1));
            } else {
                return Optional.empty();
            }

        } catch (Exception e) {
            //TODO: handle exception
        }
        return Optional.empty();
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

    public Optional<Course> update(Course entity) {
        String sql = "UPDATE `courses` SET `name` = ?, `description` = ?, `hours` = ?, `begindate` = ?, `enddate` = ?, `coursetype` = ? WHERE `courses`.`id` = ?";

        if(getById(entity.getId()) == null){
            return Optional.empty();
        } else {
            try {
                PreparedStatement preparedStatement;
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, entity.getName());
                preparedStatement.setString(2, entity.getDescription());
                preparedStatement.setInt(3, entity.getHours());
                preparedStatement.setDate(4, entity.getBeginDate());
                preparedStatement.setDate(5, entity.getEndDate());
                preparedStatement.setString(6, entity.getCourseType().toString());
                preparedStatement.setLong(7, entity.getId());

                int affectedRows = preparedStatement.executeUpdate();

                if(affectedRows == 0){
                    return Optional.empty();
                } else {
                    return this.getById(entity.getId());
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM `courses` where `id` = ?";

        try {
            if(getById(id) != null){
                PreparedStatement preparableStatement;
                preparableStatement = conn.prepareStatement(sql);
                preparableStatement.setLong(1, id);
                preparableStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
