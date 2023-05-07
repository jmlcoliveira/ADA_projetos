package graph;

import java.util.LinkedList;
import java.util.List;

public class Node {
    private int row, col, id, nrJumps;
    private final List<Node> adjacentNode;
    private int dRow;
    private int dCol;

    public Node(int x, int y, int id, int nrJumps, int dRow, int dCol) {
        this.row = x;
        this.col = y;
        this.id = (901 * x) + y;
        this.nrJumps = nrJumps;
        this.adjacentNode = new LinkedList<>();
    }

    public int[] getPos() {
        return new int[]{row, col};
    }

    public int getId() {
        return id;
    }

    public List<Node> getAdjacentNode() {
        return this.adjacentNode;
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

    public void addAdjacent(Node n) {
        this.adjacentNode.add(n);
    }

    public int getdRow() {
        return dRow;
    }

    public int getdCol() {
        return dCol;
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
        return id;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", nrJumps=" + nrJumps +
                '}';
    }
}
