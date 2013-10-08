package com.tipumc;

import java.util.ArrayList;
import java.util.Collections;

public class Solver {

    static IsBox isBox = new IsBox();
    static IsGoal isGoal = new IsGoal();

    public static Iterable<Direction> solve(State state)
    {
        Position player = state.getPlayer();
        State boxToGoal = null;
        int index = 0;

        for (Position box : state.boxes)
        {
            boxToGoal = Search.findBoxPath(state, isGoal, state.player.x, state.player.y, index);
            index++;
        }
        
        return Search.getPlayerPath(boxToGoal);
    }
}
