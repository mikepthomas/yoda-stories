package info.mikethomas.yodastories.zone.action;

public enum ActionType {

    AREA_PORTAL,
    HEAL,
    HEAL_NOT_REQUIRED,
    PUZZLE_COMPLETE,
    INTERACT,
    UNKNOWN;

    public static ActionType parse(int type) {
        switch(type) {
            case 1:
                System.out.println("Action Type " + type + " = AREA_PORTAL");
                return AREA_PORTAL;

            case 2:
                System.out.println("Action Type " + type + " = PUZZLE_COMPLETE");
                return PUZZLE_COMPLETE;

            case 3:
                System.out.println("Action Type " + type + " = INTERACT");
                return INTERACT;

            case 4:
                System.out.println("Action Type " + type + " = HEAL");
                return HEAL;

            case 5:
                System.out.println("Action Type " + type + " = HEAL_NOT_REQUIRED");
                return HEAL_NOT_REQUIRED;

            default:
                System.out.println("Action Type " + type + " = UNKNOWN");
                return UNKNOWN;
        }
    }
}
