import java.io.*;
import java.util.*;


/**
 * PentagoGame class, which house all of the user input validation and logic.
 *
 * @author Michael Ford
 *
 * TCSS 435
 * Summer 2016
 */

public class PentagoGame {
    static final String ALGORITM = "Would you like AI to use Minimax[M] or Alpha-Beta Pruning[A]?";
    static final String PIECE_SPOT = "what's your move? [B/S BD]\nex: 1/4 2R to place piece in Block: 1 Spot: 4 and rotate Block 2 Right";
    static final String PLAY_NUM = "HOW MANY PLAYERS? [1 or 2]";
    static final String FIRST_NAME = "Player 1 Name: ";
    static final String SECOND_NAME = "Player 2 Name: ";
    static final String TOKEN_COLOR = "First Player Token Color [W or B]: ";
    static final String WHICH_FIRST = "Which player should go first? [1 or 2]";
    static final String AI_NAME = "HAL 9000";
    static final boolean AI_STATUS = false;
    static final char BLACK = 'B', WHITE = 'W', LEFT = 'L', RIGHT ='R', ALPHA = 'A', MINMAX = 'M';
    static final int TREE_DEPTH  = 3;
    static final String WON = " WON!";
    static final String WELCOME =
              "****************************************\n"
            + "*          WELCOME TO PENTAGO!         *\n"
            + "****************************************\n";


    public static void main(String[] args) throws IOException {
        /* A HashMap that stores coordinates for spots within quads*/
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

        PrintStream ps = new PrintStream(new FileOutputStream("output.txt"));//for outputting
        Scanner scanner = new Scanner(System.in);
        int player = 1;//controls which player goes first
        Player p1, p2;


        boolean p1Human = AI_STATUS, p2AI = !AI_STATUS, go = false;
        String p1Name = "",p2Name = "";
        char aiAlg = ' ';
        output(WELCOME, ps);
        output(PLAY_NUM, ps);
        int playAmt = 0 ;
        while(!go){
            int numPlayers = 0;
            String line = scanner.nextLine().replaceAll("\\s+","");//remove all spaces from string
            boolean goInner = false;
            while(!goInner){//find out how many players will play, don't break until user enters proper input
                /* checks to make sure that only 1 or 2 are entered*/
                if(isParseable(line) && line.length() == 1 && (line.charAt(0) == '1' || line.charAt(0) == '2')){
                    numPlayers = Integer.parseInt(line);
                    goInner = true;
                } else {
                    /* prompt the user for input again*/
                    System.out.println("That is not the right input try again");
                    System.out.println(PLAY_NUM);
                    line = scanner.nextLine();
                }
            }
            if(numPlayers == 1 || numPlayers == 2){
                playAmt = numPlayers;//checks to see if two player game or 1 player vs AI
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
        line = scanner.nextLine().toUpperCase();//get which color the first player will be
        while(!go){
            if(line.length() == 1 && line.charAt(0) == BLACK){
                player1Token = BLACK;
                player2Token = WHITE;//player two color by default
                go = true;
            }else if(line.length() == 1 && line.charAt(0) == WHITE) {
                player1Token = WHITE;
                player2Token = BLACK;//player two color by default
                go = true;
            }else{//user input not correct, re-prompt
                output("The choices are B or W.\n", ps);
                output(TOKEN_COLOR, ps);
                line = scanner.nextLine().toUpperCase();
            }
        }
        go = false;

        if(playAmt == 2){//needs a second player name
            output(SECOND_NAME, ps);
            line = scanner.nextLine();
            p2AI = AI_STATUS;
            while(!go) {
                if(line.length() > 0){//player name can be anything as long as it's at least 1 char
                    p2Name = line;
                    go = true;
                } else {//user input not correct, re-prompt
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
                    aiAlg = Character.toUpperCase(line.charAt(0));
                    go = true;
                } else {
                    System.out.println("That's not valid, enter either A or M");
                    System.out.println(ALGORITM);
                    line = scanner.nextLine().toUpperCase();
                }
            }

        }
        go = false;

        output(WHICH_FIRST, ps);//who will go first
        line = scanner.nextLine().replaceAll("\\s+", "");//remove spaces
        int first = 0;
        while(!go){
            if(line.length() != 1 && (line.charAt(0) != '1' || line.charAt(0) != '2')){//confirm only 1 int and it's 1 or 2
                output("The only options here are 1 or 2.", ps);
                output(WHICH_FIRST, ps);
                line = scanner.nextLine();
            }else{
                first = Integer.parseInt(line);
                go = true;
            }
        }

        go = false;

        player = first;//which player will go first

        Board board;

        //assign max and min player
        if(player == 1){
            p1 = new Player(AI_STATUS, player1Token, p1Name, true);
            p2 = new Player(p2AI, player2Token, p2Name, false);
            board = new Board(p1, p2);
        }else{
            p1 = new Player(AI_STATUS, player1Token, p1Name, false);
            p2 = new Player(p2AI, player2Token, p2Name, true);
            board = new Board(p2, p1);
        }

        output("", ps);
        output("PLAYER 1: " + p1.toString() + "\n", ps);
        output("PLAYER 2: " + p2.toString() + "\n", ps);
        output(board.getGameState().toString(), ps);//initial game state
        boolean gameDone = false;
        while(!gameDone){//until a winner is declared AKA the game LOOP!
            Player current;
            //update current player
            if(player % 2 == 0){
                current = p2;
            }else{
                current = p1;
            }

            board.getGameState().setPlayer(current);

            if(current.isAI()){//Skynet is coming for us!
                long startTurn = System.currentTimeMillis();//measures the start time for each turn
                output(current.getName()+"'s turn", ps);//print out current player's name
                aiAlg = Character.toUpperCase(aiAlg);//uppercase really is the best case

                if(aiAlg == MINMAX){//we're taking the slow route
                    PentagoNode currentNode = board.minmax(board.getGameState(), TREE_DEPTH, current, MINMAX);
                    //find the first move to take
                    while(!currentNode.getParent().equals(board.getGameState()) && currentNode.getParent()!= null){
                        currentNode = currentNode.getParent();
                    }

                    board.setGameState(currentNode);//update the game state
                    output("MOVE:", ps);
                    output(board.getGameState().toString(), ps);//print it out!
                    int result = board.getGameState().winState();
                    if(result != -1){//game is over
                        gameDone = true;
                        winCheck(current, result, p1, p2, ps);
                    }
                    if(!gameDone){
                        currentNode = board.minmax(board.getGameState(), TREE_DEPTH, current, RIGHT);
//                        currentNode = newNode;
                        while (!currentNode.getParent().equals(board.getGameState()) && currentNode.getParent() != null){
                            currentNode = currentNode.getParent();
                        }
                        board.setGameState(currentNode);
                        output("ROTATION: ", ps);
                        output(board.getGameState().toString(), ps);
                        result = board.getGameState().winState();
                        if(result != -1){
                            gameDone = true;
                            winCheck(current, result, p1, p2, ps);
                        }
                    }
                }else if(aiAlg == ALPHA){
                    PentagoNode temp = board.getGameState();
                    PentagoNode newNode = board.alphaBetaPrune(temp, TREE_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, current, MINMAX);
                    PentagoNode currentNode = newNode;

                    while(!currentNode.getParent().equals(temp) && currentNode.getParent()!= null){
                        currentNode = currentNode.getParent();
                    }

                    board.setGameState(currentNode);
                    output(board.getGameState().toString(), ps);
                    int result = board.getGameState().winState();
                    if(result != -1){
                        gameDone = true;
                        winCheck(current, result, p1, p2, ps);
                    }

                    if(!gameDone){
                        temp = board.getGameState();
                        newNode = board.alphaBetaPrune(board.getGameState(), TREE_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, current, RIGHT);

                        currentNode = newNode;
                        while (!currentNode.getParent().equals(temp) && currentNode.getParent() != null){
                            currentNode = currentNode.getParent();
                        }
                        board.setGameState(currentNode);

                        output(board.getGameState().toString(), ps);
                        result = board.getGameState().winState();
                        if(result != -1){
                            gameDone = true;
                            winCheck(current, result, p1, p2, ps);
                        }
                    }

                }
                long endTurn = System.currentTimeMillis();
                System.out.println(endTurn - startTurn + " ms\n"); // How long did the AI take to make a move
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
                    boolean innerGo = false;
                    while(!innerGo){
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
                            innerGo = true;
                        }else{
                            System.out.println("Something seems wrong with your input, please try again");
                            System.out.println(PIECE_SPOT);
                            line = scanner.nextLine();
                        }
                    }

                    ArrayList<Integer> coords = spotToArray.get(spot);
                    if(!board.getGameState().changeSpace(spotQuad, coords.get(0), coords.get(1), current.getPiece())){
                        System.out.println("\nTry again  " + current.getName());
                        System.out.println(board.getGameState().toString());

                        System.out.println(PIECE_SPOT);
                        line = scanner.nextLine();

                    }else{
                        go = true;
                    }
                }
                go = false;
                output("MOVE: ", ps);
                output(board.getGameState().toString(), ps);
                int result = board.getGameState().winState();
                if(result != -1){
                    gameDone = true;
                    winCheck(current,result,p1, p2, ps);
                }
                if(!gameDone){//only enter if the game is still going after piece placement
                    if(direction == LEFT){
                        board.getGameState().rotateLeft(rotateQuad);
                    }else if(direction == RIGHT){
                        board.getGameState().rotateRight(rotateQuad);
                    }
                    output("ROTATION: ", ps);
                    output(board.getGameState().toString(), ps);
                    result = board.getGameState().winState();
                    if(result != -1){
                        gameDone = true;
                        winCheck(current,result,p1, p2, ps);
                    }
                }
            }
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

    /* helper method for print out winner*/
    static void winCheck(Player current, int result, Player p1, Player p2, PrintStream ps){
        if(result == 0){//tie game
            output("THE GAME'S A TIE!", ps);
        }else if(result == 1){//white won
            if(current.getPiece() == WHITE){
                output(current.getName() + WON, ps);
            }else{
                if(current.equals(p1)){
                    output(p2.getName() + WON, ps);
                }else{
                    output(p1.getName() + WON, ps);
                }
            }
        } else if(result == 2){// black win
            if(current.getPiece() == BLACK){
                output(current.getName() + WON, ps);
            }else{
                if(current.equals(p1)){
                    output(p2.getName() + WON, ps);
                }else{
                    output(p1.getName() + WON, ps);
                }
            }
        }
    }
}
