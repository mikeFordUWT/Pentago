package com.TCSS435;

import java.util.ArrayList;

/**
 * Board class that acts as an intermediate between PentagoNode and PantagoGame
 *
 * @author Michael Ford
 * TCSS 435
 * Summer 2016
 */
class Board{
    private PentagoNode myGameState;
    private Player maxPlayer;
    private Player minPlayer;

    /**
     * Non-parametrized constructor for Board.
     */
    public Board(Player max, Player min){
        myGameState = new PentagoNode(0, null, max);
        maxPlayer = max;
        minPlayer = min;
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
     * Update the Board's current game state.
     * @param newNode
     */
    public void setGameState(PentagoNode newNode){
        myGameState = newNode;
    }

    /**
     * A recursive Minimax implementation
     *
     * @param theNode the node to be used in each recursive call
     * @param theDepth how deep of a tree do we have?
     * @param player the player that is currently going
     * @param whichMove is it a rotation or placement
     * @return the final node with the best possible score.
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
    public PentagoNode minmax(PentagoNode theNode, int theDepth, Player player, char whichMove){

        PentagoNode tempNode = new PentagoNode(theNode.getDepth(), theNode, player);
        tempNode.setParent(theNode.getParent());
        ArrayList<PentagoNode> moves;
        ArrayList<PentagoNode> toReturn = new ArrayList<>();

        if(whichMove == 'R'){
            moves = getRotations(player, tempNode);
        }else{
            moves = getMoves(player, tempNode);
        }

        if(theDepth == 0 || moves.size() == 0){
            toReturn.add(tempNode);
            return tempNode;
        }

        if(player.isMax()){
            int bestValue = Integer.MIN_VALUE;
            for(int i = 0; i < moves.size(); i++){

                PentagoNode v = minmax(moves.get(i), theDepth - 1, minPlayer, whichMove);
                if(v.getValue() > bestValue){
                    if(toReturn.size()>0){
                        toReturn.remove(0);
                    }

                    toReturn.add(v);
                    bestValue = v.getValue();
                }
            }
        }else{
            int bestValue = Integer.MAX_VALUE;
            for(int i = 0; i< moves.size(); i++){
                PentagoNode v = minmax(moves.get(i), theDepth -1, maxPlayer, whichMove);
                if(v.getValue() < bestValue){
                    if(toReturn.size()>0){
                        toReturn.remove(0);
                    }
                    toReturn.add(v);
                    bestValue = v.getValue();
                }
            }
        }
        return toReturn.get(0);
    }


    /**
     * A Minimax optimization in the form of Alpha-Beta Pruning.
     *
     * @param theNode The Node to be evaulated
     * @param theDepth Limits the depth of the tree
     * @param alpha The alpha initially set to MIN
     * @param beta The Beta initially set to MAX
     * @param player the current player
     * @param whichMove rotation or placement
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
    public PentagoNode alphaBetaPrune(PentagoNode theNode, int theDepth, int alpha, int beta, Player player, char whichMove){
        PentagoNode tempNode = new PentagoNode(theNode.getDepth(), theNode, player);
        tempNode.setParent(theNode.getParent());
        ArrayList<PentagoNode> toReturn = new ArrayList<>();
        ArrayList<PentagoNode> moves;

        if(whichMove == 'R') {
            moves = getRotations(player, tempNode);
        }else{
            moves = getMoves(player, tempNode);
        }

        if(theDepth == 0 || moves.size() == 0){
            toReturn.add(tempNode);
            return tempNode;
        }
        if(player.isMax()){
            int bestValue = Integer.MIN_VALUE;
            toReturn.add(moves.get(0));
            for(int i = 0; i < moves.size(); i++){
                PentagoNode v = alphaBetaPrune(moves.get(i), theDepth - 1, alpha, beta, minPlayer, whichMove);

                bestValue = Integer.max(bestValue, v.getValue());

                if(bestValue > alpha){
                    alpha = bestValue;
                    toReturn.remove(0);
                    toReturn.add(0, v);
                }
                if(beta <= alpha){
                    break;
                }
            }

            return toReturn.get(0);
        }else{
            int bestValue = Integer.MAX_VALUE;
            toReturn.add(moves.get(0));
            for(int i = 0; i < moves.size(); i++){
                PentagoNode v = alphaBetaPrune(moves.get(i), theDepth - 1, alpha, beta, maxPlayer, whichMove);
                bestValue = Integer.min(bestValue, v.getValue());

                if(bestValue < beta){
                    beta = bestValue;
                    toReturn.remove(0);

                    toReturn.add(0, v);
                }

                if(beta <= alpha){
                    break;
                }

            }
            return toReturn.get(0);
        }



    }

    /* Helper method that returns the possible moves for a given node*/
    private ArrayList<PentagoNode> getMoves(Player player, PentagoNode theNode){
        ArrayList<PentagoNode> moves = new ArrayList<>();
        char[][] state = theNode.getState();
        for(int i = 0; i< state.length; i++){
            for(int j = 0; j < state.length; j++){
                if(state[i][j] == '.'){
//                    PentagoNode current = new PentagoNode(theNode.getDepth() + 1, theNode, player);//set current to empty spot
                    //Change the given spot, and update the data in the node
                    if(i < 3 && j < 3){//Q1
                        PentagoNode temp = new PentagoNode(theNode.getDepth() + 1, theNode, player);
                        temp.changeSpace(1,i, j, player.getPiece());
                        moves.add(temp);
                    }else if(i < 3 && j >=3){ //Q2
                        PentagoNode temp = new PentagoNode(theNode.getDepth() + 1, theNode, player);
                        temp.changeSpace(2, i, j-3, player.getPiece());
                        moves.add(temp);
                    }else if(i >= 3 && j < 3){ // Q3
                        PentagoNode temp = new PentagoNode(theNode.getDepth() + 1, theNode, player);
                        temp.changeSpace(3, i-3, j, player.getPiece());
                        moves.add(temp);
                    }else if(i>=3 && j >= 3){ // Q4
                        PentagoNode temp = new PentagoNode(theNode.getDepth() + 1, theNode, player);
                        temp.changeSpace(4, i-3, j-3, player.getPiece());
                        moves.add(temp);
                    }
                }
            }
        }


        return moves;
    }

    /* Helper method that returns all possible rotations */
    private ArrayList<PentagoNode> getRotations(Player player, PentagoNode theNode){
        ArrayList<PentagoNode> moves = new ArrayList<>();
        PentagoNode Q1L = rotateAndAdd(theNode, 'l', player, 1);
        moves.add(Q1L);
        PentagoNode Q1R = rotateAndAdd(theNode, 'r', player, 1);
        moves.add(Q1R);
        PentagoNode Q2L = rotateAndAdd(theNode, 'l', player, 2);
        moves.add(Q2L);
        PentagoNode Q2R = rotateAndAdd(theNode, 'r', player, 2);
        moves.add(Q2R);
        PentagoNode Q3L = rotateAndAdd(theNode, 'l', player, 3);
        moves.add(Q3L);
        PentagoNode Q3R = rotateAndAdd(theNode, 'r', player, 3);
        moves.add(Q3R);
        PentagoNode Q4L = rotateAndAdd(theNode, 'l', player, 4);
        moves.add(Q4L);
        PentagoNode Q4R = rotateAndAdd(theNode, 'r', player, 4);
        moves.add(Q4R);

        return moves;
    }

    /* Helper method that creates a new node with rotation*/
    private PentagoNode rotateAndAdd(PentagoNode theNode, char direction, Player player, int quad){
        PentagoNode newNode = new PentagoNode(theNode.getDepth()+1, theNode, player);
        if(direction == 'l'){
            newNode.rotateLeft(quad);
        } else if(direction == 'r'){
            newNode.rotateRight(quad);
        }
        return newNode;
    }
}
