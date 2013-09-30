package com.tipumc;

public final class Position {
    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
		
    }

    public String toString()
    {
        return "{" + x + ", " + y + "}";
    }

    public int x, y;
}
