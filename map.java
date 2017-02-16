import java.io.*;
import java.util.*;

public class map {

    private int city, road, broken;
    private String source, destination;
    private String map[][];
    private ArrayList<String> cities;
    private Queue<String> queue;
    private Queue<Node> nodeQueue;
    private ArrayList<String> visited;
    private Queue<String> brokenQueue;
    private Queue<Node> brokenNodeQueue;
    private ArrayList<String> brokenVisited;
    private Stack<Node> stack;
    private Stack<String> rStack;
    private ArrayList<String> onStack;
    private ArrayList<String> stackVisited;
    private Stack<String> brStack;
    private ArrayList<String> onBStack;
    private ArrayList<String> bStackVisited;
    private ArrayList<String> sPath;
    private int val = 0;

    public map() {

        Scanner s = new Scanner(System.in);
        city = s.nextInt();
        road = s.nextInt();
        s.nextLine();
        source = s.nextLine();
        destination = s.nextLine();
        broken = s.nextInt();
        s.nextLine();
        map = new String[city + 1][city + 1];
        cities = new ArrayList<String>();
        queue = new LinkedList<String>();
        nodeQueue = new LinkedList<Node>();
        visited = new ArrayList<String>();
        brokenQueue = new LinkedList<String>();
        brokenNodeQueue = new LinkedList<Node>();
        brokenVisited = new ArrayList<String>();
        stack = new Stack<Node>();
        stackVisited = new ArrayList<String>();
        rStack = new Stack<String>();
        onStack = new ArrayList<String>();
        bStackVisited = new ArrayList<String>();
        brStack = new Stack<String>();
        onBStack = new ArrayList<String>();
        sPath = new ArrayList<String>();

        for (int i = 0; i < map.length; ++i) {
            Arrays.fill(map[i], "0");
        }
        //printMap();
        int count = 1;
        for (int i = 0; i < broken; ++i) {
            StringTokenizer val = new StringTokenizer(s.nextLine(), ",");
            String c1 = val.nextToken();
            String c2 = val.nextToken();
            if (!cities.contains(c1)) {
                cities.add(c1);
                map[count][0] = c1;
                map[0][count] = c1;
                ++count;
            }
            if (!cities.contains(c2)) {
                cities.add(c2);
                map[count][0] = c2;
                map[0][count] = c2;
                ++count;
            }
            addBrokenEdge(c1, c2);
            addBrokenEdge(c2, c1);
            //System.out.println(c1+c2);
        }

        for (int i = 0; i < (road - broken); ++i) {
            //System.out.print(count);
            StringTokenizer val = new StringTokenizer(s.nextLine(), ",");
            String c1 = val.nextToken();
            String c2 = val.nextToken();
            //           System.out.println(c1+" "+c2);
            if (!cities.contains(c1)) {
                cities.add(c1);
                map[count][0] = c1;
                map[0][count] = c1;
                ++count;
            }
            if (!cities.contains(c2)) {
                cities.add(c2);
                map[count][0] = c2;
                map[0][count] = c2;
                ++count;
            }
            addEdge(c1, c2);
            addEdge(c2, c1);
            //System.out.println(c1+c2);
        }
        // System.out.println(count);
        //printMap();
        queue.add(source);
        visited.add(source);
        nodeQueue.add(new Node(source, 0, null));
        brokenQueue.add(source);
        brokenVisited.add(source);
        brokenNodeQueue.add(new Node(source, 0, null));
        stack.push(new Node(source, 0, null));
        stackVisited.add(source);
        solve();
        //solveWithBroken();
        //solveForAll();
        solveForAllBrokenRecursive(source, destination);
        solveForAllRecursive(source, destination);
       //System.out.println(minCost);
        /*Object array[]=cities.toArray();
         for(int i=0;i<array.length;++i)
         System.out.println(array[i]);*/

        //System.out.println(city+" "+road+" "+broken);
        //System.out.println(source+" "+destination);
    }

    public boolean isBroken(Stack<String> s) {
        String current = null;
        if (!s.isEmpty()) {
            current = s.pop();
        }
        while (!s.isEmpty()) {
            String next = s.pop();
            for (int i = 0; i < map.length; ++i) {
                if (map[0][i].equals(current)) {
                    for (int j = 0; j < map.length; ++j) {
                        if (map[j][0].equals(next)) {
                            if (map[i][j].equals("-1")) {
                                return true;
                            }
                        }
                    }
                }
            }
            current = next;
        }
        return false;
    }

    public void solve() {
        Node start, current, previous;
        current = new Node(null, 0, null);
        while (!queue.isEmpty()) {
            String temp = queue.remove();
            current = nodeQueue.remove();
            previous = current.previous;
            //System.out.println(temp);
            if (temp.equals(source)) {
                start = current;
                previous = start;
            }
            if (!temp.equals(destination)) {
                //visited.add(temp);
                for (int i = 0; i < map.length; ++i) {
                    if (map[0][i].equals(temp)) {
                        for (int j = 0; j < map[0].length; ++j) {
                            if (map[i][j].equals("1")) {
                                if (!visited.contains(map[j][0])) {
                                    queue.add(map[j][0]);
                                    visited.add(map[j][0]);
                                    Node n = new Node(map[j][0], current.cost + 1, current);
                                    nodeQueue.add(n);
                                }
                            }
                        }
                    }
                }
            } else {
                // minCost++;
                ArrayList<String> names = new ArrayList<String>();
                System.out.print("Shortest Path Cost: " + current.cost);
                while (!current.name.equals(source)) {
                    //System.out.println(current.name);
                    names.add(current.name);
                    current = current.previous;
                }
                //System.out.println(source);
                names.add(source);
                Collections.reverse(names);
                System.out.println(names);
                sPath = names;
                break;
            }
        }
    }


    public void solveForAllRecursive(String s, String t) {
        rStack.push(s);
        onStack.add(s);
        if (s.equals(t)) {
            //Collections.reverse(rStack);
            //rStack.pop();
            if (val != 0) {
                System.out.printf("Other paths with cost: " + (rStack.size() - 1) + rStack + "\n");
            }
            ++val;
        } else {
            for (int i = 0; i < map.length; ++i) {
                if (map[0][i].equals(s)) {
                    for (int j = 0; j < map[0].length; ++j) {
                        if (map[i][j].equals("1")) {
                            if (!onStack.contains(map[j][0])) {
                                solveForAllRecursive(map[j][0], t);
                            }
                        }
                    }
                }
            }
        }
        rStack.pop();
        onStack.remove(s);
    }

    public void solveForAllBrokenRecursive(String s, String t) {
        brStack.push(s);
        onBStack.add(s);
        if (s.equals(t)) {
            //Collections.reverse(rStack);
            //rStack.pop();
            if (isBroken((Stack<String>) brStack.clone())) {
                System.out.printf("Broken paths with cost: " + (brStack.size() - 1) + brStack + "\n");
            }
        } else {
            for (int i = 0; i < map.length; ++i) {
                if (map[0][i].equals(s)) {
                    for (int j = 0; j < map[0].length; ++j) {
                        if (map[i][j].equals("1") || map[i][j].equals("-1")) {
                            if (!onBStack.contains(map[j][0])) {
                                solveForAllBrokenRecursive(map[j][0], t);
                            }
                        }
                    }
                }
            }
        }
        brStack.pop();
        onBStack.remove(s);
    }

    

    public void addBrokenEdge(String c1, String c2) {
        for (int i = 0; i < map.length; ++i) {
            if (map[i][0].equals(c1)) {
                for (int j = 1; j < map[0].length; ++j) {
                    if (map[0][j].equals(c2)) {
                        map[i][j] = "-1";
                    }
                }
            }
        }
    }

    public void addEdge(String c1, String c2) {
        for (int i = 0; i < map.length; ++i) {
            if (map[i][0].equals(c1)) {
                for (int j = 1; j < map[0].length; ++j) {
                    if (map[0][j].equals(c2)) {
                        map[i][j] = "1";
                    }
                }
            }
        }
    }

    public void printMap() {
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map.length; ++j) {
                System.out.print(map[i][j] + "    ");
            }
            System.out.printf("\n");
        }

    }

}
