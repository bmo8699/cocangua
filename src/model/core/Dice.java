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
package model.core;

import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Sound;

import java.util.Random;


public class Dice extends ImageView {
    Image diceFace = new Image("images/dice1.png");
    int val;

    /**
     * Create GUI layout for Dice and setEventHandlers for each
     */
    public Dice() {
        setFitWidth(85);
        setFitHeight(85);
        setImage(diceFace);

        // Slight "jump" effect when hovered
        this.setOnMouseEntered(hover -> {
            this.setTranslateY(-5);
        });

        this.setOnMouseExited(endHover -> {
            this.setTranslateY(0);
        });

    }

    /**
     * Roll a dice, handle GUI display animation
     * @return rolled number
     */
    public int roll() {
        Random rand = new Random();
        int num = rand.nextInt(6) + 1;

        // Play roll animation
        RotateTransition rt = new RotateTransition(Duration.millis(100), this);
        rt.setByAngle(360);
        rt.setAutoReverse(true);
        rt.setCycleCount(3);
        rt.setAxis(Rotate.Y_AXIS);
        rt.play();  // Play roll animation
        Sound.playSound(Sound.ROLL);    // Play roll sound

        // Set new diceFace image after roll
        rt.setOnFinished(finishRoll -> {
            switch (num) {
                case 1: this.setImage(new Image("images/dice1.png")); break;
                case 2: this.setImage(new Image("images/dice2.png")); break;
                case 3: this.setImage(new Image("images/dice3.png")); break;
                case 4: this.setImage(new Image("images/dice4.png")); break;
                case 5: this.setImage(new Image("images/dice5.png")); break;
                case 6: this.setImage(new Image("images/dice6.png")); break;
            }
        });
        return num;
    }

    public int getVal(){
        return val;
    }

}
