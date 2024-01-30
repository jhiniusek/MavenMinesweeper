package org.example.src;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Minesweeper!");
        AsciGUI gui = new AsciGUI();
        GameLogic game = new GameLogic();
        Map map = new Map();
        int width=0;
        int height=0;
        int bombs=0;
        int win=0;
        int x;
        int y;
        gui.printMainMenu();
        Scanner reader = new Scanner(System.in);
        int input;
        boolean run = true;
        boolean play = false;
        while(run){
            input = game.checkInt(reader.next());
            switch(input){
                case 1: { //play
                    gui.printLevels();
                    boolean level = true;
                    boolean firstMove = true;
                    while(level){
                        input = game.checkInt(reader.next());
                        switch(input) {
                            case 1:{
                                width = 9; //x
                                height = 9; //y
                                bombs = 10;
                                map = game.startGame(width,height,bombs);
                                play = true;
                                level = false;
                                break;
                            }
                            case 2:{
                                width = 16; //x
                                height = 16; //y
                                bombs = 40;
                                map = game.startGame(width,height,bombs);
                                play = true;
                                level = false;
                                break;
                            }
                            case 3:{
                                width = 30; //x
                                height = 16; //y
                                bombs = 99;
                                map = game.startGame(width,height,bombs);
                                play = true;
                                level = false;
                                break;
                            }
                            case 4:{

                                System.out.print("How many colums? (min 5;max 80)");
                                width = game.checkInt(reader.next());
                                System.out.print("How many rows? (min 5;max 80)");
                                height = game.checkInt(reader.next());
                                System.out.print("How many bombs?");
                                bombs = game.checkInt(reader.next());
                                map = game.startCustomGame(width,height,bombs);
                                firstMove = map.isPlayable();
                                play = map.isPlayable();
                                level = false;
                                break;
                            }
                            default:{
                                System.out.println("Incorrect input.");
                                break;
                            }
                        }
                    }
                    while(firstMove){
                        System.out.print("Enter a column number: ");
                        x = game.checkInt(reader.next());
                        System.out.print("Enter a row number: ");
                        y = game.checkInt(reader.next());
                        if(x>0&&y>0&&x<=width&&y<=height){
                            Cell userCell = map.chooseCell(x,y);
                            map.spawnBombs(bombs,userCell);
                            System.out.println("BOMBS LEFT: " + map.getFlagCounter());
                            map.printMap();
                            win = map.checkWin();
                            switch (win) {
                                case 1: {
                                    System.out.println();
                                    map.printMap();
                                    System.out.println("You won!");
                                    play = false;
                                    break;
                                }
                                case 2: {
                                    System.out.println("You lost!");
                                    play = false;
                                    break;
                                }
                                default: {
                                    break;
                                }
                            }
                            firstMove = false;
                        }
                        else{
                            System.out.println("Incorrect cell coordinates.");
                        }
                    }
                    while(play){
                        System.out.print("Enter a column number: ");
                        x = game.checkInt(reader.next());
                        System.out.print("Enter a row number: ");
                        y = game.checkInt(reader.next());
                        if(x>0&&y>0&&x<=width&&y<=height){
                            Cell userCell = map.chooseCell(x,y);
                            gui.printCellOptions();
                            input = game.checkInt(reader.next());
                            switch (input){
                                case 1: {
                                    map.guess(userCell);
                                    System.out.println("BOMBS LEFT: " + map.getFlagCounter());
                                    map.printMap();
                                    win = map.checkWin();
                                    switch (win){
                                        case 1:{
                                            System.out.println();
                                            map.printMap();
                                            System.out.println("You won!");
                                            play = false;
                                            break;
                                        }
                                        case 2:{
                                            System.out.println("You lost!");
                                            play = false;
                                            break;
                                        }
                                        default:{
                                            break;
                                        }
                                    }
                                    break;
                                }
                                case 2: {
                                    map.flagCell(userCell);
                                    System.out.println("BOMBS LEFT: " + map.getFlagCounter());
                                    map.printMap();
                                    break;
                                }
                                case 3: {
                                    break;
                                }
                                default: {
                                    System.out.println("Incorrect input.");
                                    break;
                                }
                            }
                        }
                        else{
                            System.out.println("Incorrect cell coordinates.");
                        }
                        //play = false;
                    }
                    gui.printMainMenu();
                    break;
                }
                case 2: { //howToPlay
                    System.out.println("wip\n");
                    break;
                }
                case 3: { //quit
                    run = false;
                    break;
                }
                default: {
                    System.out.println("Incorrect input.");
                    break;
                }
            }
        }
    }
}