package com.tipumc;

public class Solver {

    static IsBox isBox = new IsBox();
    static IsGoal isGoal = new IsGoal();

    public static Iterable<Move> solve(State state)
    {
        Position player = state.getPlayer();
        Search.Result pathToBox = Search.dfs(state, isBox, player.x, player.y);
        Position boxPosition = pathToBox.endPosition;

        Search.Result boxToGoalPath = Search.dfs(state, isGoal, boxPosition.x, boxPosition.y);

        pathToBox.path.addAll(boxToGoalPath.path);
        return pathToBox.path;
    }
}
