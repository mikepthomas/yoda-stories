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
public class WeaponTile extends Tile {

    private static final int FLAG_BLASTER_LIGHT = 16;
    private static final int FLAG_BLASTER_HEAVY = 17;
    private static final int FLAG_LIGHTSABER = 18;
    private static final int FLAG_FORCE = 19;

    public WeaponTile(int id, boolean[] flags, BufferedImage image) {
        super(id, flags, image);
    }

    public boolean isBlasterLight() {
        return getFlag(FLAG_BLASTER_LIGHT);
    }

    public boolean isBlasterHeavy() {
        return getFlag(FLAG_BLASTER_HEAVY);
    }

    public boolean isForce() {
        return getFlag(FLAG_FORCE);
    }

    public boolean isLightsaber() {
        return getFlag(FLAG_LIGHTSABER);
    }
}
