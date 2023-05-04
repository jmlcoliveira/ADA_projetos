import graph.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapClass {
    private final char[][] map;
    private int currRow = 1;

    private int Hrow = -1, Hcol = -1;

    public MapClass(int rows, int cols) {
        //this.map=new int[rows][cols];
        this.map = new char[rows + 2][cols + 2];
        for (int i = 0; i < cols + 2; i++) {
            map[0][i] = '-';
            map[rows + 1][i] = '-';
        }
    }

    public void addRow(String row) {
        //map[currRow]=row.toCharArray();
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
        /*int count=0;
        for(char c : row.toCharArray()){
            switch (c) {
                case '.' -> map[currRow][count++] = 0;
                case 'H' -> map[currRow][count++] = Integer.MIN_VALUE;
                case '0' -> map[currRow][count++] = Integer.MAX_VALUE;
            }
        }
        currRow++;*/
        if (currRow == map.length - 1)
            this.generateGraph();
    }

    @SuppressWarnings("unchecked")
    private void generateGraph() {
        Map<Node, List<Node>> adjacent = new HashMap<>();
        int wall = -1;

        for (int i = 1; i < map.length; i++) {
            //do smth
            int nodePos = -1;
            for (int j = 1; j < map[i].length; j++) {
                char c = map[i][j];
                if ()
            }
        }
    }

    public String getBestPath(int rows, int cols) {
    }
}

