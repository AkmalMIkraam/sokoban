package com.tipumc;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Iterator;

public class Solver {

    static BoxTest isBox = new BoxTest();
    static IsGoal isGoal = new IsGoal();

    public static Iterable<Move> solve(State state)
    {
        Position player = state.getPlayer();
        ArrayList<Position> pathToBox = Search.bfs(state, isBox, player.x, player.y);
        Position boxPosition = pathToBox.get(pathToBox.size() - 1);

        ArrayList<Position> boxToGoalPath = Search.bfs(state, isGoal, boxPosition.x, boxPosition.y);

        pathToBox.addAll(boxToGoalPath);
        return new MoveIterable(pathToBox);
    }
}
