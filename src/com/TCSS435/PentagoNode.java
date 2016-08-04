package com.TCSS435;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Mike on 7/26/16.
 */
public class PentagoNode implements Comparable<PentagoNode>{
    private final char EMPTY = '.', WHITE = 'W', BLACK = 'B', TIE = 'T';
    private final ArrayList<Integer> QUADS = new ArrayList<>(Arrays.asList(1,2,3,4));
    private final String OCCUPIED = "That space isn't available";
    private final int QUAD_DIM = 3;
    private final int BOARD_DIM = 6;
    private char[][] myQ1;
    private char[][] myQ2;
    private char[][] myQ3;
    private char[][] myQ4;
    private ArrayList<PentagoNode> myChildren;
    private int myValue;
    private int myDepth;
    private PentagoNode myParent;

    public PentagoNode(char[][] q1, char[][] q2, char[][] q3, char[][] q4, int theDepth, PentagoNode parent){
        myQ1 = new char[QUAD_DIM][QUAD_DIM];
        myQ2 = new char[QUAD_DIM][QUAD_DIM];
        myQ3 = new char[QUAD_DIM][QUAD_DIM];
        myQ4 = new char[QUAD_DIM][QUAD_DIM];
        for(int i = 0; i < QUAD_DIM; i++){
            for(int j = 0; j < QUAD_DIM; j++){
                myQ1[i][j] = EMPTY;
                myQ2[i][j] = EMPTY;
                myQ3[i][j] = EMPTY;
                myQ4[i][j] = EMPTY;
            }
        }
        myDepth = theDepth;
        myParent = parent;
        myChildren = new ArrayList<>();
    }

    public void addChild(PentagoNode theChild){
        myChildren.add(theChild);
    }

    public int getValue(){
        return myValue;
    }

    public void setDepth(int theDepth){

    }

    public int getDepth(){
        return myDepth;
    }

    public char[][] getQ1() {
        return myQ1;
    }

    public char[][] getQ2() {
        return myQ2;
    }

    public char[][] getQ3() {
        return myQ3;
    }

    public char[][] getQ4() {
        return myQ4;
    }

    /**
     * A method that will update the position of a spot on the board.
     *
     * @param quadrant
     * @param row
     * @param col
     * @param b
     */
    public boolean changeSpace(int quadrant, int row, int col, char b){
        boolean toReturn = false;
        if(quadrant >= 1 && quadrant <=4 && row >= 0 && row <= 2 && col >=0 && col <= 2){
            if(QUADS.contains(quadrant)){
                if(quadrant == 1){
                    if(myQ1[row][col] == EMPTY){
                        myQ1[row][col] = b;
                        toReturn = true;
                    }else {
                        System.out.println(OCCUPIED);
                    }
                } else if(quadrant == 2){
                    if(myQ2[row][col] == EMPTY){
                        myQ2[row][col] = b;
                        toReturn = true;
                    }else {
                        System.out.println(OCCUPIED);
                    }
                } else if(quadrant == 3){
                    if(myQ3[row][col] == EMPTY){
                        myQ3[row][col] = b;
                        toReturn = true;
                    }else {
                        System.out.println(OCCUPIED);
                    }
                } else if(quadrant == 4){
                    if(myQ4[row][col] == EMPTY){
                        myQ4[row][col] = b;
                        toReturn = true;
                    }else {
                        System.out.println(OCCUPIED);
                    }
                }

            }
        }

        return toReturn;
    }

    /**
     * Checks to see if there is a win state in this node.
     * @return
     */
    public boolean winState(){
        boolean win = false;
        char[][] wholeBoard = bringItTogether();

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){

            }
        }

        return win;
    }

    /*
        Helper method for combining the Quads into one 2d Array
     */
    private char[][] bringItTogether(){
        char[][] wholeBoard = new char[BOARD_DIM][BOARD_DIM];
        for(int i = 0; i < BOARD_DIM; i++){
            for(int j = 0; j < BOARD_DIM; j++){
                if(i < BOARD_DIM/2 && j < BOARD_DIM/2){
                    wholeBoard[i][j] = myQ1[i][j];
                }
                if(i < BOARD_DIM/2 && j >= BOARD_DIM/2){
                    wholeBoard[i][j] = myQ2[i][j-QUAD_DIM];
                }
                if(i >= BOARD_DIM/2 && j < BOARD_DIM/2){
                    wholeBoard[i][j] = myQ3[i - QUAD_DIM][j];
                }
                if(i >= BOARD_DIM/2 && j >= BOARD_DIM/2){
                    wholeBoard[i][j] = myQ4[i - QUAD_DIM][j - QUAD_DIM];
                }

            }
        }

        return wholeBoard;
    }
    /**
     *
     * @return
     */
    @Override
    public String toString(){
        char[][] wholeBoard = bringItTogether();
        String line = "|";
        String newLine = "\n";
        String divider = "+---------+---------+\n";
        StringBuilder output = new StringBuilder(divider);
        for(int i = 0; i < QUAD_DIM; i++){
            for(int j = 0; j< BOARD_DIM; j++){

                if(j == 0 || j == BOARD_DIM/2){ //Add vertical line
                    output.append(line);
                }
                output.append(" " + wholeBoard[i][j] + " ");//add the char and space buffers

                if(j == BOARD_DIM - 1){ //add vertical line and drop down
                    output.append(line+newLine);
                }
            }
        }
        output.append(divider);
        for(int i = QUAD_DIM; i < BOARD_DIM; i++){
            for(int j = 0; j < BOARD_DIM; j++){
                if(j == 0 || j == BOARD_DIM/2){ //Add vertical line
                    output.append(line);
                }
                output.append(" " + wholeBoard[i][j] + " ");//add the char and space buffers
                if(j == BOARD_DIM - 1){ //add vertical line and drop down
                    output.append(line+newLine);
                }
            }
        }
        output.append(divider);
        return output.toString();
   }

   @Override
    public boolean equals(Object o){
       return this.toString().equals(o.toString());
   }


    @Override
    public int compareTo(PentagoNode o) {
        int compare;
        if(myValue > o.getValue()){
            compare = 1;
        }else if(myValue < o.getValue()) {
            compare = -1;
        }else{
            compare = 0;
        }
        return compare;
    }
}
