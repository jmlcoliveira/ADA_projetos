package graph;

import java.util.LinkedList;
import java.util.List;

public class Node {
    private int row;
    private int col;
    private final int id;
    private int nrJumps;
    private final int dRow;
    private final int dCol;
    private List<Node> adjacent;

    public Node(int x, int y, int nrJumps, int dRow, int dCol) {
        this.adjacent = new LinkedList<>();
        this.row = x;
        this.col = y;
        this.id = (901 * x) + y;
        this.nrJumps = nrJumps;
        this.dRow = dRow;
        this.dCol = dCol;
    }

    public int[] getPos() {
        return new int[]{row, col};
    }

    public int getId() {
        return id;
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

    public int getNrJumps() {
        return nrJumps;
    }

    public void setNrJumps(int nrJumps) {
        this.nrJumps = nrJumps;
    }

    public int getdRow() {
        return dRow;
    }

    public int getdCol() {
        return dCol;
    }

    public List<Node> getAdjacent() {
        return adjacent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return col == node.getCol() && row == node.getRow();
    }

    @Override
    public int hashCode() {
        return (901 * row) + col;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", nrJumps=" + nrJumps +
                ", dRow=" + dRow +
                ", dCol=" + dCol +
                '}';
    }
}
