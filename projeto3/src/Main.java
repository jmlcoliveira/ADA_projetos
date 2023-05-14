import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        // read R and L
        String[] rl = in.readLine().split(" ");
        int R = Integer.parseInt(rl[0]);
        int L = Integer.parseInt(rl[1]);

        // read population sizes and departure capacities
        Node[] graph = new Node[R + 1];
        for (int i = 1; i <= R; i++) {
            String[] pd = in.readLine().split(" ");
            int population = Integer.parseInt(pd[0]);
            int departureCapacity = Integer.parseInt(pd[1]);
            graph[i] = new Node(i, population, departureCapacity);
        }

        // read direct links
        for (int i = 0; i < L; i++) {
            String[] link = in.readLine().split(" ");
            int r1 = Integer.parseInt(link[0]);
            int r2 = Integer.parseInt(link[1]);
            graph[r1].addAdjacent(graph[r2]);
            graph[r2].addAdjacent(graph[r1]);
        }

        // read safe region
        int S = Integer.parseInt(in.readLine());

        // calculate maximum population that can reach the safe region
        int maxPop = 0;
        boolean[][] visited = new boolean[R + 1][R+1];

        // print result
        System.out.println(maxPop);
    }
}
