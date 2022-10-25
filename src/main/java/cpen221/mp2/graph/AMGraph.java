package cpen221.mp2.graph;

public class AMGraph<V extends Vertex, E extends Edge<V>> implements MGraph<V, E> {

    private int[][] adjMatrix;
//    private int numVertices;
//    private int numEdges;
    private int maxVertices;


    /**
     * Create an empty graph with an upper-bound on the number of vertices
     * @param maxVertices is greater than 1
     */
    public AMGraph(int maxVertices) {

        this.maxVertices = maxVertices;
        this.adjMatrix = new int[maxVertices][maxVertices];
//        this.numVertices = 0;
//        this.numEdges = 0;
//        this.vertices = new Vertex[maxVertices];
//        this.edges = new Edge[maxVertices][maxVertices];
    }

    public boolean addVertex(V v){

    }

}
