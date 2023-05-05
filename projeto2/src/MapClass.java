import graph.Node;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class MapClass {
    private final char[][] map;
    private final int[] D_ROW = {1, -1, 0, 0};
    private final int[] D_COL = {0, 0, 1, -1};
    private int currRow = 1;

    public MapClass(int rows, int cols) {
        this.map = new char[rows + 2][cols + 2];
        for (int i = 0; i < cols + 2; i++) {
            map[0][i] = '-';
            map[rows + 1][i] = '-';
        }
    }

    public void addRow(String row) {
        row = "-" + row + "-";
        map[currRow++] = row.toCharArray();
    }

    public String getBestPath(int row, int col) {
        int nodeId = 0;
        Node start = new Node(row, col, nodeId++, 0, 0);
        Map<Node, Node> processed = new HashMap<>(Math.max(map.length, map[0].length));
        PriorityQueue<Node> unprocessed = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                if (o1.getNrJumps() <= o2.getNrJumps() || o1.getCost() <= o2.getCost()) return -1;
                if (o1.getId() == o2.getId()) return 0;
                return 1;
            }
        });

        unprocessed.add(start);
        do {
            Node n = unprocessed.remove();
            for (int i = 0; i < 4; i++) {
                Node tempNode = new Node(n.getRow(), n.getCol(), nodeId++, n.getCost(), n.getNrJumps() + 1);
                int dRow = D_ROW[i];
                int dCol = D_COL[i];
                int nextCol = tempNode.getCol() + dCol;
                int nextRow = tempNode.getRow() + dRow;
                char nextPos = map[nextRow][nextCol];
                int count = 0;
                while (nextPos == '.') {
                    nextCol = nextCol + dCol;
                    nextRow = nextRow + dRow;
                    nextPos = map[nextRow][nextCol];
                    count++;
                }
                tempNode.setCol(nextCol - dCol);
                tempNode.setRow(nextRow - dRow);
                tempNode.setCost(tempNode.getCost() + count);
                if (nextPos == 'H') return String.valueOf(tempNode.getNrJumps());
                if (nextPos == 'O' && !tempNode.equals(n)) {
                    var temp = processed.get(tempNode);
                    if (temp != null && temp.getCost() >= tempNode.getCost()) {
                        if (temp.getNrJumps() > tempNode.getNrJumps()) {
                            processed.replace(tempNode, tempNode);
                            unprocessed.add(tempNode);
                        }
                    } else if (temp == null)
                        unprocessed.add(tempNode);
                }
            }
            processed.put(n, n);
        } while (!unprocessed.isEmpty());
        return "Stuck";
    }
}

