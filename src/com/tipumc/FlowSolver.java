package com.tipumc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class FlowSolver {
    private static class Graph
    {
        public class Node
        {
            public boolean equals(Object o)
            {
                if (o.getClass() == Node.class)
                {
                    return pos.equals(((Node)o).pos);
                }
                return false;
            }

            public ArrayList<Edge> to = new ArrayList<Edge>();
            public Position pos; //Goal or box position
            public Edge parent;
        }
        private class Edge
        {
            private Edge(Edge reverse, Node to)
            {
                this.reverse = reverse;
                this.to = to;
            }
            public Edge(Node from, Node to)
            {
                this.reverse = new Edge(this, from);
                this.to = to;
            }

            public Edge reverse;
            public Node to;
            public boolean used;
        }

        public Graph(State state, ArrayList<ArrayList<Position>> goals)
        {
            int numBoxes = goals.size();
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
                    boxNode.to.add(new Edge(boxNode, node));
                }
                source.to.add(new Edge(source, boxNode));
            }
            for (Node goalNode : allGoals.values())
            {
                goalNode.to.add(new Edge(goalNode, sink));
            }
        }
        
        public ArrayList<Link> solveFlow()
        {
            while (true)
            {
                //end should always be sink or null
                Node end = bfs(source, sink);
                if (end == null)
                    break;//End when we can no longer find a path

                Edge currentEdge = end.parent;
                end.parent = null;
                while (currentEdge != null)
                {
                    currentEdge.used = true;
                    currentEdge.reverse.used = false;
                    currentEdge = currentEdge.reverse.to.parent;
                }
            }
            ArrayList<Link> links = new ArrayList<Link>();
            int ii = 0;
            for (Edge edge : this.source.to)
            {
                Node box = edge.to;
                Node goal = getGoalFor(box);
                Link link = new Link();
                link.box = ii;
                link.goal = goal.pos;
                links.add(link);
                ++ii;
            }
            return links;
        }

        private Node getGoalFor(Node box)
        {
            for (Edge edge : box.to)
            {
                if (edge.used)
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
                    if (!child.used && child.to.parent == null)
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
