package com.TCSS435;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        String file = "results.txt";
        FileWriter fw = new FileWriter(file, true);
        PrintWriter outputFile = new PrintWriter(fw);
        PrintStream out = new PrintStream(new FileOutputStream("result.txt"));
        System.setOut(out);
        Scanner input = new Scanner(System.in);
        boolean gameOver = false;
	    Board newB = new Board();
        System.out.println("HOW MANY PLAYERS? [1 or 2]");

        System.out.println();
        System.out.println(newB.toString());
        newB.changeSpace(1, 1, 2, 'w');
        newB.changeSpace(1, 0, 0, 'b');
        newB.changeSpace(1, 2, 1, 'w');
        System.out.println(newB.toString());
        newB.rotateLeft(1);
        System.out.println(newB.toString());
    }

}
