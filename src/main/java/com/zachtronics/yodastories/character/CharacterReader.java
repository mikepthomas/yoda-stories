/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zachtronics.yodastories.character;

import com.zachtronics.yodastories.parser.BinaryReader;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Mike
 */
public class CharacterReader extends BinaryReader {

    public CharacterReader(InputStream in) {
        super(in);
    }

    public GameCharacter readObject() throws IOException {
        GameCharacter character = new GameCharacter();
        character.setName(readChars(18));
        System.out.println(character.getName());
        character.setType(CharacterType.parse(readUInt16()));
        int unknownMax = readUInt16();
        long unknownZero = readUInt32();
        for (int i = 1; i <= 3; i++) {
            int upTile1 = readUInt16();
            System.out.println(i + " up = " + upTile1);
            int downTile1 = readUInt16();
            System.out.println(i + " down = " + downTile1);
            int upTile2 = readUInt16();
            System.out.println(i + " up = " + upTile2);
            int leftTile = readUInt16();
            System.out.println(i + " left = " + leftTile);
            int downTile2 = readUInt16();
            System.out.println(i + " down = " + downTile2);
            int upTile3 = readUInt16();
            System.out.println(i + " up = " + upTile3);
            int rightTile = readUInt16();
            System.out.println(i + " right = " + rightTile);
            int downTile3 = readUInt16();
            System.out.println(i + " down = " + downTile3);
        }
        return character;
    }
}
