/**
 * Edge class
 *  @author Guilherme Pocas 60236
 *  @author Joao Oliveira 61052
 */
public class Edge {

    private final int orig;
    private final int dest;
    private int value;

    public Edge(int orig, int dest, int value) {
        this.orig = orig;
        this.dest = dest;
        this.value=value;
    }

    public int getOrig() {
        return orig;
    }

    public int getDest() {
        return dest;
    }

    public int getValue(){
        return value;
    }

    public void setValue(int value){
        this.value=value;
    }
}
