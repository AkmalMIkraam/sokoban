package com.tipumc;

public class IsNextToBox implements SearchTest {

    /**
     *
     * @param direction Is it a box in this direction?
     */
    public IsNextToBox(Direction direction, int x, int y)
    {
        direction.getDirection(dir);
        boxX = x;
        boxY = y;
    }

    @Override
    public boolean isEnd(State state, int x, int y) {
        //return state.isBox(x + dir.x, y + dir.y);
        return ((x + dir.x) == boxX & (y + dir.y) == boxY & !state.isWall(x,y) & !state.isBox(x,y));
    }

    Position dir = new Position();
    int boxX;
    int boxY;
}
