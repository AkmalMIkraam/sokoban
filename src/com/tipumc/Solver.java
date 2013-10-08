package com.tipumc;

import java.util.ArrayList;
import java.util.Collections;

public class Solver {

    static IsBox isBox = new IsBox();
    static IsGoal isGoal = new IsGoal();

    public static Iterable<Direction> solve(State state)
    {
        Position player = state.getPlayer();
        ArrayList<State> states = new ArrayList<State>();

        for (int ii = 0; ii < state.boxes.size(); ++ii)
        {
            states.add(Search.findBoxPath(state, isGoal, player.x, player.y, ii));
        }

        return Search.getPlayerPath(states.get(0));
    }
}
