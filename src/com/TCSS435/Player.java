package com.TCSS435;

/**
 * A Player class that represents a player
 */
public class Player {

    private boolean myAI;
    private char myPiece;

    /**
     * Constructor for Player.
     *
     * @param theAI is it a human or a death robot?
     */
    public Player(boolean theAI, char thePiece){
        myAI = theAI;
        myPiece = thePiece;
    }

    /**
     * Getter for HUMAN Status
     * @return
     */
    public boolean getAISTatus(){
        return myAI;
    }

    public char getPiece(){
        return myPiece;
    }
}
