package com.tipumc;

import java.util.Collections;

public class Solver {

    static IsBox isBox = new IsBox();
    static IsGoal isGoal = new IsGoal();

    public static Iterable<Direction> solve(State state)
    {
        Position player = state.getPlayer();
        Search.Result pathToBox = Search.dfs(state, isBox, player.x, player.y);
        Position boxPosition = pathToBox.endPosition;
        
        Collections.reverse(pathToBox.path);

        Search.Result boxToGoalPath = Search.dfs(state, isGoal, boxPosition.x, boxPosition.y);

        Collections.reverse(boxToGoalPath.path);
        
        pathToBox.path.addAll(boxToGoalPath.path);
        return pathToBox.path;
    }
}
