package com.tipumc;

import java.util.Collections;

public class Solver {

    static IsBox isBox = new IsBox();
    static IsGoal isGoal = new IsGoal();

    public static Iterable<Direction> solve(State state)
    {
        Position player = state.getPlayer();
        Search.Result boxToGoal = new Search.Result();
        boxToGoal.state = state;
        int index = 0;

        for (Position box : state.boxes)
        {
            boxToGoal = Search.findBoxPath(boxToGoal.state, isGoal, box.x, box.y, boxToGoal.state.player.x, boxToGoal.state.player.y, index);
            index++;
        }
        
        return Search.getPlayerPath(boxToGoal.state);
    }
}
