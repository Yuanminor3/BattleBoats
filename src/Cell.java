// Written by Yuan Jiang, jian0655
// Written by Enkhjin, boldb002
public class Cell {
    public int row;
    public int col;
    public char status;

    public Cell (int row, int col, char status){
        this.row = row;
        this.col = col;
        this.status = status;
    }

    public char getStatus(){
        return status;
    }

    public void set_status(char c){
        status = c;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public void setRow(int r){
        row = r;
    }

    public void setCol(int c){
        col = c;
    }




}
