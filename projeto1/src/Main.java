import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(in.readLine());
        Path p;
        while (t > 0) {
            p = new Path(in.readLine());
            System.out.println(p.getTime());
            t--;
        }
    }
}

class Path {

    private final char EMPTY = 'e', HARP = 'h', POTION = 'p', CLOAK = 'c',
            NO_OBJ = '\0', DOGS = '3', TROLL = 't', DRAGON = 'd';

    private final String path;
    private final int times[][];

    Path(String path) {
        this.path = path;
        char last = path.charAt(path.length() - 1);
        this.compute(path.length() - 1, last == DRAGON ? 1 : 0, last == DOGS ? 1 : 0, last == TROLL ? 1 : 0, NO_OBJ);
        this.times = new int[path.length() + 1][path.length() + 1];
    }

    private int compute(int pos, int needsCloak, int needsPotion, int needsHarp, char currObj) {
        if (pos == 0) {
            if (needsCloak + needsPotion + needsHarp > 0) return 2;
            else return 1;
        }
        int sum = 0;
        if (path.charAt(pos) == EMPTY) {
            if (currObj != NO_OBJ && needsCloak + needsPotion + needsHarp > 0)
                return 3 + compute(pos - 1, needsCloak, needsPotion, needsHarp, currObj);
            else if (currObj != NO_OBJ)
                return 2 + compute(pos - 1, needsCloak, needsPotion, needsHarp, NO_OBJ);
            else return 1 + compute(pos - 1, needsCloak, needsPotion, needsHarp, NO_OBJ);

        } else if (path.charAt(pos) == HARP) {
            if(currObj != NO_OBJ && needsHarp == 0)
                return 2 + compute(pos-1, needsCloak, needsPotion, needsHarp, NO_OBJ);
            if(currObj != NO_OBJ && needsHarp > 0)
                return 2 + compute(pos-1, needsCloak, needsPotion, needsHarp, HARP);
            if(currObj == NO_OBJ && needsHarp > 0)
                return 2 + compute(pos - 1, needsCloak, needsPotion, needsHarp, HARP);
            if(currObj == NO_OBJ && needsHarp == 0)
                return 1 + compute(pos-1, needsCloak, needsPotion, needsHarp, HARP);
        }
        else if(path.charAt(pos) == CLOAK){
            if(needsHarp > 0)
                return
        }

            if (needsCloak) {
                if (path.charAt(pos) == 'c')
                    return 2 + sum;
                else return compute(pos - 1, needsCloak, needsPotion, needsHarp, currObj);
            } else if (needsPotion) {
                if (path.charAt(pos) == 'c' || path.charAt(pos) == 'p')
                    return 2;
                else return compute(pos - 1, needsCloak, needsPotion, needsHarp, currObj);
            } else if (needsHarp) {
                if (path.charAt(pos) == 'c' || path.charAt(pos) == 'p' || path.charAt(pos) == 'h')
                    return Math.min(2 + sum, sum + compute(pos - 1, needsCloak, ));
                else return compute(pos - 1, needsCloak, needsPotion, needsHarp, currObj);
            }
    }

    public int getTime() {
        return times[times.length - 1][times.length - 1];
    }
}
