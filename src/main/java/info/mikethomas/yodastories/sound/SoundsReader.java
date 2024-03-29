package info.mikethomas.yodastories.sound;

import info.mikethomas.yodastories.parser.BinaryReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
