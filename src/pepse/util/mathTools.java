package pepse.util;

public class mathTools {

    /**
     * defualt constructor
     */
    private mathTools() {}

    /**
     * take the closest value before the given value that is a multiple of the divider
     */
    public static int clip_min(int value, int divider) {
        return value - value % divider;
    }

    /**
     * take the closest value after the given value that is a multiple of the divider
     */
    public static int clip_max(int value, int divider) {
        return value + divider - value % divider;
    }

}
