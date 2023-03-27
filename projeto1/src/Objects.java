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
