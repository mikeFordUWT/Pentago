package com.TCSS435;

/**
 * Created by Mike on 7/26/16.
 */
public class Board{

    private final int START = 0;
    private final int MIDDLE = 3;
    private final int END = 6;

    private char[][] myQ1;
    private char[][] myQ2;
    private char[][] myQ3;
    private char[][] myQ4;
    private char[][] myState;

    private int myBs;
    private int myWs;

    public Board(char[][] theState){
        if(theState.length != 6){
            System.out.println("That board is the wrong size!");
        } else {
            myQ1 = getQuadrant(theState, START, MIDDLE, START, MIDDLE);
            myQ2 = getQuadrant(theState, START, MIDDLE, MIDDLE, END);
            myQ3 = getQuadrant(theState, MIDDLE, END, START, MIDDLE);
            myQ4 = getQuadrant(theState, MIDDLE, END, MIDDLE, END);
            myState = theState;
        }
    }


    private char[][] getQuadrant(char[][] theState, int startI, int endI, int startJ, int endJ){
        char[][] quad = new char[3][3];
        for(int i = startI; i < endI; i++){
            for(int j = startJ; j < endJ; j++){
                quad[i][j] = theState[i][j];
            }
        }

        return quad;
    }

    /**
     * Get the first quadrant of the game board.
     * @return 2D Array
     */
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

    @Override
    public String toString(){
        String divder = "+-------+-------+\n";
        StringBuilder output = new StringBuilder(divder);
        for(int i = 0; i < myState.length; i++){
            for(int j = 0; j < myState.length; j++){
                if(j == 0 || j == myState.length/2){
                    output.append("|");
                } else if(i == myState.length/2){
                    output.append(divder);
                }
                output.append(myState[i][j]);
            }
        }
        output.append(divder);

        return output.toString();
    }
}
