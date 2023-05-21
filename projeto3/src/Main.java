import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        // read R and L
        String[] rl = in.readLine().split(" ");
        int R = Integer.parseInt(rl[0]);
        int L = Integer.parseInt(rl[1]);

        Rescue rescue = new Rescue(R);

        while(R>0){
            String[] line = in.readLine().split(" ");
            int population = Integer.parseInt(line[0]);
            int departureCapacity = Integer.parseInt(line[1]);
            rescue.addRegion(population, departureCapacity);
            R--;
        }

        while(L>0){
            String[] link = in.readLine().split(" ");
            int r1 = Integer.parseInt(link[0]);
            int r2 = Integer.parseInt(link[1]);
            rescue.addLink(r1, r2);
            L--;
        }

        // read safe region
        int S = Integer.parseInt(in.readLine());
        rescue.setSink(S);

        System.out.println(rescue.getResult());
    }

}
