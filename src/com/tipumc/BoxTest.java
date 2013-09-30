package com.tipumc;

public class BoxTest implements GoalTest {

    @Override
    public boolean isGoal(State state, int x, int y) {
        return state.isBox(x, y);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
