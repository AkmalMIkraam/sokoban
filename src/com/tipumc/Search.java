package com.tipumc;



import java.util.ArrayList;
import java.util.Stack;

public final class Search {

    public static final class Result
    {
        public Position endPosition;
        public ArrayList<Move> path;
    }

    public static Result dfs(State state, SearchTest test, int startX, int startY)
    {
        /* Create stack to store nodes */
        Stack<Position> nodes = new Stack<Position>();
        /* Create integers that is needed */
        int width = state.getWidth();
        int height = state.getHeight();
        /* Create 2D array to store visited positions */
        Move visitedPositions[][] = new Move[width][height];
        /* Declare an positionobject to pop to from stack */
        Position currentPosition = new Position(startX, startY);
        int x = currentPosition.x, y = currentPosition.y;
        
        /* Push the start position node, for the search on the stack */
        nodes.push(currentPosition);
        
        /* Search for a path to the wanted goal */
        while (!test.isEnd(state, currentPosition.x, currentPosition.y)){
            if(nodes.empty()){
                System.out.println("no path");
                return null;
            } else {
                currentPosition = nodes.pop();
            }
            
            x = currentPosition.x;
            y = currentPosition.y;
            
            /* Create child nodes */
            if (state.isFree(x, y-1) & visitedPositions[x][y-1] == null){
                Position possibleStep = new Position(x, y-1);
                visitedPositions[x][y-1] = Move.UP;
            }
            if (state.isFree(x, y+1) & visitedPositions[x][y+1] == null){
                Position possibleStep = new Position(x, y+1);
                visitedPositions[x][y+1] = Move.DOWN;
            }
            if (state.isFree(x-1, y) & visitedPositions[x-1][y] == null){
                Position possibleStep = new Position(x-1, y);
                visitedPositions[x-1][y] = Move.LEFT;
            }
            if (state.isFree(x+1, y) & visitedPositions[x+1][y] == null){
                Position possibleStep = new Position(x+1, y);
                visitedPositions[x+1][y] = Move.RIGHT;
            }
        }

        Result result = new Result();
        result.path = getPath(visitedPositions, x, y, startX, startY);
        result.endPosition = new Position(x, y);
        return result;
    }

    private static ArrayList<Move> getPath(Move[][] moves, int fromX, int fromY, int toX, int toY)
    {
        int x = fromX, y = fromY;
        ArrayList<Move> path = new ArrayList<Move>();
        while (x != toX && y != toY)
        {
            path.add(moves[x][y]);
        }
        return path;
    }
}
