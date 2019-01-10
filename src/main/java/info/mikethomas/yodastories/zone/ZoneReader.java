/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.mikethomas.yodastories.zone;

import info.mikethomas.yodastories.parser.BinaryReader;
import info.mikethomas.yodastories.zone.action.ActionReader;
import info.mikethomas.yodastories.zone.map.TileMapReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Mike
 */
public class ZoneReader extends BinaryReader {

    public ZoneReader(InputStream in) {
        super(in);
    }

    public Zone readObject() throws IOException {
        Zone zone = new Zone();
        zone.setId(readUInt16());

        // IZON Section
        String izon = readChars(4);
        long izonUnknown = readUInt32();

        int width = readUInt16();
        int height = readUInt16();
        zone.setWidth(width);
        zone.setHeight(height);

        boolean[] mapFlags = readBits(1);
        byte[] unused = readBytes(5);
        int terrain = readUInt16();
        byte[] mapData = readBytes(width*height*6);

        InputStream mapInput = new ByteArrayInputStream(mapData);
        TileMapReader tileMapReader = new TileMapReader(mapInput);
        zone.setMap(tileMapReader.readObject(width, height));

        // Object Data
        System.out.println("===== Map " + zone.getId() + " object data" + " =====");
        int objectInfoCount = readUInt16();
        byte[] objectInfoData = readBytes(objectInfoCount*12);
        BinaryReader objectInfo = new BinaryReader(new ByteArrayInputStream(objectInfoData));
        for (int o = 0; o < objectInfoCount; o++) {
            int type = objectInfo.readUInt16();
            int na = objectInfo.readUInt16();
            System.out.println("na=" + na);
            int x = objectInfo.readUInt16();
            System.out.println("x=" + x);
            int y = objectInfo.readUInt16();
            System.out.println("y=" + y);
            int na2 = objectInfo.readUInt16();
            System.out.println("na2=" + na2);
            int arg = objectInfo.readUInt16();
            switch (type) {
                case 0:
                    System.out.println("trigger location");
                    break;
                case 1:
                    System.out.println("spawn location");
                    break;
                case 2:
                    System.out.println("force-related location");
                    break;
                case 3:
                    System.out.println("vehicle to secondary map " + arg);
                    break;
                case 4:
                    System.out.println("vehicle to primary map " + arg);
                    break;
                case 5:
                    System.out.println("object that gives you the locator");
                    break;
                case 6:
                    System.out.println("crate item " + arg);
                    // render item in crate
                    //zone.getMap().getTopTiles()[x][y] = TileManager.get(arg);
                    break;
                case 7:
                    System.out.println("puzzle NPC " + arg);
                    //zone.getMap().getTopTiles()[x][y] = TileManager.get(arg);
                    break;
                case 8:
                    System.out.println("crate weapon " + arg);
                    // render weapon in crate
                    //zone.getMap().getTopTiles()[x][y] = TileManager.get(arg);
                    break;
                case 9:
                    System.out.println("door (in) " + arg);
                    // open doors
                    break;
                case 10:
                    System.out.println("door (out)");
                    break;
                case 11:
                    System.out.println("unused");
                    break;
                case 12:
                    System.out.println("lock");
                    break;
                case 13:
                    System.out.println("teleporter");
                    break;
                case 14:
                    System.out.println("x-wing from dagobah " + arg);
                    break;
                case 15:
                    System.out.println("x-wing to dagobah " + arg);
                    break;
            }
        }
        // IZAX Section
        String izax = readChars(4);
        int izaxLength = readUInt16();
        byte[] izaxUnknown = readBytes(izaxLength - 6);
        // IZX2 Section
        String izx2 = readChars(4);
        int izx2Length = readUInt16();
        byte[] izx2Unknown = readBytes(izx2Length - 6);
        // IZX3 Section
        String izx3 = readChars(4);
        int izx3Length = readUInt16();
        byte[] izx3Unknown = readBytes(izx3Length - 6);
        // IZX4 Section
        String izx4 = readChars(4);
        byte[] izx4Unknown = readBytes(6);
        // IACT Sections
        int numActions = readUInt16();
        for (int i = 0; i < numActions; i++) {
            System.out.println("* Action " + i);
            String iact = readChars(4);
            long actionLength = readUInt32();
            byte[] actionData = readBytes((int) actionLength);
            InputStream actionInput = new ByteArrayInputStream(actionData);
            ActionReader actionReader = new ActionReader(actionInput);
            actionReader.readObject();
        }
        return zone;
    }
}
