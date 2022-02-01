package info.mikethomas.yodastories.puzzle;

public enum RewardType {
    
    KEYCARD,
    PUZZLE_ITEM,
    PUZZLE_ITEM_RARE,
    TOOL,
    UNKNOWN;

    public static RewardType parse(int type) {
        switch(type) {
            case 0:
                System.out.println("Reward Type " + type + " = KEYCARD");
                return KEYCARD;

            case 1:
                System.out.println("Reward Type " + type + " = TOOL");
                return TOOL;

            case 2:
            System.out.println("Reward Type " + type + " = PUZZLE_ITEM_RARE");
                return PUZZLE_ITEM_RARE;

            case 4:
                System.out.println("Reward Type " + type + " = PUZZLE_ITEM");
                return PUZZLE_ITEM;

            default:
                return UNKNOWN;
        }
    }
}
