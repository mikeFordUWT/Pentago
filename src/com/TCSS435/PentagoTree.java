package com.TCSS435;

/**
 * Created by Mike on 7/26/16.
 */
public class PentagoTree {
    private PentagoNode myRoot;
    private int myDepth;


    public PentagoTree(PentagoNode theRoot){
        myRoot = theRoot;
        myDepth = 0;
    }

    public PentagoNode getRoot(){
        return myRoot;
    }

    public int getDepth(){
        return myDepth;
    }

    public void setDepth(int newDepth){
        myDepth = newDepth;
    }

}
