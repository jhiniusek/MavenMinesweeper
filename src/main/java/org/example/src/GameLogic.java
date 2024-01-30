package org.example.src;

public class GameLogic {
    public Map startGame(int width, int height, int bombs){
        Map game = new Map();
        game.generateMap(width, height);
        game.printMap();
        return game;
    }

    public int checkInt(String input){
        try{
            int i = Integer.parseInt(input);
            return i;
        }
        catch (Exception e){
        }
        return 0;
    }
    public Map startCustomGame(int width, int height, int bombs){
        if(width >= 5 && height >= 5 && width <= 80 && height <= 80){
            if(bombs > 0 && bombs <= (width*height)-1){
                Map game = new Map();
                game.generateMap(width, height);
                game.printMap();
                return game;
            }
            else {
                System.out.println("Incorrect number of bombs.");
                System.out.println("Make sure there are at least 1 bomb");
                System.out.println("and at least 1 safe cell.");
            }
        }
        else {
            System.out.println("Incorrect number of rows and columns.");
        }
        Map game = new Map();
        game.setPlayable(false);
        return game;
    }
}
