package cpen221.mp2.graph;

import java.util.*;

public class ALGraph<V extends Vertex, E extends Edge<V>> implements MGraph<V, E> {
    private Map<V, ArrayList<E>> adjList = new HashMap<>();

    // Rep invariant:
    //   if adjList contains more than one key, sum of ArrayList<E>.size() >= # of keys.
    //   for the list of Edge values, total sum of ArrayList<E>.size() % 2 = 0.
    //   ***add invariant stating that all Edge's correspond only to Vertex's within adjList***

    // Abstraction Function:
    //   represents the adjacency list graph describing
    //   where each entry represents a vertex and its respective edges

    /**
     * Creates an ALGraph object
     *
     * @return an empty ALGraph object
     */
    public ALGraph() {
        Map<Vertex, Edge> adjList = null;
    }

    /**
     * Add a vertex to the graph
     *
     * @param v vertex to add
     * @return true if the vertex was added successfully and false otherwise
     */
    @Override
    public boolean addVertex(V v){
        if (adjList.containsKey(v)){
            return false;
        }
        else{
            adjList.put(v, new ArrayList<>());
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
    public boolean vertex(V v){
        return adjList.containsKey(v);
    }

    /**
     * Add an edge of the graph
     *
     * @param e the edge to add to the graph, e.v1 and e.v2 are in adjList
     * @return true if the edge was successfully added and false otherwise
     */
    @Override
    public boolean addEdge(E e){
        if (adjList.containsKey(e.v1()) && adjList.containsKey(e.v2())){
            adjList.get(e.v1()).add(e);
            adjList.get(e.v2()).add(e);
            checkRep();
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Check if an edge is part of the graph
     *
     * @param e the edge to check in the graph
     * @return true if e is an edge in the graph and false otherwise
     */
    //add spec that edge has to exist in both v1 and v2 keys
    @Override
    public boolean edge(E e) {
        if (adjList.containsKey(e.v1()) && adjList.containsKey(e.v2())) {
            if (adjList.get(e.v1()).contains(e) && adjList.get(e.v2()).contains(e)){
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
    //add spec that edge has to exist in both v1 and v2 keys?
    @Override
    public boolean edge(V v1, V v2){
        if (adjList.containsKey(v1) && adjList.containsKey(v2)){
            for (E e : adjList.get(v1)){
                if (e.v1().equals(v2) || e.v2().equals(v2)){
                    return true;
                }
            }
            return false;
        } else{
            return false;
        }
    }

    /**
     * Determine the length on an edge in the graph
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return the length of the v1-v2 edge if this edge is part of the graph or 0 if edge
     * is not a part of graph
     */
    @Override
    public int edgeLength(V v1, V v2){
        if (adjList.containsKey(v1) && adjList.containsKey(v2)){
            for (E e : adjList.get(v1)){
                if (e.v1().equals(v2) || e.v2().equals(v2)){
                    return e.length();
                }
            }
        }
        return 0;
    }

    /**
     * Obtain the sum of the lengths of all edges in the graph
     *
     * @return the sum of the lengths of all edges in the graph, assuming there are 2 of each edge
     */
    @Override
    public int edgeLengthSum() {
        int sum = 0;
        for (V v : adjList.keySet()){
            for (E e : adjList.get(v)){
                sum += e.length();
            }
        }
        return sum/2;
    }

    /**
     * Remove an edge from the graph
     *
     * @param e the edge to remove
     * @return true if e was successfully removed and false otherwise
     */
    @Override
    public boolean remove(E e) {
        if (adjList.containsKey(e.v1()) && adjList.containsKey(e.v2())){
            if (adjList.get(e.v1()).contains(e) && adjList.get(e.v2()).contains(e)){
                adjList.get(e.v1()).remove(e);
                adjList.get(e.v2()).remove(e);
                checkRep();
                return true;
            } else {
                return false;
            }
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
        if (adjList.containsKey(v)){
            adjList.remove(v);
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
        return adjList.keySet();
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
        if (adjList.containsKey(v)){
            return new HashSet<>(adjList.get(v));
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
        for (V v : adjList.keySet()){
            allEdges.addAll(adjList.get(v));
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
        if (adjList.containsKey(v)){
            for (E e : adjList.get(v)){
                if (e.v1().equals(v)){
                    neighbours.put(e.v2(), e);
                } else {
                    neighbours.put(e.v1(), e);
                }
            }
            return neighbours;
        } else {
            return null;
        }
    }

    private void checkRep() {
        //Skips assert if any of the edge values have yet to be added**
        for (ArrayList<E> edge : adjList.values()) {
            if (edge.size() == 0) {
                return;
            }
        }

        if (adjList.size() > 1) {
            int sum = 0;
            for (ArrayList<E> edge : adjList.values()) {
                sum += edge.size();
            }
            assert (sum >= adjList.size());
            assert (sum % 2 == 0);
        }
    }
}
