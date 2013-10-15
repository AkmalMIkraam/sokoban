package com.tipumc;

import java.util.ArrayList;
import java.util.Collections;

public class Solver {

    static IsBox isBox = new IsBox();
    static IsGoal isGoal = new IsGoal();

    public static Iterable<Direction> solve(State state)
    {
        State boxToGoal;
        int px = state.player.x;
        int py = state.player.y;
        Search.Result result = new Search.Result();
        ArrayList<Position> possiblePlayerPositions = possibleStartPositions(state);
        int index = 0;
        ArrayList<Direction> toReturn = new ArrayList<Direction>();

        for (Position player : possiblePlayerPositions)
        {
            boxToGoal = Search.findBoxPath(state, isGoal, player.x, player.y, index);
            //System.err.println("Player: " + px + " " + py);
            if (boxToGoal != null){
                result = Search.bfs(boxToGoal, new IsAtPosition(px, py), boxToGoal.player.x, boxToGoal.player.y);
                if(result != null){
                    //System.err.println(result.path);
                    Collections.reverse(result.path);
                    toReturn = Search.getPlayerPath(boxToGoal);
                    toReturn.addAll(result.path);
                    Collections.reverse(toReturn);
                    return toReturn;
                } else {
                    System.err.println("No path back");
                }
            } else{
                System.err.println("No solution" + state.playerEndPos);
            }
            
        //    index++;
        }
        System.err.println("no path");
        System.exit(0);
        return null;
    }
    
    private static ArrayList<Position> possibleStartPositions(State state){
        ArrayList<Position> positions = new ArrayList<Position>();
        
        for(Position box : state.boxes){
            if(state.isFree(box.x+1,box.y)){
                positions.add(new Position(box.x+1,box.y));
            }
            if(state.isFree(box.x-1,box.y)){
                positions.add(new Position(box.x-1,box.y));
            }
            if(state.isFree(box.x,box.y+1)){
                positions.add(new Position(box.x,box.y+1));
            }
            if(state.isFree(box.x,box.y-1)){
                positions.add(new Position(box.x,box.y-1));
            }
        }
        return positions;
    }
}
