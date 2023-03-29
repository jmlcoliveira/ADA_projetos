/**
 * Enum class with the representation of the objects, including the absence of one, the empty case
 *
 * @author Guilherme Pocas 60236
 * @author Joao Oliveira 61052
 */
public enum Objects {
    EMPTY('e', 1),
    HARP('h', 4),
    POTION('p', 5),
    CLOAK('c', 6);
    private final char value;
    private final int cost;

    Objects(char value, int cost) {
        this.value = value;
        this.cost = cost;
    }

    public char getValue() {
        return value;
    }

    public int getCost() {
        return cost;
    }
}
