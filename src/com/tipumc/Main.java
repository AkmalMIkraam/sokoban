package com.tipumc;
import java.io.*;
import java.util.Vector;


public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {
        Vector<String> board = new Vector<String>();

        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));

        String line;
        while(br.ready()) {
            line = br.readLine();
            board.add(line);
        } // End while

        Map map = new Map(board);
        State initialState = new State(map, board);

        // Access
        //char = board.get(row).charAt(col);

        System.out.println("U R R U");
    } // main
} // End Main