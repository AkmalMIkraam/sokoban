package com.tipumc;
import java.io.*;
import java.util.Vector;


public class Main {

    public static Vector<String> loadBoard(String filename) throws IOException
    {
        Vector<String> board = new Vector<String>();

        BufferedReader br = new BufferedReader(new FileReader(filename));
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String line;
        while(br.ready()) {
            line = br.readLine();
            board.add(line);
        } // End while

        return board;
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException, Exception {

        Vector<String> board = loadBoard("maps/test.in");

        Map map = new Map(board);
        System.out.println(map.toString());
        State initialState = new State(map, board);

        // Access
        //char = board.get(row).charAt(col);
        
        

        Iterable<Direction> moves = Solver.solve(initialState);
        for (Direction move : moves)
        {
            System.out.print(move.toString());
        }
    } // main
} // End Main