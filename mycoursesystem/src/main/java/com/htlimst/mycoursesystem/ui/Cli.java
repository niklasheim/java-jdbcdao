package com.htlimst.mycoursesystem.ui;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.htlimst.mycoursesystem.dataaccess.MyCourseRepository;
import com.htlimst.mycoursesystem.domain.Course;
import com.htlimst.mycoursesystem.domain.CourseType;

public class Cli {
    
    Scanner scan;
    MyCourseRepository repo;

    public Cli(MyCourseRepository repo){
        this.scan = new Scanner(System.in);
        this.repo = repo;
    }

    public void start(){
        int input = 0;
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
                case 9:
                    System.out.println("Auf Wiedersehen!");
                    break;
                default:
                    inputError();
                    break;
            }
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
        System.out.println("| (5) Kurs leoschen, (4) Kurs bearbeiten     |");
        System.out.println("\\ (9) Ende                                   /");
    }

    private void inputError(){
        System.out.println("Bitte nur Zahlen aus dem Menue auswaehlen!");
    }
}
