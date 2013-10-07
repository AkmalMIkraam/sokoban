package com.tipumc;

public class IsNextToBox implements SearchTest {

    /**
     *
     * @param direction Is it a box in this direction?
     */
    public IsNextToBox(Direction direction)
    {
        direction.getDirection(dir);
    }

    @Override
    public boolean isEnd(State state, int x, int y) {
        return state.isBox(x + dir.x, y + dir.y);
    }

    Position dir = new Position();
}
