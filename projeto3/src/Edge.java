public class Edge {

    private final Node orig;
    private final Node dest;
    private final int maxCapacity;
    private int currCapacity;

    public Edge(Node orig, Node dest, int maxCapacity, int currCapacity) {
        this.orig = orig;
        this.dest = dest;
        this.maxCapacity = maxCapacity;
        this.currCapacity = currCapacity;
    }

    public Node getOrig() {
        return orig;
    }

    public Node getDest() {
        return dest;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrCapacity() {
        return currCapacity;
    }

    public void setCurrCapacity(int i) {
        currCapacity = i;
    }

    public int getAvailableCapacity() {
        return maxCapacity - currCapacity;
    }
}
