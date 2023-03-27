import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Function;

/**
 * ADA First Project Java implementation
 *
 * @author Guilherme Pocas 60236
 * @author Joao Oliveira 61052
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(in.readLine());
        Path p;
        while (t > 0) {
            p = new Path(in.readLine().toCharArray());
            System.out.println(p.getResult());
            t--;
        }
    }
}

/**
 * Class containing a path
 */
class Path {

    private final char EMPTY = 'e', HARP = 'h', POTION = 'p', CLOAK = 'c',
            DOGS = '3', TROLL = 't', DRAGON = 'd';

    private enum Objects {
        EMPTY('e'),
        HARP('h'),
        POTION('p'),
        CLOAK('c');
        private final char value;

        Objects(char value) {
            this.value = value;
        }
    }

    private final char[] path;
    //array containing the values of doing the path with each object
    private final int[] values;
    //minimum infinity value to be used in this implementation
    //the value is the max value each step can take, multiplied by the path length.
    private final int INFINITY;

    Path(char[] path) {
        this.path = path;
        this.values = new int[Objects.values().length];
        this.INFINITY = path.length*6;
        this.compute();
    }

    /**
     * @return minimum value
     */
    public int getResult() {
        int min = INFINITY;
        for (int value : values) if (value < min) min = value;
        return min;
    }

    /**
     * checks if it can pass a dog plot with the current object
     * @param objects possible objects to hold
     * @return true if possible
     */
    private boolean canPassDog(Objects objects) {
        return objects != Objects.EMPTY;
    }

    /**
     * checks if it can pass a troll plot with the current object
     * @param objects possible objects to hold
     * @return true if possible
     */
    private boolean canPassTroll(Objects objects) {
        return objects == Objects.POTION || objects == Objects.CLOAK;
    }

    /**
     * checks if it can pass a dragon plot with the current object
     * @param objects possible objects to hold
     * @return true if possible
     */
    private boolean canPassDragon(Objects objects) {
        return objects == Objects.CLOAK;
    }

    /**
     * calculates the cost of advancing to the next plot
     * @param objects current object
     * @param isMonsterPos true if the plot has a monster
     * @return the cost
     */
    private int costOfObj(Objects objects, boolean isMonsterPos) {
        if (!isMonsterPos) return objects == Objects.EMPTY ? 1 : 3;
        if (objects == Objects.CLOAK) return 6;
        if (objects == Objects.POTION) return 5;
        if (objects == Objects.HARP) return 4;
        return 1;
    }

    /**
     * updates the cost if the path is possible, if not sets it at infinite
     * @param monster generic function which tells if an object can pass certain monster
     */
    private void handleMonster(Function<Objects, Boolean> monster){
        int min = INFINITY;
        for (Objects o : Objects.values()) {
            if (monster.apply(o)) {
                values[o.ordinal()] += costOfObj(o, true);
                if (values[o.ordinal()] < min) min = values[o.ordinal()];
            }
            else values[o.ordinal()] = INFINITY;
        }
        //empty always has the min possible value
        values[Objects.EMPTY.ordinal()] = min;
    }

    /**
     * updates the cost of carrying an object.
     * @param obj object at the current plot
     * @param hasMonsterBefore true if there was a monster on the previous plot
     */
    private void handleObject(Objects obj, boolean hasMonsterBefore){
        for (Objects o : Objects.values())
            values[o.ordinal()] += costOfObj(o, false);

        if (hasMonsterBefore) values[Objects.EMPTY.ordinal()]++; //means they had an object and dropped it
        //it was possible to get here with the empty value,
        //therefore update object value to the min possible +1
        //because they will leave the spot with an object
        values[obj.ordinal()] = values[Objects.EMPTY.ordinal()] + 1;
    }

    private void compute() {
        boolean hasMonsterBefore = false;

        char elemAtPos = path[0];

        for (Objects o : Objects.values())
            if (o == Objects.EMPTY) values[o.ordinal()] = 1;
            else if (o.value == elemAtPos)
                values[o.ordinal()] = 2;
            else values[o.ordinal()] = INFINITY;

        for (int i = 1; i < path.length; i++) {
            switch (path[i]) {
                case EMPTY -> {
                    for (Objects o : Objects.values())
                        if (values[o.ordinal()] < INFINITY) {
                            if (o == Objects.EMPTY) {
                                if (hasMonsterBefore) values[o.ordinal()] += 2;
                                else values[o.ordinal()]++;
                            } else values[o.ordinal()] += 3;
                        }
                    hasMonsterBefore = false;
                }
                case DOGS -> {
                    handleMonster(this::canPassDog);
                    hasMonsterBefore = true;
                }
                case TROLL -> {
                    handleMonster(this::canPassTroll);
                    hasMonsterBefore = true;
                }
                case DRAGON -> {
                    handleMonster(this::canPassDragon);
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
