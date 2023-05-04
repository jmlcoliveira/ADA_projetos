import graph.Node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class MapClass {
    private final char[][] map;
    private final int[] D_ROW = {1, -1, 0, 0};
    private final int[] D_COL = {0, 0, 1, -1};
    private int currRow = 1;

    public String getBestPath(int row, int col) {
        int nodeId = 0;
        Node start = new Node(row, col, nodeId++, 0, 0);
        Map<Node, Node> processed = new HashMap<>(map.length * map[0].length);
        Queue<Node> unprocessed = new LinkedList<>();

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

    public MapClass(int rows, int cols) {
        this.map = new char[rows + 2][cols + 2];
        for (int i = 0; i < cols + 2; i++) {
            map[0][i] = '-';
            map[rows + 1][i] = '-';
        }
    }

    //@SuppressWarnings("unchecked")
    /*private void buildGraph(int row, int col){
        Node[] graph = new Node[map.length * map[0].length];
        graph[0] = new Node(row, col, 0, 0 ,0);
        List<Integer> nodes = new LinkedList<>();
        nodes.add(0);
        Queue<Node> unprocessed = new LinkedList<>();
        unprocessed.add(graph[0]);
        for(int i = 1; i < map.length-1; i++){
            Node temp = new Node(i, -1, -1, -1, -1);
            char firstPos = map[i][0];
            for(int j = 1; j < map[i].length-1; j++){
                char prev = map[i][j-1];
                char curr = map[i][j];
                char next = map[i][j+1];
                    if(prev=='-') continue;
            }
        }
    }*/

    public void addRow(String row) {
        row = "-" + row + "-";
        map[currRow++] = row.toCharArray();
    }

    /*enum Directions {
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
    }*/
}

