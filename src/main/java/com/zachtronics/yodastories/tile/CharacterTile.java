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
public class CharacterTile extends Tile {

    private static final int FLAG_PLAYER = 16;
    private static final int FLAG_ENEMY = 17;
    private static final int FLAG_FRIENDLY = 18;

    public CharacterTile(int id, boolean[] flags, BufferedImage image) {
        super(id, flags, image);
    }

    public boolean isEnemy() {
        return getFlag(FLAG_ENEMY);
    }

    public boolean isFriendly() {
        return getFlag(FLAG_FRIENDLY);
    }

    public boolean isPlayer() {
        return getFlag(FLAG_PLAYER);
    }
}
