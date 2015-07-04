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
public class ItemTile extends Tile {

    private static final int FLAG_KEYCARD = 16;
    private static final int FLAG_TOOL = 17;
    private static final int FLAG_PUZZLE_ITEM_RARE = 18;
    private static final int FLAG_PUZZLE_ITEM = 19;
    private static final int FLAG_LOCATOR = 20;
    private static final int FLAG_HEALTH_PACK = 22;

    public ItemTile(int id, boolean[] flags, BufferedImage image) {
        super(id, flags, image);
    }

    public boolean isHealthPack() {
        return getFlag(FLAG_HEALTH_PACK);
    }

    public boolean isKeyCard() {
        return getFlag(FLAG_KEYCARD);
    }

    public boolean isTool() {
        return getFlag(FLAG_TOOL);
    }

    public boolean isPuzzleItem() {
        return getFlag(FLAG_PUZZLE_ITEM);
    }

    public boolean isPuzzleItemRare() {
        return getFlag(FLAG_PUZZLE_ITEM_RARE);
    }

    public boolean isLocator() {
        return getFlag(FLAG_LOCATOR);
    }
}
