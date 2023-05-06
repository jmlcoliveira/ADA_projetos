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
        int minJumps = Integer.MAX_VALUE;
        int nodeId = 0;
        Node start = new Node(row, col, nodeId++, 0, -1, -1);
        Map<Node, Node> processed = new HashMap<>(Math.max(map.length, map[0].length));
        PriorityQueue<Node> unprocessed = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.getNrJumps() - o2.getNrJumps();
                /*if (o1.getNrJumps() < o2.getNrJumps()) return -1;
                if (o1.getId() == o2.getId()) return 0;
                return 1;*/
            }
        });
        //Queue<Node> unprocessed = new LinkedList<>();

        unprocessed.add(start);
        do {
            //this might give a wrong answer if there are multiples nodes with the same nrJumps.
            if (minJumps != Integer.MAX_VALUE) return String.valueOf(minJumps);
            Node n = unprocessed.remove();
            for (int i = 0; i < 4; i++) {
                int dRow = D_ROW[i];
                int dCol = D_COL[i];
                if (dRow == n.getdRow() && dCol == n.getdCol()) continue;

                Node tempNode = new Node(n.getRow(), n.getCol(), nodeId++, n.getNrJumps() + 1, dRow, dCol);
                int nextCol = tempNode.getCol() + dCol;
                int nextRow = tempNode.getRow() + dRow;
                char nextPos = map[nextRow][nextCol];

                while (nextPos == '.') {
                    nextCol = nextCol + dCol;
                    nextRow = nextRow + dRow;
                    nextPos = map[nextRow][nextCol];
                }
                tempNode.setCol(nextCol - dCol);
                tempNode.setRow(nextRow - dRow);

                int tempNodeJumps = tempNode.getNrJumps();
                if (nextPos == 'H' && tempNodeJumps < minJumps)
                    minJumps = tempNodeJumps;

                if (nextPos == 'O' && !tempNode.equals(n)) {
                    Node temp = processed.get(tempNode);
                    if (temp != null) {
                        if(temp.getNrJumps() > tempNodeJumps) {
                            processed.replace(tempNode, tempNode);
                            unprocessed.add(tempNode);
                        }
                    } else
                        unprocessed.add(tempNode);
                }
            }
            processed.put(n, n);
        } while (!unprocessed.isEmpty());
        return (minJumps == Integer.MAX_VALUE) ? "Stuck" : String.valueOf(minJumps);
    }
}

