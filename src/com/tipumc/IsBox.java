package com.tipumc;

public class IsBox implements SearchTest {

    @Override
    public boolean isEnd(State state, int x, int y) {
        return state.isBox(x, y);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
