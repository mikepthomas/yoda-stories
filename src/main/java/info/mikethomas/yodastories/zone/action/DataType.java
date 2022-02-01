package info.mikethomas.yodastories.zone.action;

public enum DataType {

    TILE,
    UNKNOWN;

    public static DataType parse(int type) {
        switch(type) {
            case 2:
                System.out.println("Data Type " + type + " = TILE");
                return TILE;

            default:
                System.out.println("Data Type " + type + " = UNKNOWN");
                return UNKNOWN;
        }
    }
}
