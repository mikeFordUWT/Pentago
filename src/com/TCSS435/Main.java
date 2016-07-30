package com.TCSS435;

public class Main {

    public static void main(String[] args) {

        boolean done = false;
	    Board newB = new Board();
        System.out.println(newB.toString());
        newB.changeSpace(1, 1, 2, 'w');
        newB.changeSpace(1, 0, 0, 'b');
        newB.changeSpace(1, 2, 1, 'w');
        System.out.println(newB.toString());
        newB.rotateLeft(1);
        System.out.println(newB.toString());
    }
}
