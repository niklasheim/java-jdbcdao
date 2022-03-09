package com.htlimst.mycoursesystem.dataaccess;

import java.sql.Date;
import java.util.List;

import com.htlimst.mycoursesystem.domain.Course;
import com.htlimst.mycoursesystem.domain.CourseType;

public interface MyCourseRepository extends BaseRepository<Course, Long> {
    List<Course> findAllCoursesByName(String name);
    List<Course> findAllCoursesByDescription(String description);
    List<Course> findAllCoursesByNameOrDescription(String searchText);
    List<Course> findAllCoursesByCourseTyp(CourseType courseType);
    List<Course> findAllCoursesByStartDate(Date date);
    List<Course> findAllCoursesByEndDate(Date date);
    List<Course> findAllRunningCourses();
}
