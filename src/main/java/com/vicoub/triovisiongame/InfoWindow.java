package com.vicoub.triovisiongame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class InfoWindow {
    private StackPane pane;
    private Label message;
    private Button startGameButton;
    private Label instructions;
    private Label playerPoints;
    private Button checkPatternButton;

    public InfoWindow() {
        pane = new StackPane();
        pane.setMinSize(UIConstants.APP_WIDTH, UIConstants.INFO_WINDOW_HEIGHT);
        pane.setTranslateX(UIConstants.APP_WIDTH / 2);
        pane.setTranslateY(UIConstants.INFO_WINDOW_HEIGHT / 2);

        message = new Label("Triovision");
        message.setMinSize(UIConstants.APP_WIDTH, UIConstants.INFO_WINDOW_HEIGHT);
        message.setFont(Font.font(24));
        message.setAlignment(Pos.CENTER);
        message.setTranslateY(-20);
        pane.getChildren().add(message);

        startGameButton = new Button("Nouvelle Partie !");
        startGameButton.setMinSize(135, 30);
        startGameButton.setTranslateY(20);
        pane.getChildren().add(startGameButton);

        instructions = new Label("Sélectionnez la couleur à bouger");
        instructions.setMinSize(UIConstants.APP_WIDTH, UIConstants.INFO_WINDOW_HEIGHT);
        instructions.setFont(Font.font(12));
        instructions.setAlignment(Pos.CENTER);
        instructions.setTranslateY(20);
        instructions.setVisible(false);
        pane.getChildren().add(instructions);

        playerPoints = new Label();
        playerPoints.setMinSize(UIConstants.APP_WIDTH, UIConstants.INFO_WINDOW_HEIGHT);
        playerPoints.setFont(Font.font(12));
        playerPoints.setAlignment(Pos.CENTER);
        playerPoints.setTranslateY(30);
        playerPoints.setVisible(false);
        pane.getChildren().add(playerPoints);

        checkPatternButton = new Button("J'ai un motif !");
        checkPatternButton.setMinSize(135, 30);
        checkPatternButton.setTranslateY(60);
        checkPatternButton.setVisible(false);
        pane.getChildren().add(checkPatternButton);

    }

    public StackPane getStackPane() {
        return pane;
    }

    public Label getInstructions() {
        return instructions;
    }

    public Label getPlayerPointsLabel() {
        return playerPoints;
    }

    public void updateMessage(String message) {
        this.message.setText(message);
    }

    public void setInstructions(String instructions) {
        this.instructions.setText(instructions);
    }

    public void setPlayerPointsMessage(String playerPointsMessage) {
        this.playerPoints.setText(playerPointsMessage);
    }

    public void setCheckPatternButtonVisibility(boolean visibility) {
        this.checkPatternButton.setVisible(visibility);
    }

    public void setStartGameButtonVisibility(boolean visibility) {
        this.startGameButton.setVisible(visibility);
    }

    public void setStartButtonOnAction(EventHandler<ActionEvent> onAction) {
        startGameButton.setOnAction(onAction);
    }

    public void setCheckPatternButtonAction(EventHandler<ActionEvent> onAction) {
        checkPatternButton.setOnAction(onAction);
    }
}
