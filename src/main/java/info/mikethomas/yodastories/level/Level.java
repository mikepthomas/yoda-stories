package info.mikethomas.yodastories.level;

import lombok.Getter;
import org.mapeditor.core.Map;

@Getter
public class Level {
    private final Map[][] level;

    public Level(int width, int height) {
        level = new Map[width][height];
    }

    public void setMapAt(int x, int y, Map map) {
        level[x][y] = map;
    }
}
