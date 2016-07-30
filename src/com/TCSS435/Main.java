package com.TCSS435;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static final String ROTATE_QUAD = "Which piece would you like to rotate?[1 - 4]";
    static final String ROTATE_DIR = "Left[l] or right[r]?";
    static final String PIECE_SPOT = "Where would you like to place a piece?[QUAD/SPOT]";
    static final String PLAY_NUM = "HOW MANY PLAYERS? [1 or 2]";
    static final String FIRST_OR = "Would you like to go first? ['y' or 'n']";
    static final boolean HUMAN = true;
    static final char BLACK = 'b', WHITE = 'w';

    static final String WELCOME =
            "****************************************\n" +
            "*          WELCOME TO PENTAGO!         *\n"  +
                    "****************************************\n";


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

        Player p1;
        Player p2;
	    Board newB = new Board();
        output(WELCOME, ps);
        output(PLAY_NUM, ps);


        int playAmt =Integer.parseInt(input.nextLine());

        if(playAmt == 1){
            //TODO allow user to specify color AKA turn
            p1 = new Player(HUMAN, BLACK);
            p2 = new Player(!HUMAN, WHITE);
            output(FIRST_OR,ps);
        }else if(playAmt == 2){
            p1 = new Player(HUMAN, BLACK);
            p1 = new Player(HUMAN, WHITE);
        }




        /*
            GAME STARTS HERE!
         */
        output("", ps);
        output(newB.toString(), ps);

        int player = 1;
        boolean gameOver = false;
        while(!gameOver){
            char piece;
            if(player %2 == 0){
                piece = 'w';
            }else{
                piece = 'b';
            }

            System.out.println(PIECE_SPOT);
            String piecePlace = input.nextLine();
            int quadrant = Character.getNumericValue(piecePlace.charAt(0));
            int spot = Character.getNumericValue(piecePlace.charAt(2));
            ArrayList<Integer> coords = spotToArray.get(spot);
            newB.changeSpace(quadrant, coords.get(0), coords.get(1), piece);
            output(newB.toString(), ps);

            System.out.println(ROTATE_QUAD);
            String quad = input.nextLine();
            int q = Integer.parseInt(quad);
            System.out.println(ROTATE_DIR);
            String dir = input.nextLine();
            if(dir.equals("l")){
                newB.rotateLeft(q);
            }else if(dir.equals("r")){
                newB.rotateRight(q);
            }else{
                System.out.println("That's not a proper direction");
            }

            output(newB.toString(), ps);



            player++;
        }
    }

    /*
        A helper method for writing to both a file and the console.®
     */
    private static void output(String msg, PrintStream out1){
        out1.println(msg);
        System.out.println(msg);
    }

}
