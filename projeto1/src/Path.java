import java.util.function.Function;

/**
 * Class containing a path
 */
class Path {

    private static final char EMPTY = 'e', HARP = 'h', POTION = 'p', CLOAK = 'c',
            DOGS = '3', TROLL = 't', DRAGON = 'd';

    private static char[] path;
    //array containing the values of doing the path with each object
    private static int[] values = new int[Objects.values().length];
    //minimum infinity value to be used in this implementation
    //the value is the max value each step can take, multiplied by the path length.
    private static final int INFINITY = 600000;
    private static boolean hasMonsterBefore = false;
    private static boolean firstRun = true;

    /**
     * @return minimum value
     */
    public static int getResult() {
        int min = INFINITY;
        for (int value : values) if (value < min) min = value;
        return min;
    }

    public static void reset() {
        values = new int[Objects.values().length];
        hasMonsterBefore = false;
        firstRun = true;
    }

    /**
     * checks if it can pass a dog plot with the current object
     *
     * @param objects possible objects to hold
     * @return true if possible
     */
    private static boolean canPassDog(Objects objects) {
        return objects != Objects.EMPTY;
    }

    /**
     * checks if it can pass a troll plot with the current object
     *
     * @param objects possible objects to hold
     * @return true if possible
     */
    private static boolean canPassTroll(Objects objects) {
        return objects == Objects.POTION || objects == Objects.CLOAK;
    }

    /**
     * checks if it can pass a dragon plot with the current object
     *
     * @param objects possible objects to hold
     * @return true if possible
     */
    private static boolean canPassDragon(Objects objects) {
        return objects == Objects.CLOAK;
    }

    /**
     * calculates the cost of advancing to the next plot
     *
     * @param objects      current object
     * @param isMonsterPos true if the plot has a monster
     * @return the cost
     */
    private static int costOfObj(Objects objects, boolean isMonsterPos) {
        if (!isMonsterPos) return objects == Objects.EMPTY ? 1 : 3;
        if (objects == Objects.CLOAK) return 6;
        if (objects == Objects.POTION) return 5;
        if (objects == Objects.HARP) return 4;
        return 1;
    }

    /**
     * updates the cost if the path is possible, if not sets it at infinite
     *
     * @param monster generic function which tells if an object can pass certain monster
     */
    private static void handleMonster(Function<Objects, Boolean> monster) {
        int min = INFINITY;
        for (Objects o : Objects.values()) {
            if (monster.apply(o)) {
                values[o.ordinal()] += costOfObj(o, true);
                if (values[o.ordinal()] < min) min = values[o.ordinal()];
            } else values[o.ordinal()] = INFINITY;
        }
        //empty always has the min possible value
        values[Objects.EMPTY.ordinal()] = min;
    }

    /**
     * updates the cost of carrying an object.
     *
     * @param obj              object at the current plot
     * @param hasMonsterBefore true if there was a monster on the previous plot
     */
    private static void handleObject(Objects obj, boolean hasMonsterBefore) {
        for (Objects o : Objects.values())
            values[o.ordinal()] += costOfObj(o, false);

        if (hasMonsterBefore)
            values[Objects.EMPTY.ordinal()]++; //means they had an object and dropped it
        //it was possible to get here with the empty value,
        //therefore update object value to the min possible +1
        //because they will leave the spot with an object
        values[obj.ordinal()] = values[Objects.EMPTY.ordinal()] + 1;
    }

    public static void addPlot(char plot) {
        if (firstRun) {
            for (Objects o : Objects.values())
                if (o == Objects.EMPTY) values[o.ordinal()] = 1;
                else if (o.getValue() == plot)
                    values[o.ordinal()] = 2;
                else values[o.ordinal()] = INFINITY;
            firstRun = false;
        } else {
            switch (plot) {
                case EMPTY -> {
                    for (Objects o : Objects.values())
                        if (o == Objects.EMPTY) {
                            if (hasMonsterBefore) values[o.ordinal()] += 2;
                            else values[o.ordinal()]++;
                        } else values[o.ordinal()] += 3;
                    hasMonsterBefore = false;
                }
                case DOGS -> {
                    handleMonster(Path::canPassDog);
                    hasMonsterBefore = true;
                }
                case TROLL -> {
                    handleMonster(Path::canPassTroll);
                    hasMonsterBefore = true;
                }
                case DRAGON -> {
                    handleMonster(Path::canPassDragon);
                    hasMonsterBefore = true;
                }
                case HARP -> {
                    handleObject(Objects.HARP, hasMonsterBefore);
                    hasMonsterBefore = false;
                }
                case POTION -> {
                    handleObject(Objects.POTION, hasMonsterBefore);
                    hasMonsterBefore = false;
                }
                case CLOAK -> {
                    handleObject(Objects.CLOAK, hasMonsterBefore);
                    hasMonsterBefore = false;
                }
            }
        }
    }
}
