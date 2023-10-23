// Written by Yuan Jiang, jian0655
// Written by Enkhjin, boldb002
public class Board {
    public static boolean otherCells(Cell[][] c, int row, int column, boolean boatForm, int i, int j, int size){
                            //Check if the position of the randomly generated ship is a duplicate of the previous one
        boolean k = true;
        if (boatForm){
            if (j+size>column || i+size>row || c[i][j].status != '-'){
                return false;
            }
            for (int m=0;m<size;m++){
                if (c[i][j+m].status != '-'){
                    k = false;
                    break;
                }
            }
        }else{
            if (j+size>column || i+size>row || c[i][j].status != '-'){
                return false;
            }
            for (int m=0;m<size;m++){
                if (c[i+m][j].status != '-'){
                    k = false;
                    break;
                }
            }
        }
        return k;
    }

    public static void creatBoats(Cell[][] c,Boat[] b, int boatsLength,int row,int column,boolean boatForm,int p){
        int i = (int)(Math.random()*row);                   //Randomly generate a boat with non-repeating position
        int j = (int)(Math.random()*column);
        while (!otherCells(c, row, column, boatForm, i, j, boatsLength)){
            i = (int)(Math.random()*row);
            j = (int)(Math.random()*column);
        }
        b[p] = new Boat(boatsLength,boatForm);
        if (boatForm){
            for (int m=0;m<boatsLength;m++){
                c[i][j+m].status = 'B';
                b[p].getPosition()[m] = c[i][j+m];
            }
        }else{
            for (int m=0;m<boatsLength;m++){
                c[i+m][j].status = 'B';
                b[p].getPosition()[m] = c[i+m][j];
            }
        }

    }


    public static boolean checkBoatsSink(Cell[] c){
        boolean sink = true;            //Check if the boats is sinking
        for (int i=0;i<c.length;i++){
            if (c[i].status != 'H'){
                sink = false;
                break;
            }
        }return sink;
    }

    public int row;
    public int column;
    public int totalShots = 0;
    public int turns = 0;
    public int shipsRemaining;
    public Cell[][] cells;
    public Boat[] boats;

    public Board(int row, int column){          //constructor
        this.row = row;
        this.column = column;
        cells = new Cell[row][column];
        for (int i=0;i<row;i++){
            for (int j=0;j<column;j++){
                cells[i][j] = new Cell(i+1, j+1, '-');
            }
        }
        if (row == 3 && column == 3){           //Generate different number of boats depending on the size of the board
            boats = new Boat[1];
            placeBoats();
            shipsRemaining = 1;
        }
        else if(row == 4 || column ==4){
            boats = new Boat[2];
            placeBoats();
            shipsRemaining = 2;
        }
        else if (row>4 && row<=6 || column>4 && column<=6){
            boats = new Boat[3];
            placeBoats();
            shipsRemaining = 3;
        }
        else if (row>6 && row<=8 || column>6 && column<=8){
            boats = new Boat[4];
            placeBoats();
            shipsRemaining =4;
        }
        else if (row>8 && row<=10 || column>8 && column<=10){
            boats = new Boat[5];
            placeBoats();
            shipsRemaining = 5;
        }
    }

    public void placeBoats(){           //Used to randomly generate all boats
        int f;
        boolean boatForm;
        int boatsLength = boats.length;
        switch (boatsLength){
            case 1: f = (int)(Math.random()*2);        //Randomly generate boat placement directions and create boats of different lengths according to different lengths
                    boatForm = (f==0);
                    creatBoats(cells,boats,2,row,column,boatForm,0);
                    break;
            case 2: f = (int)(Math.random()*2);
                    boatForm = (f==0);
                    creatBoats(cells,boats,2,row,column,boatForm,0);

                    f = (int)(Math.random()*2);
                    boatForm = (f==0);
                    creatBoats(cells,boats,3,row,column,boatForm,1);
                    break;
            case 3: f = (int)(Math.random()*2);
                    boatForm = (f==0);
                    creatBoats(cells,boats,2,row,column,boatForm,0);

                    f = (int)(Math.random()*2);
                    boatForm = (f==0);
                    creatBoats(cells,boats,3,row,column,boatForm,1);

                    f = (int)(Math.random()*2);
                    boatForm = (f==0);
                    creatBoats(cells,boats,3,row,column,boatForm,2);
                    break;
            case 4: f = (int)(Math.random()*2);
                    boatForm = (f==0);
                    creatBoats(cells,boats,2,row,column,boatForm,0);

                    f = (int)(Math.random()*2);
                    boatForm = (f==0);
                    creatBoats(cells,boats,3,row,column,boatForm,1);

                    f = (int)(Math.random()*2);
                    boatForm = (f==0);
                    creatBoats(cells,boats,3,row,column,boatForm,2);

                    f = (int)(Math.random()*2);
                    boatForm = (f==0);
                    creatBoats(cells,boats,4,row,column,boatForm,3);
                    break;
            default:f = (int)(Math.random()*2);
                    boatForm = (f==0);
                    creatBoats(cells,boats,2,row,column,boatForm,0);

                    f = (int)(Math.random()*2);
                    boatForm = (f==0);
                    creatBoats(cells,boats,3,row,column,boatForm,1);

                    f = (int)(Math.random()*2);
                    boatForm = (f==0);
                    creatBoats(cells,boats,3,row,column,boatForm,2);

                    f = (int)(Math.random()*2);
                    boatForm = (f==0);
                    creatBoats(cells,boats,4,row,column,boatForm,3);

                    f = (int)(Math.random()*2);
                    boatForm = (f==0);
                    creatBoats(cells,boats,5,row,column,boatForm,4);
        }
    }

    public void fireCell(int x,int y){    //Transfer the status of the fired boat to 'H' otherwise 'M'
        if (cells[x][y].status == '-'){
            cells[x][y].status = 'M';
        }
        else if (cells[x][y].status == 'B'){
            cells[x][y].status = 'H';
        }

    }

    public void fire(int x,int y){      //Hit all boats in range depending on the different location
        if (x==0){
            if (y==0){
                fireCell(x,y);fireCell(x+1,y);fireCell(x,y+1);fireCell(x+1,y+1);
            }else if(y==column-1){
                fireCell(x,y);fireCell(x,y-1);fireCell(x+1,y-1);fireCell(x+1,y);
            }else{
                fireCell(x,y);fireCell(x+1,y);fireCell(x,y+1);fireCell(x+1,y+1);
                fireCell(x+1,y-1);fireCell(x,y-1);
            }
        }
        else if(x<row-1){
            if (y==0){
                fireCell(x,y);fireCell(x+1,y);fireCell(x,y+1);fireCell(x+1,y+1);
                fireCell(x-1,y);fireCell(x-1,y+1);
            }else if(y==column-1){
                fireCell(x,y);fireCell(x,y-1);fireCell(x+1,y-1);fireCell(x+1,y);
                fireCell(x-1,y);fireCell(x-1,y-1);
            }else{
                fireCell(x-1,y-1);fireCell(x-1,y);fireCell(x-1,y+1);
                fireCell(x,y-1);fireCell(x,y);fireCell(x,y+1);
                fireCell(x+1,y-1);fireCell(x+1,y);fireCell(x+1,y+1);
            }
        }
        else{
            if (y==0){
                fireCell(x,y);fireCell(x-1,y);fireCell(x-1,y+1);fireCell(x,y+1);
            }else if(y==column-1){
                fireCell(x,y);fireCell(x,y-1);fireCell(x-1,y-1);fireCell(x-1,y);
            }else{
                fireCell(x,y);fireCell(x-1,y);fireCell(x-1,y+1);fireCell(x,y+1);
                fireCell(x,y-1);fireCell(x-1,y-1);
            }
        }
    }

    public boolean missile(int x, int y){       //Used to determine if this position can be used to launch a missile
        if (x<row && y<column){
            fire(x, y);
            return true;
        }else{
            return false;
        }
    }


    public String printNoMode(){        //Show the board in a mode other than Debug mode
        String s = "\n";
        for (int m=0;m<row;m++){
            for (int n=0;n<column;n++){
                if(cells[m][n].status=='M'||cells[m][n].status=='H'){
                    s += cells[m][n].status +",";
                }else{
                    s += '*'+",";
                }
            }
            s += "\n";
        }
        return s;
    }

    public String print(){              //Show the board in a debug mode
        String s = "\n";
        String s1 = "\n";
        String s2 = "";
        for (int m=0;m<row;m++){
            for (int n=0;n<column;n++){
                if(cells[m][n].status=='B'||cells[m][n].status=='H'){
                    int p=-1;
                    for(int i=0;i<boats.length;i++){
                        for(int j=0;j<boats[i].getPosition().length;j++){
                            if((cells[m][n].getRow()==boats[i].getPosition()[j].getRow())&&(cells[m][n].getCol()==boats[i].getPosition()[j].getCol())){
                                p = i+1;
                            }
                        }
                    }
                    s += cells[m][n].status+String.valueOf(p)+",";
                }else{
                    s += cells[m][n].status+",";
                }
            }
            s += "\n";
        }
        for(int i=0;i<boats.length;i++){    // Show all boats position
            s1+="Boat"+(i+1)+": ";
            for(int j=0;j<boats[i].getPosition().length;j++){
                s1 += "("+(boats[i].getPosition()[j].getRow()-1)+","+(boats[i].getPosition()[j].getCol()-1)+")";
            }
            s1 += "\n";
        }
        s2 += "Remaining ships: "+shipsRemaining;   //Show the number of remaining ships
        return s+s1+s2;
    }

    public String display(int x,int y){     //Execute different commands
        turns += 1;
        if (x>row-1||y>column-1){
            return "Penalty";
        }else{
            totalShots += 1;
            if (cells[x][y].status=='-'){
                cells[x][y].status='M';
                return "miss";
            }else if(cells[x][y].status=='H'||cells[x][y].status=='M'){
                return "hit again! Penalty!";
            }else{
                cells[x][y].status='H';
                int posi=-1;
                for (int i=0;i<boats.length;i++){
                    for (int j=0;j<boats[i].getPosition().length;j++){
                        if (boats[i].getPosition()[j]==cells[x][y]){
                            posi = i;
                        }
                    }
                }
                if (checkBoatsSink(boats[posi].getPosition())){
                    shipsRemaining--;
                    return "sunk";
                }else{
                    return "hit";
                }
            }
        }
    }

    public int drone(int direction,int index){      //  Detects the number of boats in one of the rows or columns
        int number = 0;
        if (direction==1){
            for (int i=0;i<column;i++){
                if (cells[index][i].status=='B'||cells[index][i].status=='H'){
                    number += 1;
                }
            }
        }else{
            for (int j=0;j<row;j++){
                if (cells[j][index].status=='B'||cells[j][index].status=='H'){
                    number += 1;
                }
            }
        }
        return number;
    }
}


