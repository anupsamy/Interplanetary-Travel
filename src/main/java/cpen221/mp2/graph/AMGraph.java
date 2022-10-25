package cpen221.mp2.graph;

import java.util.*;

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
        this.vertices = new ArrayList<>(maxVertices);;
        this.maxVertices = maxVertices;
    }

    //throws warning if num of vertices is equal to maxVertices
    public boolean addVertex(V v){
        if (vertices.contains(v)) {
            return false;
        } else if (vertices.size() == maxVertices) {
            throw new RuntimeException("Number of vertices is equal to maxVertices");
        } else {
            vertices.add(v);
            return true;
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
        int sum = 0;
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                if (adjMatrix[i][j] != null) {
                    sum += adjMatrix[i][j].length();
                }
            }
        }
        return sum;
    }

    @Override
    public boolean remove(E e) {
        if (vertices.contains(e.v1()) && vertices.contains(e.v2())){
            adjMatrix[vertices.indexOf(e.v1())][vertices.indexOf(e.v2())] = null;
            adjMatrix[vertices.indexOf(e.v2())][vertices.indexOf(e.v1())] = null;
            return true;
        } else {
            return false;
        }
    }

    //there may be an issue where the edge for the vertex is still in the matrix
    //ill look into it later
    @Override
    public boolean remove(V v) {
        if (vertices.contains(v)){
            vertices.remove(v);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Set<V> allVertices() {
        Set<V> allVertices = (Set<V>) vertices;
        return allVertices;
    }

    @Override
    public Set<E> allEdges(V v) {
        Set<E> allEdges = new HashSet<>();
        if (vertices.contains(v)){
            for (int i = 0; i < vertices.size(); i++) {
                if (adjMatrix[vertices.indexOf(v)][i] != null){
                    allEdges.add((E) adjMatrix[vertices.indexOf(v)][i]);
                }
            }
            return allEdges;
        } else {
            return null;
        }
    }

    @Override
    public Set<E> allEdges() {
        Set<E> allEdges = new HashSet<>();
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                if (adjMatrix[i][j] != null){
                    allEdges.add((E) adjMatrix[i][j]);
                }
            }
        }
        return allEdges;
    }

    @Override
    public Map<V, E> getNeighbours(V v) {
        Map<V, E> neighbours = new HashMap<>();
        if (vertices.contains(v)){
            for (int i = 0; i < vertices.size(); i++) {
                if (adjMatrix[vertices.indexOf(v)][i] != null){
                    neighbours.put(vertices.get(i), (E) adjMatrix[vertices.indexOf(v)][i]);
                }
            }
            return neighbours;
        } else {
            return null;
        }
    }

}
