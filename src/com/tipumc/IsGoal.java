package com.tipumc;

public class IsGoal implements SearchTest {

    @Override
    public boolean isEnd(State state, int x, int y) {
        return state.isGoal(x, y);
    }
}
