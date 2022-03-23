package com.htlimst.mycoursesystem.domain;

import java.sql.Date;

public class Student extends BaseEntity {
    
    private String name;
    private Date birthdate;

    public Student(String name, Date birthdate) throws InvalidValueException {
        super(null);
        this.setName(name);
        this.birthdate = birthdate;
    }

    public Student(Long id, String name, Date birthdate) throws InvalidValueException {
        super(id);
        this.setName(name);
        this.birthdate = birthdate;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) throws InvalidValueException {
        if(name != null && name.length() > 1){
            this.name = name;
        } else {
            throw new InvalidValueException("Studentname muss mindestens 2 Zeichen haben. ");
        }
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "Student [birthdate=" + birthdate + ", name=" + name + "]";
    }
    
}
