import graph.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MapClass {
    private final char[][] map;
    private int currRow = 1;

    private int Hrow = -1, Hcol = -1;

    public String getBestPath(int row, int col) {
        int nodeId = 0;
        Node start = new Node(row, col, nodeId++, 0, 0);
        Map<Node, Node> processed = new HashMap<>((map.length * map[0].length) / 2);
        Queue<Node> unprocessed = new ConcurrentLinkedQueue<>();

        unprocessed.add(start);
        do {
            Node n = unprocessed.remove();
            for (Directions d : Directions.values()) {
                Node tempNode = new Node(n.getRow(), n.getCol(), nodeId++, n.getCost(), n.getNrJumps() + 1);
                int dRow = d.getdRow();
                int dCol = d.getdCol();
                int nextCol = tempNode.getCol() + dCol;
                int nextRow = tempNode.getRow() + dRow;
                char nextPos = map[nextRow][nextCol];

                while (nextPos == '.') {
                    tempNode.setCol(nextCol);
                    tempNode.setRow(nextRow);
                    tempNode.setCost(tempNode.getCost() + 1);
                    nextCol = tempNode.getCol() + dCol;
                    nextRow = tempNode.getRow() + dRow;
                    nextPos = map[nextRow][nextCol];
                }
                if (nextPos == 'O' && !tempNode.equals(n)) {
                    var temp = processed.get(tempNode);
                    if (temp != null && temp.getCost() >= tempNode.getCost()) {
                        if (temp.getNrJumps() > tempNode.getNrJumps()) {
                            processed.remove(temp);
                            processed.put(tempNode, tempNode);
                            unprocessed.add(tempNode);
                        }
                    } else if (temp == null)
                        unprocessed.add(tempNode);
                }
                if (nextPos == 'H') return String.valueOf(tempNode.getNrJumps());
            }
            processed.put(n, n);
        } while (!unprocessed.isEmpty());
        return "Stuck";
    }

    public MapClass(int rows, int cols) {
        this.map = new char[rows + 2][cols + 2];
        for (int i = 0; i < cols + 2; i++) {
            map[0][i] = '-';
            map[rows + 1][i] = '-';
        }
    }

    public void addRow(String row) {
        map[currRow][0] = '-';
        map[currRow][row.length() + 1] = '-';
        int i = 1;
        for (char c : row.toCharArray()) {
            map[currRow][i++] = c;
            if (Hrow != -1 && c == 'H') {
                Hrow = currRow;
                Hcol = i - 1;
            }
        }
        currRow++;
    }

    enum Directions {
        RIGHT(0, 1),
        LEFT(0, -1),
        UP(1, 0),
        DOWN(-1, 0);

        private final int dRow;
        private final int dCol;

        Directions(int dRow, int dCol) {
            this.dRow = dRow;
            this.dCol = dCol;
        }

        public int getdCol() {
            return dCol;
        }

        public int getdRow() {
            return dRow;
        }
    }
}

