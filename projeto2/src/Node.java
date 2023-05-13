import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Node class that represents a position in the map and its adjacent nodes
 *
 * @author Guilher Pocas 60236
 * @author Joao Oliveira 61052
 */
public class Node {
    //direction of the node
    private final int dRow;
    private int col;
    //array of adjacent nodes
    private final List<Node> adjacent;
    private final int dCol;
    //row and column of the node
    private int row;

    /**
     * Constructor
     *
     * @param x    row of the node
     * @param y    column of the node
     * @param dRow direction of the node in the row
     * @param dCol direction of the node
     */
    public Node(int x, int y, int dRow, int dCol) {
        //initialize adjacent array with 4 positions, one for each direction
        this.adjacent = new LinkedList<>();
        this.row = x;
        this.col = y;
        this.dRow = dRow;
        this.dCol = dCol;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getdRow() {
        return dRow;
    }

    public int getdCol() {
        return dCol;
    }

    public void addAdjacent(Node n){
        adjacent.add(n);
    }

    public Iterator<Node> getAdjacent() {
        return adjacent.listIterator();
    }
}
