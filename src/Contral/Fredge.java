/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Contral;

import java.io.File;
import java.io.IOException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 *
 * @author HP
 */
public class Fredge extends Thread {
    
    public static Fredge instance;
    
    private Fredge() {
    }
    
    public static Fredge getInstance() throws IOException {
        if (instance == null) {
            instance = new Fredge();
        }
        return instance;
    }
    
    @Override
    public void run() {
        Media song = new Media(new File(getClass().getResource("/Auido/refrigerator.wav").getPath()).toURI().toString());
        MediaPlayer playerSong = new MediaPlayer(song);
        playerSong.setVolume(MAX_PRIORITY);
        playerSong.play();
        //loop the song
        playerSong.setOnEndOfMedia((new Runnable() {
            @Override
            public void run() {
                playerSong.seek(Duration.ZERO);
                playerSong.play();
            }
        }));
    }
}
