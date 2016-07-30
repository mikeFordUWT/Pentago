package com.TCSS435;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Mike on 7/26/16.
 */
public class PentagoNode {

    private char[][] myQ1;
    private char[][] myQ2;
    private char[][] myQ3;
    private char[][] myQ4;
    private ArrayList<PentagoNode> myChildren;

    public PentagoNode(char[][] q1, char[][] q2, char[][] q3, char[][] q4){
        myQ1 = q1;
        myQ2 = q2;
        myQ3 = q3;
        myQ4 = q4;

        myChildren = new ArrayList<>();
    }



    /**
     *
     * @return
     */
    @Override
    public String toString(){
        String divder = "+-------+-------+\n";
        StringBuilder output = new StringBuilder(divder);
        output.append("| " + myQ1[0][0] + " " + myQ1[0][1] + " "+ myQ1[0][2] + " | " + myQ2[0][0] + " " + myQ2[0][1] + " " + myQ2[0][2] + " |\n");
        output.append("| " + myQ1[1][0] + " " + myQ1[1][1] + " "+ myQ1[1][2] + " | " + myQ2[1][0] + " " + myQ2[1][1] + " " + myQ2[1][2] + " |\n");
        output.append("| " + myQ1[2][0] + " " + myQ1[2][1] + " "+ myQ1[2][2] + " | " + myQ2[2][0] + " " + myQ2[2][1] + " " + myQ2[2][2] + " |\n");
        output.append(divder);
        output.append("| " + myQ3[0][0] + " " + myQ3[0][1] + " "+ myQ3[0][2] + " | " + myQ4[0][0] + " " + myQ4[0][1] + " " + myQ4[0][2] + " |\n");
        output.append("| " + myQ3[1][0] + " " + myQ3[1][1] + " "+ myQ3[1][2] + " | " + myQ4[1][0] + " " + myQ4[1][1] + " " + myQ4[1][2] + " |\n");
        output.append("| " + myQ3[2][0] + " " + myQ3[2][1] + " "+ myQ3[2][2] + " | " + myQ4[2][0] + " " + myQ4[2][1] + " " + myQ4[2][2] + " |\n");
        output.append(divder);


        return output.toString();
   }
}
