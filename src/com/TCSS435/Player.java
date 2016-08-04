package com.TCSS435;

/**
 * A Player class that represents a player
 */
public class Player {

    private boolean myAI;
    private char myPiece;
    private String myName;

    /**
     * Constructor for Player.
     *
     * @param theAI is it a human or a death robot?
     */
    public Player(boolean theAI, char thePiece, String name){
        myAI = theAI;
        myPiece = thePiece;
        myName = name;
    }

    /**
     * Getter for AI_STATUS Status
     * @return
     */
    public boolean isAI(){
        return myAI;
    }

    public char getPiece(){
        return myPiece;
    }

    public String getName(){
        return myName;
    }
}
