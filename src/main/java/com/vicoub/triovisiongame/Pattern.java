package com.vicoub.triovisiongame;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Pattern {
    private StackPane pane;
    private Tile[][] tiles;
    private int LShaped;
    private boolean found;

    public Pattern(int inventorySlot) {
        found = false;
        pane = new StackPane();
        pane.setVisible(true);
        pane.setMinSize(UIConstants.APP_WIDTH / 4, UIConstants.INVENTORY_HEIGHT);
        if (inventorySlot == 0) {
            pane.setTranslateX(-150);
        } else if (inventorySlot == 3) {
            pane.setTranslateX(150);
        } else {
            pane.setTranslateX(UIConstants.APP_WIDTH / (4 / inventorySlot) - 150);
        }
        LShaped = new Random().nextInt(2);
        addTiles(LShaped);
    }

    private void addTiles(int LShaped) {
        tiles = new Tile[3][2];
        ArrayList<Paint> colorArray = new ArrayList<>(Arrays.asList(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW));
        for (int row = 0; row < 3; row ++) {
            for (int col = 0; col < 2; col++) {
                Tile tile = new Tile();
                tiles[row][col] = tile;
                tile.getStackPane().setTranslateX(col * 25 - 25 + 12.5);
                tile.getStackPane().setTranslateY(row * 25 + 12.5);
                if (LShaped == 1) {
                    if (((row == 0 || row == 1) && col == 1) || (row == 2 && col == 0)) {
                            tile.getBorder().setStroke(Color.TRANSPARENT);
                    } else {
                        setRandomTileColor(tile, row, col, colorArray);
                    }
                } else {
                    if (((row == 0 || row == 1) && col == 0) || (row == 2 && col == 1)) {
                        tile.getBorder().setStroke(Color.TRANSPARENT);
                    } else {
                        setRandomTileColor(tile, row, col, colorArray);
                    }
                }
                pane.getChildren().add(tile.getStackPane());
            }
        }
    }

    private void setRandomTileColor(Tile tile, int row, int col, ArrayList<Paint> colorArray) {
        int index = new Random().nextInt(colorArray.size());
        Paint color = colorArray.get(index);
        colorArray.remove(index);
        tile.setCircleColor(color);
        tile.getCircle().setFill(tile.getCircleColor());
        tile.getCircle().setCenterX(col * 25 + 25 + 12.5);
        tile.getCircle().setCenterY(row * 25 + 12.5);
        tile.getCircle().setRadius(12.5);
        tile.getStackPane().getChildren().add(tile.getCircle());
    }


    public StackPane getStackPane() {
        return pane;
    }

    public int isLShaped() {
        return LShaped;
    }

    public Paint[] getPatternColors() {
        Paint[] colors = new Paint[3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                if (tiles[i][j].getCircleColor() != null) {
                    colors[i] = tiles[i][j].getCircleColor();
                }
            }
        }
        return colors;
    }

    public void setFound() {
        found = true;
        pane.setVisible(false);
    }

    public boolean isFound() {
        return found;
    }

    private class Tile {
        private StackPane pane;
        private Circle circle;
        private Paint circleColor;
        private Rectangle border;

        public Tile() {
            pane = new StackPane();
            pane.setVisible(true);
            pane.setMinSize(25, 25);

            border = new Rectangle();
            border.setWidth(25);
            border.setHeight(25);
            border.setFill(Color.TRANSPARENT);
            border.setStroke(Color.BLACK);
            pane.getChildren().add(border);

            circle = new Circle();
        }

        public StackPane getStackPane() {
            return pane;
        }

        public Rectangle getBorder() {
            return border;
        }

        public Circle getCircle() {
            return circle;
        }

        public Paint getCircleColor() {
            return circleColor;
        }

        public void setCircleColor(Paint color) {
            this.circleColor = color;
        }
    }
}
