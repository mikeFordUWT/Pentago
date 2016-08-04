package com.TCSS435;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Mike on 7/26/16.
 */
public class Board{

    private final char EMPTY = '.', WHITE = 'w', BLACK = 'b';
    private final ArrayList<Integer> QUADS = new ArrayList<>(Arrays.asList(1,2,3,4));
    private final String OCCUPIED = "That space isn't available";
    private final int START = 0;
    private final int MIDDLE = 3;
    private final int BOARD_DIM = 3;
    private final int END = 6;

    private char[][] myQ1;
    private char[][] myQ2;
    private char[][] myQ3;
    private char[][] myQ4;
    private char[][] myState;
    private PentagoNode myGameState;
    private PentagoTree myGameTree;

    private int myBs;
    private int myWs;

    public Board(){

        myQ1 = new char[BOARD_DIM][BOARD_DIM];
        myQ2 = new char[BOARD_DIM][BOARD_DIM];
        myQ3 = new char[BOARD_DIM][BOARD_DIM];
        myQ4 = new char[BOARD_DIM][BOARD_DIM];
        for(int i = 0; i < BOARD_DIM; i++){
            for(int j = 0; j < BOARD_DIM; j++){
                myQ1[i][j] = EMPTY;
                myQ2[i][j] = EMPTY;
                myQ3[i][j] = EMPTY;
                myQ4[i][j] = EMPTY;
            }
        }
        myGameState = new PentagoNode(myQ1, myQ2, myQ3, myQ4, 0, null);
        myGameTree = new PentagoTree(new PentagoNode(myQ1, myQ2, myQ3, myQ4, 0, null));

    }


    public PentagoNode getGameState(){
        return myGameState;
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
     * Roatates the given quadrant Left.
     *
     * @param quad the quad to rotate
     */
    public void rotateLeft(int quad){
        if(QUADS.contains(quad)){
            if(quad == 1){
                rotateL(myQ1);
            }else if(quad == 2){
                rotateL(myQ2);
            } else if(quad == 3){
                rotateL(myQ3);
            } else if(quad == 4){
                rotateL(myQ4);
            }
        } else {
            System.out.println("");
        }
    }

    public void rotateRight(int quad){
        if(QUADS.contains(quad)){
            if(quad == 1){
                rotateR(myQ1);
            }else if(quad == 2){
                rotateR(myQ2);
            } else if(quad == 3){
                rotateR(myQ3);
            } else if(quad == 4){
                rotateR(myQ4);
            }
        } else {
            System.out.println("");
        }
    }

    private void rotateL(char[][] quad){
        char[][] temp = makeCopy(quad);
        for(int i = 0 ; i < quad.length; i++){
            for(int j = 0; j < quad.length; j++){
                quad[i][j] = temp[j][quad.length - i - 1];
            }
        }
    }

    private void rotateR(char[][] quad){
        char[][] temp = makeCopy(quad);
        for(int i = 0 ; i < quad.length; i++){
            for(int j = 0; j < quad.length; j++){
                quad[i][j] = temp[quad.length - j - 1][i];
            }
        }
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
                    wholeBoard[i][j] = myQ2[i][j];
                }
                if(i >= BOARD_DIM/2 && j < BOARD_DIM/2){
                    wholeBoard[i][j] = myQ3[i][j];
                }
                if(i >= BOARD_DIM/2 && j >= BOARD_DIM/2){
                    wholeBoard[i][j] = myQ4[i][j];
                }

            }
        }

        return wholeBoard;
    }

    private char[][] makeCopy(char[][] toCopy){
        char[][] copy = new char[toCopy.length][toCopy.length];
        for(int i = 0; i < toCopy.length; i++){
            for(int j = 0; j < toCopy.length; j++){
                copy[i][j] = toCopy[i][j];
            }
        }
        return copy;
    }
    @Override
    public String toString(){
        String divder = "+-------+-------+\n";
        StringBuilder output = new StringBuilder(divder);
        output.append("| " + myQ1[0][0] + " " + myQ1[0][1] + " "+ myQ1[0][2] + " | " + myQ2[0][0] + " " + myQ2[0][1] + " " + myQ2[0][2] + " |\n");
        output.append("| " + myQ1[1][0] + " " + myQ1[1][1] + " "+ myQ1[1][2] + " | " + myQ2[1][0] + " " + myQ2[1][1] + " " + myQ2[1][2] + " |\n");
        output.append("| " + myQ1[2][0] + " " + myQ1[2][1] + " "+ myQ1[2][2] + " | " + myQ2[2][0] + " " + myQ2[2][1] + " " + myQ2[2][2] + " |\n");
        output.append(divder);
        output.append("| " + myQ3[0][0] + " " + myQ3[0][1] + " "+ myQ3[0][2] + " | " + myQ4[0][0] + " " + myQ4[0][1] + " " + myQ4[0][2] + " |\n");
        output.append("| " + myQ3[1][0] + " " + myQ3[1][1] + " "+ myQ3[1][2] + " | " + myQ4[1][0] + " " + myQ4[1][1] + " " + myQ4[1][2] + " |\n");
        output.append("| " + myQ3[2][0] + " " + myQ3[2][1] + " "+ myQ3[2][2] + " | " + myQ4[2][0] + " " + myQ4[2][1] + " " + myQ4[2][2] + " |\n");
        output.append(divder);


        return output.toString();
    }




}
