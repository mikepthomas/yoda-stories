/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zachtronics.yodastories.tile;

import com.zachtronics.yodastories.parser.BinaryReader;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import tiled.core.Tile;

/**
 *
 * @author Mike
 */
public class TilesReader extends BinaryReader {

    public static final int TILE_HEIGHT = 32;
    public static final int TILE_WIDTH = 32;

    private byte[] paletteData;

    public TilesReader(InputStream in) {
        super(in);

        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            File exeFile = new File(classLoader.getResource("Yodesk.exe").getFile());
            RandomAccessFile exeReader = new RandomAccessFile(exeFile, "r");
            paletteData = new byte[1024];
            exeReader.seek(0x550F0);
            exeReader.read(paletteData);
        } catch (IOException ex) {
            Logger.getLogger(TilesReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Tile> readObject() throws IOException {
        List tiles = new ArrayList<>();
        long tilesLength = readUInt32();
        for (int i = 0; i < tilesLength / 0x404; i++) {
            boolean[] flags = readBits(4);
            BufferedImage image = new BufferedImage(TILE_WIDTH, TILE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            for (int j = 0; j < 0x400; j++) {
                int pixelData = read() & 0xFF;
                int r = paletteData[pixelData * 4 + 2] & 0xFF;
                int g = paletteData[pixelData * 4 + 1] & 0xFF;
                int b = paletteData[pixelData * 4 + 0] & 0xFF;
                Color pixelColor = new Color(r, g, b, pixelData == 0 ? 0 : 255);
                image.setRGB(j % TILE_WIDTH, j / TILE_HEIGHT, pixelColor.getRGB());
            }
            System.out.printf("Rendered Tile %d\n", i);

            Tile tile = new Tile();
            tile.setImage(image);
   
            Properties properties = tile.getProperties();
            properties.setProperty("Collidable", String.valueOf(flags[0]));
            properties.setProperty("Weapon", String.valueOf(flags[6]));
            properties.setProperty("Item", String.valueOf(flags[7]));
            properties.setProperty("Character", String.valueOf(flags[8]));
            properties.setProperty("Door", String.valueOf(flags[16]));
   
            tiles.add(tile);
        }
        return tiles;
    }
}
