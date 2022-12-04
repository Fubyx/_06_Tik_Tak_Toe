package com.example._06_tik_tak_toe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.*;

public class HelloApplication extends Application implements ActionListener {


    private boolean turnOfPlayer1 = true;
    private Button[][] field;
    private int width;
    private boolean stop = false;
    short mode;
    private Scene startingScene;
    private int turn;

    private Timer botVBotTimer;
    private Label winnerLabel;
    private Button [] toBot;
    private boolean []toBotStart;

    @Override
    public void start(Stage stage) throws IOException {
        turn = 0;
        mode = 13;
        Label label = new Label("Insert x (<15): ");
        label.setPrefWidth(1000.0);
        TextField text = new TextField();
        text.setPrefWidth(1000.0);

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "PvP",
                        "PvE",
                        "EvP",
                        "EvE",
                        "P+EvE",
                        "P+EvP+E"
                );
        ComboBox box = new ComboBox(options);
        box.setPrefWidth(1000.0);

        EventHandler buttonPress = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    width = Integer.parseInt(text.getText());
                    if (width > 14) {
                        throw new NumberFormatException();
                    }
                    try {

                        String temp = box.getSelectionModel().getSelectedItem().toString();
                        switch (temp) {
                            case "PvE" -> mode = 1;
                            case "EvP" -> mode = 2;
                            case "EvE" -> mode = 3;
                            case "P+EvE" -> mode = 4;
                            case "P+EvP+E" -> mode = 5;
                            default -> mode = 0;
                        }
                    } catch (NullPointerException ex) {
                        mode = 0;
                    }
                    initGame(stage);
                } catch (NumberFormatException ex) {
                    label.setText("Enter a valid Number (<15)");
                }
            }
        };
        Button button = new Button("Confirm");
        button.setOnAction(buttonPress);
        button.setPrefWidth(1000.0);

        Label label1 = new Label("Gamemode: ");
        label1.setPrefWidth(1000.0);


        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));

        gridPane.setVgap(10);
        gridPane.setHgap(10);


        gridPane.add(label1, 0, 0);
        gridPane.add(box, 1, 0);
        gridPane.add(label, 0, 1, 2, 1);

        gridPane.add(text, 0, 2, 2, 1);
        gridPane.add(button, 0, 3, 2, 1);

        startingScene = new Scene(gridPane, 400, 400);
        startingScene.getStylesheets().add(getClass().getResource("customButton.css").toExternalForm());

        stage.setScene(startingScene);

        stage.setTitle("Tic Tac Toe");
        stage.show();
        stage.setScene(startingScene);
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
        int limits;
        if (width < 7) {
            limits = Math.min(width, 4);
        } else {
            limits = 5;
        }
        char sign;
        // 1: check if win possible
        if (turn >= 2 * limits - 1) {
            if (turnOfPlayer1) {
                sign = 'X';
            } else {
                sign = 'O';
            }
            for (int y = 0; y < width; ++y) {
                for (int x = 0; x < width; ++x) {
                    if (botField[x][y] == '\0') {
                        botField[x][y] = sign;
                        if (checkForWin(botField)) {
                            int finalX = x;
                            int finalY = y;
                            if (turnOfPlayer1) {
                                if(Thread.currentThread().getName().equals("AWT-EventQueue-0")){
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            field[finalX][finalY].setText("X");
                                        }
                                    });
                                }else{
                                    field[finalX][finalY].setText("X");
                                }
                            } else {
                                if(Thread.currentThread().getName().equals("AWT-EventQueue-0")){
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            field[finalX][finalY].setText("O");
                                        }
                                    });
                                }else{
                                    field[finalX][finalY].setText("O");
                                }
                            }
                            return;
                        } else {
                            botField[x][y] = '\0';
                        }
                    }
                }
            }
        }

        if (turn >= 2 * limits - 3) {
            if (turnOfPlayer1) {
                sign = 'O';
            } else {
                sign = 'X';
            }
            for (int y = 0; y < width; ++y) {
                for (int x = 0; x < width; ++x) {
                    if (botField[x][y] == '\0') {
                        botField[x][y] = sign;
                        if (checkForWin(botField)) {
                            int finalX = x;
                            int finalY = y;
                            if (turnOfPlayer1) {
                                if(Thread.currentThread().getName().equals("AWT-EventQueue-0")){
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            field[finalX][finalY].setText("X");
                                        }
                                    });
                                }else{
                                    field[finalX][finalY].setText("X");
                                }
                            } else {
                                if(Thread.currentThread().getName().equals("AWT-EventQueue-0")){
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            field[finalX][finalY].setText("O");
                                        }
                                    });
                                }else{
                                    field[finalX][finalY].setText("O");
                                }
                            }
                            return;
                        } else {
                            botField[x][y] = '\0';
                        }
                    }
                }
            }
        }

        if (turnOfPlayer1) {
            sign = 'X';
        } else {
            sign = 'O';
        }

        int[] highest = new int[3];
        highest[0] = 0;
        highest[1] = 0;
        highest[2] = 0;
        for (int y = 0; y < width; ++y) {
            for (int x = 0; x < width; ++x) {
                //System.out.print("t");
                if (botField[x][y] == '\0') {
                    botField[x][y] = sign;
                    int score = newBotRec(botField, turnOfPlayer1, 0);
                    //System.out.println(score);
                    /*if (score < 0) {
                        score = score * -1;
                    }*/
                    if (highest[0] != 0 && score >= highest[0]) {

                        highest[0] = score;
                        highest[1] = x;
                        highest[2] = y;
                    } else if (score > 0) {
                        highest[0] = score;
                        highest[1] = x;
                        highest[2] = y;
                    }
                    botField[x][y] = '\0';
                }
            }
        }
        if (field[highest[1]][highest[2]].getText().equals("")) {
            if (turnOfPlayer1) {
                if(Thread.currentThread().getName().equals("AWT-EventQueue-0")){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            field[highest[1]][highest[2]].setText("X");
                        }
                    });
                }else{
                    field[highest[1]][highest[2]].setText("X");
                }
            } else {
                if(Thread.currentThread().getName().equals("AWT-EventQueue-0")){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            field[highest[1]][highest[2]].setText("O");
                        }
                    });
                }else{
                    field[highest[1]][highest[2]].setText("O");
                }
            }
            return;
        } else {
            if (field[1][1].getText().equals("")) {
                if (turnOfPlayer1) {
                    if(Thread.currentThread().getName().equals("AWT-EventQueue-0")){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                field[1][1].setText("X");
                            }
                        });
                    }else{
                        field[1][1].setText("X");
                    }
                } else {
                    if(Thread.currentThread().getName().equals("AWT-EventQueue-0")){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                field[1][1].setText("O");
                            }
                        });
                    }else{
                        field[1][1].setText("O");
                    }
                }
            }else{
                for (int y = 0; y < width; ++y) {
                    for (int x = 0; x < width; ++x) {
                        if (field[x][y].getText().equals("")) {
                            int finalY = y;
                            int finalX = x;
                            if (turnOfPlayer1) {
                                if(Thread.currentThread().getName().equals("AWT-EventQueue-0")){
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            field[finalX][finalY].setText("X");
                                        }
                                    });
                                }else{
                                    field[finalX][finalY].setText("X");
                                }
                            } else {
                                if(Thread.currentThread().getName().equals("AWT-EventQueue-0")){
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            field[finalX][finalY].setText("O");
                                        }
                                    });
                                }else{
                                    field[finalX][finalY].setText("O");
                                }
                            }
                            return;
                        }
                    }
                }
            }
        }



        /*
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
                    if (botCheckForWin(botField)) {
                        if (turnOfPlayer1) {
                            field[x][y].setText("X");
                        } else {
                            field[x][y].setText("O");
                        }
                        return;
                    }
                    int tempScore = botRecursion(botField, !turnOfPlayer1, 1);
                    if (tempScore > saveBiggestRecursionScore[0]) {
                        saveBiggestRecursionScore[0] = tempScore;
                        saveBiggestRecursionScore[1] = x;
                        saveBiggestRecursionScore[2] = y;
                    }
                    botField[x][y] = '\0';
                }
            }
        }
        if (turnOfPlayer1) {
            field[saveBiggestRecursionScore[1]][saveBiggestRecursionScore[2]].setText("X");
        } else {
            field[saveBiggestRecursionScore[1]][saveBiggestRecursionScore[2]].setText("O");
        }
         */
    }

    private int botRecursion(char[][] botField, boolean turn, int depth) {
        if (depth > 5) {
            return 0;
        }
        int score = 0;
        boolean check;
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < width; x++) {
                if (botField[x][y] == '\0') {

                    if (turn) {
                        botField[x][y] = 'X';
                    } else {
                        botField[x][y] = 'O';
                    }
                    if (botCheckForWin(botField)) {
                        if (depth == 1) {
                            botField[x][y] = '\0';
                            return -1000;
                        }
                        score -= 1;
                    } else {
                        score -= botRecursion(botField, !turn, depth + 1);
                    }
                    botField[x][y] = '\0';
                }
            }
        }
        return score;
    }


    private int newBotRec(char[][] botField, boolean p1, int depth) {
        int limits;
        if (width < 7) {
            limits = Math.min(width, 4);
        } else {
            limits = 5;
        }
        char sign;
        // 1: check if win possible
        if (turn >= 2 * limits - 1) {
            if (p1) {
                sign = 'X';
            } else {
                sign = 'O';
            }
            for (int y = 0; y < width; ++y) {
                for (int x = 0; x < width; ++x) {
                    if (botField[x][y] == '\0') {
                        botField[x][y] = sign;
                        if (checkForWin(botField)) {
                            return (int) Math.pow(10, 5 - depth);
                        } else {
                            botField[x][y] = '\0';
                        }
                    }
                }
            }
        }
        if (turn >= 2 * limits - 3) {
            if (p1) {
                sign = 'O';
            } else {
                sign = 'X';
            }
            for (int y = 0; y < width; ++y) {
                for (int x = 0; x < width; ++x) {
                    if (botField[x][y] == '\0') {
                        botField[x][y] = sign;
                        if (checkForWin(botField)) {
                            return (int) (-1 * Math.pow(10, 5 - depth));
                        } else {
                            botField[x][y] = '\0';
                        }
                    }
                }
            }
        }
        if (depth > 6 || (turn + depth >= width * width)) {
            return 0;
        }

        // stopped -----------------------------------------------------------------------------
        if (p1) {
            sign = 'O';
        } else {
            sign = 'X';
        }
        int res = 0;
        for (int y = 0; y < width; ++y) {
            for (int x = 0; x < width; ++x) {
                if (botField[x][y] == '\0') {
                    botField[x][y] = sign;
                    res += newBotRec(botField, !p1, depth + 1);
                    botField[x][y] = '\0';
                    if (res < 1 + (-2 * Math.pow(10, 5 - depth + 1))) {
                        return (int) (-1 * Math.pow(10, 5 - depth));
                    }
                }
            }
        }
        return res;
    }

    private void initGame(Stage stage) {

        GridPane gridPane = new GridPane();
        turnOfPlayer1 = true;


        //gridPane.setVgap(10);
        //gridPane.setHgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setAlignment(Pos.CENTER);

        winnerLabel = new Label("");
        winnerLabel.setPrefWidth(1000);
        winnerLabel.setPrefHeight(1000);

        field = new Button[width][width];

        EventHandler buttonPress = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!stop) {
                    if (turn >= (width * width)) {
                        winnerLabel.setText("Draw");
                        stop = true;
                        return;
                    }
                    if (e.getSource() instanceof Button temp) {
                        if((mode == 4 || mode == 5) && temp.equals(toBot[0])){
                            if(mode == 4){
                                botVBotTimer.start();
                                toBotStart[0] = true;
                                return;
                            }else{
                                toBotStart[0] = true;
                                if(toBotStart[1]){
                                    botVBotTimer.start();
                                    return;
                                }else{
                                    if(!turnOfPlayer1){
                                        actionPerformed(null);
                                    }
                                    return;
                                }
                            }
                        }else if(mode == 5 && temp.equals(toBot[1])){
                            toBotStart[1] = true;
                            if(toBotStart[0]){
                                botVBotTimer.start();
                                return;
                            }else{
                                if(turnOfPlayer1){
                                    actionPerformed(null);
                                }
                                return;
                            }
                        }
                        int effect = 13;
                        switch (mode) {
                            case 0 -> {
                                effect = 0;
                                /*
                                if (temp.getText().equals("")) {
                                    if (turnOfPlayer1) {
                                        temp.setText("X");
                                        if (checkForWin(null)) {
                                            winnerLabel.setText("Player X won");
                                            stop = true;
                                            return;
                                        }
                                    } else {
                                        temp.setText("O");
                                        if (checkForWin(null)) {
                                            winnerLabel.setText("Player O won");
                                            stop = true;
                                            return;
                                        }
                                    }
                                    turnOfPlayer1 = !turnOfPlayer1;
                                    ++turn;
                                }//*/
                            }
                            case 1 -> {
                                effect = 1;
                                /*
                                if (temp.getText().equals("") && turnOfPlayer1) {
                                    temp.setText("X");
                                    if (checkForWin(null)) {
                                        winnerLabel.setText("Player X won");
                                        stop = true;
                                        return;
                                    }

                                    turnOfPlayer1 = !turnOfPlayer1;
                                    ++turn;
                                    bot();
                                    if (checkForWin(null)) {
                                        winnerLabel.setText("Player O won");
                                        stop = true;
                                        return;
                                    }
                                    turnOfPlayer1 = !turnOfPlayer1;
                                    ++turn;
                                }//*/
                            }
                            case 2 -> {
                                effect = 2;
                                /*
                                if (temp.getText().equals("") && !turnOfPlayer1) {
                                    temp.setText("O");
                                    if (checkForWin(null)) {
                                        winnerLabel.setText("Player 0 won");
                                        stop = true;
                                        return;
                                    }
                                    turnOfPlayer1 = !turnOfPlayer1;
                                    ++turn;
                                    bot();
                                    if (checkForWin(null)) {
                                        winnerLabel.setText("Player X won");
                                        stop = true;
                                        return;
                                    }
                                    turnOfPlayer1 = !turnOfPlayer1;
                                    ++turn;
                                }//*/
                            }
                            case 4 -> {
                                if(!toBotStart[0]) {
                                    effect = 1;
                                }
                                /*
                                if (temp.getText().equals("") && turnOfPlayer1 && !toBotStart[0]) {
                                    temp.setText("X");
                                    if (checkForWin(null)) {
                                        winnerLabel.setText("Player X won");
                                        stop = true;
                                        return;
                                    }

                                    turnOfPlayer1 = !turnOfPlayer1;
                                    ++turn;
                                    bot();
                                    if (checkForWin(null)) {
                                        winnerLabel.setText("Player O won");
                                        stop = true;
                                        return;
                                    }
                                    turnOfPlayer1 = !turnOfPlayer1;
                                    ++turn;
                                }//*/
                            }
                            case 5 -> {
                                if(!toBotStart[0] && !toBotStart[1]){
                                    effect = 0;
                                }else if(!toBotStart[0]){
                                    effect = 2;
                                }else if(!toBotStart[1]){
                                    effect = 1;
                                }
                            }
                        }
                        switch(effect){
                            case 0 -> {
                                if (temp.getText().equals("")) {
                                    if (turnOfPlayer1) {
                                        temp.setText("X");
                                        if (checkForWin(null)) {
                                            winnerLabel.setText("X won");
                                            stop = true;
                                            return;
                                        }
                                    } else {
                                        temp.setText("O");
                                        if (checkForWin(null)) {
                                            winnerLabel.setText("O won");
                                            stop = true;
                                            return;
                                        }
                                    }
                                    turnOfPlayer1 = !turnOfPlayer1;
                                    ++turn;
                                }
                            }
                            case 1 -> {
                                if (temp.getText().equals("") && turnOfPlayer1) {
                                    temp.setText("X");
                                    if (checkForWin(null)) {
                                        winnerLabel.setText("X won");
                                        stop = true;
                                        return;
                                    }

                                    turnOfPlayer1 = !turnOfPlayer1;
                                    ++turn;
                                    bot();
                                    if (checkForWin(null)) {
                                        winnerLabel.setText("O won");
                                        stop = true;
                                        return;
                                    }
                                    turnOfPlayer1 = !turnOfPlayer1;
                                    ++turn;
                                }
                            }
                            case 2 -> {
                                if (temp.getText().equals("") && !turnOfPlayer1) {
                                    temp.setText("O");
                                    if (checkForWin(null)) {
                                        winnerLabel.setText("0 won");
                                        stop = true;
                                        return;
                                    }
                                    turnOfPlayer1 = !turnOfPlayer1;
                                    ++turn;
                                    bot();
                                    if (checkForWin(null)) {
                                        winnerLabel.setText("X won");
                                        stop = true;
                                        return;
                                    }
                                    turnOfPlayer1 = !turnOfPlayer1;
                                    ++turn;
                                }
                            }
                        }
                    }
                    if (turn >= (width * width)) {
                        winnerLabel.setText("Draw");
                        stop = true;
                        return;
                    }
                } else {
                    stage.setScene(startingScene);
                    stop = !stop;
                    turn = 0;
                    turnOfPlayer1 = true;
                    width = 0;
                    mode = 13;
                    if(botVBotTimer != null) {
                        botVBotTimer.stop();
                    }
                }
            }
        };

        if(mode == 4 || mode == 5) {
            if(mode == 4){
                gridPane.add(winnerLabel, 0, 0, width - 1, 1);
                toBot = new Button[1];
                toBotStart = new boolean[1];
                toBot[0] = new Button();
                toBot[0].setPrefWidth(1000);
                toBot[0].setPrefHeight(1000);
                toBot[0].setOnAction(buttonPress);
                toBot[0].setStyle("-fx-background-color: #ff0000; ");
                gridPane.add(toBot[0], width - 1, 0);
                toBotStart[0] = false;
                toBot[0].setText("A");
            }else{
                gridPane.add(winnerLabel, 1, 0, width - 2, 1);
                toBot = new Button[2];
                toBotStart = new boolean[2];

                toBot[0] = new Button();
                toBot[0].setPrefWidth(1000);
                toBot[0].setPrefHeight(1000);
                toBot[0].setOnAction(buttonPress);
                toBot[0].setStyle("-fx-background-color: #ff0000; ");
                gridPane.add(toBot[0], width - 1, 0);
                toBotStart[0] = false;

                toBot[1] = new Button();
                toBot[1].setPrefWidth(1000);
                toBot[1].setPrefHeight(1000);
                toBot[1].setOnAction(buttonPress);
                toBot[1].setStyle("-fx-background-color: #ff0000; ");
                toBot[1].setText("A1");
                gridPane.add(toBot[1], 0, 0);
                toBotStart[1] = false;
                toBot[0].setText("A2");
            }
        }else{
            gridPane.add(winnerLabel, 0, 0, width, 1);
        }

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

        Scene scene;
        if (width < 7) {
            scene = new Scene(gridPane, 300, 300);
        } else {
            scene = new Scene(gridPane, (int) ((width - 5) / 2) * 100 + 300, (int) ((width - 5) / 2) * 100 + 300);
        }
        scene.getStylesheets().add(getClass().getResource("customButton.css").toExternalForm());

        stage.setScene(scene);

        if (mode == 2 || mode == 3) {
            //bot();
            int pos = new Random().nextInt(width * width);
            field[pos%width][(int)(pos/width)].setText("X");
            turnOfPlayer1 = !turnOfPlayer1;
            ++turn;
        }
        if(mode == 3 || mode == 4 || mode == 5) {
            botVBotTimer = new Timer(1000, this);
            if(mode == 3) {
                botVBotTimer.start();
            }
            /*
            for (int count = 0; count < width * width; count++) {
                bot();
                if (checkForWin(null)) {
                    if (turnOfPlayer1) {
                        winnerLabel.setText("Player X won");
                    } else {
                        winnerLabel.setText("Player O won");
                    }
                    stop = true;
                    return;
                }
                turnOfPlayer1 = !turnOfPlayer1;
                ++turn;
                if (turn >= width * width) {
                    winnerLabel.setText("Draw");
                    stop = true;
                    return;
                }
            }*/
        }
    }

    private boolean checkForWin(char[][] field1) {
        if (field1 == null) {
            field1 = new char[width][width];
            for (int y = 0; y < width; ++y) {
                for (int x = 0; x < width; ++x) {

                    if (field[x][y].getText().equals("")) {
                        field1[x][y] = '\0';
                    } else {
                        field1[x][y] = field[x][y].getText().charAt(0);
                    }
                }
            }
        }
        return botCheckForWin(field1);
        /* returns true if someone won
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
        //*/
    }

    private boolean botCheckForWin(char[][] field) {
        // returns true if someone won
        char check = '\0';
        boolean tempcheck = true;

        // straight lines
        int limit;
        if (width < 7) {
            limit = Math.min(width, 4);
        } else {
            limit = 5;
        }
        for (int y = 0; y < width; ++y) {
            for (int x = 0; x < width; ++x) {
                check = field[x][y];
                if (check == '\0') {
                    tempcheck = false;
                    continue;
                }
                if (width - x >= limit) {
                    tempcheck = true;
                    for (int i = 0; i < limit; ++i) {
                        if (field[x + i][y] != check) {
                            tempcheck = false;
                            break;
                        }
                    }
                    if (tempcheck) {
                        return true;
                    }
                }
                if (width - y >= limit) {
                    tempcheck = true;
                    for (int i = 0; i < limit; ++i) {
                        if (field[x][y + i] != check) {
                            tempcheck = false;
                            break;
                        }
                    }
                    if (tempcheck) {
                        return true;
                    }
                }
            }
        }
        //diagonals
        for (int y = 0; y < width; ++y) {
            for (int x = 0; x < width; ++x) {
                check = field[x][y];
                if (check == '\0') {
                    tempcheck = false;
                    continue;
                }
                if (width - x >= limit && width - y >= limit) {
                    tempcheck = true;
                    for (int i = 0; i < limit; ++i) {
                        if (field[x + i][y + i] != check) {
                            tempcheck = false;
                            break;
                        }
                    }
                    if (tempcheck) {
                        return true;
                    }
                }
                if (x + 1 >= limit && width - y >= limit) {
                    tempcheck = true;
                    for (int i = 0; i < limit; ++i) {
                        if (field[x - i][y + i] != check) {
                            tempcheck = false;
                            break;
                        }

                    }
                    if (tempcheck) {
                        return true;
                    }
                }
            }
        }
        /*

        for (int y = 0; y < width; y++) {
            tempcheck = true;
            check = field[0][y];
            for (int x = 0; x < (Math.min(width, 4)); x++) {
                if (field[x][y] == '\0' || !(field[x][y] == check)) {
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
            check = field[x][0];
            for (int y = 0; y < width; y++) {
                if (field[x][y] == '\0' || !(field[x][y] == check)) {
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
        check = field[0][0];
        for (int x = 0; x < width; x++) {
            if (field[x][x] == '\0' || !(field[x][x] == check)) {
                tempcheck = false;
                break;
            }
        }
        if (tempcheck) {
            return true;
        }

        tempcheck = true;
        check = field[0][width - 1];
        for (int x = 0; x < width; x++) {
            if (field[x][width - x - 1] == '\0' || !(field[x][width - 1 - x] == check)) {
                tempcheck = false;
                break;
            }
        }
        //*/
        if (limit == 4) {
            //squares
            for (int y = 0; y < width - 1; ++y) {
                for (int x = 0; x < width - 1; ++x) {
                    if (field[x][y] != '\0') {
                        if ((field[x][y] == field[x + 1][y]) && (field[x][y] == field[x][y + 1]) && (field[x][y] == field[x + 1][y + 1])) {
                            return true;
                        }
                    }
                }
            }
            //diamond
            for (int y = 0; y < width - 2; ++y) {
                for (int x = 1; x < width - 1; ++x) {
                    if (field[x][y] != '\0') {
                        if ((field[x][y] == field[x + 1][y + 1]) && (field[x][y] == field[x - 1][y + 1]) && (field[x][y] == field[x][y + 2])) {
                            return true;
                        }
                    }
                }
            }
        } else if (limit == 5) {
            //cross
            for (int y = 0; y < width - 2; ++y) {
                for (int x = 1; x < width - 1; ++x) {
                    if (field[x][y] != '\0') {
                        if ((field[x][y] == field[x][y + 1]) && (field[x][y] == field[x + 1][y + 1]) && (field[x][y] == field[x - 1][y + 1]) && (field[x][y] == field[x][y + 2])) {
                            return true;
                        }
                    }
                }
            }
        }

        return tempcheck;
    }


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if(turn >= width * width){
            return;
        }
        bot();
        if (checkForWin(null)) {
            if (turnOfPlayer1) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        winnerLabel.setText("X won");
                    }
                });
            } else {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        winnerLabel.setText("O won");
                    }
                });
            }
            stop = true;
            return;
        }
        turnOfPlayer1 = !turnOfPlayer1;
        ++turn;
        if (turn >= width * width) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    winnerLabel.setText("Draw");
                }
            });
            stop = true;
            return;
        }
    }
}