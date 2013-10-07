package com.tipumc;

public enum Move {
    LEFT,
    RIGHT,
    UP,
    DOWN;

    void getDirection(Position dir)
    {
        dir.x = dir.y = 0;
        switch (this)
        {
        case LEFT:
            dir.x = -1;
        case RIGHT:
            dir.x = 1;
        case UP:
            dir.y = -1;
        case DOWN:
            dir.y = 1;
        }
    }

    @Override
    public String toString() {
        switch (this)
        {
            case LEFT:
                return "L";
            case RIGHT:
                return "R";
            case UP:
                return "U";
            case DOWN:
                return "D";
            default:
                return null;
        }
    }
}
