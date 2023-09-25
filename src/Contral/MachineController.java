/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Contral;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class MachineController implements Initializable {

    @FXML
    private Button coffe;
    @FXML
    private Button cupotion;
    @FXML
    private Button latte;
    @FXML
    private Button hotcoco;
    @FXML
    private Button milk;
    @FXML
    private Button tea;
    @FXML
    private ImageView order;
    @FXML
    private ImageView coin;

    @FXML
    Label state;

    String statey = "Idle";
    boolean spam = true;
    Media song[] = new Media[7];
    File cup[] = new File[6];
    @FXML
    private ImageView cofeMa;
    @FXML
    private ImageView bad;
    @FXML
    private ImageView good;
    @FXML
    private MediaView boom;
    @FXML
    private ImageView gun;
    String ending = "";
    @FXML
    private Button close;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        good.setVisible(false);
        bad.setVisible(false);
        gun.setVisible(false);
        boom.setVisible(false);
        try {
            Fredge.getInstance().start();
        } catch (IOException ex) {
            Logger.getLogger(MachineController.class.getName()).log(Level.SEVERE, null, ex);
        }
        song[0] = new Media(new File(getClass().getResource("/Auido/insert.wav").getPath()).toURI().toString());
        song[1] = new Media(new File(getClass().getResource("/Auido/Machine.wav").getPath()).toURI().toString());
        song[2] = new Media(new File(getClass().getResource("/Auido/click.wav").getPath()).toURI().toString());
        song[3] = new Media(new File(getClass().getResource("/Auido/drink.wav").getPath()).toURI().toString());
        song[4] = new Media(new File(getClass().getResource("/Auido/done.wav").getPath()).toURI().toString());
        song[5] = new Media(new File(getClass().getResource("/Auido/nope.wav").getPath()).toURI().toString());
        song[6] = new Media(new File(getClass().getResource("/Video/Boom.mp4").getPath()).toURI().toString());

        cup[0] = new File("src\\Img\\Cupotion.png");
        cup[1] = new File("src\\Img\\coffee.png");
        cup[2] = new File("src\\Img\\hotcoco.png");
        cup[3] = new File("src\\Img\\latte.png");
        cup[4] = new File("src\\Img\\tea.png");
        cup[5] = new File("src\\Img\\milk.png");
    }

    private void make(int index, String latter) {

        if (statey.equals("Select") && spam) {
            spam = false;
            MediaPlayer click = new MediaPlayer(song[2]);
            click.play();
            MediaPlayer makingCoffe = new MediaPlayer(song[1]);
            state.setText("Preparing...");
            makingCoffe.setOnEndOfMedia(() -> {
                statey = "Wating";
                File file = cup[index];
                MediaPlayer done = new MediaPlayer(song[4]);
                done.setVolume(0.3);
                done.play();
                state.setText("Done!");
                state.setStyle("-fx-text-fill:green; -fx-background-color:black;");
                Image image = new Image(file.toURI().toString());
                order.setImage(image);
                spam = true;
            });
            makingCoffe.play();
        } else if (spam && !statey.equals("Wating")) {
            MediaPlayer nope = new MediaPlayer(song[5]);
            nope.play();
            state.setText("Enter a coin");
            nope.setOnEndOfMedia(() -> {
                state.setText("");
                ending += latter;

                if (ending.length() > 20) {
                    spam = false;
                    gun.setVisible(true);
                    MediaPlayer booom = new MediaPlayer(song[6]);
                    boom.setMediaPlayer(booom);
                    Timeline timeline = new Timeline();
                    KeyFrame keyFrame = new KeyFrame(Duration.seconds(3), e -> {
                        boom.setVisible(true);
                        booom.play();
                        booom.setOnEndOfMedia(() -> {
                            bad.setVisible(true);
                        });
                    });
                    timeline.getKeyFrames().add(keyFrame);
                    timeline.play();

                }

                if (ending.equals("coemnf")) {
                    cofeMa.setVisible(false);
                    state.setVisible(false);

                    Timeline timeline = new Timeline();
                    KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), e -> {

                        //Platform.runLater(() -> {   //more later stuff nice :)
                        good.setVisible(true);//my command
                        //});
                    });
                    timeline.getKeyFrames().add(keyFrame);
                    timeline.play();

                }

            });
        }

    }

    @FXML
    private void makeCoffe(ActionEvent event) {
        make(1, "c");
    }

    @FXML
    private void makeCupotion(ActionEvent event) {
        make(0, "o");
    }

    @FXML
    private void makeLatte(ActionEvent event) {
        make(3, "f");
    }

    @FXML
    private void makeHotcoco(ActionEvent event) {
        make(2, "e");
    }

    @FXML
    private void makeMilk(ActionEvent event) {
        make(5, "m");
    }

    @FXML
    private void makeTea(ActionEvent event) {
        make(4, "n");
    }

    @FXML
    private void takeOrder(MouseEvent event) {
        if (statey.equals("Wating") && spam) {
            spam = false;
            order.setImage(null);
            MediaPlayer drink = new MediaPlayer(song[3]);
            drink.setOnEndOfMedia(() -> {
                statey = "Idle";
                state.setText("");
                state.setStyle("-fx-text-fill:red; -fx-background-color:black;");
                spam = true;

            });

            drink.play();
        }
    }

    @FXML
    private void addCoin(MouseEvent event) {
        if (statey.equals("Idle") && spam) {
            spam = false;
            ending = "";
            File file = new File("src\\Img\\coin.png");
            Image image = new Image(file.toURI().toString());
            coin.setImage(image);

            MediaPlayer addcoin = new MediaPlayer(song[0]);
            addcoin.setOnEndOfMedia(() -> {
                coin.setImage(null);
                statey = "Select";
                state.setText("Select Item");
                spam = true;
            });
            addcoin.play();

        }

    }

    @FXML
    private void closeApp(ActionEvent event) {
        Platform.exit();
    }

}
