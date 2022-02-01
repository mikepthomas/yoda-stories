package info.mikethomas.yodastories.puzzle;

import info.mikethomas.yodastories.parser.BinaryReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PuzzlesReader extends BinaryReader {

    public PuzzlesReader(InputStream in) {
        super(in);
    }

    public List<Puzzle> readObject() throws IOException {
        List puzzles = new ArrayList<>();
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
