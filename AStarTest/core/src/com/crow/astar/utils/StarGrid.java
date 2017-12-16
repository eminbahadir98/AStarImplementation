package com.crow.astar.utils;

public class StarGrid {
    
    private final boolean[][] map;
    public final int width;
    public final int height;
    
    public StarGrid(int width, int height) {
        this.width = width;
        this.height = height;
        map = new boolean[height][width];
    }

    public StarPoint[] getNeighbors(StarPoint cell, boolean diagEnabled) {

        int x = cell.x;
        int y = cell.y;

        if (x < 0 || x >= width || y < 0 || y >= height) {
            System.out.println("Invalid indices provided.");
            return null;
        }

        StarPoint[] candidates;

        if (diagEnabled) {

            candidates = new StarPoint[8];
            candidates[0] = new StarPoint(x-1 , y-1, 1);
            candidates[1] = new StarPoint(x   , y-1, 1);
            candidates[2] = new StarPoint(x+1 , y-1, 1);
            candidates[3] = new StarPoint(x-1 , y  , 1);
            candidates[4] = new StarPoint(x+1 , y  , 1);
            candidates[5] = new StarPoint(x-1 , y+1, 1);
            candidates[6] = new StarPoint(x   , y+1, 1);
            candidates[7] = new StarPoint(x+1 , y+1, 1);

            if (x == 0) {
                candidates[0] = candidates[0].withPriority(0);
                candidates[3] = candidates[3].withPriority(0);
                candidates[5] = candidates[5].withPriority(0);
            }
            if (x == width - 1) {
                candidates[2] = candidates[2].withPriority(0);
                candidates[4] = candidates[4].withPriority(0);
                candidates[7] = candidates[7].withPriority(0);
            }
            if (y == 0) {
                candidates[0] = candidates[0].withPriority(0);
                candidates[1] = candidates[1].withPriority(0);
                candidates[2] = candidates[2].withPriority(0);
            }
            if (y == height-1) {
                candidates[5] = candidates[5].withPriority(0);
                candidates[6] = candidates[6].withPriority(0);
                candidates[7] = candidates[7].withPriority(0);
            }

        } else {

            candidates = new StarPoint[4];
            candidates[0] = new StarPoint(x   , y-1, 1);
            candidates[1] = new StarPoint(x-1 , y  , 1);
            candidates[2] = new StarPoint(x+1 , y  , 1);
            candidates[3] = new StarPoint(x   , y+1, 1);

            if (x == 0) {
                candidates[1] = candidates[1].withPriority(0);
            }
            if (x == width - 1) {
                candidates[2] = candidates[2].withPriority(0);
            }
            if (y == 0) {
                candidates[0] = candidates[0].withPriority(0);
            }
            if (y == height - 1) {
                candidates[3] = candidates[3].withPriority(0);
            }
        }

        int neighborNum = 0;
        for (StarPoint candidate : candidates) {
            if (candidate.priority == 1) {
                neighborNum++;
            }
        }

        StarPoint[] neighbors = new StarPoint[neighborNum];
        int index = 0;
        for (StarPoint candidate : candidates) {
            if (candidate.priority == 1) {
                neighbors[index++] = candidate;
            }
        }

        return neighbors;
    }

    public void set(int x, int y, boolean value) {
        map[y][x] = value;
    }

    public boolean get(int x, int y) {
        return map[y][x];
    }

    public int getCost(StarPoint current, StarPoint next) {
        if (get(next.x, next.y) == true) {
            return 1048576;
        }

        if (current.x == next.x || current.y == next.y) {
            return 100;
        } else {
            return 141;
        }
    }
    
}
