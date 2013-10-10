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
        System.err.println(map.toString());
        State initialState = new State(map, board);

        Iterable<Direction> moves = Solver.solve(initialState);
        if (moves != null)
        {
            for (Direction move : moves)
            {
                System.out.print(move.toString());
            }
        }
        else
        {
            System.out.println("no path");
        }
    } // main
} // End Main