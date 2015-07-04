/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zachtronics.yodastories.tile;

import java.awt.image.BufferedImage;

/**
 *
 * @author Mike
 */
public class MiniMapTile extends Tile {

    private static final int FLAG_WALL_LOCKED_UP = 22;
    private static final int FLAG_WALL_UNLOCKED_UP = 26;
    
    public MiniMapTile(int id, boolean[] flags, BufferedImage image) {
        super(id, flags, image);
    }

    public boolean isWallLockedUp() {
        return getFlag(FLAG_WALL_LOCKED_UP);
    }

    public boolean isWallUnlockedUp() {
        return getFlag(FLAG_WALL_UNLOCKED_UP);
    }
}
