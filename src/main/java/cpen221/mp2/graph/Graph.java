package cpen221.mp2.graph;

import javax.swing.*;
import java.util.*;

/**
 * Represents a graph with vertices of type V.
 *
 * @param <V> represents a vertex type
 */
public class Graph<V extends Vertex, E extends Edge<V>> extends ALGraph<V,E> implements ImGraph<V, E>, MGraph<V, E> {
    // TODO: Implement this type
    // You can re-implement this graph, or use composition and
    // rely on your implementation of ALGraph or AMGraph

    /**
     * Constructor using ALGraph
     *
     */
    public Graph() {
        ALGraph<Vertex, Edge<Vertex>> graph = new ALGraph<Vertex, Edge<Vertex>>();
    }

    /**
     * Find the edge that connects two vertices if such an edge exists.
     * This method should not permit graph mutations.
     *
     * @param v1 one end of the edge
     * @param v2 the other end of the edge
     * @return the edge connecting v1 and v2
     */
//    @Override
//    public E getEdge(V v1, V v2) {
//        E toReturn = null;
//        var copy = new HashMap<>(getMap());
//        for (E e : copy.get(v1)) {
//            if (e.v1().equals(v2) || e.v2().equals(v2)) {
//                toReturn = e;
//            }
//        }
//        return toReturn;
//    }
    @Override
    public E getEdge(V v1, V v2) {

        //Get all edges in graph
        Set<E> edgeSet = this.allEdges();

        //Check whether edge is in set
        for(E edge : edgeSet) {
            if(edge.v1().equals(v1) && edge.v2().equals(v2)) {
                return (E) edge.clone();
            }
            else if(edge.v1().equals(v2) && edge.v2().equals(v1)) {
                return (E) edge.clone();
            }
        }

        return null;
    }

    /**
     * Compute the shortest path from source to sink
     *
     * @param source the start vertex
     * @param sink   the end vertex
     * @return the vertices, in order, on the shortest path from source to sink (both end points are part of the list)
     */
    @Override
    public List<V> shortestPath(V source, V sink) {
        HashMap<V, V> recentPred = new HashMap<>();
        Map<V, E> neighbours;
        Set<V> visitedV = new HashSet<>();
        HashMap<V, Integer> currentVs = new HashMap<>();
        HashMap<V, Integer> distToNode = new HashMap<>();
        List<V> dijkstraPath = new ArrayList<>();

        //returns a List with a single element (source/sink) if the source and sink are the same vertex
        if (source.equals(sink)) {
            dijkstraPath.add(source);
            return dijkstraPath;
        }

        //First we add the source and all other vertex's to the distToNode Map
        //We initialize the source vertex with distance = 0, and every other node with max distance
        distToNode.put(source, 0);

        Object[] vertices = allVertices().toArray();
        for (int i = 0; i < vertices.length; i++) {
            if (!vertices[i].equals(source)) {
                distToNode.put((V) vertices[i], Integer.MAX_VALUE);
            }
        }

        //We start with our source Vertex, hence we add it to our currentVs queue
        currentVs.put(source, 0);

        //Now we start the central loop
        //terminates once every Vertex in the currentV's has been removed
        while (currentVs.size() > 0) {

            //Remove the vertex with the minimum distance in the map
            int min = -1;
            V minimum = null;
            for (Map.Entry<V, Integer> entry : currentVs.entrySet()) {
                V key = entry.getKey();
                Integer dist = entry.getValue();
                if (min == -1 || dist <= min) {
                    min = dist;
                    minimum = key;
                }
            }

            currentVs.remove(minimum);

            //We also add this Vertex to the visited vertices list, so that we can avoid checking it multiple times
            visitedV.add(minimum);

            //Add the neighbouring vertices of the vertex we just removed from the map
            //we only add the vertices that we haven't already 'visited'
            neighbours = getNeighbours(minimum);
            for (V key : neighbours.keySet()) {
                if (!visitedV.contains(key)) {

                    //distance = distance(current -> removed Vertex) + distance(removed Vertex -> neighbour)
                    int distance = distToNode.get(minimum) + getEdge(minimum, key).length();

                    //checking to see if this new distance is shorter than current distance value that is set in map
                    //if it is, we update the distance value in our distToNode with the newer value,
                    //we change the most recent predecessor for the neighbour Vertex to the Vertex we just removed
                    //we also update the value in our currentV's map
                    if (distance < distToNode.get(key)) {
                        distToNode.put(key, distance);
                        recentPred.put(key, minimum);
                        currentVs.put(key, distance);
                    }
                }
            }
        }

        //return an empty list if there is no connection between source and sink
        if (distToNode.get(sink) == 0) {
            return dijkstraPath;
        }

        //Now we need to actually create the list of the shortest path
        //We do this using our recentPred map
        dijkstraPath.add(sink);

        while (!dijkstraPath.contains(source)) {
            for (Map.Entry<V, V> entry : recentPred.entrySet()) {
                V after = entry.getKey();
                V before = entry.getValue();
                if (after.equals(dijkstraPath.get(dijkstraPath.size() - 1))) {
                    dijkstraPath.add(before);
                    if (dijkstraPath.contains(source)) {
                        break;
                    }
                }
            }
        }

        Collections.reverse(dijkstraPath);
        return dijkstraPath;
    }

    /**
     * Compute the length of a given path
     *
     * @param path indicates the vertices on the given path
     * @return the length of path
     */
    @Override
    public int pathLength(List<V> path) {
        int length = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            length += getEdge(path.get(i), path.get(i + 1)).length();
        }
        return length;
    }

    /**
     * Obtain all vertices w that are no more than a <em>path distance</em> of range from v.
     *
     * @param v     the vertex to start the search from.
     * @param range the radius of the search.
     * @return a map where the keys are the vertices in the neighbourhood of v,
     * and the value for key w is the last edge on the shortest path
     * from v to w.
     */
    @Override
    public Map<V, E> getNeighbours(V v, int range) {
        Set<V> validV = new HashSet<>();
        Map<V, E> neighbours = new HashMap<>();

        //Creates a set that contains all the valid Vertices that is withing the range limit
        List<V> vertices = new ArrayList<>(allVertices());
        for (V vertex : vertices) {
            List<V> path = shortestPath(v, vertex);
            if (pathLength(path) <= range) {
                validV.addAll(path);
                validV.remove(v);
            }
        }

        //Creates a List of all the valid vertices within range from the set
        List<V> validVList = new ArrayList<>(validV);
        List<E> lastEdge = new ArrayList<>();

        //We calculate the last edge of the shortest path from the input to the entries in the List
        //Add the calculated edges to ArrayList lastEdge
        for (V value : validVList) {
            List<V> path = shortestPath(v, value);
            if (path.size() > 1) {
                E edge = getEdge(path.get(path.size() - 2), path.get(path.size() - 1));
                lastEdge.add(edge);
            } else if (path.size() == 1) {
                E edge = getEdge(v, path.get(0));
                lastEdge.add(edge);
            } else {
                E edge = getEdge(v, v);
                lastEdge.add(edge);
            }
        }

        //Both lists are the same size, we add it to the Map with the same iterator
        for (int i = 0; i < validVList.size(); i++) {
            neighbours.put(validVList.get(i), lastEdge.get(i));
        }

        return neighbours;
    }

    /**
     * Return a set with k connected components of the graph.
     *
     * <ul>
     * <li>When k = 1, the method returns one graph in the set, and that graph
     * represents the minimum spanning tree of the graph.
     * See: https://en.wikipedia.org/wiki/Minimum_spanning_tree</li>
     *
     * <li>When k = n, where n is the number of vertices in the graph, then
     * the method returns a set of n graphs, and each graph contains a
     * unique vertex and no edge.</li>
     *
     * <li>When k is in [2, n-1], the method partitions the graph into sub-graphs
     * such that for any two vertices V_i and V_j, if vertex V_i is in subgraph
     * G_a and vertex V_j is in subgraph G_b (a != b), and there is an edge
     * between V_i and V_j then there must exist some vertex V_k in G_a such
     * that the length of the edge between V_i and V_k is at most the length
     * of the edge between V_i and V_j.</li>
     * </ul>
     *
     * @param k
     * @return a set of graph partitions such that a vertex in one partition
     * is no closer to a vertex in a different partition than it is to a vertex
     * in its own partition.
     */
    @Override
    public Set<ImGraph<V, E>> minimumSpanningComponents(int k) {
        return null;
    }

    /**
     * Compute the diameter of the graph.
     * <ul>
     * <li>The diameter of a graph is the length of the longest shortest path in the graph.</li>
     * <li>If a graph has multiple components then we will define the diameter
     * as the diameter of the largest component.</li>
     * </ul>
     *
     * @return the diameter of the graph.
     */
    @Override
    public int diameter() {
        int diameter = 0;

        for (V vertex : allVertices()) {
            for (V vertex2 : allVertices()) {
                if (vertex != vertex2) {
                    List<V> path = shortestPath(vertex, vertex2);
                    if (pathLength(path) > diameter) {
                        diameter = pathLength(path);
                    }
                }
            }
        }
        return diameter;
    }

    /**
     * Compute the center of the graph.
     *
     * <ul>
     * <li>For a vertex s, the eccentricity of s is defined as the maximum distance
     * between s and any other vertex t in the graph.</li>
     *
     * <li>The center of a graph is the vertex with minimum eccentricity.</li>
     *
     * <li>If a graph is not connected, we will define the graph's center to be the
     * center of the largest connected component.</li>
     * </ul>
     *
     * @return the center of the graph.
     */
    @Override
    public V getCenter() {
        return null;
    }

    //// add all new code above this line ////

    /**
     * This method removes some edges at random while preserving connectivity
     * <p>
     * DO NOT CHANGE THIS METHOD
     * </p>
     * <p>
     * You will need to implement allVertices() and allEdges(V v) for this
     * method to run correctly
     *</p>
     * <p><strong>requires:</strong> this graph is connected</p>
     *
     * @param rng random number generator to select edges at random
     */
    public void pruneRandomEdges(Random rng) {
        class VEPair {
            V v;
            E e;

            public VEPair(V v, E e) {
                this.v = v;
                this.e = e;
            }
        }
        /* Visited Nodes */
        Set<V> visited = new HashSet<>();
        /* Nodes to visit and the cpen221.mp2.graph.Edge used to reach them */
        Deque<VEPair> stack = new LinkedList<VEPair>();
        /* Edges that could be removed */
        ArrayList<E> candidates = new ArrayList<>();
        /* Edges that must be kept to maintain connectivity */
        Set<E> keep = new HashSet<>();

        V start = null;
        for (V v : this.allVertices()) {
            start = v;
            break;
        }
        if (start == null) {
            // nothing to do
            return;
        }
        stack.push(new VEPair(start, null));
        while (!stack.isEmpty()) {
            VEPair pair = stack.pop();
            if (visited.add(pair.v)) {
                keep.add(pair.e);
                for (E e : this.allEdges(pair.v)) {
                    stack.push(new VEPair(e.distinctVertex(pair.v), e));
                }
            } else if (!keep.contains(pair.e)) {
                candidates.add(pair.e);
            }
        }
        // randomly trim some candidate edges
        int iterations = rng.nextInt(candidates.size());
        for (int count = 0; count < iterations; ++count) {
            int end = candidates.size() - 1;
            int index = rng.nextInt(candidates.size());
            E trim = candidates.get(index);
            candidates.set(index, candidates.get(end));
            candidates.remove(end);
            remove(trim);
        }
    }

}
