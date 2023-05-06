import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader in = new BufferedReader(new FileReader("map.txt"));
        String[] line = in.readLine().split(" ");

        int rows = Integer.parseInt(line[0]);
        int cols = Integer.parseInt(line[1]);
        int test = Integer.parseInt(line[2]);

        long start = System.currentTimeMillis();
        MapClass m = new MapClass(rows, cols);
        while (rows > 0) {
            m.addRow(in.readLine());
            rows--;
        }
        long loadTime = System.currentTimeMillis() - start;
        System.out.printf("Took %dms to load the map.\n", loadTime);
        long timeToRunTests = System.currentTimeMillis();
        while (test > 0) {
            line = in.readLine().split(" ");
            rows = Integer.parseInt(line[0]);
            cols = Integer.parseInt(line[1]);
            start = System.currentTimeMillis();
            System.out.println(m.getBestPath(rows, cols));
            System.out.printf("Took %dms to run test %d\n", System.currentTimeMillis()-start, test);
            test--;
        }
        timeToRunTests = System.currentTimeMillis() - timeToRunTests;
        System.out.printf("Took %dms to run all tests\n", timeToRunTests);
        System.out.println("Total time taken: " + (loadTime + timeToRunTests) + "ms");
    }
}
