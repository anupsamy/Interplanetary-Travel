package cpen221.mp2.graph;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class AMGraph<V extends Vertex, E extends Edge<V>> implements MGraph<V, E> {

    private Edge[][] adjMatrix;
    private ArrayList<V> vertices;
    private int maxVertices;


    /**
     * Create an empty graph with an upper-bound on the number of vertices
     * @param maxVertices is greater than 1
     */
    public AMGraph(int maxVertices) {
        this.adjMatrix = new Edge[maxVertices][maxVertices];
        this.vertices = new ArrayList<>();
        this.maxVertices = maxVertices;
    }

    //throws warning if num of vertices is equal to maxVertices
    public boolean addVertex(V v){
        if (vertices.contains(v)) {
            return false;
        } else {
            if (vertices.size() == maxVertices) {
                throw new RuntimeException("Number of vertices is equal to maxVertices");
            } else {
                vertices.add(v);
                return true;
            }
        }
    }

    @Override
    public boolean vertex(V v){
        return vertices.contains(v);
    }

    @Override
    public boolean addEdge(E e){
        if (vertices.contains(e.v1()) && vertices.contains(e.v2())){
            adjMatrix[vertices.indexOf(e.v1())][vertices.indexOf(e.v2())] = e;
            adjMatrix[vertices.indexOf(e.v2())][vertices.indexOf(e.v1())] = e;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean edge(E e){
        if (vertices.contains(e.v1()) && vertices.contains(e.v2())){
            if (adjMatrix[vertices.indexOf(e.v1())][vertices.indexOf(e.v2())] != null){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean edge(V v1, V v2){
        if (vertices.contains(v1) && vertices.contains(v2)){
            if (adjMatrix[vertices.indexOf(v1)][vertices.indexOf(v2)] != null){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    //spec says returns 0 if edge length doesn't exist
    public int edgeLength(V v1, V v2){
        if (vertices.contains(v1) && vertices.contains(v2)){
            if (adjMatrix[vertices.indexOf(v1)][vertices.indexOf(v2)] != null){
                return adjMatrix[vertices.indexOf(v1)][vertices.indexOf(v2)].length();
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    @Override
    public int edgeLengthSum() {
        return 0;
    }

    @Override
    public boolean remove(E e) {
        return false;
    }

    @Override
    public boolean remove(V v) {
        return false;
    }

    @Override
    public Set<V> allVertices() {
        return null;
    }

    @Override
    public Set<E> allEdges(V v) {
        return null;
    }

    @Override
    public Set<E> allEdges() {
        return null;
    }

    @Override
    public Map<V, E> getNeighbours(V v) {
        return null;
    }

}
