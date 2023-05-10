import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Main class that reads the input and prints the result
 *
 * @author Guilher Pocas 60236
 * @author Joao Oliveira 61052
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        //Read R, C, T
        String[] line = in.readLine().split(" ");

        int rows = Integer.parseInt(line[0]);
        int cols = Integer.parseInt(line[1]);
        int test = Integer.parseInt(line[2]);

        MapClass m = new MapClass(rows, cols);

        //Read the map and add it to the MapClass
        while (rows > 0) {
            m.addRow(in.readLine());
            rows--;
        }

        //Read the test cases and print the result
        while (test > 0) {
            line = in.readLine().split(" ");
            rows = Integer.parseInt(line[0]);
            cols = Integer.parseInt(line[1]);
            System.out.println(m.getBestPath(rows, cols));
            test--;
        }
    }
}
