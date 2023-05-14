import java.util.LinkedList;
import java.util.List;

public class Node {

    private final int id;
    private int population;
    private int departureCapacity;
    private final List<Node> adjacent = new LinkedList<>();

    public Node(int id, int population, int departureCapacity) {
        this.id = id;
        this.population = population;
        this.departureCapacity = departureCapacity;
    }

    public int getId() {
        return id;
    }

    public int getPopulation() {
        return population;
    }

    public int getDepartureCapacity() {
        return departureCapacity;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setDepartureCapacity(int departureCapacity) {
        this.departureCapacity = departureCapacity;
    }

    public void addAdjacent(Node node) {
        adjacent.add(node);
    }

    public List<Node> getAdjacent() {
        return adjacent;
    }
}
