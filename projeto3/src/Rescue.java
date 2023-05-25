import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Rescue class
 *
 *  @author Guilherme Pocas 60236
 *  @author Joao Oliveira 61052
 */
public class Rescue {

    private final List<Edge>[] graph;
    private final int source = 0;
    private int sink;
    private int edgeCounter = 1;

    /**
     * Constructor
     *
     * @param regions number of regions
     */
    @SuppressWarnings("unchecked")
    public Rescue(int regions) {
        graph = new List[regions * 2 + 1];
        for (int i = 0; i < regions * 2 + 1; i++)
            graph[i] = new LinkedList<>();
    }

    /**
     * Get the max flow
     *
     * @return max flow
     */
    public int getResult() {
        return edmondsKarp(this.graph, this.source, this.sink);
    }

    /**
     * Set the sink
     *
     * @param sink safe region
     */
    public void setSink(int sink) {
        this.sink = sink * 2;
        //edge from sink entry to sink exit is always the second edge
        Edge e = graph[this.sink - 1].get(1);
        //set value to max, from sink entry to sink exit
        e.setValue(Integer.MAX_VALUE);
    }

    /**
     * Add a region to the graph
     *
     * @param population        population of the region
     * @param departureCapacity departure capacity of the region
     */
    public void addRegion(int population, int departureCapacity) {
        //entry
        Edge entry = new Edge(source, edgeCounter, population);
        graph[source].add(entry);
        Edge reverse = new Edge(edgeCounter, source, 0);
        graph[edgeCounter].add(reverse);
        //exit
        Edge exit = new Edge(edgeCounter, edgeCounter + 1, departureCapacity);
        reverse = new Edge(edgeCounter + 1, edgeCounter, 0);
        graph[edgeCounter].add(exit);
        graph[edgeCounter + 1].add(reverse);
        edgeCounter += 2;
    }

    /**
     * Add a link between two regions.
     * Integer max value is used to represent a value equal or greater
     * than departure capacity, for simplicity purposes.
     *
     * @param r1 region 1
     * @param r2 region 2
     */
    public void addLink(int r1, int r2) {
        r1 = r1 * 2 - 1;
        r2 = r2 * 2 - 1;
        Edge e = new Edge(r1 + 1, r2, Integer.MAX_VALUE);
        graph[r1 + 1].add(e);
        e = new Edge(r2, r1 + 1, 0);
        graph[r2].add(e);
        e = new Edge(r2 + 1, r1, Integer.MAX_VALUE);
        graph[r2 + 1].add(e);
        e = new Edge(r1, r2 + 1, 0);
        graph[r1].add(e);
    }

    /**
     * Edmonds-Karp algorithm
     *
     * @param graph  adjacency list
     * @param source entry node
     * @param sink   exit node
     * @return max flow
     */
    private int edmondsKarp(List<Edge>[] graph, int source, int sink) {
        //all values are initialized to 0
        int[][] flow = new int[graph.length][graph.length];

        int[] via = new int[graph.length];
        int flowValue = 0;
        int increment;
        while ((increment = findPath(graph, flow, source, sink, via)) != 0) {
            flowValue += increment;
            int node = sink;
            while (node != source) {
                int origin = via[node];
                flow[origin][node] += increment;
                flow[node][origin] -= increment;
                node = origin;
            }
        }
        return flowValue;
    }

    /**
     * Find a path from source to sink
     *
     * @param graph  adjacency list
     * @param flow   flow matrix
     * @param source entry node
     * @param sink   exit node
     * @param via    via matrix
     * @return path increment
     */
    private int findPath(List<Edge>[] graph, int[][] flow, int source, int sink, int[] via) {
        Queue<Integer> waiting = new LinkedList<>();
        boolean[] found = new boolean[graph.length];
        for (int i = 0; i < graph.length; i++) {
            found[i] = false;
        }

        int[] pathIncr = new int[graph.length];
        waiting.add(source);
        found[source] = true;
        via[source] = source;
        pathIncr[source] = Integer.MAX_VALUE;

        do {
            int origin = waiting.remove();
            for (Edge e : graph[origin]) {
                int destin = e.getDest();
                int residue = e.getValue() - flow[origin][destin];
                if (!found[destin] && residue > 0) {
                    via[destin] = origin;
                    pathIncr[destin] = Math.min(pathIncr[origin], residue);
                    if (destin == sink)
                        return pathIncr[destin];
                    waiting.add(destin);
                    found[destin] = true;
                }
            }
        } while (!waiting.isEmpty());
        return 0;
    }
}
