/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.mikethomas.yodastories.puzzle;

import info.mikethomas.yodastories.parser.BinaryReader;
import info.mikethomas.yodastories.parser.DataParser;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Mike
 */
public class PuzzleReader extends BinaryReader {

    public PuzzleReader(InputStream in) {
        super(in);
    }

    public Puzzle readObject() throws IOException {
        Puzzle puzzle = new Puzzle();
        puzzle.setType(PuzzleType.parse((int) readUInt32()));
        puzzle.setReward(RewardType.parse((int) readUInt32()));

        long unknown1 = readUInt32();
        System.out.println("unknown 1 = " + unknown1);
        int unknown287 = readUInt16();
        if (unknown1 > 0) {
            int unknown2 = readUInt16();
            System.out.println("unknown 2 = " + unknown2);
        }
        System.out.println("unknown 287 = " + unknown287);

        int startLength = readUInt16();
        puzzle.setStartText(readChars(startLength));
        System.out.println("start: " + puzzle.getStartText());

        int endLength = readUInt16();
        puzzle.setEndText(readChars(endLength));
        System.out.println("end: " + puzzle.getEndText());

        int rewardLength = readUInt16();
        puzzle.setRewardText(readChars(rewardLength));
        System.out.println("reward: " + puzzle.getRewardText());

        if (unknown1 <= 0) {
            int unknown4 = readUInt16();
        }
        int unknown5 = readUInt16();
        int itemA = readUInt16();
        int itemB = readUInt16();

        System.out.println("item A = " + itemA);
        System.out.println("item B = " + itemB);
        puzzle.setItem(DataParser.TILE_SET.getTile(itemA));
        return puzzle;
    }
}
