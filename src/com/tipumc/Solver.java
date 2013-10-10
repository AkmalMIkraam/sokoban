package com.tipumc;

import java.util.ArrayList;
import java.util.Collections;

public class Solver {

    static IsBox isBox = new IsBox();
    static IsGoal isGoal = new IsGoal();

    public static Iterable<Direction> solve(State state)
    {
        State boxToGoal = state;
        Search.Result result = new Search.Result();
        int index = 0;
        ArrayList<Direction> toReturn = new ArrayList<Direction>();

        //for (Position box : state.boxes)
        //{
            boxToGoal = Search.findBoxPath(boxToGoal, isGoal, boxToGoal.player.x, boxToGoal.player.y, index);
        //    index++;
        //}
        result = Search.bfs(boxToGoal, new IsAtPosition(state.player.x, state.player.y), boxToGoal.player.x, boxToGoal.player.y);
        Collections.reverse(result.path);
        toReturn = Search.getPlayerPath(boxToGoal);
        toReturn.addAll(result.path);
        Collections.reverse(toReturn);
        
        return toReturn;
    }
}
