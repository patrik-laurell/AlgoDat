import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

class StableMarriage {
    static LinkedList<Man> men = new LinkedList<Man>();
    static Woman[] women;
    static Man[] manArray;

    public static void main(String[] args) {
        parseInput();
        matchMake();
    }

    private static void parseInput() {
        Scanner sc = new Scanner(System.in);
        String line;
        boolean readNames = true;
        do {
            line = sc.nextLine();
        } while(line.startsWith("#"));

        women = new Woman[Integer.parseInt(line.substring(2))];
        manArray = new Man[Integer.parseInt(line.substring(2))];

        line = sc.nextLine();
        while (!line.isEmpty()) {
                int id = Integer.parseInt(line.substring(0, line.indexOf(' ')));
                String name = line.substring(line.indexOf(' ') + 1);
                if(id % 2 == 0) {
                    women[(id-1)/2] = new Woman(name);
                } else {
                    Man m = new Man((id-1)/2, name);
                    men.add(m);
                    manArray[(id-1)/2] = m;
                }
            line = sc.nextLine();
        }

        while(sc.hasNextLine()) {
            line = sc.nextLine();
            int id = Integer.parseInt(line.substring(0, line.indexOf(':')));
            String[] strprefs = line.substring(line.indexOf(':') + 2).split(" ");
            int[] prefs = new int[strprefs.length];
            if(id % 2 != 0) {
                for(int i=0; i<prefs.length; i++) {
                    prefs[i] = (Integer.parseInt(strprefs[i])-1)/2;
                }
                manArray[(id-1)/2].preferences = prefs;
            } else {
                for(int i=0; i<prefs.length; i++) {
                    prefs[(Integer.parseInt(strprefs[i])-1)/2] = i;
                }
                women[(id-1)/2].preferences = prefs;
            }

        }

        /*while(sc.hasNextLine()) {
            line = sc.nextLine();
            if(line.equals("")) {
                readNames = false;
                continue;
            } else if(line.charAt(0) == 'n') {
                women = new Woman[Integer.parseInt(line.substring(2))];
                manArray = new Man[Integer.parseInt(line.substring(2))];
            } else if(readNames) {
                int id = Integer.parseInt(line.substring(0, line.indexOf(' ')));
                String name = line.substring(line.indexOf(' ') + 1);
                if(id % 2 == 0) {
                    women[(id-1)/2] = new Woman(name);
                } else {
                    Man m = new Man((id-1)/2, name);
                    men.add(m);
                    manArray[(id-1)/2] = m;
                }
            } else {
                int id = Integer.parseInt(line.substring(0, line.indexOf(':')));
                String[] strprefs = line.substring(line.indexOf(':') + 2).split(" ");
                int[] prefs = new int[strprefs.length];
                if(id % 2 != 0) {
                    for(int i=0; i<prefs.length; i++) {
                        prefs[i] = (Integer.parseInt(strprefs[i])-1)/2;
                    }
                    manArray[(id-1)/2].preferences = prefs;
                } else {
                    for(int i=0; i<prefs.length; i++) {
                        prefs[(Integer.parseInt(strprefs[i])-1)/2] = i;
                    }
                    women[(id-1)/2].preferences = prefs;
                }
            }
        }*/
    }

    private static void matchMake() {
        while(men.size() > 0) {
           Man m = men.pop();
           Woman w = women[m.preferences[m.currentWoman++]];
           if(w.man == null) {
               w.man = m;
               m.woman = w;
           } else if(w.preferences[w.man.id] > w.preferences[m.id]) {
               Man tmp = w.man;
               w.man = m;
               m.woman = w;
               men.add(tmp);
           }else {
               men.add(m);
           }
        }
        for(int i=0; i<manArray.length; i++) {
            System.out.println(manArray[i].name + " -- " + manArray[i].woman.name);
        }
    }

    private static class Man {
        int[] preferences;
        int currentWoman;
        int id;
        Woman woman;
        String name;

        public Man(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    private static class Woman {
        int[] preferences;
        Man man;
        String name;

        public Woman(String name) {
            this.name = name;
        }
    }
}



