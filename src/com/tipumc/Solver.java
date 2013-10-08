package com.tipumc;

import java.util.ArrayList;
import java.util.Collections;

public class Solver {

    static IsBox isBox = new IsBox();
    static IsGoal isGoal = new IsGoal();

    public static Iterable<Direction> solve(State state)
    {
        State boxToGoal = state;
        int index = 0;

        for (Position box : state.boxes)
        {
            boxToGoal = Search.findBoxPath(boxToGoal, isGoal, boxToGoal.player.x, boxToGoal.player.y, index);
            index++;
        }
        
        return Search.getPlayerPath(boxToGoal);
    }
}
