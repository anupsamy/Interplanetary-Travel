package cpen221.mp2.graph;

public class AMGraph<V extends Vertex, E extends Edge<V>> implements MGraph<V, E> {

    /**
     * Create an empty graph with an upper-bound on the number of vertices
     * @param maxVertices is greater than 1
     */
    public AMGraph(int maxVertices) {

        if (maxVertices <= 1) {
            throw new IllegalArgumentException("maxVertices is not greater than 1");
        }

    }

}
