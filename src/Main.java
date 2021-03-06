/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2020A
  Assessment: Final Project
  Created date: 20/12/2019

  By:
  Phan Quoc Binh (3715271)
  Tran Mach So Han (3750789)
  Tran Kim Bao (3740819)
  Nguyen Huu Duy (3703336)
  Nguyen Minh Trang (3751450)

  Last modified: 14/1/2019

  By:
  Nguyen Huu Duy (3703336)

  Acknowledgement: see readme.md
*/


import controller.MenuController;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import helper.StaticContainer;
import model.Score;
import model.Sound;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static helper.StaticContainer.*;
import static helper.LayoutContainer.*;

public class Main extends Application implements Initializable{

    public Label endGameLb;
    public Button newGameBtn;
    public Button quitBtn;
    public Text winnerText;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        FXMLLoader menu = new FXMLLoader(getClass().getResource("view/menu.fxml"));
        FXMLLoader game = new FXMLLoader(getClass().getResource("view/game.fxml"));

        Parent menuDisplay = menu.load(); // load main menu

        MenuController menuController = menu.getController();

        //TODO: go to main game, if no players is null then the game plays itself
        menuController.startBtn.setOnAction(actionEvent -> {
            // Pre-process the static array of Player
            if (numberOfPlayer > 1){
                for (int i = 0 ; i < players.length;i++){
                    if (players[i].isClickedOn()){
                        players[i].setConnectionStatus(players[i].getToggler().isSelected() ? StaticContainer.ConnectionStatus.BOT : StaticContainer.ConnectionStatus.PLAYER);
                        players[i].setName(players[i].getTextField().getText());
                    }
                    else {
                        players[i].setName("XXX");
                    }
                }
                try {
                    score = new Score();
                    score.getPoint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Load main game
                Parent gameDisplay = null;
                try {
                    gameDisplay = game.load();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(gameDisplay, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
                scene.getStylesheets().add(getClass().getResource("cocangua.css").toExternalForm());
                primaryStage.setScene(scene);
            }
        });

        gameStop();


        primaryStage.setTitle("Co Ca Ngua");
        Scene scene = new Scene(menuDisplay, 1200 , 900);
        scene.getStylesheets().add(getClass().getResource("cocangua.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * once all the piece of nest reach 6-5-4-3
     */
    private void gameStop(){
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (int i = 0; i < players.length ; i++){
                    if (players[i].isGetToHouse()){
                        try {
                            Sound.playSound(Sound.WIN);
                            Sound.isMute = true;
                            Sound.playSound(Sound.THEME);
                            displayMessage();
                            turn = 2;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        timer.stop();
                    }
                }
            }
        };
        timer.start();
    }

    /**
     * load the end UI
     * @throws IOException
     */
    private void displayMessage() throws IOException {
        alertBox = new Stage();
        alertBox.initModality(Modality.APPLICATION_MODAL);
        alertBox.setMinWidth(250);

        FXMLLoader end = new FXMLLoader(getClass().getResource("view/end.fxml"));
        Parent root = end.load();
        root.getStylesheets().add(getClass().getResource("cocangua.css").toExternalForm());
        alertBox.setScene(new Scene(root));
        alertBox.setTitle(language.getWinScreenTitle());
        alertBox.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        newGameBtn.setText(language.getNewGame());
//        quitBtn.setText(language.getQuit());
        endGameLb.setText(language.getEndGameLabel());

        String max_person = players[0].getName();
        int max_score = players[0].getPoints();
        for (int i = 0; i < players.length ; i++){
            if (players[i].getPoints() > max_score){
                max_person = players[i].getName();
                max_score = players[i].getPoints();
            }
        }
        winnerText.setText(max_person + " " + language.getResultStatement() + " " + max_score);
    }

    /**
     * restart new game
     * @throws Exception
     */
    @FXML
    public void loadNewGame() throws Exception {
        alertBox.close();
        score = new Score();
        score.savePoint();
        start(window);      //call the start in main
    }

    /**
     * quit game
     */
    @FXML
    public void quitGame(){
        System.exit(0);
    }


    public static void main(String[] args) {
        launch(args);
    }


}
