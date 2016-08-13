package com.TCSS435;

/**
 * A PentagoTree class for use with Pentago Nodes.
 *
 * @author Michael Ford
 * TCSS 435
 * Summer 2016
 */

public class PentagoTree {
    private PentagoNode myRoot;
    private int myDepth;

    /**
     * Constructor.
     * @param theRoot the node that will be the root
     */
    public PentagoTree(PentagoNode theRoot){
        myRoot = theRoot;
        myDepth = 0;
    }

    /**
     * Get the tree's root.
     *
     * @return the root
     */
    public PentagoNode getRoot(){
        return myRoot;
    }

    /**
     * Get the current depth of the tree.
     *
     * @return the depth of the tree
     */
    public int getDepth(){
        return myDepth;
    }

    /**
     * Set the depth of the tree
     * @param newDepth the depth the tree will be
     */
    public void setDepth(int newDepth){
        myDepth = newDepth;
    }

}
