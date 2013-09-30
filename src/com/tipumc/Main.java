package com.tipumc;
import java.io.*;
import java.util.Vector;


public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException, Exception {
        Vector<String> board = new Vector<String>();

        BufferedReader br = new BufferedReader(new FileReader("maps/all.slc00001.in"));
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String line;
        while(br.ready()) {
            line = br.readLine();
            board.add(line);
        } // End while


        System.out.println("Board size "+board.size());
        Map map = new Map(board);
        
        System.out.println(map.toString());
        State initialState = new State(map, board);

        // Access
        //char = board.get(row).charAt(col);

        System.out.println("U R R U");
    } // main
} // End Main