package com.tipumc.test;
import com.tipumc.*;

import java.util.ArrayList;
import java.util.Vector;

public class SearchTests
{
    public static void Assert(boolean l) throws Exception
    {
        if (!l)
        {
            throw new Exception();
        }
    }

    private static <T> boolean elementOf(Iterable<T> iter, T v)
    {
        for (T elem : iter)
        {
            if (elem.equals(v))
            {
                return true;
            }
        }
        return false;
    }

    public static void testFindAll() throws Exception
    {
        Vector<String> board = Main.loadBoard("maps/boxes.in");
        Map map = new Map(board);
        State state = new State(map, board);

        ArrayList<Position> positions = Search.findAll(state, new IsBox(), state.getPlayer().x, state.getPlayer().y);

        Assert (elementOf(positions, new Position(2, 2)));
    }
}