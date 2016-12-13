/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zachtronics.yodastories.sound;

import com.zachtronics.yodastories.parser.BinaryReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mike
 */
public class SoundsReader extends BinaryReader {

    public SoundsReader(InputStream in) {
        super(in);
    }

    public List<Sound> readObject() throws IOException {
        List<Sound> sounds = new ArrayList<>();
        int soundUnknown = readUInt16();
        while (available() != 0) {
            int filenameLength = readUInt16();
            String filename = readChars(filenameLength);
            sounds.add(new Sound(filename));
        }
        return sounds;
    }
}
