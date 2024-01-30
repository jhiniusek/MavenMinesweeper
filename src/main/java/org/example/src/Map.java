package org.example.src;

import java.util.ArrayList;

public class Map {
    private ArrayList<Cell> map = new ArrayList<Cell>();
    private ArrayList<Cell> toDecideFate = new ArrayList<Cell>();
    private int width;
    private int height;
    private int bombs;
    private int flagCounter;
    private boolean isPlayable = true;

    public boolean isPlayable() {
        return isPlayable;
    }

    public int getFlagCounter() {
        return flagCounter;
    }

    public void setPlayable(boolean playable) {
        isPlayable = playable;
    }

    //JUNIT TEST PURPOSE
    public ArrayList<Cell> getMap() {
        return map;
    }

    public ArrayList<Cell> getToDecideFate() {
        return toDecideFate;
    }

    public void generateMap(int width, int height){
        this.width = width;
        this.height = height;
        for(int x = 0; x < height; x++){
            for(int y = 0; y < width; y++){
                Cell newCell = new Cell(x+1, y+1);
                map.add(newCell);
                toDecideFate.add(newCell);
            }
        }
    }

    public void printMap(){
        int rowCounter = 0;
        for(int x = 0; x < width*height; x++){
            System.out.print(map.get(x).display());
            rowCounter++;
            if(rowCounter==width){
                System.out.print("\n");
                rowCounter=0;
            }
        }
    }

    public Cell chooseCell(int x, int y){
        for(Cell cell : map) {
            if (cell.getX() == y && cell.getY() == x) {
                return cell;
            }
        }
        return map.getFirst();
    }
    public ArrayList<Cell> getNeighbours(Cell active){
        ArrayList<Cell> neighbours = new ArrayList<Cell>();
        int x = active.getX();
        int y = active.getY();
        for(Cell cell : map){
            if((cell.getX()==x-1||cell.getX()==x||cell.getX()==x+1)&&(cell.getY()==y-1||cell.getY()==y||cell.getY()==y+1)){
                neighbours.add(cell);
            }
        }
        return neighbours;
    }

    public ArrayList<Cell> getUnrevealedNeighbours(Cell active){
        ArrayList<Cell> neighbours = new ArrayList<Cell>();
        int x = active.getX();
        int y = active.getY();
        for(Cell cell : map){
            if((cell.getX()==x-1||cell.getX()==x||cell.getX()==x+1)&&
                    (cell.getY()==y-1||cell.getY()==y||cell.getY()==y+1)&&
                    (cell.getIsCovered())&&
                    ((cell.getStatus()!=4)||(cell.getStatus()!=2))){
                neighbours.add(cell);
            }
        }
        return neighbours;
    }

    public void revealNeighbours(ArrayList<Cell> neighbours){
        for(Cell cell : neighbours){
            guess(cell);
        }
    }

    public void guess(Cell guess){
        if(!guess.getIsCovered()){ }
        else{
            if(guess.getStatus()==1){
                guess.discoverCell();
                if(guess.getValue()==0){
                    ArrayList<Cell> neighbours = getUnrevealedNeighbours(guess);
                    revealNeighbours(neighbours);
                }
            }
            if(guess.getStatus()==2||guess.getStatus()==4){
                System.out.println("Cannot reveal flagged cell!");
            }
            if(guess.getStatus()==3){
                guess.discoverCell();
            }
        }
    }

    public void flagCell(Cell x){
        if(x.getIsCovered()){
            switch(x.getStatus()){
                case 1:{
                    x.setStatus(2);
                    flagCounter--;
                    break;
                }
                case 2:{
                    x.setStatus(1);
                    flagCounter++;
                    break;
                }
                case 3:{
                    x.setStatus(4);
                    flagCounter--;
                    break;
                }
                case 4:{
                    x.setStatus(3);
                    flagCounter++;
                    break;
                }
            }
        }
        else{
            System.out.println("You cannot flag revealed cell!");
        }
    }

    public void spawnNumbers(){
        for(Cell check : map){
            if(check.getStatus() == 3){ }
            else{
                ArrayList<Cell> neighbours = getNeighbours(check);
                int bombcounter = 0;
                for(Cell maybeBomb : neighbours){
                    if(maybeBomb.getStatus()==3){
                        bombcounter++;
                    }
                }
                check.setStatus(1);
                check.setValue(bombcounter);
            }
        }
    }

    public void spawnBombs(int bombs, Cell start){
        this.bombs = bombs;
        this.flagCounter = bombs;
        toDecideFate.remove(start);
        for(int i = 0; i < bombs; i++) {
            int bomb = (int) (Math.random() * toDecideFate.size());
            Cell newBomb = toDecideFate.get(bomb);
            newBomb.setStatus(3);
            toDecideFate.remove(newBomb);
        }
        spawnNumbers();
        guess(start);
    }

    public int checkWin(){
        int checker = 0;
        for(Cell x : map){
            if(x.getStatus() == 1 && !x.getIsCovered()){
                checker++;
            }
        }
        if(checker == width*height - bombs){
            for(Cell x : map){
                if(x.getStatus()==3){
                    flagCell(x);
                }
            }
            return 1;
        }
        for(Cell x : map){
            if(x.getStatus() == 3 && !x.getIsCovered()){
                return 2;
            }
        }
        return 0;
    }
}
