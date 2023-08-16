package com.vicoub.triovisiongame;


import javafx.scene.layout.StackPane;

public class Inventory {
    private StackPane pane;
    private Pattern[] patterns = new Pattern[4];

    public Inventory(InfoWindow infoWindow) {
        pane = new StackPane();
        pane.setVisible(false);
        pane.setMinSize(UIConstants.APP_WIDTH, UIConstants.INVENTORY_HEIGHT);
        pane.setTranslateX(UIConstants.APP_WIDTH / 2);
        pane.setTranslateY(UIConstants.INFO_WINDOW_HEIGHT + UIConstants.BOARD_HEIGHT + UIConstants.INVENTORY_HEIGHT / 2);
        for (int i = 0; i < 4; i++) {
            Pattern pattern = new Pattern(i);
            patterns[i] = pattern;
            pane.getChildren().add(pattern.getStackPane());
        }
    }

    public Pattern[] getPatterns() {
        return patterns;
    }

    public StackPane getStackPane() {
        return pane;
    }

    public void setVisibility(boolean visibility) {
        pane.setVisible(visibility);
    }
}
