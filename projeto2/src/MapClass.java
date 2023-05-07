import graph.Node;

import java.util.*;

public class MapClass {
    private final char[][] map;
    private final int[] D_ROW = {1, -1, 0, 0};
    private final int[] D_COL = {0, 0, 1, -1};
    private int currRow = 1;
    Map<Node, Node> graph = new HashMap<>();
    Node goal = null;

    public MapClass(int rows, int cols) {
        this.map = new char[rows + 2][cols + 2];
        //this.graph = new Node[rows*cols];
        for (int i = 0; i < cols + 2; i++) {
            map[0][i] = '-';
            map[rows + 1][i] = '-';
        }
    }

    public void addRow(String row) {
        row = "-" + row + "-";
        map[currRow++] = row.toCharArray();
        /*if (currRow == map.length - 1) {
            for(int i=1; i<map.length-1;i++){
                for(int j=1; j<map[0].length-1;j++){
                    if(map[i][j] == 'H'){
                        goal = new Node(i,j,0,0,0,0);
                        return;
                    }
                }
            }
        }*/
    }

    private void buildGraph() {
        int rows = map.length - 1;
        int cols = map[0].length - 1;
        int count = 0;
        Node n1 = null;
        Node n2 = null;
        int goalLinked = -1;

        for (int i = 1; i < map.length; i++) {
            List<Node> toLink = new LinkedList<>();
            for (int j = 1; j < cols; j++) {
                char curr = map[i][j];
                if (curr == 'O') continue;
                char before = map[i][j - 1];
                char after = map[i][j + 1];
                char above = map[i - 1][j];
                char below = map[i + 1][j];
                if (curr == 'H') {
                    goal = new Node(i, j, 0, 0, 0, 0);
                    graph.put(goal, goal);
                    if (n1 != null) {
                        n1.addAdjacent(goal);
                        goalLinked = j;
                        goal.addAdjacent(n1);
                    }
                }
                //else if (before == 'O' && after == 'O') continue;
                else if ((before == 'O' || after == 'O') && (above == '.' || below == '.') && curr == '.') {
                    if (n1 == null) {
                        n1 = new Node(i, j, 0, 0, 0, 0);
                    } else {
                        n2 = new Node(i, j, 0, 0, 0, 0);
                        n1.addAdjacent(n2);
                        n2.addAdjacent(n1);
                        graph.put(n1, n1);
                        graph.put(n2, n2);
                        if (goalLinked == j) {
                            if (goal.getAdjacentNode().get(0).getId() == n1.getId()) {
                                n2.addAdjacent(goal);
                                goal.addAdjacent(n2);
                                for (Node n : toLink) {
                                    goal.addAdjacent(n);
                                    n.addAdjacent(goal);
                                }
                            }
                        }
                        for (Node n : toLink) {
                            n.addAdjacent(n1);
                            n.addAdjacent(n2);
                            graph.put(n, n);
                        }
                        toLink = new LinkedList<>();
                        n1 = null;
                        n2 = null;
                    }
                } else if ((above == '.' || below == '.') && !(above == '.' && below == '.') && curr == '.')
                    toLink.add(new Node(i, j, 0, 0, 0, 0));
            }
            if (n1 != null)
                for (Node n : toLink)
                    n.addAdjacent(n1);
            n1 = null;
            n2 = null;
        }
        n1 = null;
        n2 = null;
        assert goal != null;
        for (int j = 1; j < cols; j++) {
            List<Node> toLink = new LinkedList<>();
            for (int i = 1; i < map.length; i++) {
                char curr = map[i][j];
                if (curr == 'O') continue;
                char before = map[i][j - 1];
                char after = map[i][j + 1];
                char above = map[i - 1][j];
                char below = map[i + 1][j];
                if (curr == 'H') {
                    if (n1 != null) {
                        n1.addAdjacent(goal);
                        goal.addAdjacent(n1);
                    }
                }
                //else if (above == 'O' && below == 'O') continue;
                else if ((before == '.' || after == '.') && (above == 'O' || below == 'O') && !(before == '.' && after == '.') && curr == '.') {
                    var toSearch = new Node(i, j, 0, 0, 0, 0);
                    var temp = graph.get(toSearch);
                    if (n1 == null)
                        n1 = temp;
                    else {
                        temp.addAdjacent(n1);
                        n1.addAdjacent(temp);
                        for (Node n : toLink) {
                            n.addAdjacent(n1);
                            n.addAdjacent(temp);
                            graph.put(n, n);
                        }
                        toLink = new LinkedList<>();
                        n1 = null;
                    }
                } else if ((before == '.' || after == '.') && !(before == '.' && after == '.') && curr == '.')
                    toLink.add(graph.get(new Node(i, j, 0, 0, 0, 0)));
            }
            n1 = null;
            n2 = null;
        }
    }

    public String getBestPath1(int row, int col) {
        var temp = new Node(row, col, 0, 0, 0, 0);
        Node start = graph.get(temp);
        if (start == null) {
            for (int i = 0; i < 4; i++) {
                int dRow = D_ROW[i];
                int dCol = D_COL[i];
                int nextCol = col + dCol;
                int nextRow = row + dRow;
                char nextPos = map[nextRow][nextCol];

                while (nextPos == '.') {
                    nextCol = nextCol + dCol;
                    nextRow = nextRow + dRow;
                    nextPos = map[nextRow][nextCol];
                }
                if (nextPos == 'H') return "1";
                if (nextPos == 'O') {
                    temp.addAdjacent(graph.get(new Node(nextRow - dRow, nextCol - dCol, 0, 0, 0, 0)));
                }
            }
            start = temp;
        }
        Queue<Node> unprocessed = new LinkedList<>();
        unprocessed.add(start);
        Map<Node, Node> processed = new HashMap<>(Math.max(map.length, map[0].length));
        do {
            Node n = unprocessed.remove();
            if (!processed.containsKey(n)) {
                for (Node adj : n.getAdjacentNode()) {
                    if (adj == null) continue;
                    adj.setNrJumps(adj.getNrJumps() + 1);
                    if (adj.equals(goal)) return String.valueOf(adj.getNrJumps());
                    if (!processed.containsKey(adj))
                        unprocessed.add(adj);
                }
            }
            processed.put(n, n);
        } while (!unprocessed.isEmpty());
        return "Stuck";
    }

    public String getBestPath(int row, int col) {
        //int minJumps = Integer.MAX_VALUE;
        int nodeId = 0;
        Node start = new Node(row, col, 0, 0, -1, -1);
        Map<Node, Node> processed = new HashMap<>(Math.max(map.length, map[0].length));
        PriorityQueue<Node> unprocessed = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                //return o1.getCost()-o2.getCost();
                //return o1.getNrJumps() - o2.getNrJumps();
                if (o1.getNrJumps() < o2.getNrJumps() || o1.getCost() < o2.getCost()) {
                    if (o1.getNrJumps() < o2.getNrJumps()) return (o1.getNrJumps() - o2.getNrJumps());
                    return o1.getCost() - o2.getCost();
                }
                if (o1.getId() == o2.getId()) return 0;
                return 1;
            }
        });
        //Queue<Node> unprocessed = new LinkedList<>();

        unprocessed.add(start);
        int minH = Integer.MAX_VALUE;
        do {
            //this might give a wrong answer if there are multiples nodes with the same nrJumps.
            //if (minJumps != Integer.MAX_VALUE) return String.valueOf(minJumps);
            Node n = unprocessed.remove();

            for (int i = 0; i < 4; i++) {
                int dRow = D_ROW[i];
                int dCol = D_COL[i];
                if (dRow == -n.getdRow() && dCol == -n.getdCol()) continue;

                Node tempNode = new Node(n.getRow(), n.getCol(), n.getCost(), n.getNrJumps() + 1, dRow, dCol);
                int nextCol = tempNode.getCol() + dCol;
                int nextRow = tempNode.getRow() + dRow;
                char nextPos = map[nextRow][nextCol];
                int cost = 0;
                while (nextPos == '.') {
                    nextCol = nextCol + dCol;
                    nextRow = nextRow + dRow;
                    nextPos = map[nextRow][nextCol];
                    cost++;
                }
                tempNode.setCol(nextCol - dCol);
                tempNode.setRow(nextRow - dRow);
                tempNode.setCost(tempNode.getCost() + cost);

                int tempNodeJumps = tempNode.getNrJumps();
                if (nextPos == 'H') {
                    if (minH > tempNode.getNrJumps()) minH = tempNode.getNrJumps();
                    continue;
                }

                if (nextPos == 'O' && !tempNode.equals(n)) {
                    Node temp = processed.get(tempNode);
                    if (temp != null) {
                        if (temp.getNrJumps() > tempNodeJumps) {
                            processed.replace(tempNode, tempNode);
                            unprocessed.add(tempNode);
                        }
                    } else
                        unprocessed.add(tempNode);
                }
            }
            processed.put(n, n);
        } while (!unprocessed.isEmpty());
        return minH < Integer.MAX_VALUE ? String.valueOf(minH) : "Stuck";
    }
}
