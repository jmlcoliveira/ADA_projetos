import java.util.Iterator;
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
    //Array containing the directions    DOWN     UP     RIGHT    LEFT
    private final int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    //Graph containing all the visited nodes
    private final Node[][] graph;
    //Current row
    private int currRow = 1;
    //Constants
    private final char
            WALL = 'O',
            HOLE = 'H',
            EMPTY = '.',
            OFF_BOUNDARIES = '-';

    /**
     * Constructor
     *
     * @param rows number of rows
     * @param cols number of columns
     */
    public MapClass(int rows, int cols) {
        this.graph = new Node[rows + 1][cols + 1];
        this.map = new char[rows + 2][cols + 2];
        for (int i = 0; i < cols + 2; i++) {
            map[0][i] = OFF_BOUNDARIES;
            map[rows + 1][i] = OFF_BOUNDARIES;
        }
    }

    /**
     * Adds a row to the map
     *
     * @param row row to be added
     */
    public void addRow(String row) {
        row = OFF_BOUNDARIES + row + OFF_BOUNDARIES;
        map[currRow++] = row.toCharArray();
    }


    /**
     * Method that returns the adjacent nodes of a node
     * If the node is already in the graph, returns the adjacent nodes
     * If not, finds the adjacent nodes and adds them to the graph
     *
     * @param n node to get the adjacent nodes
     * @return iterator of the adjacent nodes
     */
    private Iterator<Node> getAdjacent(Node n){
        int row = n.getRow();
        int col = n.getCol();
        if(graph[row][col] == null){
            for (int[] direction : DIRECTIONS) {
                int dRow = direction[0];
                int dCol = direction[1];

                Node tempNode = new Node(row, col, dRow, dCol);

                int nextCol = tempNode.getCol() + dCol;
                int nextRow = tempNode.getRow() + dRow;
                char nextPos = map[nextRow][nextCol];
                //if the position is a wall, skip it because it can't move in that direction
                if (nextPos == WALL) continue;

                //find a position that is not a wall
                while (nextPos == EMPTY) {
                    nextCol = nextCol + dCol;
                    nextRow = nextRow + dRow;
                    nextPos = map[nextRow][nextCol];
                }
                tempNode.setCol(nextCol - dCol);
                tempNode.setRow(nextRow - dRow);


                //if the position is a wall, means that it moved and got there
                if (nextPos == WALL || nextPos == HOLE) {
                    if (nextPos == HOLE) {
                        tempNode.setCol(nextCol);
                        tempNode.setRow(nextRow);
                    }
                    n.addAdjacent(tempNode);
                }
            }
            graph[row][col] = n;
        }
        return graph[row][col].getAdjacent();
    }

    /**
     * Method that finds the best path
     *
     * @param row row of the starting point
     * @param col column of the starting point
     * @return the best path if found or "Stuck" if not
     */
    public int getBestPath(int row, int col) {
        if (map[row][col] == HOLE) return 0;
        if (map[row][col] == WALL) return -1;
        //starting node
        Node start = new Node(row, col, 0, 0);
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
                Iterator<Node> it = getAdjacent(n);
                while (it.hasNext()) {
                    Node adj = it.next();
                    //if trying to go back, skip it
                    if (adj.getdRow() == -n.getdRow() && adj.getdCol() == -n.getdCol()) continue;
                    if(map[adj.getRow()][adj.getCol()] == HOLE)
                        return level;
                    if(!processed[adj.getRow()][adj.getCol()]){
                        unprocessed2.add(adj);
                    }
                }
                processed[r][c] = true;
            } while (!unprocessed.isEmpty());
            //swap queues
            unprocessed = unprocessed2;
            unprocessed2 = new LinkedList<>();
        }
        return -1;
    }
}
