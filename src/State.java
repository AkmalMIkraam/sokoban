import java.util.ArrayList;

public class State implements Comparable<State>{

    public State(Map map, Position player, ArrayList<Position> boxes, ArrayList<Direction> playerPath, State parent)//More parameters for player and boxes
    {
        this.map = map;
        this.player = player;
        this.boxes = boxes;
        this.playerPath = playerPath;
        this.parent = parent;
        this.goals = parent.goals;
        this.playerEndPos = parent.playerEndPos;
    }

    public State(Map map, ArrayList<String> boardinv, ArrayList<String> board) throws Exception
    {
        this.map = map;
        //Read in the initial position of the boxes and the player.
        this.player = findPlayer(board);
        this.boxes = findBoxes(board);
        this.goals = findGoals(board);
        this.playerEndPos = findPlayerEndPos();
        this.boxes = findBoxes(boardinv);
        this.goals = findGoals(boardinv);
    }

    private Position findPlayer(ArrayList<String> board) throws Exception
    {
        for (int y = 0; y < board.size(); ++y)
        {
            for (int x = 0; x < board.get(y).length(); ++x)
            {
                char c = board.get(y).charAt(x);
                if (c == '@' || c == '+')
                    return new Position(x, y);
            }
        }
        throw new Exception("Could not find the player in the board");
    }

    private ArrayList<Position> findBoxes(ArrayList<String> board)
    {
        ArrayList<Position> boxes = new ArrayList<Position>();
        for (int y = 0; y < board.size(); ++y)
        {
            for (int x = 0; x < board.get(y).length(); ++x)
            {
                char c = board.get(y).charAt(x);
                if (c == '$' || c == '*')
                    boxes.add(new Position(x, y));
            }
        }
        return boxes;
    }
    
    private ArrayList<Position> findGoals(ArrayList<String> board)
    {
        ArrayList<Position> goals = new ArrayList<Position>();
        for (int y = 0; y < board.size(); ++y)
        {
            for (int x = 0; x < board.get(y).length(); ++x)
            {
                char c = board.get(y).charAt(x);
                if (c == '.' | c == '*' | c == '+')
                    goals.add(new Position(x, y));
            }
        }
        return goals;
    }
    
    private ArrayList<Position> findPlayerEndPos()
    {
        Search.Result result;
        ArrayList<Position> positions = new ArrayList<Position>();
        for(Position box : this.boxes){
            result = Search.dfs(this, new IsAtPosition(box.x+1,box.y), this.player.x, this.player.y);
            if(result != null){
                positions.add(new Position(box.x+1,box.y));
            }
            result = Search.dfs(this, new IsAtPosition(box.x-1,box.y), this.player.x, this.player.y);
            if(result != null){
                positions.add(new Position(box.x-1,box.y));
            }
            result = Search.dfs(this, new IsAtPosition(box.x,box.y+1), this.player.x, this.player.y);
            if(result != null){
                positions.add(new Position(box.x,box.y+1));
            }
            result = Search.dfs(this, new IsAtPosition(box.x,box.y-1), this.player.x, this.player.y);
            if(result != null){
                positions.add(new Position(box.x,box.y-1));
            }
        }
        return positions;
    }
    
    public boolean isFinal(){
        int numOfBoxOnGoal = boxesOnGoal();
        return(numOfBoxOnGoal == goals.size() && isAtPossibleEndPosition());
    }
    
    private int boxesOnGoal(){
        int numOfBoxOnGoal = 0;
        for (Position boxPos : boxes){
            for(Position goalPos : goals){
                //System.err.println(goalPos + " " + boxPos);
                if(boxPos.equals(goalPos)){
                    numOfBoxOnGoal++;
                }
            }
        }
        return numOfBoxOnGoal;
    }
    
    private boolean isAtPossibleEndPosition(){
        for (Position pos : playerEndPos)
        {
            //System.err.println("Boxes: " + pos);
            if (player.x == pos.x & player.y == pos.y)
                return true;
        }
        return false;
    }

    
    public int getHeight()
    {
        return this.map.getHeight();
    }
    
    public int getWidth()
    {
        return this.map.getWidth();
    }
            
    /**
     *
     * @param x
     * @param y
     * @return Whether the tile is free to move onto or not
     */
    public boolean isFree(int x, int y)
    {
        return (!this.isWall(x, y) && !this.isBox(x, y));
    }

    /*
    is* functions for convenience in this class which only forwards
     */
    public boolean isBox(int x, int y)
    {
        for (Position pos : boxes)
        {
            //System.err.println("Boxes: " + pos);
            if (x == pos.x & y == pos.y)
                return true;
        }
        return false;
    }
    public boolean isEmpty(int x, int y)
    {
        return map.isEmpty(x, y);
    }

    public boolean isGoal(int x, int y)
    {
        for (Position pos : goals)
        {
            if (x == pos.x & y == pos.y)
                return true;
        }
        return false;
    }

    public boolean isWall(int x, int y)
    {
        return map.isWall(x, y);
    }

    public Position getPlayer()
    {
        return player;
    }

    /**
     *
     * @return A string to be used when debugging the AI
     */
    public String toString()
    {
        String stringOut = "";
        boolean boxBool, playerBool;
        for (int i = 0; i < map.getHeight(); i++) { // For y-coordinates
            for (int j = 0; j < map.getWidth(); j++) { // For x-coordinates
                // See if there is a box with these exact coordinates
                boxBool = false;
                playerBool = false;
                for (int b = 0; b < boxes.size(); b++) {
                    if (boxes.get(b).x == j & boxes.get(b).y == i) {
                        boxBool = true;
                    } 
                }
                // See if there is a player with these exact coordinates
                if (player.x == j & player.y == i) {
                    playerBool = true;
                }
                // Add to the string
                if (boxBool & map.mapMatrix[i][j] == '.') {
                    stringOut += '*';
                } else if (playerBool & map.mapMatrix[i][j] == '.') {
                    stringOut += '+';
                } else if (playerBool){
                    stringOut += '@';
                } else if (boxBool) {
                    stringOut += '$';
                } else {
                    stringOut += map.mapMatrix[i][j];
                }
            }
            if (i < map.getHeight()-1) { // No new line on last line
                stringOut += System.getProperty("line.separator"); // New line
            }
        }
        return stringOut;
    }
    
    public int compareTo(State s){
        
        int compare1;
        int compare2;
        int boxesOnGoal1 = this.boxesOnGoal();
        
        if(boxesOnGoal1 > 0.8 * boxes.size()){
            compare2 = boxesOnGoal1;
            compare1 = s.boxesOnGoal();
            //System.err.println("Changed");
        } else {
            compare1 = manhattanDistance(this);
            compare2 = manhattanDistance(s);
        }
        
        if(compare1 < compare2){
            return -1;
        }
        else if(compare2 < compare1){
            return 1;
        } else {
            return 0;
        }
    }
    
    private int manhattanDistance(State s){
        int distance = 0;
        int totalDistance = 0;
        for(Position box : s.boxes){
            int min = 10000;
            for(Position goal : s.goals){
                distance = Math.abs(goal.x-box.x) + Math.abs(goal.y-box.y);
                if (distance < min){
                    min = distance;
                }
            }
            totalDistance += min;
        }
        return totalDistance;
    }
    
    /*private static boolean isInTunnel(Position box){
        return ((this.isWall(box.x+1,box.y) && this.isWall(box.x-1,box.y)) | (this.isWall(box.x,box.y+1) && this.isWall(box.x,box.y-1)));
    }*/

    /**
     * We don't necessarily have to store objects like this but it would work as a start.
     */
    public ArrayList<Position> boxes;
    public ArrayList<Position> goals;
    public ArrayList<Position> playerEndPos;
    public Position player;
    public Map map;
    public ArrayList<Direction> playerPath;
    public State parent = null;
}
