/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zachtronics.yodastories.zone.map;

import com.zachtronics.yodastories.tile.Tile;

/**
 *
 * @author Mike
 */
public class TileMap {

    private Tile[][] topTiles, middleTiles, bottomTiles;

    public Tile[][] getTopTiles() {
        return topTiles;
    }

    public void setTopTiles(Tile[][] topTiles) {
        this.topTiles = topTiles;
    }

    public Tile[][] getMiddleTiles() {
        return middleTiles;
    }

    public void setMiddleTiles(Tile[][] middleTiles) {
        this.middleTiles = middleTiles;
    }

    public Tile[][] getBottomTiles() {
        return bottomTiles;
    }

    public void setBottomTiles(Tile[][] bottomTiles) {
        this.bottomTiles = bottomTiles;
    }
}
