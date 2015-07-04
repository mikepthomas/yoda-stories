/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zachtronics.yodastories.tile;

import com.zachtronics.yodastories.tileset.TileSet;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author Mike
 */
public class TileManager {

    private static final TileSet tileSet = new TileSet();

    public static List<Tile> getTiles() {
        return tileSet.getTiles();
    }

    public static void setTiles(List<Tile> tiles) {
        TileManager.tileSet.setTiles(tiles);;
    }

    public static Tile get(int index) {
        return tileSet.getTiles().get(index);
    }

    public static void saveTileSet(String location) {
        try {
            JAXBContext context = JAXBContext.newInstance(TileSet.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(tileSet, new File(location));
        } catch (JAXBException ex) {
            Logger.getLogger(TileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
