/**
 * Enum class with the representation of the objects, including the absence of one, the empty case
 *
 * @author Guilherme Pocas 60236
 * @author Joao Oliveira 61052
 */
public enum Objects {
    EMPTY('e'),
    HARP('h'),
    POTION('p'),
    CLOAK('c');
    private final char value;

    Objects(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }
}
