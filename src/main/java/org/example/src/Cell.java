package org.example.src;

public class Cell {
    private int x = 0;      //position
    private int y = 0;
    private String graphic = "[ ]";
    private int status = 0; //1 - Number  2 - Incorrect Flag  3 - Bomb  4 - Flagged Bomb
    private int value = 0;  //how many bombs neighbouring
    private boolean isCovered = true;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setStatus(int i){
        status = i;
    }

    public int getStatus(){
        return status;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void discoverCell(){
        this.isCovered = false;
    }

    public boolean getIsCovered(){
        return isCovered;
    }

    public String display(){
        if(isCovered){
            if(status==2||status==4) {
                graphic = "\033[32m[F]\033[0m";
                return graphic;
            }
            else{
                graphic = "[ ]";
                return graphic;
            }
        }
        else{
            switch(status){
                case 1: {
                    graphic = "[" + value + "]";
                    return graphic;
                }
                case 3: {
                    graphic = "\033[31m[*]\033[0m";
                    return graphic;
                }
                default: {
                    graphic = "[ ]";
                    return graphic;
                }
            }
        }
    }
}
