public enum Direction {
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
            break;
        case RIGHT:
            dir.x = 1;
            break;
        case UP:
            dir.y = -1;
            break;
        case DOWN:
            dir.y = 1;
            break;
        }
    }

    @Override
    public String toString() {
        switch (this)
        {
            case LEFT:
                return "R";
            case RIGHT:
                return "L";
            case UP:
                return "D";
            case DOWN:
                return "U";
            default:
                return null;
        }
    }
}
