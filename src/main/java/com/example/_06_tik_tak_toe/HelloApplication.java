package com.example._06_tik_tak_toe;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
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
import javafx.scene.effect.Glow;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.scene.media.Media;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.swing.Timer;





//Sound Effect by <a href="https://pixabay.com">

public class HelloApplication extends Application implements ActionListener {
    private boolean turnOfPlayer1 = true;
    private Button[][] field;
    private int width;
    private boolean stop = false;
    private short mode;
    private Scene startingScene;
    private int turn;

    private Timer botVBotTimer;
    private Label winnerLabel;
    private Button[] toBot;
    private boolean[] toBotStart;
    private RotateTransition [][]displayWin;
    private boolean decisiveCheck;

    private Glow winningGlow;
    private Timer glowTimer;
    private boolean glowRising;
    private MediaPlayer botWinSound;
    private MediaPlayer occupiedSound;
    private MediaPlayer playerWinSound;
    private MediaPlayer drawSound;
    private boolean playerWin;

    @Override
    public void start(Stage stage) throws IOException {
        decisiveCheck = false;
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


    public void minimaxBot() {
        if (this.turn >= width * width) {
            return;
        }
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
                    if (botCheckForWin(botField)) {
                        final int tempx = x;
                        final int tempy = y;
                        if (turnOfPlayer1) {
                            if (Thread.currentThread().getName().equals("AWT-EventQueue-0")) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        field[tempx][tempy].setText("X");
                                    }
                                });
                            } else {
                                field[x][y].setText("X");
                            }
                        } else {
                            if (Thread.currentThread().getName().equals("AWT-EventQueue-0")) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        field[tempx][tempy].setText("O");
                                    }
                                });
                            } else {
                                field[x][y].setText("O");
                            }
                        }
                        return;
                    }
                    int tempScore = -botRecursion(botField, !turnOfPlayer1, 1);
                    if (tempScore > saveBiggestRecursionScore[0] && tempScore != 0) {
                        saveBiggestRecursionScore[0] = tempScore;
                        saveBiggestRecursionScore[1] = x;
                        saveBiggestRecursionScore[2] = y;
                    }
                    botField[x][y] = '\0';
                }
            }
        }
        if (turnOfPlayer1) {
            if (Thread.currentThread().getName().equals("AWT-EventQueue-0")) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        field[saveBiggestRecursionScore[1]][saveBiggestRecursionScore[2]].setText("X");
                    }
                });
            } else {
                field[saveBiggestRecursionScore[1]][saveBiggestRecursionScore[2]].setText("X");
            }
        } else {
            if (Thread.currentThread().getName().equals("AWT-EventQueue-0")) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        field[saveBiggestRecursionScore[1]][saveBiggestRecursionScore[2]].setText("O");
                    }
                });
            } else {
                field[saveBiggestRecursionScore[1]][saveBiggestRecursionScore[2]].setText("O");
            }
        }
    }

    private int botRecursion(char[][] botField, boolean turn, int depth) {
        if (depth > 8 || this.turn + depth >= width * width) {
            return 1;
        }

        int saveScore = 0, tempScore;
        int[][] scores = new int[width][width];

        for (int y = 0; y < width; y++) {
            for (int x = 0; x < width; x++) {
                scores[x][y] = 0;
                if (botField[x][y] == '\0') {
                    if (turn) {
                        botField[x][y] = 'X';
                    } else {
                        botField[x][y] = 'O';
                    }
                    if (botCheckForWin(botField)) {
                        botField[x][y] = '\0';
                        return (10 - depth);
                        //scores[x][y] = -2;
                    } else {
                        tempScore = -botRecursion(botField, !turn, depth + 1);
                        /*if (tempScore > 1) {
                            return tempScore;
                        }*/
                        if (tempScore > saveScore || saveScore == 0) {
                            saveScore = tempScore;
                        }
                    }
                    botField[x][y] = '\0';
                }
            }
        }
        if (saveScore == 0) {
            return 1;
        }
        return saveScore;
    }



    private void initGame(Stage stage) {

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Platform.exit();
                System.exit(0);
            }
        });

        GridPane gridPane = new GridPane();
        turnOfPlayer1 = true;

        gridPane.setPadding(new Insets(20));
        gridPane.setAlignment(Pos.CENTER);

        winnerLabel = new Label("");
        winnerLabel.setPrefWidth(1000);
        winnerLabel.setPrefHeight(1000);

        field = new Button[width][width];

        displayWin = new RotateTransition[width][width];

        winningGlow = new Glow(0.6);

        glowTimer = new Timer(100, this);
        glowRising = true;

        String musicFile ="src/audio/botWin.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        botWinSound = new MediaPlayer(sound);
        playerWin = true;

        musicFile = "src/audio/occupied.mp3";
        sound = new Media(new File(musicFile).toURI().toString());
        occupiedSound = new MediaPlayer(sound);
        occupiedSound.setVolume(0.5);

        musicFile = "src/audio/playerWin.mp3";
        sound = new Media(new File(musicFile).toURI().toString());
        playerWinSound = new MediaPlayer(sound);
        playerWinSound.setVolume(0.5);

        musicFile = "src/audio/drawSound.mp3";
        sound = new Media(new File(musicFile).toURI().toString());
        drawSound = new MediaPlayer(sound);

        EventHandler buttonPress = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!stop) {
                    if (turn >= (width * width)) {
                        winnerLabel.setText("Draw");
                        drawSound.stop();
                        drawSound.play();
                        stop = true;
                        return;
                    }
                    if (e.getSource() instanceof Button temp) {
                        if(!temp.getText().equals("")){
                            occupiedSound.stop();
                            occupiedSound.play();
                        }
                        if ((mode == 4 || mode == 5) && temp.equals(toBot[0])) {
                            if (mode == 4) {
                                botVBotTimer.start();
                                toBotStart[0] = true;
                                return;
                            } else {
                                toBotStart[0] = true;
                                if (toBotStart[1]) {
                                    botVBotTimer.start();
                                    return;
                                } else {
                                    if (!turnOfPlayer1) {
                                        actionPerformed(null);
                                    }
                                    return;
                                }
                            }
                        } else if (mode == 5 && temp.equals(toBot[1])) {
                            toBotStart[1] = true;
                            if (toBotStart[0]) {
                                botVBotTimer.start();
                                return;
                            } else {
                                if (turnOfPlayer1) {
                                    actionPerformed(null);
                                }
                                return;
                            }
                        }
                        int effect = 13;
                        switch (mode) {
                            case 0 -> {
                                effect = 0;
                            }
                            case 1 -> {
                                effect = 1;
                            }
                            case 2 -> {
                                effect = 2;
                            }
                            case 4 -> {
                                if (!toBotStart[0]) {
                                    effect = 1;
                                }
                            }
                            case 5 -> {
                                if (!toBotStart[0] && !toBotStart[1]) {
                                    effect = 0;
                                } else if (!toBotStart[0]) {
                                    effect = 2;
                                } else if (!toBotStart[1]) {
                                    effect = 1;
                                }
                            }
                        }
                        switch (effect) {
                            case 0 -> {
                                if (temp.getText().equals("")) {
                                    decisiveCheck = true;
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
                                    decisiveCheck = false;
                                    turnOfPlayer1 = !turnOfPlayer1;
                                    ++turn;
                                }
                            }
                            case 1 -> {
                                if (temp.getText().equals("") && turnOfPlayer1) {
                                    temp.setText("X");
                                    decisiveCheck = true;
                                    if (checkForWin(null)) {
                                        winnerLabel.setText("X won");
                                        stop = true;
                                        return;
                                    }
                                    decisiveCheck = false;
                                    turnOfPlayer1 = !turnOfPlayer1;
                                    ++turn;

                                    minimaxBot();

                                    decisiveCheck = true;
                                    playerWin = false;
                                    if (checkForWin(null)) {
                                        winnerLabel.setText("O won");
                                        stop = true;
                                        return;
                                    }
                                    decisiveCheck = false;
                                    playerWin = true;
                                    turnOfPlayer1 = !turnOfPlayer1;
                                    ++turn;
                                }
                            }
                            case 2 -> {
                                if (temp.getText().equals("") && !turnOfPlayer1) {
                                    temp.setText("O");
                                    decisiveCheck = true;
                                    if (checkForWin(null)) {
                                        winnerLabel.setText("0 won");
                                        stop = true;
                                        return;
                                    }
                                    decisiveCheck = false;
                                    turnOfPlayer1 = !turnOfPlayer1;
                                    ++turn;
                                    minimaxBot();

                                    decisiveCheck = true;
                                    playerWin = false;
                                    if (checkForWin(null)) {
                                        winnerLabel.setText("X won");
                                        stop = true;
                                        return;
                                    }
                                    decisiveCheck = false;
                                    playerWin = true;
                                    turnOfPlayer1 = !turnOfPlayer1;
                                    ++turn;
                                }
                            }
                        }
                    }
                    if (turn >= (width * width)) {
                        winnerLabel.setText("Draw");
                        drawSound.stop();
                        drawSound.play();
                        stop = true;
                    }
                } else {
                    stage.setScene(startingScene);
                    decisiveCheck = false;
                    stop = !stop;
                    turn = 0;
                    turnOfPlayer1 = true;
                    width = 0;
                    mode = 0;
                    if (botVBotTimer != null) {
                        botVBotTimer.stop();
                    }
                    glowTimer.stop();
                    playerWinSound.stop();
                    drawSound.stop();
                }
            }
        };

        if (mode == 4 || mode == 5) {
            if (mode == 4) {
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
            } else {
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
        } else {
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

                displayWin[x][y] = new RotateTransition();
                displayWin[x][y].setDuration(Duration.millis(1000));
                displayWin[x][y].setAxis(Rotate.Z_AXIS);
                displayWin[x][y].setAutoReverse(true);
                displayWin[x][y].setCycleCount(500);
                displayWin[x][y].setByAngle(360);
                displayWin[x][y].setNode(field[x][y]);
            }
        }

        Scene scene;
        if (width < 7) {
            scene = new Scene(gridPane, 300, 300);
        } else {
            scene = new Scene(gridPane, (int) ((width - 5) / 2) * 100 + 300, (int) ((width - 5) / 2) * 100 + 300);
        }
        scene.getStylesheets().add(getClass().getResource("customButton.css").toExternalForm());

        stage.setScene(scene);

        if (mode == 2 || mode == 3) {
            /* random placement on first action of bot
            int pos = new Random().nextInt(width * width);
            field[pos % width][(int) (pos / width)].setText("X");
            //*/
            minimaxBot();
            turnOfPlayer1 = !turnOfPlayer1;
            ++turn;
        }
        if (mode == 3 || mode == 4 || mode == 5) {
            botVBotTimer = new Timer(1000, this);
            if (mode == 3) {
                botVBotTimer.start();
            }
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
    }

    private boolean botCheckForWin(char[][] field) {
        // returns true if someone won
        char check = '\0';
        boolean tempcheck = true;

        //setting win conditions
        int limit;
        if(mode == 0){
            if (width < 7) {
                limit = Math.min(width, 4);
            } else {
                limit = 5;
            }
        }else{
            limit = width;
        }

        // straight lines
        for (int y = 0; y < width; ++y) {
            for (int x = 0; x < width; ++x) {
                check = field[x][y];
                if (check == '\0') {
                    tempcheck = false;
                    continue;
                }
                //horizontal
                if (width - x >= limit) {
                    tempcheck = true;
                    for (int i = 0; i < limit; ++i) {
                        if (field[x + i][y] != check) {
                            tempcheck = false;
                            break;
                        }
                    }
                    if (tempcheck) {
                        if (decisiveCheck) {
                            winDisplay(x, y, 0);
                        }
                        return true;
                    }
                }
                //vertical
                if (width - y >= limit) {
                    tempcheck = true;
                    for (int i = 0; i < limit; ++i) {
                        if (field[x][y + i] != check) {
                            tempcheck = false;
                            break;
                        }
                    }
                    if (tempcheck) {
                        if(decisiveCheck) {
                            winDisplay(x, y, 1);
                        }
                        return true;
                    }
                }
            }
        }
        //diagonals
        for (int y = 0; y < width; ++y) {
            if(width - y > limit){
                break;
            }
            for (int x = 0; x < width; ++x) {
                check = field[x][y];
                if (check == '\0') {
                    tempcheck = false;
                    continue;
                }
                //to the right
                if (width - x >= limit) {
                    tempcheck = true;
                    for (int i = 0; i < limit; ++i) {
                        if (field[x + i][y + i] != check) {
                            tempcheck = false;
                            break;
                        }
                    }
                    if (tempcheck) {
                        if (decisiveCheck) {
                            winDisplay(x, y, 2);
                        }
                        return true;
                    }
                }
                // to the left
                if (x + 1 >= limit) {
                    tempcheck = true;
                    for (int i = 0; i < limit; ++i) {
                        if (field[x - i][y + i] != check) {
                            tempcheck = false;
                            break;
                        }
                    }
                    if (tempcheck) {
                        if (decisiveCheck) {
                            winDisplay(x, y, 3);
                        }
                        return true;
                    }
                }

            }
        }

        if (limit == 4) {
            /*squares
            for (int y = 0; y < width - 1; ++y) {
                for (int x = 0; x < width - 1; ++x) {
                    if (field[x][y] != '\0') {
                        if ((field[x][y] == field[x + 1][y]) && (field[x][y] == field[x][y + 1]) && (field[x][y] == field[x + 1][y + 1])) {
                            if(decisiveCheck) {
                                winDisplay(x, y, 4);
                            }
                            return true;
                        }
                    }
                }
            }*/
            //diamond
            for (int y = 0; y < width - 2; ++y) {
                for (int x = 1; x < width - 1; ++x) {
                    if (field[x][y] != '\0') {
                        if ((field[x][y] == field[x + 1][y + 1]) && (field[x][y] == field[x - 1][y + 1]) && (field[x][y] == field[x][y + 2])) {
                            if(decisiveCheck) {
                                winDisplay(x, y, 5);
                            }
                            return true;
                        }
                    }
                }
            }
        } else if (limit == 5 && mode == 0) {
            //cross
            for (int y = 0; y < width - 2; ++y) {
                for (int x = 1; x < width - 1; ++x) {
                    if (field[x][y] != '\0') {
                        if ((field[x][y] == field[x][y + 1]) && (field[x][y] == field[x + 1][y + 1]) && (field[x][y] == field[x - 1][y + 1]) && (field[x][y] == field[x][y + 2])) {
                            if (decisiveCheck) {
                                winDisplay(x, y, 6);
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return tempcheck;
    }

    private void winDisplay(int x, int y, int type) {
        if (!playerWin) {
            botWinSound.stop();
            botWinSound.play();
        }else{
            playerWinSound.stop();
            playerWinSound.play();
        }
        int limits;
        if(mode == 0){
            if (width < 7) {
                limits = Math.min(width, 4);
            } else {
                limits = 5;
            }
        }else{
            limits = width;
        }
        glowTimer.start();
        switch(type){
            case 0 -> {//horizontal
                for(int i = 0; i < limits; ++i){
                    field[x + i][y].setStyle("-fx-background-color: #ffd700");
                    field[x + i][y].setEffect(winningGlow);
                    displayWin[x + i][y].play();
                    glowTimer.start();
                }
            }
            case 1 -> {//vertical
                for(int i = 0; i < limits; ++i){
                    field[x][y + i].setStyle("-fx-background-color: #ffd700");
                    field[x][y + i].setEffect(winningGlow);
                    displayWin[x][y + i].play();
                    glowTimer.start();
                }
            }
            case 2 -> {//diagonal right
                for(int i = 0; i < limits; ++i){
                    field[x + i][y + i].setStyle("-fx-background-color: #ffd700");
                    field[x + i][y + i].setEffect(winningGlow);
                    displayWin[x + i][y + i].play();
                    glowTimer.start();
                }
            }
            case 3 -> {//diagonals left
                for(int i = 0; i < limits; ++i){
                    field[x - i][y + i].setStyle("-fx-background-color: #ffd700");
                    field[x - i][y + i].setEffect(winningGlow);
                    displayWin[x - i][y + i].play();
                    glowTimer.start();
                }
            }
            case 4 -> {//square
                field[x][y].setStyle("-fx-background-color: #ffd700");
                field[x][y].setEffect(winningGlow);
                displayWin[x][y].play();

                field[x + 1][y].setStyle("-fx-background-color: #ffd700");
                field[x + 1][y].setEffect(winningGlow);
                displayWin[x + 1][y].play();

                field[x][y + 1].setStyle("-fx-background-color: #ffd700");
                field[x][y + 1].setEffect(winningGlow);
                displayWin[x][y + 1].play();

                field[x + 1][y + 1].setStyle("-fx-background-color: #ffd700");
                field[x + 1][y + 1].setEffect(winningGlow);
                displayWin[x + 1][y + 1].play();
            }
            case 5 -> {//diamond
                field[x][y].setStyle("-fx-background-color: #ffd700");
                field[x][y].setEffect(winningGlow);
                displayWin[x][y].play();

                field[x + 1][y + 1].setStyle("-fx-background-color: #ffd700");
                field[x + 1][y + 1].setEffect(winningGlow);
                displayWin[x + 1][y + 1].play();

                field[x - 1][y + 1].setStyle("-fx-background-color: #ffd700");
                field[x - 1][y + 1].setEffect(winningGlow);
                displayWin[x - 1][y + 1].play();

                field[x][y + 2].setStyle("-fx-background-color: #ffd700");
                field[x][y + 2].setEffect(winningGlow);
                displayWin[x][y + 2].play();
            }
            case 6 -> {//cross
                field[x][y].setStyle("-fx-background-color: #ffd700");
                field[x][y].setEffect(winningGlow);
                displayWin[x][y].play();

                field[x + 1][y + 1].setStyle("-fx-background-color: #ffd700");
                field[x + 1][y + 1].setEffect(winningGlow);
                displayWin[x + 1][y + 1].play();

                field[x - 1][y + 1].setStyle("-fx-background-color: #ffd700");
                field[x - 1][y + 1].setEffect(winningGlow);
                displayWin[x - 1][y + 1].play();

                field[x][y + 1].setStyle("-fx-background-color: #ffd700");
                field[x][y + 1].setEffect(winningGlow);
                displayWin[x][y + 1].play();

                field[x][y + 2].setStyle("-fx-background-color: #ffd700");
                field[x][y + 2].setEffect(winningGlow);
                displayWin[x][y + 2].play();
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if(e.getSource().equals(glowTimer)){
            double glowLevel = winningGlow.getLevel();
            if(glowLevel > 1) {
                glowRising = false;
            }else if(glowLevel < 0.1){
                glowRising = true;
            }
            if(glowRising){
                winningGlow.setLevel(glowLevel + 0.1);
            }else{
                winningGlow.setLevel(glowLevel - 0.1);
            }
            return;
        }
        if (turn >= width * width) {
            return;
        }
        minimaxBot();
        decisiveCheck = true;
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
        decisiveCheck = false;
        turnOfPlayer1 = !turnOfPlayer1;
        ++turn;
        if (turn >= width * width) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    winnerLabel.setText("Draw");
                    drawSound.stop();
                    drawSound.play();
                }
            });
            stop = true;
        }
    }
}