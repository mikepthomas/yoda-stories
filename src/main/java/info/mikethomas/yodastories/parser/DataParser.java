package info.mikethomas.yodastories.parser;

import info.mikethomas.yodastories.character.CharactersReader;
import info.mikethomas.yodastories.character.GameCharacter;
import info.mikethomas.yodastories.exceptions.ParseException;
import info.mikethomas.yodastories.level.Level;
import info.mikethomas.yodastories.puzzle.Puzzle;
import info.mikethomas.yodastories.puzzle.PuzzlesReader;
import info.mikethomas.yodastories.sound.Sound;
import info.mikethomas.yodastories.sound.SoundsReader;
import info.mikethomas.yodastories.tile.TilesReader;
import info.mikethomas.yodastories.zone.Zone;
import info.mikethomas.yodastories.zone.ZonesReader;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import org.mapeditor.core.Properties;
import org.mapeditor.core.Tile;
import org.mapeditor.core.TileSet;
import org.mapeditor.io.TMXMapWriter;
import org.mapeditor.util.ImageHelper;

/**
 *
 * @author Mike
 */
public class DataParser {

    public static final TileSet TILE_SET = new TileSet();

    private static List<GameCharacter> characters;
    private static List<Zone> zones = new ArrayList<>();
    private static List<Puzzle> puzzles;
    private static List<Sound> sounds;

    public static void main(String[] args) throws IOException {
        String directory = new File("src/site/resources").getAbsolutePath();
        DataParser parser = new DataParser();

        for (Tile tile : TILE_SET) {
            Properties properties = tile.getProperties();
            int id = tile.getId();
            String name = properties.getProperty("Name");
            if (name != null) {
                saveImage(directory + "/items/" + name + " (" + id + ")", tile.getImage());
            }
        }
        for (Tile tile : TILE_SET) {
            tile.setSource(tile.getId() + ".png");
            saveImage(directory + "/tiles/" + tile.getId(), tile.getImage());
        }
        TILE_SET.setName("Yoda Stories");
        TILE_SET.setSource(directory + "/tiles/" + TILE_SET.getName() + ".tsx");

        TMXMapWriter writer = new TMXMapWriter();
        writer.writeTileset(TILE_SET, TILE_SET.getSource());

        for (Zone zone : zones) {
            File file = new File("src/site/apt/maps/" + zone.getId(), "index.apt");
            FileUtils.writeStringToFile(file, "[combined.png] Map Image", "UTF-8");
        }
        for (Zone zone : zones) {
            String mapDirectory = directory + "/maps";
            zone.saveImage(mapDirectory);
            String filename = mapDirectory + "/" + zone.getId() + "/tiled.tmx";
            writer.writeMap(zone.getMap(), filename);
            writer.writeMap(zone.getMap(), filename + ".gz");
        }

        Level one = new Level(7, 5);
        one.setMapAt(3, 2, zones.get(2).getMap());
    }

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
                        List<Tile> tiles = tilesReader.readObject();
                        for (Tile tile : tiles) {
                            TILE_SET.addTile(tile);
                        }
                        break;

                    case TNAM:
                        long tileNamesLength = binaryReader.readUInt32();
                        byte[] tileNamesData = binaryReader.readBytes((int) tileNamesLength);
                        BinaryReader tileNamesInput = new BinaryReader(new ByteArrayInputStream(tileNamesData));
                        while (tileNamesInput.available() != 0) {
                            int itemNumber = tileNamesInput.readUInt16();
                            String itemName = tileNamesInput.readChars(24);
                            if (itemNumber != Character.MAX_VALUE) {
                                Tile tile = TILE_SET.getTile(itemNumber);
                                tile.getProperties().setProperty("Name", itemName);
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
                        for (GameCharacter character : characters) {
                            
                        }
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
                System.err.println(ex);
            }
        }
    }

    public List<Zone> getZones() {
        return zones;
    }

    private static void saveImage(String source, BufferedImage image) throws IOException {
        File file = new File(source + ".png");
        FileUtils.writeByteArrayToFile(file, ImageHelper.imageToPNG(image));
        System.out.printf("Saved Tile %s\n", file.getAbsolutePath());
    }
}
