package com.tipumc;

public interface GoalTest {

    /**
     * Interface to be used when determining the end of a search
     * @param state The state being searched
     * @param x Position
     * @param y Position
     * @return True if the tile is the searched for tile
     */
    public boolean isGoal(State state, int x, int y);
}
