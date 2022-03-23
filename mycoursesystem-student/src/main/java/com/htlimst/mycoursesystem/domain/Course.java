package com.htlimst.mycoursesystem.domain;

import java.sql.Date;

public class Course extends BaseEntity {
    
    private String name;
    private String description;
    private int hours;
    private Date beginDate;
    private Date endDate;
    private CourseType courseType;



    


    public Course(String name, String description, int hours, Date beginDate, Date endDate,CourseType courseType) throws InvalidValueException {
        super(null);
        this.setName(name);
        this.description = description;
        this.hours = hours;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.courseType = courseType;
    }

    public Course(Long id, String name, String description, int hours, Date beginDate, Date endDate,CourseType courseType) throws InvalidValueException {
        super(id);
        this.setName(name);
        this.description = description;
        this.hours = hours;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.courseType = courseType;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) throws InvalidValueException {
        if(name != null && name.length() > 1){
            this.name = name;
        } else {
            throw new InvalidValueException("Kursname muss mindestens 2 Zeichen haben. ");
        }
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getHours() {
        return hours;
    }
    public void setHours(int hours) {
        this.hours = hours;
    }
    public Date getBeginDate() {
        return beginDate;
    }
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public CourseType getCourseType() {
        return courseType;
    }
    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }
    @Override
    public String toString() {
        return "Course [id=" + this.getId() + ", name=" + name + ", beginDate=" + beginDate + ", courseType=" + courseType + ", description=" + description
                + ", endDate=" + endDate + ", hours=" + hours + "]";
    }

    

    
}
