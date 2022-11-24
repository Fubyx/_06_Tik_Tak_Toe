package com.example._06_tik_tak_toe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class HelloController {
    @FXML
    private TextField txt;
    @FXML
    private Label label;
    private boolean turn = false;

    @FXML
    protected void startButtonClicked(){
        try {
            int x = Integer.parseInt(txt.getText());
            System.out.println(x);
        }catch (NumberFormatException e){
            label.setText("Enter a valid Number");
        }
    }


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