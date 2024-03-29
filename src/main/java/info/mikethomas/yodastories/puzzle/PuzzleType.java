package info.mikethomas.yodastories.puzzle;

public enum PuzzleType {

    QUEST,
    QUEST_IN_PROGRESS,
    STORY,
    STORY_INTRO,
    UNKNOWN;

    public static PuzzleType parse(int type) {
        switch(type) {
            case 0:
                System.out.println("Puzzle Type " + type + " = QUEST");
                return QUEST;

            case 1:
                System.out.println("Puzzle Type " + type + " = QUEST_IN_PROGRESS");
                return QUEST_IN_PROGRESS;

            case 2:
            System.out.println("Puzzle Type " + type + " = STORY");
                return STORY;

            case 3:
                System.out.println("Puzzle Type " + type + " = STORY_INTRO");
                return STORY_INTRO;

            default:
                return UNKNOWN;
        }
    }
}
