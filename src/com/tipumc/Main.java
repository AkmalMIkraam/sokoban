package com.tipumc;
import java.io.*;
import java.util.ArrayList;


public class Main {

    public static ArrayList<String> loadBoard(String filename) throws IOException
    {
        ArrayList<String> board = new ArrayList<String>();

        //BufferedReader br = new BufferedReader(new FileReader(filename));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

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

        ArrayList<String> board = loadBoard("maps/all.slc00100.in");

        Map map = new Map(board);
        Map invertMap = Map.inverted(map);
        System.err.println(map.toString());
        State initialState = new State(invertMap, map.inverseMap, board);

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