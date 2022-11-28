package com.example._06_tik_tak_toe;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {


    private boolean turnOfPlayer1 = true;
    private Button[][] field;
    private int width;
    private boolean stop = false;

    @Override
    public void start(Stage stage) throws IOException {
        Label label = new Label("Insert x: ");
        label.setPrefWidth(1000.0);
        TextField text = new TextField();
        text.setPrefWidth(1000.0);
        EventHandler buttonPress = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    width = Integer.parseInt(text.getText());
                    initGame(stage);
                } catch (NumberFormatException ex) {
                    label.setText("Enter a valid Number under 20");
                }
            }
        };
        Button button = new Button("Confirm");
        button.setOnAction(buttonPress);
        button.setPrefWidth(1000.0);
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));

        gridPane.setVgap(10);
        gridPane.setHgap(10);

        gridPane.add(label, 0, 0);
        gridPane.add(text, 0, 1);
        gridPane.add(button, 0, 2);

        Scene scene = new Scene(gridPane, 300, 300);
        scene.getStylesheets().add(getClass().getResource("customButton.css").toExternalForm());

        stage.setScene(scene);

        stage.setTitle("Tic Tac Toe");
        stage.show();
        stage.setScene(scene);
    }

    private void bot() {
        char[][] botField = new char[width][width];
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < width; x++) {
                if (field[x][y].getText().equals("")) {
                    botField[x][y] = '\0';
                } else {
                    botField[x][y] = field[x][y].getText().charAt(0);
                }
            }
        }
        int[] saveBiggestRecursionScore = new int[3];
        saveBiggestRecursionScore[0] = -100000;
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < width; x++) {
                if (botField[x][y] == '\0') {
                    if (turnOfPlayer1) {
                        botField[x][y] = 'X';
                    } else {
                        botField[x][y] = 'O';
                    }
                    int tempScore = botRecursion(botField, false);
                    if (tempScore > saveBiggestRecursionScore[0]) {
                        saveBiggestRecursionScore[0] = tempScore;
                        saveBiggestRecursionScore[1] = x;
                        saveBiggestRecursionScore[2] = y;
                    }
                    botField[x][y] = '\0';
                }
            }
        }
    }

    private int botRecursion(char[][] botField, boolean turn) {
        int score = 0;

        return score;
    }

    private void initGame(Stage stage) {

        GridPane gridPane = new GridPane();


        //gridPane.setVgap(10);
        //gridPane.setHgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setAlignment(Pos.CENTER);

        Label winnerLabel = new Label("");
        winnerLabel.setPrefWidth(1000);
        gridPane.add(winnerLabel, 0, 0, width, 1);

        field = new Button[width][width];
        EventHandler buttonPress = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!stop) {
                    if (e.getSource() instanceof Button) {
                        Button temp = (Button) e.getSource();
                        if (temp.getText().equals("")) {
                            if (turnOfPlayer1) {
                                temp.setText("X");
                                if (checkForWin()) {
                                    winnerLabel.setText("Player X won");
                                    stop = true;
                                }
                            } else {
                                temp.setText("O");
                                if (checkForWin()) {
                                    winnerLabel.setText("Player O won");
                                    stop = true;
                                }
                            }
                            turnOfPlayer1 = !turnOfPlayer1;
                        }
                    }
                }
            }
        };

        for (int y = 0; y < width; y++) {
            for (int x = 0; x < width; x++) {
                field[x][y] = new Button();
                // Allow the button to be wider overriding preferred width
                field[x][y].setPrefWidth(1000.0);
                field[x][y].setPrefHeight(1000.0);
                field[x][y].setOnAction(buttonPress);
                // set position in gridpane
                gridPane.add(field[x][y], x, y + 1);
            }
        }

        // using instance method for directly adding the node with columnspan and rowspan
        // gridPane.add(someButton,0,2,TWO_COLUMN_SPAN,1);

        Scene scene = new Scene(gridPane, 300, 300);
        scene.getStylesheets().add(getClass().getResource("customButton.css").toExternalForm());

        stage.setScene(scene);
    }

    private boolean checkForWin() {
        // returns true if someone won
        String checkString = "";
        boolean tempcheck = true;

        // straight lines

        for (int y = 0; y < width; y++) {
            tempcheck = true;
            checkString = field[0][y].getText();
            for (int x = 0; x < width; x++) {
                if (field[x][y].getText().equals("") || !field[x][y].getText().equals(checkString)) {
                    tempcheck = false;
                    break;
                }
            }
            if (tempcheck) {
                return true;
            }
        }
        for (int x = 0; x < width; x++) {
            tempcheck = true;
            checkString = field[x][0].getText();
            for (int y = 0; y < width; y++) {
                if (field[x][y].getText().equals("") || !field[x][y].getText().equals(checkString)) {
                    tempcheck = false;
                    break;
                }
            }
            if (tempcheck) {
                return true;
            }
        }

        // diagonals

        tempcheck = true;
        checkString = field[0][0].getText();
        for (int x = 0; x < width; x++) {
            if (field[x][x].getText().equals("") || !field[x][x].getText().equals(checkString)) {
                tempcheck = false;
                break;
            }
        }
        if (tempcheck) {
            return true;
        }

        tempcheck = true;
        checkString = field[0][width - 1].getText();
        for (int x = 0; x < width; x++) {
            if (field[x][width - x - 1].getText().equals("") || !field[x][width - 1 - x].getText().equals(checkString)) {
                tempcheck = false;
                break;
            }
        }
        if (tempcheck) {
            return true;
        }

        return false;
    }


    public static void main(String[] args) {
        launch();
    }
}