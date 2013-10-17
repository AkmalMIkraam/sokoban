public class IsGoal implements SearchTest {

    @Override
    public boolean isEnd(State state, int x, int y) {
        return state.isFinal();
    }
    
    public boolean isFree(State state){
        return (!state.isWall(0, 0) && !state.isBox(0, 0));
    }
}
