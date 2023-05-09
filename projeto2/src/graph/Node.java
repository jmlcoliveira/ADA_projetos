package graph;

public class Node {
    public static int count = 0;
    private int row;
    private int col;
    private final int id;
    private final int dRow;
    private final int dCol;
    private final Node[] adjacent;

    public Node(int x, int y, int dRow, int dCol) {
        count++;
        this.adjacent = new Node[4];
        this.row = x;
        this.col = y;
        this.id = (901 * x) + y;
        this.dRow = dRow;
        this.dCol = dCol;
    }

    public int[] getPos() {
        return new int[]{row, col};
    }

    public int getId() {
        return (901 * row) + col;
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

    public void setAdjacent(int i, Node tempNode) {
        this.adjacent[i] = tempNode;
    }

    public int getdRow() {
        return dRow;
    }

    public int getdCol() {
        return dCol;
    }

    public Node getAdjacent(int i) {
        return adjacent[i];
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
                ", dRow=" + dRow +
                ", dCol=" + dCol +
                '}';
    }
}
