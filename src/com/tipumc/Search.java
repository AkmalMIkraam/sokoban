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
        /* Boolean to check if path is found */
        Boolean foundObject = false;
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
        while (!foundObject){
            if(nodes.empty()){
                System.out.println("no path");
                return null;
            } else {
                currentPosition = nodes.pop();
            }
            
            if (test.isEnd(state, currentPosition.x, currentPosition.y)){
                foundObject = true;
                //System.err.println("isEnd");
                break;
            }
            
            x = currentPosition.x;
            y = currentPosition.y;
            //System.err.println(x + " " + y);
            
            /* Create child nodes */
            if (visitedPositions[x][y-1] == null && (state.isFree(x, y-1) || test.isEnd(state, x, y-1))){
                Position possibleStep = new Position(x, y-1);
                visitedPositions[x][y-1] = Move.UP;
                nodes.push(possibleStep);
            }
            if (visitedPositions[x][y+1] == null && (state.isFree(x, y+1) || test.isEnd(state, x, y+1))){
                Position possibleStep = new Position(x, y+1);
                visitedPositions[x][y+1] = Move.DOWN;
                nodes.push(possibleStep);
            }
            if (visitedPositions[x-1][y] == null && (state.isFree(x-1, y) || test.isEnd(state, x-1, y))){
                Position possibleStep = new Position(x-1, y);
                visitedPositions[x-1][y] = Move.LEFT;
                nodes.push(possibleStep);
            }
            if (visitedPositions[x+1][y] == null && (state.isFree(x+1, y) || test.isEnd(state, x+1, y))){
                Position possibleStep = new Position(x+1, y);
                visitedPositions[x+1][y] = Move.RIGHT;
                nodes.push(possibleStep);
            }
        }
        //System.err.println("Outside While");

        Result result = new Result();
        result.path = getPath(visitedPositions, currentPosition.x, currentPosition.y, startX, startY);
        result.endPosition = new Position(currentPosition.x, currentPosition.y);
        return result;
    }

    private static ArrayList<Move> getPath(Move[][] moves, int fromX, int fromY, int toX, int toY)
    {
        int x = fromX, y = fromY;
        System.err.println(toX + " TO " + toY);
        System.err.println(fromX + " FROM " + fromY);
        ArrayList<Move> path = new ArrayList<Move>();
        Position dir = new Position();
        while ((y != toY) | (x != toX) )
        {
            //System.err.println(x + " B " + y);
            
            Move move = moves[x][y];
            path.add(move);
            
            move.getDirection(dir);
            x -= dir.x;
            y -= dir.y;
            //System.err.println(x + " A " + y);
        }
        return path;
    }
}
