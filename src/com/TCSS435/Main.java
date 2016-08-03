package com.TCSS435;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static final String ROTATE_QUAD = "Which piece would you like to rotate and which direction?[ex. 1L or 4R]";
    static final String ROTATE_DIR = "Left[l] or right[r]?";
    static final String PIECE_SPOT = "Where would you like to place a piece?[QUAD/SPOT]";
    static final String PLAY_NUM = "HOW MANY PLAYERS? [1 or 2]";
    static final String FIRST_OR = "Would you like to go first? ['y' or 'n']";
    static final String FIRST_NAME = "Player 1 Name: ";
    static final String SECOND_NAME = "Player 2 Name: ";
    static final String TOKEN_COLOR = "First Player Token Color [W or B]: ";
    static final String WHICH_FIRST = "Which player should go first? [1 or 2]";
    static final String AI_NAME = "HAL 9000";
    static final boolean HUMAN = true;
    static final char BLACK = 'B', WHITE = 'W', LEFT = 'L', RIGHT ='R';

    static final String WELCOME =
              "****************************************\n"
            + "*          WELCOME TO PENTAGO!         *\n"
            + "****************************************\n";


    public static void main(String[] args) throws IOException {
        final HashMap<Integer, ArrayList<Integer>> spotToArray = new HashMap<>();
        spotToArray.put(1, new ArrayList<>(Arrays.asList(0,0)));
        spotToArray.put(2, new ArrayList<>(Arrays.asList(0,1)));
        spotToArray.put(3, new ArrayList<>(Arrays.asList(0,2)));
        spotToArray.put(4, new ArrayList<>(Arrays.asList(1,0)));
        spotToArray.put(5, new ArrayList<>(Arrays.asList(1,1)));
        spotToArray.put(6, new ArrayList<>(Arrays.asList(1,2)));
        spotToArray.put(7, new ArrayList<>(Arrays.asList(2,0)));
        spotToArray.put(8, new ArrayList<>(Arrays.asList(2,1)));
        spotToArray.put(9, new ArrayList<>(Arrays.asList(2,2)));

        PrintStream ps = new PrintStream(new FileOutputStream("result.txt"));
        Scanner input = new Scanner(System.in);
        int player = 1;//controls which player goes first
        Player p1;
        Player p2;

        boolean p1Human = HUMAN;
        boolean p2Human = !HUMAN;
        String p1Name = "";
        String p2Name = "";
	    Board board = new Board();
        output(WELCOME, ps);
        output(PLAY_NUM, ps);
        int playAmt = 0 ;
        boolean players = false;
        while(!players){
            int pA = Integer.parseInt(input.nextLine());
            if(pA > 0 && pA< 3){
                playAmt = pA;
                players = true;
            }else{
                System.out.println("There can only be 1 or 2 players. Try Again");
                System.out.println(PLAY_NUM);
            }
        }
        output(FIRST_NAME, ps);
        p1Name = input.nextLine();


        output(TOKEN_COLOR, ps);
        char p1Token = ' ';
        char p2Token = ' ';
        char tokenColor =' ';
        boolean token = false;

        String nextLine = input.nextLine().toUpperCase();
        while(!token){

            if(nextLine.length() == 1 && nextLine.charAt(0) == BLACK){
                p1Token = BLACK;
                p2Token = WHITE;
                token = true;
            }else if(nextLine.length() == 1 && nextLine.charAt(0) == WHITE){
                p1Token = WHITE;
                p2Token = BLACK;
                token = true;
            }else{
                output("The choices are B or W.", ps);
                output(TOKEN_COLOR, ps);
                nextLine = input.nextLine().toUpperCase();
            }

        }

        if(playAmt == 2){
            output(SECOND_NAME, ps);
            p2Name = input.nextLine();
            p2Human = HUMAN;
        } else {
            p2Name = AI_NAME;
        }




        if(playAmt == 1){
            output(WHICH_FIRST, ps);
            int first = 0;
            boolean done = false;
            nextLine = input.nextLine();
            while(!done){
                if(nextLine.length() != 1 && (nextLine.charAt(0) != '1' || nextLine.charAt(0) != '2')){
                    output("The only options here are 1 or 2.", ps);
                    output(WHICH_FIRST, ps);
                    nextLine = input.nextLine();
                }else{
                    first = Integer.parseInt(nextLine);
                    done = true;
                }
            }
            if(first == 1){
                player = 1;
            }else{
                player = 2;
            }

        }
        p1 = new Player(HUMAN, p1Token, p1Name);
        p2 = new Player(p2Human, p2Token, p2Name);







        /*
            GAME STARTS HERE!
         */
        output("", ps);
        output(board.toString(), ps);


        boolean gameOver = false;
        while(!gameOver){
            Player current;
            char piece;
            if(player % 2 == 0){ //PLAYER 2
                current = p2;
            }else{ //PLAYER 1
                current = p1;
            }

            if(current.isAI()){//that means it's an AI!

            }else{

            }

            System.out.println(PIECE_SPOT);
            //TODO update by using the boolean return of changeSpace
            String piecePlace = input.nextLine();
            int quadrant = Character.getNumericValue(piecePlace.charAt(0));
            int spot = Character.getNumericValue(piecePlace.charAt(2));
            ArrayList<Integer> coords = spotToArray.get(spot);
            board.changeSpace(quadrant, coords.get(0), coords.get(1), current.getPiece());
            output(board.toString(), ps);

            System.out.println(ROTATE_QUAD);



            String rotate = input.nextLine();
            int quad = 0;
            char direction = ' ';
            boolean parsable = false;
            while(!parsable){
                if(rotate.length() == 2){
                    if(isParseable(String.valueOf(rotate.charAt(0))) && (rotate.charAt(1) == LEFT || rotate.charAt(1) == RIGHT)){
                        quad = Integer.parseInt(String.valueOf(rotate.charAt(0)));
                        direction = rotate.charAt(1);
                        parsable = true;
                    }else{
                        System.out.println("Input must start with an integer [1-4] and have [L or R] as the second character");
                        System.out.println(ROTATE_QUAD);
                        rotate = input.nextLine();
                    }
                }else{
                    System.out.println("The input must be exactly 2 characters long [ex. 1L or 4R]");
                    System.out.println(ROTATE_QUAD);
                    rotate = input.nextLine();
                }
            }

            if(direction == LEFT){
                board.rotateLeft(quad);
            }else if(direction == RIGHT){
                board.rotateRight(quad);
            }
            output(board.toString(), ps);
            player++;
        }
    }

    /*
        A helper method for writing to both a file and the console.®
     */
    static void output(String msg, PrintStream out1){
        out1.println(msg);
        System.out.println(msg);
    }

    /*
        Helper method to check is a string can be parsed into an Integer.
     */
    static boolean isParseable(String toParse){
        boolean p = true;
        try{
            Integer.parseInt(toParse);
        }catch (NumberFormatException e){
            p = false;
        }
        return p;
    }
}
