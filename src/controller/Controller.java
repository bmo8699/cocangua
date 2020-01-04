package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import model.*;
import view.PieceView;

import java.net.URL;
import java.util.ResourceBundle;

import static helper.Helper.*;

/**
 * Input listeners
 */

public class Controller implements Initializable {
    public Label faceDice;
    @FXML
    private BorderPane container;
    @FXML
    private Button rollDiceBtn;
    Player player = new Player("Hello");
    private int moveAmount;
    Map map;

    int NUM_OF_PLAYER = 4;
    Player[] players = new Player[NUM_OF_PLAYER];

    /**
     * Set color
     */
    final private static Color[] PIECE_COLOR = {Color.BEIGE, Color.BLUEVIOLET, Color.RED, Color.KHAKI};
    //Piece color

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        map = new Map();
        // draw board
        container.setCenter(map);

        createPlayers(NUM_OF_PLAYER);

        /**Test code goes here*/

//        for (int i = 0; i < players.length; i++) {
//            System.out.println(players[i].getName());
//        }

        //Move the piece with move amount 3



        //Run through nest color and create piece
        var ref = new Object() {
            int check = 0;
        };
        for (int i = 0; i < Map.REGION_COLOR.length; i++){
            int finalI = i;
            Piece piece = new Piece(i,-1);
            PieceView p = new PieceView(PIECE_COLOR[i]);
            p.startPosition(map, i);
            p.setOnMouseClicked(event -> {
                if (ref.check == 4){
                    ref.check = 0;
                }
                if(player.isRolled()) {
                    if (ref.check == finalI) {
                        ref.check++;
                        if (piece.getCurrentPosition() != -1 || moveAmount == 6) {
                            player.resetCheck();
                            //First set the piece block is false, should be deleted
                            piece.setBlocked(false);
                            int nextPosition = p.movePiece(piece.getCurrentPosition(), moveAmount, map, Map.REGION_COLOR[finalI], piece.isHome(), piece.isBlocked());
                            piece.setCurrentPosition(nextPosition);
                            if (piece.isHome()) {
                                piece.setHome(false);
                            }
                        }
                    }
                }
            });
            map.getChildren().add(p);
        }

        /***/


    }



    /**
     * Input listener for dice rolling button
     * @param mouseEvent
     * @return void;
     */
    @FXML
    private void rollDice(MouseEvent mouseEvent) {
        //Roll dice here, wilasdasd

        Dice dice = new Dice();
        rollDiceBtn.setOnMouseClicked(event -> {
            player.roll();
            moveAmount = player.getDices()[0].getFace();
            faceDice.setText("" + moveAmount);
        });
    }
}
