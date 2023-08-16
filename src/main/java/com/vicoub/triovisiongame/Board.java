package com.vicoub.triovisiongame;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class Board {

    private StackPane pane = new StackPane();
    private InfoWindow infoWindow;
    private Tile[][] tiles = new Tile[4][4];
    private Player[] players;
    private int playerTurn = 1;
    private Paint selectedColor;

    public Board(InfoWindow infoWindow, Player[] players) {
        this.infoWindow = infoWindow;
        this.players = players;
        setupPane();
        addTiles();
    }

    private void setupPane() {
        pane.setVisible(false);
        pane.setMinSize(UIConstants.APP_WIDTH, UIConstants.BOARD_HEIGHT);
        pane.setTranslateX(UIConstants.APP_WIDTH / 2);
        pane.setTranslateY(UIConstants.INFO_WINDOW_HEIGHT + UIConstants.BOARD_HEIGHT / 2);
    }

    public StackPane getStackPane() {
        return pane;
    }

    private void addTiles() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Tile tile = new Tile();
                tiles[row][col] = tile;
                tile.getStackPane().setTranslateX(col * 100 - 150);
                tile.getStackPane().setTranslateY(row * 100 - 100);
                setDefaultTileColor(tile, row, col);
                tile.getCircle().setCenterX(col * 100 + 50);
                tile.getCircle().setCenterY(col * 100 - 100 + 50);
                tile.getCircle().setRadius(50);
                pane.getChildren().add(tile.getStackPane());
            }
        }
    }

    private void setDefaultTileColor(Tile tile, int row, int col) {
        if (row == 0 && (col == 1 || col == 2)) tile.setCircleColor(Color.GREEN);
        else if (row == 3 && (col == 1 || col == 2)) tile.setCircleColor(Color.BLUE);
        else if (col == 0 && (row == 1 || row == 2)) tile.setCircleColor(Color.RED);
        else if (col == 3 && (row == 1 || row == 2)) tile.setCircleColor(Color.YELLOW);
        else tile.setCircleColor(Color.TRANSPARENT);
    }

    public void resetBoard() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                setDefaultTileColor(tiles[row][col], row, col);
            }
        }
    }

    public Paint handleClick(Paint color) {
        int numberOfTilesSelected = countSelectedTiles();
        if (numberOfTilesSelected == 1) {
            selectedColor = color;
            infoWindow.setInstructions("Sélectionner la case (différente et vide) que vous voulez remplir.");
            return Color.TRANSPARENT;
        } else if (numberOfTilesSelected == 2) {
            unselectTiles();
            changePlayerTurn();
            return selectedColor;
        }
        return null;
    }

    private void unselectTiles() {
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile.isSelected()) tile.unselectTile();
            }
        }
    }

    private int countSelectedTiles() {
        int count = 0;
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile.isSelected()) count++;
            }
        }
        return count;
    }

    public void changePlayerTurn() {
        players[playerTurn - 1].getInventory().setVisibility(false);
        playerTurn = playerTurn == 1 ? 2 : 1;
        infoWindow.updateMessage("Au tour du Joueur " + playerTurn);
        infoWindow.setInstructions("Sélectionnez la couleur que vous voulez bouger");
        infoWindow.setPlayerPointsMessage("Vous avez " + players[playerTurn - 1].getPoints() + " points.");
        players[playerTurn - 1].getInventory().setVisibility(true);
    }

    public boolean checkPattern() {
        // Retrieve the patterns for the current player
        List<Pattern> patterns = List.of(players[playerTurn - 1].getInventory().getPatterns());

        for (Pattern pattern : patterns) {
            if (!pattern.isFound()) {
                for (int row = 0; row < 4; row++) {
                    for (int col = 0; col < 4; col++) {
                        if (tiles[row][col].getCircleColor() == pattern.getPatternColors()[2]) {
                            if (pattern.isLShaped() == 1) {
                                if (checkLShapedPatterns(row, col, pattern)) {
                                    return true;
                                }
                            } else {
                                if (checkOtherPatterns(row, col, pattern)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean checkLShapedPatterns(int row, int col, Pattern pattern) {
        int[][] offsets = {
                {-1, -1, -2, -1},
                {-1, 1, -1, 2},
                {1, 1, 2, 1},
                {1, -1, 1, -2}
        };
        return checkPatternWithOffsets(row, col, pattern, offsets);
    }

    private boolean checkOtherPatterns(int row, int col, Pattern pattern) {
        int[][] offsets = {
                {-1, 1, -2, 1},
                {-1, -1, -1, -2},
                {1, -1, 2, -1},
                {1, 1, 1, 2}
        };
        return checkPatternWithOffsets(row, col, pattern, offsets);
    }

    private boolean checkPatternWithOffsets(int row, int col, Pattern pattern, int[][] offsets) {
        for (int[] offset : offsets) {
            try {
                if (tiles[row + offset[0]][col + offset[1]].getCircleColor() == pattern.getPatternColors()[1]
                        && tiles[row + offset[2]][col + offset[3]].getCircleColor() == pattern.getPatternColors()[0]) {
                    pattern.setFound();
                    return true;
                }
            } catch (Exception e) {
                // Ignore out of bounds exception
            }
        }
        return false;
    }


    public boolean checkWinner() {
        boolean result = true;
        for (Pattern pattern : players[playerTurn - 1].getInventory().getPatterns()) {
            if (!pattern.isFound()) {
                result = false;
            }
        }
        return result;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setVisibility(boolean visibility) {
        pane.setVisible(visibility);
    }
    public class Tile {
        private StackPane pane = new StackPane();
        private Circle circle = new Circle();
        private Paint circleColor;
        private boolean selected;

        private Tile() {
            setupTile();
        }

        private void setupTile() {
            pane.setMinSize(100, 100);
            Rectangle border = new Rectangle(100, 100, Color.TRANSPARENT);
            border.setStroke(Color.BLACK);
            pane.getChildren().addAll(border, circle);

            pane.setOnMouseClicked(e -> {
                selected = !selected;
                Paint newColor = handleClick(circleColor);
                if (newColor != null) {
                    setCircleColor(newColor);
                }
            });
        }

        public boolean isSelected() {
            return selected;
        }

        public void unselectTile() {
            selected = false;
        }

        public StackPane getStackPane() {
            return pane;
        }

        public Circle getCircle() {
            return circle;
        }

        public Paint getCircleColor() {
            return circleColor;
        }

        public void setCircleColor(Paint color) {
            this.circleColor = color;
            circle.setFill(color);
        }
    }
}
