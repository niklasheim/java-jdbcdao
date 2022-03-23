package com.htlimst.mycoursesystem.dataaccess;

import java.util.Date;
import java.util.List;

import com.htlimst.mycoursesystem.domain.Student;

public interface MyStudentRepository extends BaseRepository<Student, Long> {
    List<Student> findAllByName(String searchText);
    List<Student> findAllByBirthDate(Date birthDate);
    List<Student> findAllWithBirthDatesBetween(Date beginDate, Date endDate);
}
