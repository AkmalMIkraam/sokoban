package com.tipumc;

public class IsGoal implements GoalTest {

    @Override
    public boolean isGoal(State state, int x, int y) {
        return state.isGoal(x, y);
    }
}
