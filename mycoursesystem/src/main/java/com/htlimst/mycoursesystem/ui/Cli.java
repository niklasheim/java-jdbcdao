package com.htlimst.mycoursesystem.ui;

import java.util.Scanner;

public class Cli {
    
    Scanner scan;

    public Cli(){
        this.scan = new Scanner(System.in);
    }

    public void start(){
        int input = 0;

        while (input != 9) {
            showMenue();
            input = this.scan.nextInt();

            switch (input) {
                case 1:
                    System.out.println("Eingeben");
                    break;
                case 2:
                    System.out.println("Alle Kurse ausgeben");
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

    public void showMenue(){
        System.out.println("/---------------Kursmanagement---------------\\");
        System.out.println("| (1) Kurs eingeben, (2) Alle Kurse anzeigen |");
        System.out.println("\\ (9) Ende                                   /");
    }

    private void inputError(){
        System.out.println("Bitte nur Zahlen aus dem Menue auswaehlen!");
    }
}
