import java.util.LinkedList;
import java.util.Queue;

/**
 * Class that represents the map and has the method to find the best path
 *
 * @author Guilher Pocas 60236
 * @author Joao Oliveira 61052
 */
public class MapClass {
    //char matrix that represents the map
    private final char[][] map;
    //Arrays that represent the directions
    private final int[] D_ROW = {1, -1, 0, 0};
    private final int[] D_COL = {0, 0, 1, -1};
    //Graph containing all the visited nodes
    private final Node[][] graph;
    //Current row
    private int currRow = 1;

    /**
     * Constructor
     * @param rows number of rows
     * @param cols number of columns
     */
    public MapClass(int rows, int cols) {
        this.graph = new Node[rows + 1][cols + 1];
        this.map = new char[rows + 2][cols + 2];
        for (int i = 0; i < cols + 2; i++) {
            map[0][i] = '-';
            map[rows + 1][i] = '-';
        }
    }

    /**
     * Adds a row to the map
     * @param row row to be added
     */
    public void addRow(String row) {
        row = "-" + row + "-";
        map[currRow++] = row.toCharArray();
    }

    /**
     * Method that finds the best path
     * @param row row of the starting point
     * @param col column of the starting point
     * @return the best path if found or "Stuck" if not
     */
    public String getBestPath(int row, int col) {
        if (map[row][col] == 'H') return "0";
        //starting node
        Node start = new Node(row, col, -1, -1);
        //initialize processed array
        boolean[][] processed = new boolean[graph.length][graph[0].length];
        //initialize unprocessed queues
        //unprocessed queue for the first level
        Queue<Node> unprocessed = new LinkedList<>();
        //unprocessed2 queue for the next level
        Queue<Node> unprocessed2 = new LinkedList<>();
        unprocessed.add(start);
        int level = 0;
        while (!unprocessed.isEmpty()) {
            level++;
            do {
                Node n = unprocessed.remove();
                int r = n.getRow();
                int c = n.getCol();
                if (processed[r][c]) continue;
                for (int i = 0; i < 4; i++) {
                    int dRow = D_ROW[i];
                    int dCol = D_COL[i];
                    //if the node is going back to the previous node, skip it
                    if (dRow == -n.getdRow() && dCol == -n.getdCol()) continue;

                    Node tempNode = new Node(r, c, dRow, dCol);

                    Node nodeAtGraph = graph[r][c];
                    if (nodeAtGraph != null) {
                        Node adj = nodeAtGraph.getAdjacent(i);
                        if (adj != null) {
                            tempNode = adj;
                            int tr = tempNode.getRow();
                            int tc = tempNode.getCol();
                            if (!processed[tr][tc])
                                unprocessed2.add(tempNode);
                        }
                    }

                    int nextCol = tempNode.getCol() + dCol;
                    int nextRow = tempNode.getRow() + dRow;
                    char nextPos = map[nextRow][nextCol];
                    //if the position is a wall, skip it because it can't move in that direction
                    if (nextPos == 'O') continue;

                    //find a position that is not a wall
                    while (nextPos == '.') {
                        nextCol = nextCol + dCol;
                        nextRow = nextRow + dRow;
                        nextPos = map[nextRow][nextCol];
                    }
                    tempNode.setCol(nextCol - dCol);
                    tempNode.setRow(nextRow - dRow);

                    //if the position is the hole, return the level
                    if (nextPos == 'H') {
                        return String.valueOf(level);
                    }

                    //if the position is a wall, means that it moved and got there
                    if (nextPos == 'O') {
                        if (!processed[tempNode.getRow()][tempNode.getCol()]) {
                            unprocessed2.add(tempNode);
                            n.setAdjacent(i, tempNode);
                        }
                    }
                }
                graph[r][c] = n;
                processed[r][c] = true;
            } while (!unprocessed.isEmpty());
            //swap queues
            unprocessed = unprocessed2;
            unprocessed2 = new LinkedList<>();
        }
        return "Stuck";
    }
}
