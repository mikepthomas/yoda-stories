package info.mikethomas.yodastories.zone.map;

import info.mikethomas.yodastories.parser.BinaryReader;
import info.mikethomas.yodastories.parser.DataParser;

import java.io.IOException;
import java.io.InputStream;

import org.mapeditor.core.Map;
import org.mapeditor.core.Tile;
import org.mapeditor.core.TileLayer;

public class TileMapReader extends BinaryReader {

    public TileMapReader(InputStream in) {
        super(in);
    }

    public Map readObject(int width, int height) throws IOException {
        Map map = new Map(width, height);
        map.addTileset(DataParser.TILE_SET);

        TileLayer bottomLayer = new TileLayer(map, width, height);
        bottomLayer.setName("Bottom");
        map.addLayer(bottomLayer);

        TileLayer middleLayer = new TileLayer(map, width, height);
        middleLayer.setName("Middle");
        map.addLayer(middleLayer);

        TileLayer topLayer = new TileLayer(map, width, height);
        topLayer.setName("Top");
        map.addLayer(topLayer);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int bottomTile = readUInt16();
                if (bottomTile != 0xFFFF) {
                    Tile tile = DataParser.TILE_SET.getTile(bottomTile);
                    bottomLayer.setTileAt(x, y, tile);
                }
                int middleTile = readUInt16();
                if (middleTile != 0xFFFF) {
                    Tile tile = DataParser.TILE_SET.getTile(middleTile);
                    middleLayer.setTileAt(x, y, tile);
                }
                int topTile = readUInt16();
                if (topTile != 0xFFFF) {
                    Tile tile = DataParser.TILE_SET.getTile(topTile);
                    topLayer.setTileAt(x, y, tile);
                }
            }
        }

        return map;
    }
}
