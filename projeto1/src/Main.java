import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(in.readLine());
        Path p;
        while(t > 0){
            p = new Path(in.readLine());
            System.out.println(p.getTime());
            t--;
        }
    }
}

class Path {
    private final String path;
    Path(String path){
        this.path = path;
    }

    public int getTime() {
        return 0;
    }
}
