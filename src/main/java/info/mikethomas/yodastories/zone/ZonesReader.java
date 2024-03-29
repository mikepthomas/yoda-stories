package info.mikethomas.yodastories.zone;

import info.mikethomas.yodastories.parser.BinaryReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ZonesReader extends BinaryReader {

    public ZonesReader(InputStream in) {
        super(in);
    }

    public List<Zone> readObject() throws IOException {
        List<Zone> zones = new ArrayList<>();
        int count = readUInt16();
        for (int i = 0; i < count; i++) {
            int zoneUnknown = readUInt16();
            long zoneLength = readUInt32();
            byte[] zoneData = readBytes((int) zoneLength);

            InputStream zoneInput = new ByteArrayInputStream(zoneData);
            ZoneReader zoneReader = new ZoneReader(zoneInput);
            zones.add(zoneReader.readObject());
        }
        return zones;
    }
}
