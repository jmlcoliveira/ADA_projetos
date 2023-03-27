import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * ADA First Project Java implementation
 *
 * @author Guilherme Pocas 60236
 * @author Joao Oliveira 61052
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(in.readLine());
        while (t > 0) {
            Path.setPath(in.readLine().toCharArray());
            System.out.println(Path.getResult());
            Path.reset();
            t--;
        }
    }
}
