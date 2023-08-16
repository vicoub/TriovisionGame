package com.vicoub.triovisiongame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TrioVisionApplication extends Application {
    private Stage stage;

    private InfoWindow infoWindow;
    private Board board;
    private Player[] players = new Player[2];
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, UIConstants.APP_WIDTH, UIConstants.APP_HEIGHT + 50);
        stage.setTitle("Triovision !");
        initLayout(root);
        stage.setScene(scene);
        stage.show();
    }

    private void initPlayers(BorderPane root) {
        for (int i = 0; i < 2; i++) {
            Player player = new Player(i + 1, initInventory(root));
            players[i] = player;
        }
    }

    private void initLayout(BorderPane root) {
        initInfoCenter(root);
        initPlayers(root);
        initTileBoard(root);
    }

    private Inventory initInventory(BorderPane root) {
        Inventory inventory = new Inventory(infoWindow);
        root.getChildren().add(inventory.getStackPane());
        return inventory;
    }

    private void initTileBoard(BorderPane root) {
        board = new Board(infoWindow, players);
        root.getChildren().add(board.getStackPane());
        infoWindow.setCheckPatternButtonAction(checkPattern());
    }

    private void initInfoCenter(BorderPane root) {
        infoWindow = new InfoWindow();
        infoWindow.setStartButtonOnAction(startNewGame());
        root.getChildren().add(infoWindow.getStackPane());
    }

    private EventHandler<ActionEvent> startNewGame() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                infoWindow.setStartGameButtonVisibility(false);
                infoWindow.updateMessage("Au tour du Joueur 1");
                infoWindow.getInstructions().setVisible(true);
                infoWindow.getPlayerPointsLabel().setVisible(true);
                infoWindow.setPlayerPointsMessage("Tu as " + players[0].getPoints() + " points.");
                infoWindow.setCheckPatternButtonVisibility(true);
                board.setVisibility(true);
                players[0].getInventory().setVisibility(true);
            }
        };
    }

    private EventHandler<ActionEvent> checkPattern() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (board.checkPattern()) {
                    players[board.getPlayerTurn() - 1].addPoints(1);
                    System.out.println("Correct ! +1 Point");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (Exception e) {
                        ;
                    }
                    if (board.checkWinner()) {
                        endGame();
                    } else {
                        board.changePlayerTurn();
                        board.resetBoard();
                    }
                } else {
                    System.out.println("FAUX !");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (Exception e) {
                        ;
                    }
                    board.changePlayerTurn();
                }
            }
        };
    }

    private void endGame(){
        System.out.println("JOUEUR " + board.getPlayerTurn() + " GAGNE !");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            ;
        }
        Platform.exit();
        System.exit(0);

    }

    public static void main(String[] args) {
        launch();
    }
}