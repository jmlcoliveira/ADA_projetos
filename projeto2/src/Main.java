import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] line = in.readLine().split(" ");
        int rows = Integer.parseInt(line[0]);
        int cols = Integer.parseInt(line[1]);
        int test = Integer.parseInt(line[2]);
        MapClass m = new MapClass(rows, cols);
        while (rows > 0) {
            m.addRow(in.readLine());
            rows--;
        }
        while (test > 0) {
            line = in.readLine().split(" ");
            rows = Integer.parseInt(line[0]);
            cols = Integer.parseInt(line[1]);
            System.out.println(m.getBestPath(rows, cols));
            test--;
        }
    }
}
