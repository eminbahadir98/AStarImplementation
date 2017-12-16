package com.crow.astar.utils;

public class StarPoint implements Comparable {
    
    public final int x;
    public final int y;
    public int priority;
    
    public StarPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public StarPoint(int x, int y, int priority) {
        this(x, y);
        this.priority = priority;
    }
    
    public StarPoint withPriority(int priority) {
        return new StarPoint(x, y, priority);
    }
    
    public int heuristic(StarPoint other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }
    
    @Override
    public int compareTo(Object other) {
        return this.priority - ((StarPoint) other).priority;
    }
    
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StarPoint)) {
            return false;
        }
        return this.x == ((StarPoint) other).x && this.y == ((StarPoint) other).y;
    }
    
    @Override
    public int hashCode() {
        return x * 17 + y * 31;
    }
    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
}
