package com.crow.astar;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.crow.astar.utils.StarGrid;
import com.crow.astar.utils.StarPoint;


public class AStarExample extends ApplicationAdapter {
    
    static final int WIDTH = 10;
    static final int HEIGHT = 7;
    
    static final int BLOCK = 64;
    static final int HALF = BLOCK / 2;
    static final int GAP = BLOCK / 8;
    
    AStarSearcher searcher;
    Array<StarPoint> path;
    ShapeRenderer drawer;
    SpriteBatch writer;
    BitmapFont font;
    
    @Override
    public void create() {
        searcher = new AStarSearcher(new StarGrid(WIDTH, HEIGHT),
            new StarPoint(0, 0), new StarPoint(WIDTH - 1, HEIGHT - 1), true);
        path = searcher.applySearch();
        drawer = new ShapeRenderer();
        writer = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glLineWidth(5);
        
        checkInput();
        
        drawer.setAutoShapeType(true);
        drawer.begin();
        
        drawer.set(ShapeRenderer.ShapeType.Line);
        drawer.setColor(Color.BLACK);
        drawGrid();
        
        drawer.set(ShapeRenderer.ShapeType.Filled);
        drawer.setColor(Color.CYAN);
        drawPoints();
        
        drawer.set(ShapeRenderer.ShapeType.Filled);
        drawer.setColor(Color.GRAY);
        drawObstacles();
        
        drawer.set(ShapeRenderer.ShapeType.Line);
        drawPath();
        
        drawer.end();
        
        writer.begin();
        font.draw(writer, "Controls: Left click to add/remove block. "
            + "Right click to set new destination.", GAP, BLOCK * HEIGHT + 3 * GAP);
        writer.end();
    }
	
    @Override
    public void dispose() {
        drawer.dispose();
        writer.dispose();
    }
    
    void checkInput() {
        if (Gdx.input.justTouched()) {
            int clickX = Gdx.input.getX();
            int clickY = Gdx.graphics.getHeight() - Gdx.input.getY();
            boolean isLeft = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
            int gridX = clickX / BLOCK;
            int gridY = clickY / BLOCK;
            if (gridX >= searcher.grid.width || gridY >= searcher.grid.height) {
                return;
            }
            if (isLeft) {
                searcher.grid.set(gridX, gridY, !searcher.grid.get(gridX, gridY));
            } else {
                searcher.goal = new StarPoint(gridX, gridY);
            }
            path = searcher.applySearch();
        }
    }
    
    void drawGrid() {
        for (int i = 1; i <= searcher.grid.width; i++) {
            drawer.line(BLOCK*i, 0, BLOCK*i, searcher.grid.height*BLOCK);
        }
        for (int i = 1; i <= searcher.grid.height; i++) {
            drawer.line(0, BLOCK*i, searcher.grid.width*BLOCK, BLOCK*i);
        }
    }
    
    void drawPoints() {
        drawer.rect(searcher.start.x * BLOCK + GAP, searcher.start.y * BLOCK + GAP,
                    BLOCK - 2 * GAP, BLOCK - 2 * GAP);
        drawer.rect(searcher.goal.x * BLOCK + GAP, searcher.goal.y * BLOCK + GAP,
                    BLOCK - 2 * GAP, BLOCK - 2 * GAP);
    }
    
    void drawObstacles() {
        for (int i = 0; i < searcher.grid.width; i++) {
            for (int j = 0; j < searcher.grid.height; j++) {
                if (searcher.grid.get(i, j)) {
                    drawer.rect(i * BLOCK + GAP, j * BLOCK + GAP,
                                BLOCK - 2 * GAP, BLOCK - 2 * GAP);
                }
            }
        }
    }
    
    void drawPath() {
        boolean isBlue = true;
        for (int i = 0; i < path.size - 1; i++) {
            if (isBlue) {
                drawer.setColor(Color.BLUE);
            } else {
                drawer.setColor(Color.RED);
            }
            isBlue = !isBlue;
            drawConnection(path.get(i), path.get(i + 1));
        }
    }
    
    void drawConnection(StarPoint p1, StarPoint p2) {
        drawer.line(p1.x * BLOCK + HALF, p1.y * BLOCK + HALF,
                    p2.x * BLOCK + HALF, p2.y * BLOCK + HALF);
    }
    
}
