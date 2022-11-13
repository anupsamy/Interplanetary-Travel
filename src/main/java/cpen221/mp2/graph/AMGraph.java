package cpen221.mp2.graph;

import java.util.*;

public class AMGraph<V extends Vertex, E extends Edge<V>> implements MGraph<V, E> {
    private Edge[][] adjMatrix;
    private ArrayList<V> vertices;
    private int maxVertices;

    /*
    Representation Invariant:
        The number of Vertex instances in vertices does not exceed maxVertices.
        All Edge instances in adjMatrix correspond to Vertex instances in vertices.

    Abstraction Function:
        Represents a graph described using an adjacency matrix, where each entry represents an edge with coordinates
        corresponding to a set of indices in the list of vertices.

        Representation: AMGraph instance.
        Abstraction: A graph with a number of vertices and edges which connect certain vertices.
     */

    /**
     * Create an empty graph with an upper-bound on the number of vertices
     * @param maxVertices is greater than 1
     */
    public AMGraph(int maxVertices) {
        this.adjMatrix = new Edge[maxVertices][maxVertices];
        this.vertices = new ArrayList<>(maxVertices);
        this.maxVertices = maxVertices;
    }


    /**
     * Add a vertex to the graph
     *
     * @param v vertex to add
     * @return true if the vertex was added successfully and false otherwise
     */
    public boolean addVertex(V v) {
        if (vertices.contains(v)) {
            return false;
        } else if (vertices.size() == maxVertices) {
            return false;
        } else {
            vertices.add(v);
            checkRep();
            return true;
        }
    }

    /**
     * Check if a vertex is part of the graphg
     *
     * @param v vertex to check in the graph
     * @return true of v is part of the graph and false otherwise
     */
    @Override
    public boolean vertex(V v) {
        return vertices.contains(v);
    }

    /**
     * Add an edge of the graph
     *
     * @param e the edge to add to the graph
     * @return true if the edge was successfully added and false otherwise
     */
    @Override
    public boolean addEdge(E e) {
        if (vertices.contains(e.v1()) && vertices.contains(e.v2())) {
            adjMatrix[vertices.indexOf(e.v1())][vertices.indexOf(e.v2())] = e;
            adjMatrix[vertices.indexOf(e.v2())][vertices.indexOf(e.v1())] = e;
            checkRep();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if an edge is part of the graph
     *
     * @param e the edge to check in the graph
     * @return true if e is an edge in the graoh and false otherwise
     */
    @Override
    public boolean edge(E e) {
        if (vertices.contains(e.v1()) && vertices.contains(e.v2())) {
            if (adjMatrix[vertices.indexOf(e.v1())][vertices.indexOf(e.v2())] != null) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Check if v1-v2 is an edge in the graph
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return true of the v1-v2 edge is part of the graph and false otherwise
     */
    @Override
    public boolean edge(V v1, V v2) {
        if (vertices.contains(v1) && vertices.contains(v2)) {
            if (adjMatrix[vertices.indexOf(v1)][vertices.indexOf(v2)] != null) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Determine the length on an edge in the graph
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return the length of the v1-v2 edge if this edge is part of the graph
     */
    @Override
    //spec says returns 0 if edge length doesn't exist
    public int edgeLength(V v1, V v2) {
        if (vertices.contains(v1) && vertices.contains(v2)) {
            if (adjMatrix[vertices.indexOf(v1)][vertices.indexOf(v2)] != null) {
                return adjMatrix[vertices.indexOf(v1)][vertices.indexOf(v2)].length();
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * Obtain the sum of the lengths of all edges in the graph
     *
     * @return the sum of the lengths of all edges in the graph
     */
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
        return sum / 2;
    }

    /**
     * Remove an edge from the graph
     *
     * @param e the edge to remove
     * @return true if e was successfully removed and false otherwise
     */
    @Override
    public boolean remove(E e) {
        if (vertices.contains(e.v1()) && vertices.contains(e.v2())) {
            adjMatrix[vertices.indexOf(e.v1())][vertices.indexOf(e.v2())] = null;
            adjMatrix[vertices.indexOf(e.v2())][vertices.indexOf(e.v1())] = null;
            checkRep();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Remove a vertex from the graph
     *
     * @param v the vertex to remove
     * @return true if v was successfully removed and false otherwise
     */
    @Override
    public boolean remove(V v) {
        if (vertices.contains(v)) {
            vertices.remove(v);
            checkRep();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Obtain a set of all vertices in the graph.
     * Access to this set **should not** permit graph mutations.
     *
     * @return a set of all vertices in the graph
     */
    @Override
    public Set<V> allVertices() {
        Set<V> allVertices = (Set<V>) vertices;
        return allVertices;
    }

    /**
     * Obtain a set of all vertices incident on v.
     * Access to this set **should not** permit graph mutations.
     *
     * @param v the vertex of interest
     * @return all edges incident on v
     */
    @Override
    public Set<E> allEdges(V v) {
        Set<E> allEdges = new HashSet<>();
        if (vertices.contains(v)) {
            for (int i = 0; i < vertices.size(); i++) {
                if (adjMatrix[vertices.indexOf(v)][i] != null) {
                    allEdges.add((E) adjMatrix[vertices.indexOf(v)][i]);
                }
            }
            return allEdges;
        } else {
            return null;
        }
    }

    /**
     * Obtain a set of all edges in the graph.
     * Access to this set **should not** permit graph mutations.
     *
     * @return all edges in the graph
     */
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

    /**
     * Obtain all the neighbours of vertex v.
     * Access to this map **should not** permit graph mutations.
     *
     * @param v is the vertex whose neighbourhood we want.
     * @return a map containing each vertex w that neighbors v and the edge between v and w.
     */
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

    /**
     * Asserts that Representation Invariants have not been broken:
     *         The number of Vertex instances in vertices does not exceed maxVertices.
     *         All Edge instances in adjMatrix correspond to Vertex instances in vertices.
     */
    private void checkRep() {
        assert vertices.size() <= maxVertices;
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix.length; j++) {
                if (adjMatrix[i][j] != null) {
                    assert (vertices.contains(adjMatrix[i][j].v1()) && vertices.contains(adjMatrix[i][j].v2()));
                }
            }
        }
    }
}
