// Written by Yuan Jiang, jian0655
// Written by Enkhjin, boldb002
import java.util.Scanner;
public class Game {



    public static void main(String[] args){
        int row = 0;
        int column = 0;
        int mode = 0;
        System.out.println("Please input the number of rows between 3 and 10(inclusive): ");
        Scanner myRow = new Scanner(System.in);
        while (myRow.hasNextInt()){             //input a board row value
            row = myRow.nextInt();
            if (3 <= row && row <= 10){
                break;
            }
            else{
                System.out.println("Invalid input! Please input valid number between 3 and 10(inclusive): ");
            }
        }

        System.out.println("Please input the number of columns between 3 and 10(inclusive): ");
        Scanner myColumn = new Scanner(System.in);
        while (myColumn.hasNextInt()){
            column = myColumn.nextInt();            //input a board column value
            if (3 <= column && column <= 10){
                break;
            }
            else{
                System.out.println("Invalid input! Please input valid number between 3 and 10(inclusive): ");
            }
        }

        System.out.println("Do you want to open Debug Mode? input 1(Yes) or 2(No)?");
        Scanner myMode = new Scanner(System.in);
        while (myMode.hasNextInt()){
            mode = myMode.nextInt();                    //Ask if open debug mode
            if(mode!=1 && mode!=2){
                System.out.println("Invalid input! Please input valid number 1(Yes) or 2(No): ");
            }else{
                break;
            }
        }


        Board board = new Board(row, column);           // Creats a new board
        if(mode==1){
            System.out.println(board.print());
        }else{
            System.out.println(board.printNoMode());
        }
        boolean drone = true;           //Used to determine if the drone power is no longer available

        System.out.println("Game is beginning!");
        System.out.println("Input x and y to hit boat! Or input powers!(drone or missile)");
        Scanner myHit = new Scanner(System.in);
        while (myHit.hasNextLine()){
            String hit = myHit.nextLine();
            if (hit.equals("drone")){           //Handling of the drone power
                if (!drone){
                    System.out.println("Drone has been used the max amount of times.");
                    System.out.println("Input x and y to hit boat! Or input powers!(drone or missile)");
                }
                else{Scanner myDrone = new Scanner(System.in);
                System.out.println("Would you like to scan a row or column?  Type in r for row or c for column");
                String t="";
                while (myDrone.hasNextLine()){
                    t = myDrone.nextLine();
                    if (t.equals("r")||t.equals("c")){
                        break;
                    }else{
                        System.out.println("Invalid input. Please type in r for row or c for column");
                    }
                }
                int direction;
                if(t.equals("r")){
                    direction = 1;
                }else{
                    direction = 2;
                }
                Scanner whichRow = new Scanner(System.in);
                System.out.println(" Which row or column would you like to scan? Input an integer");
                int n=-1;
                while (whichRow.hasNextInt()){
                    n = whichRow.nextInt();
                    if(n>row||n>column||n<=0){
                        System.out.println("Invalid Input. Please type in a number within the boundaries of the board.");
                    }else{
                        break;
                    }
                }
                board.turns++;
                System.out.println("Turn " + board.turns+": Drone!");
                System.out.println("\n"+"Targets:  "+board.drone(direction,n-1));   //If the user input has no problem, then execute drone method
                    if(mode==1){
                        System.out.println(board.print());
                    }else{
                        System.out.println(board.printNoMode());
                    }
                drone = false;      // the user cannot use drone power any more
                    System.out.println("Input x and y to hit boat! Or input powers!(drone or missile)");
            }}else if(hit.equals("missile")){                   //Handling of the missile power
                int m=-1,n=-1;
                System.out.println("Where would you like to launch your missile(input two integer with a space)");
                Scanner myMissile = new Scanner(System.in);
                while (myMissile.hasNextLine()){
                    String[] bounds = myMissile.nextLine().split(" ");
                    m = Integer.valueOf(bounds[0]);
                    n = Integer.valueOf(bounds[1]);
                    if (board.missile(m,n)){
                        break;
                    }else{
                        System.out.println("Invalid Input");
                    }
                }
                board.fire(m,n);        //If the user input has no problem, then execute missile method
                board.turns++;
                System.out.println("Turn " + board.turns+": fire!");
                for(int i=0;i<board.boats.length;i++){      //Check if all ships are sunk to determine if the game is over
                    boolean sink = true;
                    for(int j=0;j<board.boats[i].getPosition().length;j++){
                        if (board.boats[i].getPosition()[j].status=='B'){
                            sink = false;
                            break;
                        }
                    }
                    if (sink){
                        board.shipsRemaining--;
                    }
                }
                if(board.shipsRemaining>0){
                    if(mode==1){
                        System.out.println(board.print());
                    }else{
                        System.out.println(board.printNoMode());
                    }
                    System.out.println("Input x and y to hit boat! Or input powers!(drone or missile)");
                }else{                                                      //if all ships are sunk, the game is over
                    System.out.println("You fired all boats! Game finish!");
                    if(mode==1){
                        System.out.println(board.print());
                    }else{
                        System.out.println(board.printNoMode());
                    }
                    System.out.println("Turn " + board.turns+" You fired all boats! Game finish!");
                    break;
                }
            }else if(hit.matches("\\d \\d{2}")||hit.matches("\\d \\d")){        //Handling of the row and column the user inputs
                int k;
                int l;
            String[] c = hit.split(" ");
            k = Integer.parseInt(c[0]);
            l = Integer.parseInt(c[1]);
            String outCome = board.display(k,l);
            if(board.shipsRemaining>0) {
                if (outCome.equals("sunk")) {           //the situation "sunk"
                    System.out.println("Turn " + board.turns + ": The user select " + k + "," + l + " and " + outCome);
                } else if (outCome.equals("miss")) {        //the situation "miss"
                    System.out.println("Turn " + board.turns + ": The user select " + k + "," + l + " and " + outCome);
                } else if (outCome.equals("Penalty")) {         //the situation "out of bounds"
                    System.out.println("Turn " + board.turns + ": The user select " + k + "," + l + " which is out of bounds.  Penalty.");
                    board.turns += 1;
                    System.out.println("Turn " + board.turns + ": skipped");
                } else if (outCome.equals("hit again! Penalty!")) {         //the situation "hit again"
                    System.out.println("Turn " + board.turns + ": The user select " + k + "," + l + " again and is penalized by losing turn " + board.turns);
                    board.turns += 1;
                    System.out.println("Turn " + board.turns + ": skipped");
                } else if (outCome.equals("hit")) {         //the situation "hit"
                    System.out.println("Turn " + board.turns + ": The user select " + k + "," + l + " and " + outCome);
                }
                if (mode == 1) {
                    System.out.println(board.print());
                }else{
                    System.out.println(board.printNoMode());
                }
                System.out.println("Input x and y to hit boat! Or input powers!(drone or missile)");
            }else{
                System.out.println("Turn " + board.turns+" All boats sunk and Game finish!");
                break;
            }

        }else{
                System.out.println("Input x and y to hit boat! Or input powers!(drone or missile)");
            }
        }

    }
}
