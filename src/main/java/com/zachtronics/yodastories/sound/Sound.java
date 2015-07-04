/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zachtronics.yodastories.sound;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Mike
 */
public class Sound {

    private final String filename;
    
    public Sound(String filename) {
        super();

        this.filename = filename;
    }

    public void playSound() throws IOException {
        AudioInputStream stream = null;
        try {
            InputStream soundFile = Sound.class.getResourceAsStream("/sfx/" + filename);
            stream = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format = stream.getFormat();
            Info info = new Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stream != null) stream.close();
        }
    }
}
