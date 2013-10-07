package com.tipumc;

public final class Position {
    public Position()
    {
        this.x = this.y = 0;
    }

    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
		
    }

    public boolean equals(Object o)
    {
        return o.getClass() == this.getClass() && equals((Position)o);
    }

    public boolean equals(Position p)
    {
        return x == p.x && y == p.y;
    }

    public String toString()
    {
        return "{" + x + ", " + y + "}";
    }

    public int x, y;
}
