package com.tipumc;



import java.util.*;


public final class Search {

    public static final class Result
    {
        public Position endPosition;
        public ArrayList<Direction> path;
        public State state;
    }

    public static Result dfs(State state, SearchTest test, int startX, int startY)
    {
        /* Create stack to store nodes */
        Stack<Position> nodes = new Stack<Position>();
        /* Create integers that is needed */
        int width = state.getWidth();
        int height = state.getHeight();
        /* Create 2D array to store visited positions */
        Direction visitedPositions[][] = new Direction[width][height];
        /* Declare an positionobject to pop to from stack */
        Position currentPosition = new Position(startX, startY);
        int x = currentPosition.x, y = currentPosition.y;
        
        /* Push the start position node, for the search on the stack */
        nodes.push(currentPosition);
        
        /* Search for a path to the wanted goal */
        while (!nodes.empty()){
            currentPosition = nodes.pop();
            x = currentPosition.x;
            y = currentPosition.y;
            
            if (test.isEnd(state, currentPosition.x, currentPosition.y)){
                Result result = new Result();
                result.path = getPath(visitedPositions, currentPosition.x, currentPosition.y, startX, startY);
                result.endPosition = new Position(currentPosition.x, currentPosition.y);
                return result;
            }

            /* Create child nodes */
            testAddPosition(state, test, nodes, visitedPositions, x, y-1, Direction.UP);
            testAddPosition(state, test, nodes, visitedPositions, x, y+1, Direction.DOWN);
            testAddPosition(state, test, nodes, visitedPositions, x-1, y, Direction.LEFT);
            testAddPosition(state, test, nodes, visitedPositions, x+1, y, Direction.RIGHT);
        }
        return null;
    }

    public static Result bfs(State state, SearchTest test, int startX, int startY)
    {
        /* Create stack to store nodes */
        Queue<Position> nodes = new LinkedList<Position>();
        /* Create integers that is needed */
        int width = state.getWidth();
        int height = state.getHeight();
        /* Create 2D array to store visited positions */
        Direction visitedPositions[][] = new Direction[width][height];
        /* Declare an positionobject to pop to from stack */
        Position currentPosition = new Position(startX, startY);
        int x = currentPosition.x, y = currentPosition.y;

        /* Push the start position node, for the search on the stack */
        nodes.add(currentPosition);

        /* Search for a path to the wanted goal */
        while (!nodes.isEmpty()){
            currentPosition = nodes.remove();
            x = currentPosition.x;
            y = currentPosition.y;

            if (test.isEnd(state, currentPosition.x, currentPosition.y)){
                Result result = new Result();
                result.path = getPath(visitedPositions, currentPosition.x, currentPosition.y, startX, startY);
                result.endPosition = new Position(currentPosition.x, currentPosition.y);
                return result;
            }

            /* Create child nodes */
            testAddPosition(state, test, nodes, visitedPositions, x, y-1, Direction.UP);
            testAddPosition(state, test, nodes, visitedPositions, x, y+1, Direction.DOWN);
            testAddPosition(state, test, nodes, visitedPositions, x-1, y, Direction.LEFT);
            testAddPosition(state, test, nodes, visitedPositions, x+1, y, Direction.RIGHT);
        }
        return null;
    }

    private static ArrayList<Direction> getPath(Direction[][] moves, int fromX, int fromY, int toX, int toY)
    {
        int x = fromX, y = fromY;
        //System.err.println(toX + " TO " + toY);
        //System.err.println(fromX + " FROM " + fromY);
        ArrayList<Direction> path = new ArrayList<Direction>();
        Position dir = new Position();
        while ((y != toY) | (x != toX) )
        {
            //System.err.println(x + " B " + y);

            Direction move = moves[x][y];
            path.add(move);

            move.getDirection(dir);
            x -= dir.x;
            y -= dir.y;
            //System.err.println(x + " A " + y);
        }
        return path;
    }

    public static ArrayList<Position> findAll(State state, SearchTest test, int startX, int startY)
    {
        /* Create stack to store nodes */
        Stack<Position> nodes = new Stack<Position>();
        /* Create integers that is needed */
        int width = state.getWidth();
        int height = state.getHeight();
        /* Create 2D array to store visited positions */
        Direction visitedPositions[][] = new Direction[width][height];
        /* Declare an positionobject to pop to from stack */
        Position currentPosition = new Position(startX, startY);
        int x = currentPosition.x, y = currentPosition.y;

        /* Push the start position node, for the search on the stack */
        nodes.push(currentPosition);

        ArrayList<Position> result = new ArrayList<Position>();
        /* Search for a path to the wanted goal */
        while (!nodes.empty()){
            currentPosition = nodes.pop();
            x = currentPosition.x;
            y = currentPosition.y;
            if (test.isEnd(state, x, y))
            {
                result.add(new Position(x, y));
            }

            /* Create child nodes */
            testAddPosition(state, test, nodes, visitedPositions, x, y-1, Direction.UP);
            testAddPosition(state, test, nodes, visitedPositions, x, y+1, Direction.DOWN);
            testAddPosition(state, test, nodes, visitedPositions, x-1, y, Direction.LEFT);
            testAddPosition(state, test, nodes, visitedPositions, x+1, y, Direction.RIGHT);
        }

        return result;
    }

    private static void testAddPosition(State state, SearchTest test, Collection<Position> nodes, Direction[][] visitedPositions, int x, int y, Direction move)
    {
        if (visitedPositions[x][y] == null & (state.isFree(x, y) | test.isEnd(state, x, y))){
            Position possibleStep = new Position(x, y);
            visitedPositions[x][y] = move;
            nodes.add(possibleStep);
        }
    }


    public static State findBoxPath(State state, SearchTest test, int playerStartX, int playerStartY, int boxIndex)
    {
        /* Create stack to store nodes */
        Queue<State> nodes = new LinkedList<State>();
        /* Create integers that is needed */
        int width = state.getWidth();
        int height = state.getHeight();
        /* Create 2D array to store visited positions */
        Direction visitedPositions[][] = new Direction[width][height];
        /* Declare an positionobject to pop to from stack */
        State currentState = state;
        currentState.player = new Position(playerStartX, playerStartY);
        int x /*= currentPosition.x*/, y /*= currentPosition.y*/;

        /* Push the start position node, for the search on the stack */
        nodes.add(currentState);

        /* Search for a path to the wanted goal */
        while (!nodes.isEmpty()){
            currentState = nodes.remove();
            

            if (test.isEnd(currentState, 0, 0)){
                return currentState;
            }
            boxIndex = 0;

            /* Create child nodes */
            for (Position box : currentState.boxes)
            {
                x = box.x;
                y = box.y;
                
                //System.err.println(x + " " + y);
                
                testBoxAddPosition(currentState, nodes, visitedPositions, currentState.player, x, y - 1, x, y - 2, Direction.UP, boxIndex);
                testBoxAddPosition(currentState, nodes, visitedPositions, currentState.player, x, y + 1, x, y + 2, Direction.DOWN, boxIndex);
                testBoxAddPosition(currentState, nodes, visitedPositions, currentState.player, x - 1, y, x - 2, y, Direction.LEFT, boxIndex);
                testBoxAddPosition(currentState, nodes, visitedPositions, currentState.player, x + 1, y, x + 2, y, Direction.RIGHT, boxIndex);
                
                boxIndex++;
            }
            
        }
        System.err.println("no path");
        System.exit(0);
        return null;
    }

    private static void testBoxAddPosition(State state, Queue<State> nodes, Direction[][] visitedPositions, Position player, int boxX, int boxY, int boxX2, int boxY2, Direction move, int index)
    {
        //System.err.println(boxX2 + " " + boxY2);
        //System.err.println(boxX + " " + boxY);
        if ((visitedPositions[boxX][boxY] == null && state.isFree(boxX, boxY) ) && state.isFree(boxX2, boxY2))
        {
            //System.err.println("IS FREE");
            Position boxPos = state.boxes.get(index);
            //Check that the player can actually move into position to pull the box
            Result result = Search.bfs(state, new IsAtPosition(move, boxPos.x, boxPos.y), player.x, player.y);
            if (result != null)
            {
                ArrayList<Position> boxes = new ArrayList<Position>(state.boxes);
                boxes.set(index, new Position(boxX, boxY));
                State possibleStep = new State(state.map, new Position(boxX2, boxY2), boxes, result.path, state, state.goals);
                Collections.reverse(possibleStep.playerPath);
                possibleStep.playerPath.add(move);
                visitedPositions[boxX][boxY] = move;
                nodes.add(possibleStep);
            }
        }
    }
    
    public static ArrayList<Direction> getPlayerPath(State current){
        if (current.parent == null){
            return current.playerPath;
        } else {
            ArrayList<Direction> tempPath = getPlayerPath(current.parent);
            if (tempPath == null){
                tempPath = new ArrayList<Direction>();
                tempPath.add(current.playerPath.get(current.playerPath.size()-1));
                //Collections.reverse(tempPath);
            } else {
                tempPath.addAll(current.playerPath);
            }
            System.err.println(current.toString());
            System.err.println("playerPath : " + current.playerPath);
            //System.err.println("BoxPosition: " + current.boxes.get(0).x + " " + current.boxes.get(0).y);
            return tempPath;
        }
        
    }
}
