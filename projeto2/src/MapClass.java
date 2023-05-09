import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class MapClass {
    private final char[][] map;
    private final int[] D_ROW = {1, -1, 0, 0};
    private final int[] D_COL = {0, 0, 1, -1};
    private int currRow = 1;
    private final Node[] graph;
    static int multiplyFactor;

    public MapClass(int rows, int cols) {
        multiplyFactor = Math.max(rows, cols);
        this.graph = new Node[rows * multiplyFactor + cols + 1];
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
        if (map[row][col] == 'H') return "0";
        Node start = new Node(row, col, -1, -1);
        Set<Node> processed = new HashSet<>(Math.max(map.length, map[0].length));
        Queue<Node> unprocessed = new LinkedList<>();
        Queue<Node> unprocessed2 = new LinkedList<>();
        unprocessed.add(start);
        int level = 0;
        while (!unprocessed.isEmpty()) {
            level++;
            do {
                Node n = unprocessed.remove();
                if (processed.contains(n)) continue;
                for (int i = 0; i < 4; i++) {
                    int dRow = D_ROW[i];
                    int dCol = D_COL[i];
                    if (dRow == -n.getdRow() && dCol == -n.getdCol()) continue;

                    Node tempNode = new Node(n.getRow(), n.getCol(), dRow, dCol);

                    Node nodeAtGraph = graph[n.getId()];
                    if (nodeAtGraph != null) {
                        Node adj = nodeAtGraph.getAdjacent(i);
                        if (adj != null) {
                            tempNode = adj;
                            if (!processed.contains(tempNode))
                                unprocessed2.add(tempNode);
                        }
                    }

                    int nextCol = tempNode.getCol() + dCol;
                    int nextRow = tempNode.getRow() + dRow;
                    char nextPos = map[nextRow][nextCol];
                    if (nextPos == 'O') continue;

                    while (nextPos == '.') {
                        nextCol = nextCol + dCol;
                        nextRow = nextRow + dRow;
                        nextPos = map[nextRow][nextCol];
                    }
                    tempNode.setCol(nextCol - dCol);
                    tempNode.setRow(nextRow - dRow);

                    if (nextPos == 'H') {
                        return String.valueOf(level);
                    }

                    if (nextPos == 'O' && !tempNode.equals(n)) {
                        if (!processed.contains(tempNode)) {
                            unprocessed2.add(tempNode);
                            n.setAdjacent(i, tempNode);
                        }
                    }
                }
                graph[n.getId()] = n;
                processed.add(n);
            } while (!unprocessed.isEmpty());
            unprocessed = unprocessed2;
            unprocessed2 = new LinkedList<>();
        }
        return "Stuck";
    }
}
