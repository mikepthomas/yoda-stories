package info.mikethomas.yodastories.character;

import info.mikethomas.yodastories.parser.BinaryReader;

import java.io.IOException;
import java.io.InputStream;

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
            System.out.println(i + " up shoot/character = " + upTile1);
            int downTile1 = readUInt16();
            System.out.println(i + " down shoot/character = " + downTile1);
            int upTile2 = readUInt16();
            System.out.println(i + " up lightsabre = " + upTile2);
            int leftTile = readUInt16();
            System.out.println(i + " left shoot/character  = " + leftTile);
            int downTile2 = readUInt16();
            System.out.println(i + " down lightsabre = " + downTile2);
            int upTile3 = readUInt16();
            System.out.println(i + " up lightsabre = " + upTile3);
            int rightTile = readUInt16();
            System.out.println(i + " right shoot/lightsabre = " + rightTile);
            int downTile3 = readUInt16();
            System.out.println(i + " down weapon/character = " + downTile3);
        }
        // Character
        // 1 = Stationary
        // 2 = Walk 1
        // 3 = Walk 2
        // if 2 or 3 = 65535, no animation for tile
        
        // Weapon
        // 1 = Bullet / Weapon
        // 2/3 = Character holding weapon
        return character;
    }
}
