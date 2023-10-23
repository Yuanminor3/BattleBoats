// Written by Yuan Jiang, jian0655
// Written by Enkhjin, boldb002
public class Boat {
    public int size;
    public boolean form;
    public Cell[] position;                 //Position of the ship on the board with a specific length

    public Boat(int size, boolean form){
        this.size = size;
        this.form = form;
        position = new Cell[size];
    }

    public int getSize(){
        return size;
    }

    public boolean getForm(){
        return form;
    }

    public void setSize(int size){
        this.size = size;
    }

    public void setForm(boolean form){
        this.form = form;
    }

    public Cell[] getPosition() {
        return position;
    }
}

