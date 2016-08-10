package com.TCSS435;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Board class that acts as an intermediate between PentagoNode and PantagoGame
 *
 * @author Michael Ford
 * TCSS 435
 * Summer 2016
 */
class Board{
//    private final char EMPTY = '.', WHITE = 'w', BLACK = 'b';
//    private final ArrayList<Integer> QUADS = new ArrayList<>(Arrays.asList(1,2,3,4));
//    private final String OCCUPIED = "That space isn't available";
//    private final int START = 0;
//    private final int MIDDLE = 3;
//    private final int BOARD_DIM = 6;
//    private final int QUAD_DIM = 3;
//    private final int END = 6;

    private PentagoNode myGameState;

    private int myBs;
    private int myWs;
    private Player playerOne;
    private Player playerTwo;

    /**
     * Non-parametrized constructor for Board.
     */
    public Board(Player player1, Player player2){
        myGameState = new PentagoNode(0, null, player1);
        playerOne = player1;
        playerTwo = player2;
    }

    /**
     * Get the Board's PentagoNode.
     *
     * @return the PentagoNode
     */
    public PentagoNode getGameState(){
        return myGameState;
    }


    /**
     * A depth limited minmax.
     *
     * @param theDepth
     * @param player
     * @return
     */
    /*
01 function minimax(node, depth, maximizingPlayer)
02     if depth = 0 or node is a terminal node
03         return the heuristic value of node

04     if maximizingPlayer
05         bestValue := −∞
06         for each child of node
07             v := minimax(child, depth − 1, FALSE)
08             bestValue := max(bestValue, v)
09         return bestValue

10     else    (* minimizing player *)
11         bestValue := +∞
12         for each child of node
13             v := minimax(child, depth − 1, TRUE)
14             bestValue := min(bestValue, v)
15         return bestValue
     */
    public PentagoNode minmax(PentagoNode theNode, int theDepth, Boolean maxPlayer){
        PentagoNode toReturn;
        PentagoNode current = theNode;
        PentagoTree tree = new PentagoTree(current);


        int bestScore = 0;
        if(maxPlayer){
            bestScore = Integer.MIN_VALUE;
//            ArrayList<PentagoNode> moves = getMoves(player, current);
            for(int i = 0; i < moves.size(); i++){

            }

        }else{
            bestScore = Integer.MAX_VALUE;
        }
        boolean done = false;
//        while(!done){
//
//        }
        return toReturn;
    }

    /**
     * A depth limited minmax with alpha beta pruning.
     *
     * @param theDepth
     * @return
     */
    /*
01 function alphabeta(node, depth, α, β, maximizingPlayer)
02      if depth = 0 or node is a terminal node
03          return the heuristic value of node
04      if maximizingPlayer
05          v := -∞
06          for each child of node
07              v := max(v, alphabeta(child, depth - 1, α, β, FALSE))
08              α := max(α, v)
09              if β ≤ α
10                  break (* β cut-off *)
11          return v
12      else
13          v := ∞
14          for each child of node
15              v := min(v, alphabeta(child, depth - 1, α, β, TRUE))
16              β := min(β, v)
17              if β ≤ α
18                  break (* α cut-off *)
19          return v
     */
    public String alphaBetaPrune(int theDepth, Player player){
        String toReturn = "";
        PentagoNode root = myGameState;
        PentagoTree tree = new PentagoTree(root);

        root.setPlayer(player);

        boolean done = false;
//        while(!done){
//
//        }
        return toReturn;
    }


    //return possible moves, with the placement first, followed by the rotations
    private ArrayList<PentagoNode> getMoves(Player player, PentagoNode theNode){
        ArrayList<PentagoNode> moves = new ArrayList<>();
        char[][] state = theNode.getState();
        for(int i = 0; i< state.length; i++){
            for(int j = 0; j < state.length; j++){
                if(state[i][j] == '.'){
                    PentagoNode current = new PentagoNode(theNode.getDepth() + 1, theNode, player);
                    //Change the given spot, and update the data in the node
                    if(i < 3 && j < 3){
                        current.changeSpace(1,i, j, player.getPiece());
                    }else if(i < 3 && j >=3){
                        current.changeSpace(2, i, j-3, player.getPiece());
                    }else if(i >= 3 && j < 3){
                        current.changeSpace(3, i-3, j, player.getPiece());
                    }else if(i>=3 && j >= 3){
                        current.changeSpace(4, i-3, j-3, player.getPiece());
                    }

                    moves.add(current);
                    if(current.winState() == -1){
                        PentagoNode Q1L = rotateAndAdd(current, 'l',player, 1);
                        moves.add(Q1L);
                        PentagoNode Q1R = rotateAndAdd(current, 'r', player, 1);
                        moves.add(Q1R);
                        PentagoNode Q2L = rotateAndAdd(current, 'l', player, 2);
                        moves.add(Q2L);
                        PentagoNode Q2R = rotateAndAdd(current, 'r', player, 2);
                        moves.add(Q2R);
                        PentagoNode Q3L = rotateAndAdd(current, 'l', player, 3);
                        moves.add(Q3L);
                        PentagoNode Q3R = rotateAndAdd(current, 'r', player, 3);
                        moves.add(Q3R);
                        PentagoNode Q4L = rotateAndAdd(current, 'l', player, 4);
                        moves.add(Q4L);
                        PentagoNode Q4R = rotateAndAdd(current, 'r', player, 4);
                        moves.add(Q4R);
                    }
                }
            }
        }


        return moves;
    }

    private PentagoNode rotateAndAdd(PentagoNode theNode, char direction, Player player, int quad){
        PentagoNode newNode = new PentagoNode(theNode.getDepth()+1, theNode, player);
        if(direction == 'l'){
            newNode.rotateLeft(quad);
        } else if(direction == 'r'){
            newNode.rotateLeft(quad);
        }
        return newNode;
    }

}
