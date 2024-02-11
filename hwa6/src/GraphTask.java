import java.util.*;

/**
 * Container class to different classes, that makes the whole set of classes one class formally.
 *
 * Used sources:
 * https://youtu.be/thLQYBlz2DM
 * https://www.geeksforgeeks.org/bridge-in-a-graph/
 * https://cp-algorithms.com/graph/bridge-searching.html
 */
public class GraphTask {
    public static void main(String[] args) {
        new GraphTask().run();
    }

    public void run() {
        test1();
        test2();
        test3();
        test4();
        test5();
    }

    public void test1() {
        Graph graph = new Graph("G1");

        Vertex v2 = graph.createVertex("v2");
        Vertex v1 = graph.createVertex("v1");
        Vertex v0 = graph.createVertex("v0");

        graph.createArc("a0", v0, v1);

        graph.createArc("a1", v1, v2);
        graph.createArc("a2", v1, v0);

        graph.createArc("a3", v2, v1);

        System.out.println(graph);
        System.out.println(graph.findBridges());
    }

    public void test2() {
        Graph graph = new Graph("G2");

        Vertex v4 = graph.createVertex("v4");
        Vertex v3 = graph.createVertex("v3");
        Vertex v2 = graph.createVertex("v2");
        Vertex v1 = graph.createVertex("v1");
        Vertex v0 = graph.createVertex("v0");

        graph.createArc("a0", v0, v1);
        graph.createArc("a1", v0, v2);
        graph.createArc("a2", v0, v3);

        graph.createArc("a3", v1, v0);
        graph.createArc("a4", v1, v2);

        graph.createArc("a5", v2, v0);
        graph.createArc("a6", v2, v1);

        graph.createArc("a7", v3, v0);
        graph.createArc("a8", v3, v4);

        graph.createArc("a9", v4, v3);

        System.out.println(graph);
        System.out.println(graph.findBridges());
    }

    public void test3() {
        Graph graph = new Graph("G3");

        Vertex v5 = graph.createVertex("v5");
        Vertex v4 = graph.createVertex("v4");
        Vertex v3 = graph.createVertex("v3");
        Vertex v2 = graph.createVertex("v2");
        Vertex v1 = graph.createVertex("v1");
        Vertex v0 = graph.createVertex("v0");

        graph.createArc("a00", v0, v1);
        graph.createArc("a01", v0, v2);

        graph.createArc("a02", v1, v0);
        graph.createArc("a03", v1, v2);
        graph.createArc("a04", v1, v3);
        graph.createArc("a05", v1, v4);

        graph.createArc("a06", v2, v0);
        graph.createArc("a07", v2, v1);

        graph.createArc("a08", v3, v1);
        graph.createArc("a09", v3, v5);

        graph.createArc("a10", v4, v1);
        graph.createArc("a11", v4, v5);

        graph.createArc("a12", v5, v3);
        graph.createArc("a13", v5, v4);

        System.out.println(graph);
        System.out.println(graph.findBridges());
    }

    public void test4() {
        Graph graph = new Graph("G4");

        Vertex v8 = graph.createVertex("v8");
        Vertex v7 = graph.createVertex("v7");
        Vertex v6 = graph.createVertex("v6");
        Vertex v5 = graph.createVertex("v5");
        Vertex v4 = graph.createVertex("v4");
        Vertex v3 = graph.createVertex("v3");
        Vertex v2 = graph.createVertex("v2");
        Vertex v1 = graph.createVertex("v1");
        Vertex v0 = graph.createVertex("v0");

        graph.createArc("a00", v0, v1);
        graph.createArc("a01", v0, v2);

        graph.createArc("a02", v1, v0);
        graph.createArc("a03", v1, v2);
        graph.createArc("a04", v1, v3);

        graph.createArc("a05", v2, v0);
        graph.createArc("a06", v2, v1);
        graph.createArc("a07", v2, v3);
        graph.createArc("a08", v2, v5);

        graph.createArc("a09", v3, v1);
        graph.createArc("a10", v3, v2);
        graph.createArc("a11", v3, v4);

        graph.createArc("a12", v4, v3);

        graph.createArc("a13", v5, v2);
        graph.createArc("a14", v5, v6);
        graph.createArc("a15", v5, v8);

        graph.createArc("a16", v6, v5);
        graph.createArc("a17", v6, v7);

        graph.createArc("a18", v7, v6);
        graph.createArc("a19", v7, v8);

        graph.createArc("a20", v8, v5);
        graph.createArc("a21", v8, v7);

        System.out.println(graph);
        System.out.println(graph.findBridges());
    }

    public void test5() {
        Graph graph = new Graph("G5");
        graph.createRandomSimpleGraph(2000, 3500);

        long start = System.nanoTime();
        graph.findBridges();
        long end = System.nanoTime();

        long microseconds = (end - start) / 1000L;
        System.out.print("\nG5\n" + microseconds + "us");
    }

    class Vertex {
        private String id;
        private Vertex next;
        private Arc first;
        private int info = 0;
        private int disc = 0;
        private int low = 0;

        Vertex(String s, Vertex v, Arc e) {
            id = s;
            next = v;
            first = e;
        }

        Vertex(String s) {
            this(s, null, null);
        }

        @Override
        public String toString() {
            return id;
        }
    }

    /**
     * Arc represents one arrow in the graph.
     * Two-directional edges are represented by two Arc objects (for both directions).
     */
    class Arc {
        private String id;
        private Vertex target;
        private Arc next;
        private int info = 0;

        Arc(String s, Vertex v, Arc a) {
            id = s;
            target = v;
            next = a;
        }

        Arc(String s) {
            this(s, null, null);
        }

        @Override
        public String toString() {
            return id;
        }
    }

    class Graph {
        private String id;
        private Vertex first;
        private int info = 0;

        Graph(String s, Vertex v) {
            id = s;
            first = v;
        }

        Graph(String s) {
            this(s, null);
        }

        public Vertex createVertex(String vid) {
            Vertex res = new Vertex(vid);
            res.next = first;
            first = res;

            return res;
        }

        public void createArc(String aid, Vertex from, Vertex to) {
            Arc res = new Arc(aid);
            res.next = from.first;
            from.first = res;
            res.target = to;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            String separator = System.lineSeparator();

            builder.append(separator)
                   .append(id)
                   .append(separator);

            for (Vertex vertex = first; vertex != null; vertex = vertex.next) {
                builder.append(vertex)
                       .append(" -->");

                for (Arc a = vertex.first; a != null; a = a.next) {
                    builder.append(" ")
                           .append(a)
                           .append(String.format(" (%s->%s)", vertex, a.target));
                }

                builder.append(separator);
            }

            return builder.toString();
        }

        /**
         * Create a connected undirected random tree with n vertices.
         * Each new vertex is connected to some random existing vertex.
         *
         * @param n number of vertices added to this graph
         */
        public void createRandomTree(int n) {
            if (n <= 0) {
                return;
            }

            Vertex[] vertices = new Vertex[n];

            for (int i = 0; i < n; i++) {
                vertices[i] = createVertex("v" + (n - i));

                if (i > 0) {
                    int randomIndex = (int) (Math.random() * i);
                    Vertex randomVertex = vertices[randomIndex];

                    String toLabel = "a" + randomVertex.toString() + "_" + vertices[i].toString();
                    createArc(toLabel, randomVertex, vertices[i]);

                    String fromLabel = "a" + vertices[i].toString() + "_" + randomVertex;
                    createArc(fromLabel, vertices[i], randomVertex);
                }
            }
        }

        /**
         * Create an adjacency matrix of this graph.
         * Side effect: corrupts info fields in the graph.
         *
         * @return adjacency matrix
         */
        public int[][] createAdjMatrix() {
            info = 0;
            Vertex vertex = first;

            while (vertex != null) {
                vertex.info = info++;
                vertex = vertex.next;
            }

            int[][] result = new int[info][info];
            vertex = first;

            while (vertex != null) {
                int i = vertex.info;
                Arc arc = vertex.first;

                while (arc != null) {
                    int j = arc.target.info;
                    result[i][j]++;
                    arc = arc.next;
                }

                vertex = vertex.next;
            }

            return result;
        }

        /**
         * Create a connected simple (undirected, no loops, no multiple arcs) random graph with n vertices and m edges.
         *
         * @param n number of vertices
         * @param m number of edges
         */
        public void createRandomSimpleGraph(int n, int m) {
            if (n <= 0) {
                return;
            }

            if (n > 2500) {
                throw new IllegalArgumentException("Too many vertices: " + n);
            }

            if (m < n - 1 || m > n * (n - 1) / 2) {
                throw new IllegalArgumentException("Impossible number of edges: " + m);
            }

            first = null;
            createRandomTree(n); // n - 1 edges created here

            Vertex[] vertices = new Vertex[n];
            Vertex vertex = first;
            int c = 0;

            while (vertex != null) {
                vertices[c++] = vertex;
                vertex = vertex.next;
            }

            int[][] connected = createAdjMatrix();
            int edgeCount = m - n + 1; // remaining edges

            while (edgeCount > 0) {
                int i = (int) (Math.random() * n); // random source
                int j = (int) (Math.random() * n); // random target

                // no loops, no multiple edges, and not already created
                if (i == j || connected[i][j] != 0 || connected[j][i] != 0) {
                    continue;
                }

                Vertex vi = vertices[i];
                Vertex vj = vertices[j];

                String toEdge = "a" + vi + "_" + vj;
                createArc(toEdge, vi, vj);
                connected[i][j] = 1;

                String fromEdge = "a" + vj + "_" + vi;
                createArc(fromEdge, vj, vi);
                connected[j][i] = 1;

                edgeCount--;
            }
        }

        /**
         * Find all bridges in the graph using depth-first search.
         *
         * @return List of bridges (Arc objects) in the graph
         */
        public List<Arc> findBridges() {
            List<Arc> bridges = new ArrayList<>();

            findBridgesDFS(first, null, 1, bridges);

            return bridges;
        }

        /**
         * Finds all bridges in the graph using depth-first search.
         *
         * @param current currently handled vertex
         * @param parent  parent vertex of the current vertex
         * @param disc    discovery number of the current vertex
         * @param bridges list of bridges
         */
        private void findBridgesDFS(Vertex current, Vertex parent, int disc, List<Arc> bridges) {
            if (current == null) {
                return;
            }

            current.disc = disc;
            current.low = disc++;
            Arc arc = current.first;

            while (arc != null) {
                Vertex neighbor = arc.target;

                if (neighbor != parent) {
                    if (neighbor.disc > 0) {
                        current.low = Math.min(current.low, neighbor.disc);
                    } else {
                        findBridgesDFS(neighbor, current, disc, bridges);

                        current.low = Math.min(current.low, neighbor.low);

                        if (neighbor.low > current.disc) {
                            bridges.add(arc);
                            bridges.add(findReverseBridge(neighbor, current));
                        }
                    }
                }

                arc = arc.next;
            }
        }

        /**
         * Finds the reverse bridge connecting the given neighbor and current vertex.
         *
         * @param neighbor neighbor vertex
         * @param current  current vertex
         * @return reverse bridge
         * @throws RuntimeException if the reverse bridge is not found
         */
        private Arc findReverseBridge(Vertex neighbor, Vertex current) {
            for (Arc bridge = neighbor.first; bridge != null; bridge = bridge.next) {
                if (bridge.target == current) {
                    return bridge;
                }
            }

            throw new RuntimeException("Undirected graph should always have connection for both directions");
        }
    }
}