import java.util.function.Function;

/**
 * Class containing a path
 *
 * @author Guilherme Pocas 60236
 * @author Joao Oliveira 61052
 */
class Path {

    private static final char EMPTY = 'e', HARP = 'h', POTION = 'p', CLOAK = 'c',
            DOGS = '3', TROLL = 't', DRAGON = 'd';

    //array containing the costs of doing the path with each object
    private static int[] costs = new int[Objects.values().length];
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
        for (int c : costs) if (c < min) min = c;
        return min;
    }

    public static void reset() {
        costs = new int[Objects.values().length];
        hasMonsterBefore = false;
        firstRun = true;
    }

    /**
     * checks if it can pass a dog plot with the current object
     *
     * @param obj possible objects to hold
     * @return true if possible
     */
    private static boolean canPassDog(Objects obj) {
        return obj != Objects.EMPTY;
    }

    /**
     * checks if it can pass a troll plot with the current object
     *
     * @param obj possible objects to hold
     * @return true if possible
     */
    private static boolean canPassTroll(Objects obj) {
        return obj == Objects.POTION || obj == Objects.CLOAK;
    }

    /**
     * checks if it can pass a dragon plot with the current object
     *
     * @param obj possible objects to hold
     * @return true if possible
     */
    private static boolean canPassDragon(Objects obj) {
        return obj == Objects.CLOAK;
    }

    /**
     * calculates the cost of advancing to the next plot
     *
     * @param obj          current object
     * @param isMonsterPos     true if the plot has a monster
     * @param hasMonsterBefore optional parameter with the indication if there was a monster before
     * @return the cost
     */
    private static int costOfObj(Objects obj, boolean isMonsterPos, boolean... hasMonsterBefore) {
        if (!isMonsterPos)
            if (obj == Objects.EMPTY)
                return (hasMonsterBefore.length > 0 && hasMonsterBefore[0]) ? 2 : 1;
            else return 3;

        return obj.getCost();
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
                costs[o.ordinal()] += costOfObj(o, true);
                if (costs[o.ordinal()] < min) min = costs[o.ordinal()];
            } else costs[o.ordinal()] = INFINITY;
        }
        //empty always has the min possible value
        costs[Objects.EMPTY.ordinal()] = min;
    }

    /**
     * updates the cost of carrying an object.
     *
     * @param obj              object at the current plot
     * @param hasMonsterBefore true if there was a monster on the previous plot
     */
    private static void handleObject(Objects obj, boolean hasMonsterBefore) {
        for (Objects o : Objects.values())
            costs[o.ordinal()] += costOfObj(o, false, hasMonsterBefore);

        //it was possible to get here with the empty value,
        //therefore update object value to the min possible +1
        //because they will leave the spot with an object
        costs[obj.ordinal()] = costs[Objects.EMPTY.ordinal()] + 1;
    }

    public static void addPlot(char plot) {
        if (firstRun) {
            for (Objects o : Objects.values())
                if (o == Objects.EMPTY) costs[o.ordinal()] = 1;
                else if (o.getValue() == plot)
                    costs[o.ordinal()] = 2;
                else costs[o.ordinal()] = INFINITY;
            firstRun = false;
        } else {
            switch (plot) {
                case EMPTY -> {
                    for (Objects o : Objects.values())
                        costs[o.ordinal()] += costOfObj(o, false, hasMonsterBefore);
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
