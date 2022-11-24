package com.example._06_tik_tak_toe;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {


    private boolean turnOfPlayer1 = true;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Tic Tac Toe");
        stage.show();
        initGame(stage, 4);

    }

    private void initGame(Stage stage, int width) {

        GridPane gridPane = new GridPane();


        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setAlignment(Pos.CENTER);

        Label winnerLabel = new Label("");
        winnerLabel.setPrefWidth(1000);
        gridPane.add(winnerLabel, 0, 0, width, 1);

        Button[][] fields = new Button[width][width];
        EventHandler buttonPress = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (e.getSource() instanceof Button) {
                    Button temp = (Button) e.getSource();
                    if (temp.getText().equals("")) {
                        if (turnOfPlayer1) {
                            temp.setText("X");
                        } else {
                            temp.setText("O");
                        }
                        turnOfPlayer1 = !turnOfPlayer1;
                    }
                }
            }
        };

        for (int y = 0; y < width; y++) {
            for (int x = 0; x < width; x++) {
                fields[x][y] = new Button();
                // Allow the button to be wider overriding preferred width
                fields[x][y].setPrefWidth(1000.0);
                fields[x][y].setPrefHeight(1000.0);
                fields[x][y].setOnAction(buttonPress);
                // set position in gridpane
                gridPane.add(fields[x][y], x, y+1);
            }
        }

        // using instance method for directly adding the node with columnspan and rowspan
        // gridPane.add(someButton,0,2,TWO_COLUMN_SPAN,1);

        Scene scene = new Scene(gridPane, 300, 300);
        scene.getStylesheets().add(getClass().getResource("customButton.css").toExternalForm());

        stage.setScene(scene);
    }
    private boolean checkwin() {
        // returns true if someone won

        return false;
    }

    public static void main(String[] args) {
        launch();
    }
}