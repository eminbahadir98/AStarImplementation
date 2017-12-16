package com.crow.astar;

import com.badlogic.gdx.utils.Array;
import com.crow.astar.utils.StarGrid;
import com.crow.astar.utils.StarPoint;
import java.util.PriorityQueue;
import java.util.HashMap;


public class AStarSearcher {
    
    public StarGrid grid;
    public StarPoint start;
    public StarPoint goal;
    public boolean enableDiag;
        
    public AStarSearcher(StarGrid grid, StarPoint start, StarPoint goal, boolean enableDiag){
        this.grid = grid;
        this.start = start;
        this.goal = goal;
        this.enableDiag = enableDiag;
    }
    
    public Array<StarPoint> applySearch() {
        HashMap<StarPoint, StarPoint> result = search(start, goal, grid);
        Array<StarPoint> path = new Array<StarPoint>();
        StarPoint toPoint = goal;
        StarPoint fromPoint = result.get(toPoint);
        while (fromPoint != null) {
            path.add(toPoint);
            toPoint = fromPoint;
            fromPoint = result.get(toPoint);
        }
        path.add(toPoint);
        path.reverse();
        return path;
    }
    
    public HashMap<StarPoint, StarPoint> search(StarPoint start, StarPoint goal, StarGrid grid) {
        PriorityQueue<StarPoint> frontier = new PriorityQueue();
        frontier.add(start.withPriority(0));
        HashMap<StarPoint, StarPoint> cameFrom = new HashMap<StarPoint, StarPoint>();
        HashMap<StarPoint, Integer> costSoFar = new HashMap<StarPoint, Integer>();
        cameFrom.put(start, null);
        costSoFar.put(start, 0);
        while (!frontier.isEmpty()) {
            StarPoint current = frontier.poll();
            if (current.equals(goal)) {
                return cameFrom;
            }
            StarPoint[] neighbors = grid.getNeighbors(current, enableDiag);
            for (StarPoint next : neighbors) {
                int newCost = costSoFar.get(current) + grid.getCost(current, next);
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    int priority = newCost + next.heuristic(goal);
                    frontier.add(next.withPriority(priority));
                    cameFrom.put(next, current);
                }
            }
        }
        return null;
    }
    
}
