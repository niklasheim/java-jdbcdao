package com.htlimst.mycoursesystem.ui;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.htlimst.mycoursesystem.dataaccess.MyCourseRepository;
import com.htlimst.mycoursesystem.domain.Course;

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

            switch (input) {
                case 1:
                    System.out.println("Eingeben");
                    break;
                case 2:
                    System.out.println("Alle Kurse ausgeben");
                    showAllCourses();
                    break;
                case 3:
                    System.out.println("Kurs mit ID ausgeben");
                    System.out.printf("ID eingeben: ");
                    id = this.scan.nextLong();
                    showCourseByID(id);
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
        System.out.println("\\ (9) Ende                                   /");
    }

    private void inputError(){
        System.out.println("Bitte nur Zahlen aus dem Menue auswaehlen!");
    }
}
