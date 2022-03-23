package com.htlimst.mycoursesystem.ui;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.htlimst.mycoursesystem.dataaccess.MyCourseRepository;
import com.htlimst.mycoursesystem.dataaccess.MyStudentRepository;
import com.htlimst.mycoursesystem.domain.Course;
import com.htlimst.mycoursesystem.domain.CourseType;
import com.htlimst.mycoursesystem.domain.Student;

public class Cli {
    
    Scanner scan;
    MyCourseRepository repo;
    MyStudentRepository studentRepository;

    public Cli(MyCourseRepository repo, MyStudentRepository studentRepository){
        this.scan = new Scanner(System.in);
        this.studentRepository = studentRepository;
        this.repo = repo;
    }

    public void start(){
        int input = 0;
        String date, date2;
        Long id = null;

        while (input != 9) {
            showMenue();
            input = this.scan.nextInt();
            scan.nextLine();

            switch (input) {
                case 1:
                    System.out.println("Kursdaten eingeben: ");
                    insertCourse();
                    break;
                case 2:
                    System.out.println("Alle Kurse ausgeben");
                    showAllCourses();
                    break;
                case 3:
                    System.out.println("Kurs mit ID ausgeben");
                    System.out.printf("ID eingeben: ");
                    id = this.scan.nextLong();
                    scan.nextLine();
                    showCourseByID(id);
                    break;
                case 4:
                    System.out.println("Kurs mit ID akualisieren");
                    System.out.printf("ID eingeben: ");
                    id = this.scan.nextLong();
                    scan.nextLine();
                    updateCourseDetails(id);
                    break;
                case 5:
                    System.out.println("Kurs mit ID leoschen");
                    System.out.printf("ID eingeben: ");
                    id = this.scan.nextLong();
                    scan.nextLine();
                    deleteCourseByID(id);
                    break;
                case 6:
                    System.out.println("Student eingeben: ");
                    insertStudent();
                    break;
                case 7:
                    System.out.println("Alle Studenten ausgeben");
                    showAllStudents();
                    break;
                case 8:
                    System.out.println("Student mit ID ausgeben");
                    System.out.printf("ID eingeben: ");
                    id = this.scan.nextLong();
                    scan.nextLine();
                    showStudentByID(id);
                    break;
                case 9:
                    System.out.println("Student mit GB ausgeben");
                    System.out.printf("GB eingeben: ");
                    date = this.scan.nextLine();
                    showStudentByGB(date);
                    break;
                case 10:
                    System.out.println("Student mit GB zwischen ausgeben");
                    System.out.printf("GB Start eingeben: ");
                    date = this.scan.nextLine();
                    System.out.printf("GB Ende eingeben: ");
                    date2 = this.scan.nextLine();
                    showStudentByGBBetween(date, date2);
                    break;
                case 20:
                    System.out.println("Auf Wiedersehen!");
                    break;
                default:
                    inputError();
                    break;
            }
        }
    }

    private void showStudentByGBBetween(String date, String date2) {
        List<Student> students = studentRepository.findAllWithBirthDatesBetween(Date.valueOf(date), Date.valueOf(date2));

        if(students.size() > 0){
            for (Student student : students) {
                System.out.println(student);
            }
        } else {
            System.out.println("Studentenliste leer.");
        }
    }

    private void showStudentByGB(String date) {
        List<Student> students = studentRepository.findAllByBirthDate(Date.valueOf(date));

        if(students.size() > 0){
            for (Student student : students) {
                System.out.println(student);
            }
        } else {
            System.out.println("Studentenliste leer.");
        }
    }

    private void showStudentByID(Long id) {
        Optional<Student> student = studentRepository.getById(id);

        if(student.isPresent()){
            System.out.println(student);
        } else {
            System.out.println("Student existiert nicht.");
        }
    }

    private void showAllStudents() {
        List<Student> list = null;

        list = studentRepository.getAll();

        if(list.size() > 0){
            for (Student student : list) {
                System.out.println(student);
            }
        } else {
            System.out.println("Studentenliste leer.");
        }
    }

    private void insertStudent() {
        try {
            String name, birthdate;

                System.out.println("Zu aendernde Daten eingeben: ");
                System.out.println("Name: ");
                name = scan.nextLine();
                System.out.println("GB: ");
                birthdate = scan.nextLine();

                Optional<Student> optionalStudentUpdated = studentRepository.insert(
                    new Student(null, name, Date.valueOf(birthdate))
                );

                if(optionalStudentUpdated.isPresent()){
                    System.out.println("Student erstellt: " + optionalStudentUpdated);
                } else {
                    System.out.println("Student wurde nicht erstellt.");
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertCourse() {
        try {
            String name, description, hours, dateFrom, dateTo, courseType;

                System.out.println("Zu aendernde Daten eingeben: ");
                System.out.println("Name: ");
                name = scan.nextLine();
                System.out.println("Beschreibung: ");
                description = scan.nextLine();
                System.out.println("Stunden: ");
                hours = scan.nextLine();
                System.out.println("Von: ");
                dateFrom = scan.nextLine();
                System.out.println("Bis: ");
                dateTo = scan.nextLine();
                System.out.println("Typ: ");
                courseType = scan.nextLine();

                Optional<Course> optionalCourseUpdated = repo.insert(
                    new Course(null, name, description, Integer.parseInt(hours), Date.valueOf(dateFrom), Date.valueOf(dateTo), CourseType.valueOf(courseType))
                );

                if(optionalCourseUpdated.isPresent()){
                    System.out.println("Kurs erstellt: " + optionalCourseUpdated);
                } else {
                    System.out.println("Kurs wurde nicht erstellt.");
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteCourseByID(Long id) {
        try {
            repo.deleteById(id);
            System.out.println("Kurs wurde geloescht.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateCourseDetails(Long id) {
        try {
            Optional<Course> courseOptional = repo.getById(id);
            if(courseOptional.isEmpty()){
                System.out.println("Kurs mit ID nicht gefunden.");
            } else {
                Course course = courseOptional.get();
                System.out.println("Aenderungen fur folgenden Kurs: ");
                System.out.println(course.toString());

                String name, description, hours, dateFrom, dateTo, courseType;

                System.out.println("Zu aendernde Daten eingeben: ");
                System.out.println("Name: ");
                name = scan.nextLine();
                System.out.println("Beschreibung: ");
                description = scan.nextLine();
                System.out.println("Stunden: ");
                hours = scan.nextLine();
                System.out.println("Von: ");
                dateFrom = scan.nextLine();
                System.out.println("Bis: ");
                dateTo = scan.nextLine();
                System.out.println("Typ: ");
                courseType = scan.nextLine();

                Optional<Course> optionalCourseUpdated = repo.update(
                    new Course(
                        course.getId(), 
                        name.equals("") ? course.getName() : name, 
                        description.equals("") ? course.getDescription() : description,
                        hours.equals("") ? course.getHours() : Integer.parseInt(hours),
                        dateFrom.equals("") ? course.getBeginDate() : Date.valueOf(dateFrom),
                        dateTo.equals("") ? course.getEndDate() : Date.valueOf(dateTo),
                        courseType.equals("") ? course.getCourseType() : CourseType.valueOf(courseType)
                        )
                );

                if(optionalCourseUpdated.isPresent()){
                    System.out.println("Kurs aktualisiert: " + optionalCourseUpdated);
                } else {
                    System.out.println("Kurs wurde nicht aktualisiert.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAllCourses(){
        List<Course> list = null;

        list = repo.getAll();

        if(list.size() > 0){
            for (Course course : list) {
                System.out.println(course);
            }
        } else {
            System.out.println("Kursliste leer.");
        }
    }

    private void showCourseByID(Long id){

        Optional<Course> course = repo.getById(id);

        if(course.isPresent()){
            System.out.println(course);
        } else {
            System.out.println("Kurs existiert nicht.");
        }
    }

    public void showMenue(){
        System.out.println("/---------------Kursmanagement---------------\\");
        System.out.println("| (1) Kurs eingeben, (2) Alle Kurse anzeigen |");
        System.out.println("| (3) Kurs ausgeben, (4) Kurs bearbeiten     |");
        System.out.println("| (5) Kurs leoschen,                         |");
        System.out.println("| (6) Student eingeben, (7) Alle Studenten   |");
        System.out.println("| (8) Student ausgeben, (9) Student mit GB   |");
        System.out.println("| (10) Student mit GB zwischen               |");
        System.out.println("\\ (20) Ende                                  /");
    }

    private void inputError(){
        System.out.println("Bitte nur Zahlen aus dem Menue auswaehlen!");
    }
}
