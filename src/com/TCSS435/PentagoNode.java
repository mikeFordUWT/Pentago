package com.TCSS435;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Mike on 7/26/16.
 */
public class PentagoNode implements Comparable<PentagoNode> {
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
    private Player myPlayer;
    private int myAlpha;
    private int myBeta;
    private int win;

    private int bWin, wWin;


    private int maxValue, minValue;

    public PentagoNode(int theDepth, PentagoNode parent, Player player){
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
        myPlayer = player;
        myChildren = new ArrayList<>();
        myAlpha = Integer.MIN_VALUE;
        myBeta = Integer.MAX_VALUE;
        maxValue = 0;
        minValue = 0;
        win = -1;
        bWin = 0;
        wWin = 0;

        findValue();
        myValue = maxValue + minValue;


    }

    public void setPlayer(Player thePlayer){
        myPlayer = thePlayer;
    }
    public void addChild(PentagoNode theChild){
        myChildren.add(theChild);
    }

    public ArrayList<PentagoNode> getChildren(){
        return myChildren;
    }

    public PentagoNode getParent(){
        return myParent;
    }

    public void setParent(PentagoNode theParent){
        myParent = theParent;
    }

    public int getAlpha(){
        return myAlpha;
    }

    public void setAlpha(int newAlpha){
        myAlpha = newAlpha;
    }

    public int getBeta(){
        return myBeta;
    }

    public void setBeta(int newBeta){
        myBeta = newBeta;
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

    private int tie(){
        int count = 0;
        char[][] whole = bringItTogether();
        for(int i =0; i < whole.length; i++){
            for(int j = 0; j < whole.length; j++){
                if(whole[i][j] == EMPTY){
                    count++;
                }
            }
        }
        return count;
    }

    private ArrayList<Integer> checkRow(char[][] board, int row){
        ArrayList<Integer> wb = new ArrayList<>();
        int b = 0, w = 0;
        int bInRow = 1;
        int wInRow = 1;
        for(int i = 1; i < board.length; i++){
            char current = board[row][i], previous = board[row][i-1];
            if(current == previous && current != EMPTY){
                if(current == BLACK){
                    bInRow++;
                    if(bInRow == 3 || bInRow == 4){
                        b+=10;
                    }else if(bInRow == 5){
                        b+=80;
                        bWin++;
                    }
                }else{
                    wInRow++;
                    if(wInRow == 3 || wInRow == 4){
                        w += 10;
                    }else if(wInRow == 5){
                        w += 80;
                        wWin++;
                    }
                }
            }else{
                bInRow = 0;
                wInRow = 0;
            }
        }

        wb.add(w);
        wb.add(b);
        return wb;
    }

    private ArrayList<Integer> checkColumn(char[][] board, int column){
        ArrayList<Integer> wb = new ArrayList<>();
        int b = 0, w = 0;
        int bInRow = 1;
        int wInRow = 1;
        for(int i = 1; i < board.length; i++){
            char current = board[i][column], previous = board[i-1][column];
            if(current == previous && current != EMPTY){
                if(current == BLACK){
                    bInRow++;
                    if(bInRow == 3 || bInRow == 4){
                        b+=10;
                    }else if(bInRow == 5){
                        b+=80;
                        bWin++;
                    }
                }else{
                    wInRow++;
                    if(wInRow == 3 || wInRow == 4){
                        w += 10;
                    }else if(wInRow == 5){
                        w += 80;
                        wWin++;
                    }
                }
            }else{
                bInRow = 0;
                wInRow = 0;
            }
        }
        wb.add(w);
        wb.add(b);
        return  wb;
    }


    private ArrayList<Integer> checkDiag(char[][] board, int start, int stop, int offset){
        ArrayList<Integer> wb = new ArrayList<>();
        int b = 0, w = 0;
        int bInRow = 1;
        int wInRow = 1;
        for(int i = start; i<=stop; i++){
            char current = board[i + (1-offset)][i], previous = board[i - offset][i-1];
            if(current == previous && current != EMPTY){
                if(current == BLACK){
                    bInRow++;
                    if(bInRow == 3 || bInRow == 4){
                        b += 10;
                    }else if(bInRow == 5){
                        b += 80;
                        bWin++;
                    }

                }else{
                    wInRow++;
                    if(wInRow == 3 || wInRow == 4){
                        w += 10;
                    }else if(wInRow == 5){
                        w += 80;
                        wWin++;
                    }
                }
            }else{
                bInRow = 0;
                wInRow = 0;
            }
        }
        wb.add(w);
        wb.add(b);
        return wb;
    }

    private ArrayList<Integer> checkAnti(char[][] board, int start, int stop, int offset){
        ArrayList<Integer> wb = new ArrayList<>();
        int b= 0, w=0;
        int bInRow = 1;
        int wInRow = 1;
        for(int i = start; i <= stop; i++){
            char current = board[offset - i][i], previous = board[offset+1-i][i-1];
            if(current == previous && current != EMPTY){
                if(current == BLACK){
                    bInRow++;
                    if(bInRow == 3 || bInRow == 4){
                        b += 10;
                    }else if(bInRow == 5){
                        b += 80;
                        bWin++;
                    }

                }else{
                    wInRow++;
                    if(wInRow == 3 || wInRow == 4){
                        w += 10;
                    }else if(wInRow == 5){
                        w += 80;
                        wWin++;
                    }
                }
            }else{
                bInRow = 0;
                wInRow = 0;
            }
        }

        wb.add(w);
        wb.add(b);
        return wb;
    }

    /*
        Check all 18 ways a player can win for values, 10 for 3 in a row, 20 for 4 in a row and 100 for 5 in a row
     */
    private void findValue(){
        int b = 0, w = 0;
        char[][] wholeBoard = bringItTogether();
        /*
            ROW 0
         */
        ArrayList<Integer> row0 = checkRow(wholeBoard, 0);
        b+= row0.get(1);
        w+= row0.get(0);
//        for(int i = 1; i < wholeBoard.length; i++){
//            char current = wholeBoard[0][i], previous = wholeBoard[0][i-1];
//            if(current == previous && current != EMPTY){
//                if(current == BLACK){
//                    bInRow++;
//                    if(bInRow == 3 || bInRow == 4){
//                        b += 10;
//                    }else if(bInRow == 5){
//                        b += 80;
//                    }
//
//                }else{
//                    wInRow++;
//                    if(wInRow == 3 || wInRow == 4){
//                        w += 10;
//                    }else if(wInRow == 5){
//                        w += 80;
//                    }
//                }
//            }else{
//                bInRow = 0;
//                wInRow = 0;
//            }
//        }
//
//        bInRow = 0;
//        wInRow = 0;

        /*
            ROW 1
         */
        ArrayList<Integer> row1 = checkRow(wholeBoard, 1);
        b += row1.get(1);
        w += row1.get(0);
//        for(int i = 1; i < wholeBoard.length; i++){
//            char current = wholeBoard[1][i], previous = wholeBoard[1][i-1];
//            if(current == previous && current != EMPTY){
//                if(current == BLACK){
//                    bInRow++;
//                    if(bInRow == 3 || bInRow == 4){
//                        b += 10;
//                    }else if(bInRow == 5){
//                        b += 80;
//                    }
//
//                }else{
//                    wInRow++;
//                    if(wInRow == 3 || wInRow == 4){
//                        w += 10;
//                    }else if(wInRow == 5){
//                        w += 80;
//                    }
//                }
//            }else{
//                bInRow = 0;
//                wInRow = 0;
//            }
//        }
//
//        bInRow = 0;
//        wInRow = 0;

        /*
            ROW 2
         */
        ArrayList<Integer> row2 = checkRow(wholeBoard, 2);
        b += row2.get(1);
        w += row2.get(0);
//        for(int i = 1; i < wholeBoard.length; i++){
//            char current = wholeBoard[2][i], previous = wholeBoard[2][i-1];
//            if(current == previous && current != EMPTY){
//                if(current == BLACK){
//                    bInRow++;
//                    if(bInRow == 3 || bInRow == 4){
//                        b += 10;
//                    }else if(bInRow == 5){
//                        b += 80;
//                    }
//
//                }else{
//                    wInRow++;
//                    if(wInRow == 3 || wInRow == 4){
//                        w += 10;
//                    }else if(wInRow == 5){
//                        w += 80;
//                    }
//                }
//            }else{
//                bInRow = 0;
//                wInRow = 0;
//            }
//        }
//        bInRow = 0;
//        wInRow = 0;

        /*
            ROW 3
         */
        ArrayList<Integer> row3 = checkRow(wholeBoard, 3);
        b+= row3.get(1);
        w+= row3.get(0);
//        for(int i = 1; i < wholeBoard.length; i++){
//            char current = wholeBoard[3][i], previous = wholeBoard[3][i-1];
//            if(current == previous && current != EMPTY){
//                if(current == BLACK){
//                    bInRow++;
//                    if(bInRow == 3 || bInRow == 4){
//                        b += 10;
//                    }else if(bInRow == 5){
//                        b += 80;
//                    }
//
//                }else{
//                    wInRow++;
//                    if(wInRow == 3 || wInRow == 4){
//                        w += 10;
//                    }else if(wInRow == 5){
//                        w += 80;
//                    }
//                }
//            }else{
//                bInRow = 0;
//                wInRow = 0;
//            }
//        }
//
//        bInRow = 0;
//        wInRow = 0;

        /*
            ROW 4
         */
        ArrayList<Integer> row4 = checkRow(wholeBoard, 4);
        b+= row4.get(1);
        w+= row4.get(0);
//        for(int i = 1; i < wholeBoard.length; i++){
//            char current = wholeBoard[4][i], previous = wholeBoard[4][i-1];
//            if(current == previous && current != EMPTY){
//                if(current == BLACK){
//                    bInRow++;
//                    if(bInRow == 3 || bInRow == 4){
//                        b += 10;
//                    }else if(bInRow == 5){
//                        b += 80;
//                    }
//
//                }else{
//                    wInRow++;
//                    if(wInRow == 3 || wInRow == 4){
//                        w += 10;
//                    }else if(wInRow == 5){
//                        w += 80;
//                    }
//                }
//            }else{
//                bInRow = 0;
//                wInRow = 0;
//            }
//        }
//        bInRow = 0;
//        wInRow = 0;

        /*
            ROW 5
         */
        ArrayList<Integer> row5 = checkRow(wholeBoard, 5);
        b += row5.get(1);
        w += row5.get(0);
//        for(int i = 1; i < wholeBoard.length; i++){
//            char current = wholeBoard[5][i], previous = wholeBoard[5][i-1];
//            if(current == previous && current != EMPTY){
//                if(current == BLACK){
//                    bInRow++;
//                    if(bInRow == 3 || bInRow == 4){
//                        b += 10;
//                    }else if(bInRow == 5){
//                        b += 80;
//                    }
//
//                }else{
//                    wInRow++;
//                    if(wInRow == 3 || wInRow == 4){
//                        w += 10;
//                    }else if(wInRow == 5){
//                        w += 80;
//                    }
//                }
//            }else{
//                bInRow = 0;
//                wInRow = 0;
//            }
//        }
//
//        bInRow = 0;
//        wInRow = 0;


        /*
            COLUMN 0
         */
        ArrayList<Integer> col0 = checkColumn(wholeBoard, 0);
        b += col0.get(1);
        w += col0.get(0);
//        for(int i = 1; i < wholeBoard.length; i++){
//            char current = wholeBoard[i][0], previous = wholeBoard[i-1][0];
//            if(current == previous && current != EMPTY){
//                if(current == BLACK){
//                    bInRow++;
//                    if(bInRow == 3 || bInRow == 4){
//                        b += 10;
//                    }else if(bInRow == 5){
//                        b += 80;
//                    }
//
//                }else{
//                    wInRow++;
//                    if(wInRow == 3 || wInRow == 4){
//                        w += 10;
//                    }else if(wInRow == 5){
//                        w += 80;
//                    }
//                }
//            }else{
//                bInRow = 0;
//                wInRow = 0;
//            }
//        }
//
//        bInRow = 0;
//        wInRow = 0;

        /*
            COLUMN 1
         */
        ArrayList<Integer> col1 = checkColumn(wholeBoard, 1);
        b += col1.get(1);
        w += col1.get(0);
//        for(int i = 1; i < wholeBoard.length; i++){
//            char current = wholeBoard[i][1], previous = wholeBoard[i-1][1];
//            if(current == previous && current != EMPTY){
//                if(current == BLACK){
//                    bInRow++;
//                    if(bInRow == 3 || bInRow == 4){
//                        b += 10;
//                    }else if(bInRow == 5){
//                        b += 80;
//                    }
//
//                }else{
//                    wInRow++;
//                    if(wInRow == 3 || wInRow == 4){
//                        w += 10;
//                    }else if(wInRow == 5){
//                        w += 80;
//                    }
//                }
//            }else{
//                bInRow = 0;
//                wInRow = 0;
//            }
//        }
//
//        bInRow = 0;
//        wInRow = 0;

        /*
            COLUMN 2
         */
        ArrayList<Integer> col2 = checkColumn(wholeBoard, 2);
        b += col2.get(1);
        w += col2.get(0);
//        for(int i = 1; i < wholeBoard.length; i++){
//            char current = wholeBoard[i][2], previous = wholeBoard[i-1][2];
//            if(current == previous && current != EMPTY){
//                if(current == BLACK){
//                    bInRow++;
//                    if(bInRow == 3 || bInRow == 4){
//                        b += 10;
//                    }else if(bInRow == 5){
//                        b += 80;
//                    }
//
//                }else{
//                    wInRow++;
//                    if(wInRow == 3 || wInRow == 4){
//                        w += 10;
//                    }else if(wInRow == 5){
//                        w += 80;
//                    }
//                }
//            }else{
//                bInRow = 0;
//                wInRow = 0;
//            }
//        }
//
//        bInRow = 0;
//        wInRow = 0;

        /*
            COLUMN 3
         */
        ArrayList<Integer> col3 = checkColumn(wholeBoard, 3);
        b += col3.get(1);
        w += col3.get(0);
//        for(int i = 1; i < wholeBoard.length; i++){
//            char current = wholeBoard[i][3], previous = wholeBoard[i-1][3];
//            if(current == previous && current != EMPTY){
//                if(current == BLACK){
//                    bInRow++;
//                    if(bInRow == 3 || bInRow == 4){
//                        b += 10;
//                    }else if(bInRow == 5){
//                        b += 80;
//                    }
//
//                }else{
//                    wInRow++;
//                    if(wInRow == 3 || wInRow == 4){
//                        w += 10;
//                    }else if(wInRow == 5){
//                        w += 80;
//                    }
//                }
//            }else{
//                bInRow = 0;
//                wInRow = 0;
//            }
//        }
//        bInRow = 0;
//        wInRow = 0;

        /*
            COLUMN 4
         */
        ArrayList<Integer> col4 = checkColumn(wholeBoard, 4);
        b += col4.get(1);
        w += col4.get(0);
//        for(int i = 1; i < wholeBoard.length; i++){
//            char current = wholeBoard[i][4], previous = wholeBoard[i-1][4];
//            if(current == previous && current != EMPTY){
//                if(current == BLACK){
//                    bInRow++;
//                    if(bInRow == 3 || bInRow == 4){
//                        b += 10;
//                    }else if(bInRow == 5){
//                        b += 80;
//                    }
//
//                }else{
//                    wInRow++;
//                    if(wInRow == 3 || wInRow == 4){
//                        w += 10;
//                    }else if(wInRow == 5){
//                        w += 80;
//                    }
//                }
//            }else{
//                bInRow = 0;
//                wInRow = 0;
//            }
//        }
//        bInRow = 0;
//        wInRow = 0;

        /*
            COLUMN 5
         */
        ArrayList<Integer> col5 = checkColumn(wholeBoard, 5);
        b += col5.get(1);
        w += col5.get(0);

//        for(int i = 1; i < wholeBoard.length; i++){
//            char current = wholeBoard[i][5], previous = wholeBoard[i-1][5];
//            if(current == previous && current != EMPTY){
//                if(current == BLACK){
//                    bInRow++;
//                    if(bInRow == 3 || bInRow == 4){
//                        b += 10;
//                    }else if(bInRow == 5){
//                        b += 80;
//                    }
//
//                }else{
//                    wInRow++;
//                    if(wInRow == 3 || wInRow == 4){
//                        w += 10;
//                    }else if(wInRow == 5){
//                        w += 80;
//                    }
//                }
//            }else{
//                bInRow = 0;
//                wInRow = 0;
//            }
//        }
//        bInRow = 0;
//        wInRow = 0;

        /*
            MAIN DIAGONAL
         */
        ArrayList<Integer> diag0 = checkDiag(wholeBoard, 1, 5, 1);
        b += diag0.get(1);
        w += diag0.get(0);
//        for(int i = 1; i < wholeBoard.length; i++){
//            char current = wholeBoard[i][i], previous = wholeBoard[i-1][i-1];
//            if(current == previous && current != EMPTY){
//                if(current == BLACK){
//                    bInRow++;
//                    if(bInRow == 3 || bInRow == 4){
//                        b += 10;
//                    }else if(bInRow == 5){
//                        b += 80;
//                    }
//
//                }else{
//                    wInRow++;
//                    if(wInRow == 3 || wInRow == 4){
//                        w += 10;
//                    }else if(wInRow == 5){
//                        w += 80;
//                    }
//                }
//            }else{
//                bInRow = 0;
//                wInRow = 0;
//            }
//        }
//
//        bInRow = 0;
//        wInRow = 0;

        /*
            UPPER DIAGONAL
         */
        ArrayList<Integer> diag1 = checkDiag(wholeBoard, 2, 5, 2);
        b += diag1.get(1);
        w += diag1.get(0);
//        for(int i = 1; i < wholeBoard.length - 1; i++){
//            char current = wholeBoard[i][i+1], previous = wholeBoard[i-1][i];
//            if(current == previous && current != EMPTY){
//                if(current == BLACK){
//                    bInRow++;
//                    if(bInRow == 3 || bInRow == 4){
//                        b += 10;
//                    }else if(bInRow == 5){
//                        b += 80;
//                    }
//
//                }else{
//                    wInRow++;
//                    if(wInRow == 3 || wInRow == 4){
//                        w += 10;
//                    }else if(wInRow == 5){
//                        w += 80;
//                    }
//                }
//            }else{
//                bInRow = 0;
//                wInRow = 0;
//            }
//        }
//
//        bInRow = 0;
//        wInRow = 0;

        /*
            LOWER DIAGONAL
         */
        ArrayList<Integer> diag2 = checkDiag(wholeBoard, 1, 4, 0);
        b += diag2.get(1);
        w += diag2.get(0);
//        for(int i = 1; i < wholeBoard.length-1; i++){
//            char current = wholeBoard[i+1][i], previous = wholeBoard[i][i-1];
//            if(current == previous && current != EMPTY){
//                if(current == BLACK){
//                    bInRow++;
//                    if(bInRow == 3 || bInRow == 4){
//                        b += 10;
//                    }else if(bInRow == 5){
//                        b += 80;
//                    }
//
//                }else{
//                    wInRow++;
//                    if(wInRow == 3 || wInRow == 4){
//                        w += 10;
//                    }else if(wInRow == 5){
//                        w += 80;
//                    }
//                }
//            }else{
//                bInRow = 0;
//                wInRow = 0;
//            }
//        }
//
//        bInRow = 0;
//        wInRow = 0;

        /*
            MAIN ANTI DIAGONAL
         */
        ArrayList<Integer> anti0 = checkAnti(wholeBoard, 1, 5, 5);
        b += anti0.get(1);
        w += anti0.get(0);

//        for(int i = 1; i < wholeBoard.length; i++){
//            char current = wholeBoard[5-i][i], previous = wholeBoard[6-i][i-1];
//            if(current == previous && current != EMPTY){
//                if(current == BLACK){
//                    bInRow++;
//                    if(bInRow == 3 || bInRow == 4){
//                        b += 10;
//                    }else if(bInRow == 5){
//                        b += 80;
//                    }
//
//                }else{
//                    wInRow++;
//                    if(wInRow == 3 || wInRow == 4){
//                        w += 10;
//                    }else if(wInRow == 5){
//                        w += 80;
//                    }
//                }
//            }else{
//                bInRow = 0;
//                wInRow = 0;
//            }
//        }
//
//        bInRow = 0;
//        wInRow = 0;

        /*
            UPPER ANTI DIAGONAL
         */
        ArrayList<Integer> anti1 = checkAnti(wholeBoard, 1, 4, 4);
        b += anti1.get(1);
        w += anti1.get(0);
//        for(int i = 1; i < wholeBoard.length - 1 ; i++){
//            char current = wholeBoard[4-i][i], previous = wholeBoard[5-i][i-1];
//            if(current == previous && current != EMPTY){
//                if(current == BLACK){
//                    bInRow++;
//                    if(bInRow == 3 || bInRow == 4){
//                        b += 10;
//                    }else if(bInRow == 5){
//                        b += 80;
//                    }
//
//                }else{
//                    wInRow++;
//                    if(wInRow == 3 || wInRow == 4){
//                        w += 10;
//                    }else if(wInRow == 5){
//                        w += 80;
//                    }
//                }
//            }else{
//                bInRow = 0;
//                wInRow = 0;
//            }
//        }
//
//        bInRow = 0;
//        wInRow = 0;
        /*
            Lower Anti-diagonal
         */
        ArrayList<Integer> anti2 = checkAnti(wholeBoard, 2, 5, 6);
        b += anti2.get(1);
        w += anti2.get(0);
//        for(int i = 1; i < wholeBoard.length - 1 ; i++){
//            char current = wholeBoard[5-i][i+1], previous = wholeBoard[6-i][i];
//            if(current == previous && current != EMPTY){
//                if(current == BLACK){
//                    bInRow++;
//                    if(bInRow == 3 || bInRow == 4){
//                        b += 10;
//                    }else if(bInRow == 5){
//                        b += 80;
//                    }
//
//                }else{
//                    wInRow++;
//                    if(wInRow == 3 || wInRow == 4){
//                        w += 10;
//                    }else if(wInRow == 5){
//                        w += 80;
//                    }
//                }
//            }else{
//                bInRow = 0;
//                wInRow = 0;
//            }
//        }

        //check at end and pass back to appropriate values
        if(myPlayer.isMax()){//current player is a max
            if(myPlayer.getPiece() == BLACK){
                maxValue = b;
                minValue = w;
            }else{
                minValue = b;
                maxValue = w;
            }
        }else{//current player is min
            if(myPlayer.getPiece() == BLACK){
                minValue = b;
                maxValue = w;
            }else{
                maxValue = b;
                minValue = w;
            }
        }

//        minValue = -minValue;
        myValue = maxValue + minValue;
        int x = 0;
    }

    /**
     * Checks to see if there is a win state in this node.
     *
     * @return -1 if game not done, 0 if game tie, 1 if White wins 2 if Black wins
     */
    public int winState() {
        if(bWin > wWin){
            win = 2;
        }else if(bWin < wWin) {
            win = 1;
        }else if(bWin > 0 && wWin > 0 && bWin == wWin && tie() == 0) {
            win = 0;
        }else {
            win=-1;
        }
        return win;
    }

    /**
     * Rotates the given quadrant Left.
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
        findValue();
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
        findValue();
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

    private char[][] makeCopy(char[][] toCopy){
        char[][] copy = new char[toCopy.length][toCopy.length];
        for(int i = 0; i < toCopy.length; i++){
            for(int j = 0; j < toCopy.length; j++){
                copy[i][j] = toCopy[i][j];
            }
        }
        return copy;
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
        findValue();
        return toReturn;
    }

    /**
     * Checks to see if a node is a leaf in the tree.
     *
     * @return true if a leaf
     */
    public boolean isLeaf(){
        return myChildren.size() == 0;
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
        return Integer.compare(myValue, o.getValue());
    }
}
