/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zachtronics.yodastories.tileset;

import com.zachtronics.yodastories.tile.Tile;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mike
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "tileset")
public class TileSet {

    public TileSet() {
        super();
        
        name = "Yoda Stories";
        tileWidth = 32;
        tileHeight = 32;
    }

    @XmlAttribute(name="name")
    private String name;

    @XmlAttribute(name="tilewidth")
    private int tileWidth;

    @XmlAttribute(name="tileheight")
    private int tileHeight;

    @XmlElement(name="terraintypes")
    private TerrainTypes terrainTypes;

    @XmlElement(name="tile")
    private List<Tile> tiles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    public TerrainTypes getTerrainTypes() {
        return terrainTypes;
    }

    public void setTerrainTypes(TerrainTypes terrainTypes) {
        this.terrainTypes = terrainTypes;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }
}
