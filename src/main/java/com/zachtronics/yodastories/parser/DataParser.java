package com.zachtronics.yodastories.parser;

import com.zachtronics.yodastories.character.CharactersReader;
import com.zachtronics.yodastories.character.GameCharacter;
import com.zachtronics.yodastories.exceptions.ParseException;
import com.zachtronics.yodastories.puzzle.Puzzle;
import com.zachtronics.yodastories.puzzle.PuzzlesReader;
import com.zachtronics.yodastories.sound.Sound;
import com.zachtronics.yodastories.sound.SoundsReader;
import com.zachtronics.yodastories.tile.Tile;
import com.zachtronics.yodastories.tile.TileManager;
import com.zachtronics.yodastories.tile.TilesReader;
import com.zachtronics.yodastories.zone.Zone;
import com.zachtronics.yodastories.zone.ZonesReader;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Mike
 */
public class DataParser {

    public static void main(String[] args) throws IOException {
        DataParser parser = new DataParser();

        for (Map.Entry<Integer, String> entry : tileNames.entrySet()) {
            int id = entry.getKey();
            String name = entry.getValue();
            Tile tile = TileManager.get(id);
            tile.getImage().saveImage("src/site/resources/items/" + name + " (" + id + ")");
        }
        for (Tile tile : TileManager.getTiles()) {
            tile.getImage().saveImage("src/site/resources/tiles/" + tile.getId());
        }
        TileManager.saveTileSet("/Volumes/Documents/Yoda Stories.tsx");

        for (Zone zone : zones) {
            File file = new File("src/site/apt/maps/" + zone.getId(), "index.apt");
            FileUtils.writeStringToFile(file, "[combined.png] Map Image");
        }
        for (Zone zone : zones) {
            zone.saveImage("src/site/resources/maps");
        }
    }

    private static List<GameCharacter> characters;
    private static List<Zone> zones = new ArrayList<Zone>();
    private static List<Puzzle> puzzles;
    private static List<Sound> sounds;
    private static Map<Integer, String> tileNames = new HashMap<Integer, String>();

    public DataParser() {

        ClassLoader classLoader = DataParser.class.getClassLoader();
        InputStream dataFile = classLoader.getResourceAsStream("yodesk.dta");
        BinaryReader binaryReader = new BinaryReader(new BufferedInputStream(dataFile));

        boolean keepReading = true;
        while (keepReading) {
            try {
                Section section = Section.valueOf(binaryReader.readChars(4));

                switch (section) {
                    case VERS:
                        long version = binaryReader.readUInt32();
                        break;

                    case STUP:
                    case CHWP:
                    case CAUX:
                        long sectionLength = binaryReader.readUInt32();
                        byte[] sectionData = binaryReader.readBytes((int) sectionLength);
                        break;

                    case SNDS:
                        long soundsLength = binaryReader.readUInt32();
                        byte[] soundsData = binaryReader.readBytes((int) soundsLength);
                        InputStream soundsInput = new ByteArrayInputStream(soundsData);
                        SoundsReader soundsReader = new SoundsReader(soundsInput);
                        sounds = soundsReader.readObject();
                        break;

                    case PUZ2:
                        long puz2Length = binaryReader.readUInt32();
                        byte[] puz2Data = binaryReader.readBytes((int) puz2Length);
                        InputStream puz2Input = new ByteArrayInputStream(puz2Data);
                        PuzzlesReader puzzlesReader = new PuzzlesReader(puz2Input);
                        puzzles = puzzlesReader.readObject();
                        break;

                    case TILE:
                        TilesReader tilesReader = new TilesReader(binaryReader);
                        TileManager.setTiles(tilesReader.readObject());
                        break;

                    case TNAM:
                        long tileNamesLength = binaryReader.readUInt32();
                        byte[] tileNamesData = binaryReader.readBytes((int) tileNamesLength);
                        BinaryReader tileNamesInput = new BinaryReader(new ByteArrayInputStream(tileNamesData));
                        while (tileNamesInput.available() != 0) {
                            int itemNumber = tileNamesInput.readUInt16();
                            String itemName = tileNamesInput.readChars(24);
                            if (itemNumber != Character.MAX_VALUE) {
                                tileNames.put(itemNumber, itemName);
                            }
                        }
                        System.out.println("");
                        break;

                    case CHAR:
                        long charactersLength = binaryReader.readUInt32();
                        byte[] charactersData = binaryReader.readBytes((int) charactersLength);
                        InputStream charactersInput = new ByteArrayInputStream(charactersData);
                        CharactersReader charactersReader = new CharactersReader(charactersInput);
                        characters = charactersReader.readObject();
                        break;

                    case ZONE:
                        ZonesReader zonesReader = new ZonesReader(binaryReader);
                        zones = zonesReader.readObject();
                        break;

                    case ENDF:
                        keepReading = false;
                        break;

                    default:
                        throw new ParseException("Unknown section: " + section);
                }
            } catch (IOException ex) {
                Logger.getLogger(DataParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List<Zone> getZones() {
        return zones;
    }
}
