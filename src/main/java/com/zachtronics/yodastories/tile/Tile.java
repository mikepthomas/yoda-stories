/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zachtronics.yodastories.tile;

import java.awt.image.BufferedImage;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mike
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "tile")
public class Tile {

    private static final int FLAG_GAME_OBJECT = 0;
    private static final int FLAG_BOTTOM = 1;
    private static final int FLAG_MIDDLE = 2;
    private static final int FLAG_BLOCK = 3;
    private static final int FLAG_TOP = 4;
    private static final int FLAG_MINI_MAP = 5;
    private static final int FLAG_WEAPON = 6;
    private static final int FLAG_ITEM = 7;
    private static final int FLAG_CHARACTER = 8;
    private static final int FLAG_DOOR = 16;

    @XmlAttribute(name="id")
    private int id;
    
    @XmlAttribute(name="terrain")
    private String terrain;

    @XmlElement(name="image")
    private TileImage image;
    
    private boolean[] flags;

    public Tile() {
        super();
    }

    public Tile(int id, boolean[] flags, BufferedImage image) {
        this();
        this.id = id;
        this.flags = flags;
        this.image = new TileImage(image);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public TileImage getImage() {
        return image;
    }

    public void setImage(TileImage image) {
        this.image = image;
    }

    public boolean getFlag(int pos) {
        return flags[pos];
    }

    public boolean isGameObject() {
        return getFlag(FLAG_GAME_OBJECT);
    }

    public boolean isBottom() {
        return getFlag(FLAG_BOTTOM);
    }

    public boolean isMiddle() {
        return getFlag(FLAG_MIDDLE);
    }

    public boolean isBlock() {
        return getFlag(FLAG_BLOCK);
    }

    public boolean isTop() {
        return getFlag(FLAG_TOP);
    }

    public boolean isMiniMap() {
        return getFlag(FLAG_MINI_MAP);
    }

    public boolean isWeapon() {
        return getFlag(FLAG_WEAPON);
    }

    public boolean isItem() {
        return getFlag(FLAG_ITEM);
    }

    public boolean isCharacter() {
        return getFlag(FLAG_CHARACTER);
    }

    public boolean isDoor() {
        return getFlag(FLAG_DOOR);
    }
}
