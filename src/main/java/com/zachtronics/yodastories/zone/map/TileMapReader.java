/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zachtronics.yodastories.zone.map;

import com.zachtronics.yodastories.parser.BinaryReader;
import com.zachtronics.yodastories.tile.Tile;
import com.zachtronics.yodastories.tile.TileManager;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Mike
 */
public class TileMapReader extends BinaryReader {

    public TileMapReader(InputStream in) {
        super(in);
    }

    public TileMap readObject(int width, int height) throws IOException {
        Tile[][] bottomTiles = new Tile[width][height];
        Tile[][] middleTiles = new Tile[width][height];
        Tile[][] topTiles = new Tile[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int bottomTile = readUInt16();
                if (bottomTile != 0xFFFF) {
                    bottomTiles[x][y] = TileManager.get(bottomTile);
                }
                int middleTile = readUInt16();
                if (middleTile != 0xFFFF) {
                    middleTiles[x][y] = TileManager.get(middleTile);
                }
                int topTile = readUInt16();
                if (topTile != 0xFFFF) {
                    topTiles[x][y] = TileManager.get(topTile);
                }
            }
        }

        TileMap map = new TileMap();
        map.setTopTiles(topTiles);
        map.setMiddleTiles(middleTiles);
        map.setBottomTiles(bottomTiles);

        return map;
    }
}
