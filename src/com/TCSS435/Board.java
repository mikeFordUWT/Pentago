package com.TCSS435;

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
    public PentagoNode minmax(int theDepth, Player player){
        String toReturn = "";
        PentagoNode root = myGameState;
        PentagoTree tree = new PentagoTree(root);

        PentagoNode current = root;

        if(player.isMax()){

        }else{

        }
        boolean done = false;
//        while(!done){
//
//        }
        return current;
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

        if(player.isMax()){

        }else{

        }

        boolean done = false;
//        while(!done){
//
//        }
        return toReturn;
    }
}
