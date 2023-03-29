/**
 * Class containing a path
 *
 * @author Guilherme Pocas 60236
 * @author Joao Oliveira 61052
 */
class Path {

    private static final char EMPTY = 'e', HARP = 'h', POTION = 'p', CLOAK = 'c',
            DOGS = '3', TROLL = 't', DRAGON = 'd';
    private static final int INFINITY = 600000;

    private static String path;

    /**
     * @return minimum value
     */
    public static int getResult(String path) {
        Path.path=path;
        return Math.min(Math.min(cost(path.length()-1, EMPTY), cost(path.length()-1, HARP)), Math.min(cost(path.length()-1, POTION), cost(path.length()-1, CLOAK)));
    }

    private static boolean monst(int i){
        char c = path.charAt(i);
        return c==DOGS || c==TROLL || c==DRAGON;
    }

    private static int cost(int i, char obj){
        if(i==0 && obj==EMPTY) return 1;
        if(i == 0 && path.charAt(i) == obj) return 2;
        if((i==0 && path.charAt(i) != obj) || !canPass(path.charAt(i), obj)) return INFINITY;
        if(monst(i-1) && obj==EMPTY) return Math.min(cost(i-1, HARP), Math.min(cost(i-1, POTION), cost(i-1, CLOAK))) + 2;
        if(!monst(i-1)&&obj != EMPTY&&path.charAt(i)==obj) return cost(i-1, EMPTY) + 2;
        if(monst(i-1)&&obj != EMPTY&&path.charAt(i)==obj) return cost(i, EMPTY)+1;
        return cost(i-1, obj) + costOfObj(i, obj);
    }

    private static int costOfObj(int i, char obj){
        if(!monst(i) && obj==EMPTY && !monst(i-1)) return 1;
        if(!monst(i) && obj==EMPTY && monst(i-1)) return 2;
        if(!monst(i) && obj!=EMPTY) return 3;
        if(monst(i) && obj==HARP && canPass(path.charAt(i), HARP)) return 4;
        if(monst(i) && obj==POTION && canPass(path.charAt(i), POTION)) return 5;
        if(monst(i) && obj==CLOAK && canPass(path.charAt(i), CLOAK)) return 6;
        return INFINITY;
    }

    private static boolean canPass(char m, char obj){
        return (m==DRAGON && obj==CLOAK) || (m==TROLL && (obj==CLOAK || obj==POTION)) || (m==DOGS && obj != EMPTY) || (m!=DRAGON && m!=TROLL && m!=DOGS);
    }
}
