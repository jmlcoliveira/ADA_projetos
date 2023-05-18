import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        // read R and L
        String[] rl = in.readLine().split(" ");
        int R = Integer.parseInt(rl[0]);
        int L = Integer.parseInt(rl[1]);

        // read population sizes and departure capacities
        Node[] nodes = new Node[R + 1];
        for (int i = 1; i <= R; i++) {
            String[] pd = in.readLine().split(" ");
            int population = Integer.parseInt(pd[0]);
            int departureCapacity = Integer.parseInt(pd[1]);
            nodes[i] = new Node(i, population, departureCapacity);
        }

        // read direct links
        Map<Integer, List<Edge>> graph = new HashMap<>();
        boolean[] isRoot = new boolean[R + 1];
        for (int i = 0; i < R + 1; i++) {
            isRoot[i] = true;
        }

        for (int i = 0; i < L; i++) {
            String[] link = in.readLine().split(" ");
            int r1 = Integer.parseInt(link[0]);
            int r2 = Integer.parseInt(link[1]);
            nodes[r1].addAdjacent(nodes[r2]);
            nodes[r2].addAdjacent(nodes[r1]);
            var edg1 = new Edge(nodes[r1], nodes[r2], nodes[r1].getDepartureCapacity(), nodes[r1].getDepartureCapacity()-nodes[r1].getPopulation());
            var edg2 = new Edge(nodes[r2], nodes[r1], nodes[r2].getDepartureCapacity(), nodes[r1].getDepartureCapacity()-nodes[r2].getPopulation());
            graph.computeIfAbsent(r1, l -> new LinkedList<>()).add(edg1);
            graph.computeIfAbsent(r2, l -> new LinkedList<>()).add(edg2);
            //graph.add();
            isRoot[r2] = false;
        }

        Node root = new Node(0, 0, 0);
        nodes[0] = root;
        graph.computeIfAbsent(root.getId(), l -> new LinkedList<>());
        for (int i = 1; i < R + 1; i++) {
            //if (isRoot[i]) {
                var edge = new Edge(root, nodes[i], Integer.MAX_VALUE, Integer.MAX_VALUE);
                graph.get(root.getId()).add(edge);
            //}
        }

        // read safe region
        int S = Integer.parseInt(in.readLine());

        // calculate maximum population that can reach the safe region
        //int maxPop = 0;
        //boolean[][] visited = new boolean[R + 1][R+1];

        //edmoncrap
        int[][] flow = new int[R + 1][R + 1];
        for (List<Edge> l : graph.values()) {
            for (Edge e : l)
                flow[e.getOrig().getId()][e.getDest().getId()] = 0;
        }
        Node[] via = new Node[R + 1];
        int flowValue = 0;
        int increment;
        while ((increment = findPath(nodes, graph, flow, nodes[0], nodes[S], via)) != 0) {
            flowValue += increment;
            Node node = nodes[S];
            while (node != nodes[0]) {
                Node origin = via[node.getId()];
                flow[origin.getId()][node.getId()] += increment;
                flow[node.getId()][origin.getId()] -= increment;
                node = origin;
            }
        }

        // print result
        System.out.println(flowValue);
    }

    public static int findPath(Node[] nodes, Map<Integer, List<Edge>> graph, int[][] flow, Node source, Node sink, Node[] via) {
        Queue<Node> waiting = new LinkedList<>();
        boolean[] found = new boolean[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            found[i] = false;
        }

        int[] pathIncr = new int[nodes.length];
        waiting.add(source);
        found[source.getId()] = true;
        via[source.getId()] = source;
        pathIncr[source.getId()] = Integer.MAX_VALUE;

        do {
            Node origin = waiting.remove();
            for (Edge e : graph.get(origin.getId()).stream().toList()) {
                //if (e.getOrig().equals(origin)) {
                    Node destin = e.getDest();
                    int residue = e.getCurrCapacity() - flow[origin.getId()][destin.getId()];
                    if(!found[destin.getId()] && residue > 0){
                        via[destin.getId()] = origin;
                        pathIncr[destin.getId()] = Math.min(pathIncr[origin.getId()], residue);
                        if(destin.getId() == sink.getId())
                            return pathIncr[destin.getId()];
                        waiting.add(destin);
                        found[destin.getId()] = true;
                    }
                //}
            }
        } while (!waiting.isEmpty());
        return 0;
    }
}
