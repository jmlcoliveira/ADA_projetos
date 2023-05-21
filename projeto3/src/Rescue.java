import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Rescue {

    private final List<Edge>[] graph;
    private final int source = 0;
    private int S;
    private int count=1;

    @SuppressWarnings("unchecked")
    public Rescue(int regions){
        graph = new List[regions*2 + 1];
        for(int i = 0; i < regions*2 + 1; i++)
            graph[i] = new LinkedList<>();
    }

    public int getResult(){
        return edmondsKarp(this.graph, this.source, this.S);
    }

    public void setSink(int sink){
        this.S = sink*2;
        for(Edge e : graph[S-1])
            e.setValue(Integer.MAX_VALUE);
    }

    public void addRegion(int population, int departureCapacity){
        //entry
        Edge entry = new Edge(source, count, population);
        graph[source].add(entry);
        Edge reverse = new Edge(count, source, 0);
        graph[count].add(reverse);
        //exit
        Edge exit = new Edge(count, count+1, departureCapacity);
        reverse = new Edge(count+1, count, 0);
        graph[count].add(exit);
        graph[count+1].add(reverse);
        count+=2;
    }

    public void addLink(int r1, int r2){
        r1=r1*2-1;
        r2=r2*2-1;
        Edge e = new Edge(r1+1, r2, Integer.MAX_VALUE);
        graph[r1+1].add(e);
        e = new Edge(r2, r1+1, 0);
        graph[r2].add(e);
        e = new Edge(r2+1, r1, Integer.MAX_VALUE);
        graph[r2+1].add(e);
        e = new Edge(r1, r2+1, 0);
        graph[r1].add(e);
    }

    private int edmondsKarp(List<Edge>[] graph, int source, int sink){

        int[][] flow = new int[graph.length][graph.length];
        for (List<Edge> l : graph) {
            for (Edge e : l)
                flow[e.getOrig()][e.getDest()] = 0;
        }
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
            for (Edge e : graph[origin].stream().toList()) {
                int destin = e.getDest();
                int residue = e.getValue() - flow[origin][destin];
                if(!found[destin] && residue > 0){
                    via[destin] = origin;
                    pathIncr[destin] = Math.min(pathIncr[origin], residue);
                    if(destin == sink)
                        return pathIncr[destin];
                    waiting.add(destin);
                    found[destin] = true;
                }
            }
        } while (!waiting.isEmpty());
        return 0;
    }
}
