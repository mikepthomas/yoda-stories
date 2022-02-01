package info.mikethomas.yodastories.puzzle;

import lombok.Data;
import org.mapeditor.core.Tile;

@Data
public class Puzzle {

    private PuzzleType type;
    private RewardType reward;
    private String startText;
    private String endText;
    private String rewardText;
    private Tile item;
}
