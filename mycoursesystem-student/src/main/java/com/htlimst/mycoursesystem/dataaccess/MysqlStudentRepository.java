package com.htlimst.mycoursesystem.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.htlimst.mycoursesystem.domain.Student;

public class MysqlStudentRepository implements MyStudentRepository {

    private Connection conn;

    public MysqlStudentRepository() throws ClassNotFoundException, SQLException{
        this.conn = MysqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/kurssystem", "root", null);
    }

    public Optional<Student> insert(Student entity) {
        try {
            String sql = "INSERT INTO `students` ( `name`, `birthdate`) VALUES (?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDate(2, entity.getBirthdate());

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
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Student> getById(Long id) {
        String sql = "SELECT * FROM `students` WHERE `id` = ?";
        try {
            PreparedStatement preparableStatement = conn.prepareStatement(sql);
            preparableStatement.setLong(1, id);
            ResultSet resultSet = preparableStatement.executeQuery();
            resultSet.next();
            Student course = new Student(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getDate("birthdate"));
            
            return Optional.of(course);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Student> getAll() {
        String sql = "SELECT * FROM `students`";
        try {
            PreparedStatement preparableStatement;
            preparableStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparableStatement.executeQuery();
            ArrayList<Student> arrayList = new ArrayList<Student>();
            while (resultSet.next()) {
                arrayList.add(new Student(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getDate("birthdate")));
            }
            return arrayList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Optional<Student> update(Student entity) {
        String sql = "UPDATE `students` SET `name` = ?, `birthdate` = ? WHERE `students`.`id` = ?";

        if(getById(entity.getId()) == null){
            return Optional.empty();
        } else {
            try {
                PreparedStatement preparedStatement;
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, entity.getName());
                preparedStatement.setDate(2, entity.getBirthdate());
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
        String sql = "DELETE FROM `students` where `id` = ?";

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

    public List<Student> findAllByName(String searchedName) {
        try {
            String sql = "SELECT * FROM students WHERE LOWER(name) LIKE LOWER(?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "%" + searchedName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Student> studentList = new ArrayList<Student>();

            while(resultSet.next()) {
                studentList.add(
                        new Student(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getDate("birthDate")
                        ));
            }

            return studentList;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Student> findAllByBirthDate(Date birthDate) {
        try {
            String sql = "SELECT * FROM students WHERE `birthdate` = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setDate(1, (java.sql.Date) birthDate);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Student> studentList = new ArrayList<Student>();

            while(resultSet.next()) {
                studentList.add(
                        new Student(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getDate("birthDate")
                        ));
            }

            return studentList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public List<Student> findAllWithBirthDatesBetween(Date beginDate, Date endDate) {
        String sql = "SELECT * FROM students WHERE birthDate BETWEEN ? AND ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setDate(1, (java.sql.Date) beginDate);
            preparedStatement.setDate(2, (java.sql.Date) endDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Student> studentList = new ArrayList<Student>();

            while (resultSet.next()) {
                studentList.add(
                        new Student(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getDate("birthDate")
                        ));
            }
            return studentList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    
}
