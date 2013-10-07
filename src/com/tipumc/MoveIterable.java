package com.tipumc;

import java.util.ArrayList;
import java.util.Iterator;

public class MoveIterable implements Iterable<Direction> {

    public MoveIterable(ArrayList<Position> positions)
    {
        this.positions = positions;
    }

    @Override
    public Iterator<Direction> iterator() {
        return new Iterator<Direction>() {
            @Override
            public boolean hasNext() {
                return previous < positions.size() - 1;
            }

            @Override
            public Direction next() {
                Position prev = positions.get(previous);
                Position current = positions.get(previous + 1);
                Direction result = null;
                if (current.x - prev.x == -1)
                {
                    result = Direction.LEFT;
                }
                if (current.x - prev.x == 1)
                {
                    result = Direction.RIGHT;
                }
                if (current.y - prev.y == -1)
                {
                    result = Direction.UP;
                }
                if (current.y - prev.y == 1)
                {
                    result = Direction.DOWN;
                }
                return result;
            }

            @Override
            public void remove() {
                previous++;
            }

            int previous;
        };
    }
    ArrayList<Position> positions;
}
