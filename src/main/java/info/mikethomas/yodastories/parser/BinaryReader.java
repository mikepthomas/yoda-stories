package info.mikethomas.yodastories.parser;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BinaryReader extends FilterInputStream {

    public BinaryReader(InputStream in) {
        super(in);
    }

    public byte[] readBytes(int size) throws IOException {
        byte[] buf = new byte[size];
        read(buf, 0, buf.length);
        return buf;
    }

    public String readChars(int size) throws IOException {
        byte buf[] = readBytes(size);
        int i;
        for (i = 0; i < buf.length && buf[i] != 0; i++) { }
        return new String(buf, 0, i, "UTF-8");
    }

    public long readUInt32() throws IOException {
        return convertUInt32(readBytes(Integer.BYTES));
    }

    public int readUInt16() throws IOException {
        return convertUInt16(readBytes(Short.BYTES));
    }

    private long convertUInt32(byte[] bytes) {
        return ((bytes[3] & 0xFF) << 24)
             | ((bytes[2] & 0xFF) << 16)
             | convertUInt16(bytes);
    }

    private int convertUInt16(byte[] bytes) {
        return ((bytes[1] & 0xFF) << 8)
             | ((bytes[0] & 0xFF));
    }

    public boolean[] readBits(int size) throws IOException {
        byte[] bytes = readBytes(size);
        int numBits = bytes.length * 8;
        boolean[] bits = new boolean[numBits];
        for (int i = 0; i < numBits; i++) {
            bits[i] = (bytes[i / 8] >> i % 8 & 1) == 1;
        }
        return bits;
    }
}
