package com.TCSS435;

import java.io.*;
import java.util.*;

public class PentagoGame {
    static final String ROTATE_QUAD = "Which piece would you like to rotate and which direction?[ex. 1L or 4R]";
    static final String ALGORITM = "Would you like AI to use Minimax[M] or Alpha-Beta Pruning[A]?";
    static final String ROTATE_DIR = "Left[l] or right[r]?";
    static final String PIECE_SPOT = "what's your move? [B/S BD]\nex: 1/4 2R to place piece in Block: 1 Spot: 4 and rotate Block 2 Right";
    static final String PLAY_NUM = "HOW MANY PLAYERS? [1 or 2]";
    static final String FIRST_OR = "Would you like to go first? ['y' or 'n']";
    static final String FIRST_NAME = "Player 1 Name: ";
    static final String SECOND_NAME = "Player 2 Name: ";
    static final String TOKEN_COLOR = "First Player Token Color [W or B]: ";
    static final String WHICH_FIRST = "Which player should go first? [1 or 2]";
    static final String AI_NAME = "HAL 9000";
    static final boolean AI_STATUS = false;
    static final char BLACK = 'B', WHITE = 'W', LEFT = 'L', RIGHT ='R', ALPHA = 'A', MINMAX = 'M';

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

        PrintStream ps = new PrintStream(new FileOutputStream("output.txt"));
        Scanner scanner = new Scanner(System.in);
        int player = 1;//controls which player goes first
        Player p1;
        Player p2;


        boolean p1Human = AI_STATUS;
        boolean p2Human = !AI_STATUS;
        String p1Name = "";
        String p2Name = "";
        char aiAlg = ' ';
	    Board board = new Board();
        output(WELCOME, ps);
        output(PLAY_NUM, ps);
        int playAmt = 0 ;
        boolean go = false;
        while(!go){
            int numPlayers = 0;
            String line = scanner.nextLine();
            boolean goInner = false;
            while(!goInner){
                if(isParseable(line) && line.length() == 1 && (line.charAt(0) == '1' || line.charAt(0) == '2')){
                    numPlayers = Integer.parseInt(line);
                    goInner = true;
                } else {
                    System.out.println("That is not the right input try again");
                    System.out.println(PLAY_NUM);
                    line = scanner.nextLine();
                }
            }

            if(numPlayers == 1 || numPlayers == 2){
                playAmt = numPlayers;
                go = true;
            } else {
                System.out.println("There can only be 1 or 2 players. Try Again");
                System.out.println(PLAY_NUM);
            }
        }
        go = false;
        output(FIRST_NAME, ps);
        String line = scanner.nextLine();
        while(!go){
            if(line.length() > 0){
                p1Name = line;
                go = true;
            } else {
                System.out.println("You have to enter a name");
                output(FIRST_NAME, ps);
                line = scanner.nextLine();
            }
        }

        go = false;

        char player1Token = ' ';
        char player2Token = ' ';
        output(TOKEN_COLOR, ps);
        line = scanner.nextLine().toUpperCase();
        while(!go){
            if(line.length() == 1 && line.charAt(0) == BLACK){
                player1Token = BLACK;
                player2Token = WHITE;
                go = true;
            }else if(line.length() == 1 && line.charAt(0) == WHITE) {
                player1Token = WHITE;
                player2Token = BLACK;
                go = true;
            }else{
                output("The choices are B or W.\n", ps);
                output(TOKEN_COLOR, ps);
                line = scanner.nextLine().toUpperCase();
            }
        }

        go = false;

        if(playAmt == 2){
            output(SECOND_NAME, ps);
            line = scanner.nextLine();
            p2Human = AI_STATUS;
            while(!go) {
                if(line.length() > 0){
                    p2Name = line;
                    go = true;
                } else {
                    System.out.println("You have to enter a name.");
                    output(SECOND_NAME, ps);
                    line = scanner.nextLine();
                }
            }
        } else {
            p2Name = AI_NAME;
            output(ALGORITM, ps);
            line = scanner.nextLine().toUpperCase();
            while(!go){
                if(line.length() == 1 && (line.charAt(0) == ALPHA || line.charAt(0) == MINMAX)){
                    aiAlg = line.charAt(0);
                    go = true;
                } else {
                    System.out.println("That's not valid, enter either A or M");
                    System.out.println(ALGORITM);
                    line = scanner.nextLine().toUpperCase();
                }
            }

        }
        go = false;

        output(WHICH_FIRST, ps);
        line = scanner.nextLine();
        int first = 0;
        while(!go){
            if(line.length() != 1 && (line.charAt(0) != '1' || line.charAt(0) != '2')){
                output("The only options here are 1 or 2.", ps);
                output(WHICH_FIRST, ps);
                line = scanner.nextLine();
            }else{
                first = Integer.parseInt(line);
                go = true;
            }
        }

        go = false;

        player = first;

        p1 = new Player(AI_STATUS, player1Token, p1Name);
        p2 = new Player(p2Human, player2Token, p2Name);


        output("", ps);
        output(board.getGameState().toString(), ps);
        boolean gameDone = false;
        while(!gameDone){
            Player current;
            char piece;
            int p = player % 2;
            if(player % 2 == 0){
                current = p2;
            }else{
                current = p1;
            }

            if(current.isAI()){
                //TODO fill this out

            }else{
                output(current.getName()+"'s turn", ps);
                System.out.println(PIECE_SPOT);
                line = scanner.nextLine();
                int spotQuad = 0;
                int spot = 0;
                int rotateQuad = 0;
                char direction = ' ';
                while(!go){
                    boolean parse = false;
                    boolean validDir = false;
                    if(line.length() == 6){
                        parse = isParseable(String.valueOf(line.charAt(0)))
                                && isParseable(String.valueOf(line.charAt(2)))
                                && isParseable(String.valueOf(line.charAt(4)));
                        validDir = line.toUpperCase().charAt(5) == LEFT || line.toUpperCase().charAt(5) == RIGHT;
                    }
                    boolean inRange = false;
                    if(parse){
                        int one = Integer.parseInt(String.valueOf(line.charAt(0)));
                        int two = Integer.parseInt(String.valueOf(line.charAt(2)));
                        int three = Integer.parseInt(String.valueOf(line.charAt(4)));
                        inRange = one > 0 && one < 5 && two > 0 && two < 10 && three > 0 && three < 5;
                    }

                    if(line.length() == 6 && line.contains("/") && line.contains(" ") && parse && inRange && validDir){
                        spotQuad = Integer.parseInt(String.valueOf(line.charAt(0)));
                        spot = Integer.parseInt(String.valueOf(line.charAt(2)));
                        rotateQuad = Integer.parseInt(String.valueOf(line.charAt(4)));
                        direction = line.toUpperCase().charAt(5);
                        go = true;
                    }else{
                        System.out.println("Something seems wrong with your input, please try again");
                        System.out.println(PIECE_SPOT);
                        line = scanner.nextLine();
                    }
                }

                ArrayList<Integer> coords = spotToArray.get(spot);
//                TODO where I left off!



            }

            player++;
        }

        boolean players = false;


        while(!players){
            boolean parse = false;
            int pA = 0;
            String nl = scanner.nextLine();
            while(!parse){
                if(isParseable(nl)){
                    pA = Integer.parseInt(nl);
                    parse = true;
                }else{
                    System.out.println("That is not the right input try again");
                    System.out.println(PLAY_NUM);
                    nl = scanner.nextLine();
                }
            }

            if(pA > 0 && pA< 3){
                playAmt = pA;
                players = true;
            }else{
                System.out.println("There can only be 1 or 2 players. Try Again");
                System.out.println(PLAY_NUM);
            }
        }
        output(FIRST_NAME, ps);
        p1Name = scanner.nextLine();


        output(TOKEN_COLOR, ps);
        char p1Token = ' ';
        char p2Token = ' ';
        char tokenColor =' ';
        boolean token = false;

        String nextLine = scanner.nextLine().toUpperCase();
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
                output("The choices are B or W.\n", ps);
                output(TOKEN_COLOR, ps);
                nextLine = scanner.nextLine().toUpperCase();
            }

        }

        if(playAmt == 2){
            output(SECOND_NAME, ps);
            p2Name = scanner.nextLine();
            p2Human = AI_STATUS;
        } else {
            p2Name = AI_NAME;
            output(ALGORITM, ps);
            boolean cont = false;
            String inString = scanner.nextLine().toUpperCase();
            while(!cont){
                if(inString.length() == 1 && (inString.charAt(0) == 'A'|| inString.charAt(0) == 'M')){
                    aiAlg = inString.charAt(0);
                    cont = true;
                }else{
                    System.out.println("That's not valid, enter either A or M");
                    System.out.println(ALGORITM);
                    inString = scanner.nextLine().toUpperCase();
                }
            }
        }




        if(playAmt == 1){
            output(WHICH_FIRST, ps);
            int first2 = 0;
            boolean done2 = false;
            nextLine = scanner.nextLine();
            while(!done2){
                if(nextLine.length() != 1 && (nextLine.charAt(0) != '1' || nextLine.charAt(0) != '2')){
                    output("The only options here are 1 or 2.", ps);
                    output(WHICH_FIRST, ps);
                    nextLine = scanner.nextLine();
                }else{
                    first2 = Integer.parseInt(nextLine);
                    done2 = true;
                }
            }
            if(first2 == 1){
                player = 1;
            }else{
                player = 2;
            }

        }
        p1 = new Player(AI_STATUS, p1Token, p1Name);
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

            System.out.println(current.getName());
            System.out.println(PIECE_SPOT);
            //TODO update by using the boolean return of changeSpace
            String piecePlace = scanner.nextLine();
            boolean cont = false;
            while(!cont){
                if(piecePlace.length() == 6 && piecePlace.contains("/") && piecePlace.contains(" ")){
                    String first1 = String.valueOf(piecePlace.charAt(0));
                    String second = String.valueOf(piecePlace.charAt(2));
                    String third = String.valueOf(piecePlace.charAt(4));
                    String fourth = String.valueOf(piecePlace.charAt(5));
                    if(isParseable(first1) && isParseable(second) && isParseable(third)){

                    }
                }else{

                }
            }
            int quadrant = Character.getNumericValue(piecePlace.charAt(0));
            int spot = Character.getNumericValue(piecePlace.charAt(2));
            ArrayList<Integer> coords = spotToArray.get(spot);
            board.changeSpace(quadrant, coords.get(0), coords.get(1), current.getPiece());
            output(board.toString(), ps);

            System.out.println(ROTATE_QUAD);
            String rotate = scanner.nextLine();
            int quad = 0;
            char direction = ' ';
            boolean parsable = false;
            while(!parsable){
                if(rotate.length() == 2){
                    String R = rotate.toUpperCase();
                    if(isParseable(String.valueOf(rotate.charAt(0))) && (R.charAt(1) == LEFT || R.charAt(1) == RIGHT)){
                        quad = Integer.parseInt(String.valueOf(rotate.charAt(0)));
                        direction = R.charAt(1);
                        parsable = true;
                    }else{
                        System.out.println("Input must start with an integer [1-4] and have [L or R] as the second character");
                        System.out.println(ROTATE_QUAD);
                        rotate = scanner.nextLine();
                    }
                }else{
                    System.out.println("The input must be exactly 2 characters long [ex. 1L or 4R]");
                    System.out.println(ROTATE_QUAD);
                    rotate = scanner.nextLine();
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
