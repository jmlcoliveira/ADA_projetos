package graph;

import java.util.LinkedList;
import java.util.List;

public class Node {
    static int num = 1;
    int x, y, id;
    List<Node> adjacentNode;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.id = num++;
        this.adjacentNode = new LinkedList<>();
    }

    public int[] getPos() {
        return new int[]{x, y};
    }

    public List<Node> getAdjacentNode() {
        return this.adjacentNode;
    }

    public void addAdjacent(Node n) {
        this.adjacentNode.add(n);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id == node.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
