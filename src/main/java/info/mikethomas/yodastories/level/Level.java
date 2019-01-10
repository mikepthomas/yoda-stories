/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.mikethomas.yodastories.level;

import org.mapeditor.core.Map;

/**
 *
 * @author Mike
 */
public class Level {
    private final Map[][] level;

    public Level(int width, int height) {
        level = new Map[width][height];
    }

    public void setMapAt(int x, int y, Map map) {
        level[x][y] = map;
    }

    public Map[][] getLevel() {
        return level;
    }
}
