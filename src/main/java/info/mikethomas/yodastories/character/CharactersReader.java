/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.mikethomas.yodastories.character;

import info.mikethomas.yodastories.parser.BinaryReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mike
 */
public class CharactersReader extends BinaryReader {

    public CharactersReader(InputStream in) {
        super(in);
    }

    public List<GameCharacter> readObject() throws IOException {
        List characters = new ArrayList<>();
        while (available() != 0) {
            int charId = readUInt16();
            String icha = readChars(4);
            long ichaLength = readUInt32();
            byte[] characterData = readBytes((int) ichaLength);
            InputStream characterInput = new ByteArrayInputStream(characterData);
            CharacterReader characterReader = new CharacterReader(characterInput);
            characters.add(characterReader.readObject());
        }
        return characters;
    }
}
