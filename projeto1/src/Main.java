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
            System.out.println(p.compute());
            t--;
        }
    }
}

class Path {

    private final char EMPTY = 'e', HARP = 'h', POTION = 'p', CLOAK = 'c',
            NO_OBJ = '\0', DOGS = '3', TROLL = 't', DRAGON = 'd';
    private final String path;

    Path(String path) {
        this.path = path;

    }

    private int min(int a, int b, int c, int d){
        return Math.min(Math.min(a, b), Math.min(c, d));
    }

    public int compute(){
        int e=1, c=1, p=1, h=1;
        boolean hasC=false, hasP=false, hasH=false, hasMonsterBefore=false;

        char elemAtPos = path.charAt(0);
        switch(elemAtPos){
            case CLOAK -> {
                c++;
                hasC=true;
            }
            case POTION -> {
                p++;
                hasP=true;
            }
            case HARP -> {
                h++;
                hasH=true;
            }
        }

        for(int i = 1; i < path.length(); i++){
            elemAtPos = path.charAt(i);
            switch(elemAtPos){
                case EMPTY -> {
                    if(hasMonsterBefore) e+=2; else e++;
                    if(hasC) c+=3; else if(hasMonsterBefore) c+=2; else c++;
                    if(hasP) p+=3; else if(hasMonsterBefore) p+=2; else p++;
                    if(hasH) h+=3; else if(hasMonsterBefore) h+=2; else h++;
                    hasMonsterBefore=false;
                }
                case DOGS -> {
                    if(hasC) c+=6; else c = Math.min(h+4, p+5);
                    if(hasP) p+=5; else p = Math.min(h+4, c);
                    if(hasH) h+=4; else h = Math.min(p, c);
                    e = Math.min(Math.min(c, p), h);
                    hasMonsterBefore=true;
                }
                case TROLL -> {
                    if(hasC) c+=6; else c=p+5;
                    if(hasP) p+=5; else p=c;
                    hasH = false;
                    h = e = Math.min(c, p);
                    hasMonsterBefore=true;
                }
                case DRAGON -> {
                    if(hasC){
                        c+=6;
                        e = h = p = c;
                        hasH=false;
                        hasP=false;
                    }
                    hasMonsterBefore=true;
                }
                case HARP -> {
                    if(hasH){
                        h = e;
                        h+=3;
                    }
                    else {
                        if(hasMonsterBefore) h+=3; else h+=2;
                        hasH = true;
                    }
                    if(hasMonsterBefore) e+=2; else e++;
                    if(hasP) p+=3; else p++;
                    if(hasC) c+=3; else c++;
                }
                case POTION -> {
                    if(hasP) {
                        p = e;
                        p += 3;
                    }
                    else {
                        if(hasMonsterBefore) p+=3; else p+=2;
                        hasP = true;
                    }
                    if(hasC) c+=3; else c++;
                    if(hasH) h+=3; else h++;
                }
                case CLOAK -> {
                    if(hasC){
                        c = e;
                        c+=3;
                    }
                    else {
                        if(hasMonsterBefore) c+=3; else c+=2;
                        hasC = true;
                    }
                    if(hasMonsterBefore) e+=2; else e++;
                    if(hasP) p+=3; else p++;
                    if(hasH) h+=3; else h++;
                }
            }
        }
        return min(e, c, p, h);
    }

}
