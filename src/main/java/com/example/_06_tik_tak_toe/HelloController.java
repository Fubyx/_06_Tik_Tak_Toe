package com.example._06_tik_tak_toe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;
    private boolean turn = false;

    @FXML
    protected void clicked(ActionEvent e) {
        if(e.getSource().getClass().equals(Button.class)){
            Button temp = (Button) e.getSource();
            if(temp.getText() != ""){
                return;
            }
            if(turn){
                temp.setText("X");
                turn = false;
            }else{
                temp.setText("O");
                turn = true;
            }
        }

    }
}