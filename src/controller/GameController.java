package controller;

import helper.InputHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;

import javafx.scene.layout.*;

import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import model.*;
import helper.Map;
import model.core.Dice;


import static javafx.scene.paint.Color.BLACK;
import static helper.StaticContainer.*;


import java.net.URL;
import java.util.ResourceBundle;

import static model.Sound.THEME;


/**
 * Input listeners
 */

public class GameController implements Initializable {
    public Button button;
    @FXML
    private BorderPane container;
    @FXML
    private HBox topBar;
    @FXML
    private HBox bottomBar;
    @FXML
    private ToggleButton pauseBtn;
    @FXML
    private ToggleButton soundBtn;
    @FXML
    public ChoiceBox<String> languageBox;


    Language language = new Language("en","US");
    // Text fields that needs updating
    @FXML private Label nameLbBlue;
    @FXML private Label nameLbYellow;
    @FXML private Label nameLbGreen;
    @FXML private Label nameLbRed;
    @FXML public Label scoreLbBlue;
    @FXML public Label scoreLbYellow;
    @FXML public Label scoreLbGreen;
    @FXML public Label scoreLbRed;
    @FXML private TextField activityLog;    // Update notifications (kick, block etc.)



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chooseLanguage();
        updateName();

        // draw board
        Map map = new Map();
        container.setCenter(map);

        Dice dice1 = new Dice();
        Dice dice2 = new Dice();
        setDiceOnClick(dice1, dice2);
        topBar.getChildren().addAll(dice1, dice2);

        for (int i = 0; i < players.length;i++) {
            if (players[i].getConnectionStatus() == ConnectionStatus.OFF) {
                nestMap.get(i).setDisplayDisconnected();
            }
        }
        //set sound
        Sound.playSound(THEME);



        //============================[test]============================

        // test index
        System.out.println(spaceMap.size() + " " + houseMap.size());

        Circle c = new Circle(12);
        c.setFill(BLACK);

        // test move
        map.getChildren().add(c);
        double x = map.getHouseX(Map.RED_HOUSE_1 + 3);
        double y = map.getHouseY(Map.RED_HOUSE_1 + 3);
        //double x = map.getSpaceX(Map.BLUE_ARRIVAL);
        //double y = map.getSpaceY(Map.BLUE_ARRIVAL);
        c.setLayoutX(x);
        c.setLayoutY(y);

        InputHandler inputHandler = new InputHandler();
        new Thread(inputHandler).start();

    }


    @FXML
    private void setSound(ActionEvent event) {
        if (!Sound.isMute) {
            Sound.isMute = true;
            Sound.playSound(THEME);
        } else {
            Sound.isMute = false;
            Sound.playSound(THEME);
        }
    }



    private void chooseLanguage(){
        changeChoiceBoxInGame(this);
        languageBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals("Tiếng Việt")){
                    language.setLanguage("vi","VN");
                    loadLangue();
                }
                else{
                    language.setLanguage("en","US");
                    loadLangue();
                }
            }
        });
    }

    private void loadLangue(){

    }

    /**===========================[Test code goes here]===========================*/

    private void updateName(){
        nameLbBlue.setText(players[0].getName());
        nameLbYellow.setText(players[1].getName());
        nameLbGreen.setText(players[2].getName());
        nameLbRed.setText(players[3].getName());
    }
    /**===========================[End of view.test code]===========================*/
    void updatePoint(){
        scoreLbBlue.setText(players[0].getPoints()+"");
        scoreLbYellow.setText(players[1].getPoints()+"");
        scoreLbGreen.setText(players[2].getPoints()+"");
        scoreLbRed.setText(players[3].getPoints()+"");
    }

}
