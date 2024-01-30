package org.example.src;

public class AsciGUI {
    public void printMainMenu(){
        System.out.println("1. New game");
        System.out.println("2. How to play");
        System.out.println("3. Quit");
        System.out.print("Enter a digit: ");
    }

    public void printLevels(){
        System.out.println("Choose a level:");
        System.out.println("\n1. Beginner");
        System.out.println("2. Intermediate");
        System.out.println("3. Expert");
        System.out.println("4. Custom");
        System.out.print("Enter a digit: ");
    }

    public void printCellOptions(){
        System.out.println("What to do with the cell?");
        System.out.println("\n1. Discover");
        System.out.println("2. Toggle Flag");
        System.out.println("3. Back");
        System.out.print("Enter a digit: ");
    }
}
