package com.tipumc;

import java.util.ArrayList;
import java.util.Iterator;

public class MoveIterable implements Iterable<Move> {

    public MoveIterable(ArrayList<Position> positions)
    {
        this.positions = positions;
    }

    @Override
    public Iterator<Move> iterator() {
        return new Iterator<Move>() {
            @Override
            public boolean hasNext() {
                return previous < positions.size() - 1;
            }

            @Override
            public Move next() {
                Position prev = positions.get(previous);
                Position current = positions.get(previous + 1);
                Move result = Move.NONE;
                if (current.x - prev.x == -1)
                {
                    result = Move.LEFT;
                }
                if (current.x - prev.x == 1)
                {
                    result = Move.RIGHT;
                }
                if (current.y - prev.y == -1)
                {
                    result = Move.UP;
                }
                if (current.y - prev.y == 1)
                {
                    result = Move.DOWN;
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
