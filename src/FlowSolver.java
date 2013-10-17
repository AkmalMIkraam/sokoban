import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class FlowSolver {
    private static class Graph
    {
        public class Node
        {
            public ArrayList<Edge> from = new ArrayList<Edge>();
            public ArrayList<Edge> to = new ArrayList<Edge>();
            public Position pos; //Goal or box position
            public Edge parent; //Parent field is used to get the path after bfs
        }

        private class Edge
        {
            public Edge(Node to)
            {
                this.to = to;
            }

            public Edge reverse;
            public Node to;
            public int used;
        }

        /**
         * Creates a graph to solve the Bi-partite matching problem using Edmonds-Karps algorithm
         * @param state The current state of the world
         * @param goals A list where each list is which goals are reachable from the box
         *              at the same index
         */
        public Graph(State state, ArrayList<ArrayList<Position>> goals)
        {
            HashMap<Position, Node> allGoals = new HashMap<Position, Node>();
            for (int boxIndex = 0; boxIndex < goals.size(); ++boxIndex)
            {
                Node boxNode = new Node();
                boxNode.pos = state.boxes.get(boxIndex);
                for (Position goal : goals.get(boxIndex))
                {
                    Node node = allGoals.get(goal);
                    if (node == null)
                    {
                        node = new Node();
                        node.pos = goal;
                        allGoals.put(goal, node);
                    }
                    Edge next = new Edge(node);
                    next.reverse = new Edge(boxNode);
                    next.reverse.reverse = next;
                    boxNode.to.add(next);
                    node.from.add(next.reverse);
                }
                Edge to = new Edge(boxNode);
                to.reverse = new Edge(source);
                to.reverse.reverse = to;
                source.to.add(to);
                boxNode.from.add(to.reverse);
            }
            for (Node goalNode : allGoals.values())
            {
                Edge to = new Edge(sink);
                to.reverse = new Edge(goalNode);
                to.reverse.reverse = to;
                goalNode.to.add(to);
                sink.from.add(to.reverse);
            }
        }

        /**
         * Gives a list holding the maximum amount of matches that are possible
         * between boxes and goals
         * @return
         */
        public ArrayList<Link> solveFlow()
        {
            while (true)
            {
                //end should always be sink or null when no more matches are possible
                Node end = bfs(source, sink);
                if (end == null)
                    break;//End when we can no longer find a path

                Edge currentEdge = end.parent;
                end.parent = null;
                while (currentEdge != null)
                {
                    currentEdge.used += 1;
                    currentEdge.reverse.used -= 1;
                    currentEdge = currentEdge.reverse.to.parent;
                }
            }
            ArrayList<Link> links = new ArrayList<Link>();
            int ii = 0;
            for (Edge edge : this.source.to)
            {
                Node box = edge.to;
                Node goal = getGoalFor(box);
                if (goal != null)
                {
                    Link link = new Link();
                    link.box = ii;
                    link.goal = goal.pos;
                    links.add(link);
                }
                ++ii;
            }
            return links;
        }

        private Node getGoalFor(Node box)
        {
            for (Edge edge : box.to)
            {
                if (edge.used == 1)
                {
                    return edge.to;
                }
            }
            return null;
        }

        Node bfs(Node from, Node to)
        {
            cleanParents(from);
            Queue<Node> toVisit = new LinkedList<Node>();
            toVisit.add(from);

            while(!toVisit.isEmpty())
            {
                Node current = toVisit.remove();
                if (current == to)
                {
                    return current;//path
                }
                for (Edge child : current.to)
                {
                    //1 is the capacity of the edge
                    if (1 - child.used > 0 && child.to.parent == null)
                    {
                        toVisit.add(child.to);
                        child.to.parent = child;
                    }
                }
                for (Edge child : current.from)
                {
                    if (1 - child.used > 0 && child.to.parent == null && child.to != from)
                    {
                        toVisit.add(child.to);
                        child.to.parent = child;
                    }
                }
            }
            return null;
        }
        void cleanParents(Node n)
        {
            n.parent = null;
            for (Edge next : n.to)
            {
                cleanParents(next.to);
            }
        }

        Node source = new Node();
        Node sink = new Node();
    }

    public static class Link
    {
        int box;
        Position goal;

        public String toString()
        {
            return "{" + box + ", " + goal + "}";
        }
    }
    public static ArrayList<Link> solve(State state, ArrayList<ArrayList<Position>> foundGoals)
    {
        Graph graph = new Graph(state, foundGoals);
        return graph.solveFlow();
    }
}
