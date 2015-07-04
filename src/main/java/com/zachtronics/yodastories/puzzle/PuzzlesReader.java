/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zachtronics.yodastories.puzzle;

import com.zachtronics.yodastories.parser.BinaryReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mike
 */
public class PuzzlesReader extends BinaryReader {

    public PuzzlesReader(InputStream in) {
        super(in);
    }

    public List<Puzzle> readObject() throws IOException {
        List puzzles = new ArrayList<Puzzle>();
        while (available() != 0) {
            int puzzleNumber = readUInt16();
            if (puzzleNumber != Character.MAX_VALUE) {
                System.out.println("===== Puzzle " + puzzleNumber + " =====");
                String ipuz = readChars(4);
                long ipuzLength = readUInt32();
                byte[] ipuzData = readBytes((int) ipuzLength);
                InputStream ipuzInput = new ByteArrayInputStream(ipuzData);
                PuzzleReader puzzleReader = new PuzzleReader(ipuzInput);
                puzzles.add(puzzleReader.readObject());
            }
        }
        return puzzles;
    }
}
