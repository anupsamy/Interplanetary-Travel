package cpen221.mp2.graph;

import javax.swing.*;
import java.util.*;

/**
 * Represents a graph with vertices of type V.
 *
 * @param <V> represents a vertex type
 */
public class Graph<V extends Vertex, E extends Edge<V>> extends ALGraph<V,E> implements ImGraph<V, E>, MGraph<V, E> {

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
     * Precondition: The given vertices are connected by an edge in the graph
     *
     * @param v1 one end of the edge
     * @param v2 the other end of the edge
     * @return the edge connecting v1 and v2
     */
    @Override
    public E getEdge(V v1, V v2) {

        Set<E> edgeSet = this.allEdges();

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
     * @param source the start Vertex
     * @param sink the end Vertex
     * @return the vertices, in order, on the shortest path from source to sink (both end points are part of the list)
     * returns list with one element representing Vertex is the source and sink are the same Vertex
     * returns an empty list if there is no connection to the sink Vertex
     */
    @Override
    public List<V> shortestPath(V source, V sink) {
        HashMap<V, V> recentPred = new HashMap<>();
        Map<V, E> neighbours;
        Set<V> visitedV = new HashSet<>();
        HashMap<V, Integer> currentVs = new HashMap<>();
        HashMap<V, Integer> distToNode = new HashMap<>();
        List<V> dijkstraPath = new ArrayList<>();

        if (source.equals(sink)) {
            dijkstraPath.add(source);
            return dijkstraPath;
        }

        distToNode.put(source, 0);

        Object[] vertices = allVertices().toArray();
        for (int i = 0; i < vertices.length; i++) {
            if (!vertices[i].equals(source)) {
                distToNode.put((V) vertices[i], Integer.MAX_VALUE);
            }
        }

        currentVs.put(source, 0);

        while (currentVs.size() > 0) {

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

            visitedV.add(minimum);

            neighbours = getNeighbours(minimum);
            for (V key : neighbours.keySet()) {
                if (!visitedV.contains(key)) {

                    int distance = distToNode.get(minimum) + getEdge(minimum, key).length();

                    if (distance < distToNode.get(key)) {
                        distToNode.put(key, distance);
                        recentPred.put(key, minimum);
                        currentVs.put(key, distance);
                    }
                }
            }
        }

        if (distToNode.get(sink) == Integer.MAX_VALUE) {
            return dijkstraPath;
        }

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
     * @return the length of path, or 0 if the path is empty
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

        List<V> vertices = new ArrayList<>(allVertices());
        for (V vertex : vertices) {
            List<V> path = shortestPath(v, vertex);
            if (pathLength(path) <= range) {
                validV.addAll(path);
                validV.remove(v);
            }
        }

        List<V> validVList = new ArrayList<>(validV);
        List<E> lastEdge = new ArrayList<>();

        for (V value : validVList) {
            List<V> path = shortestPath(v, value);
            E edge = getEdge(path.get(path.size() - 2), path.get(path.size() - 1));
            lastEdge.add(edge);
        }

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
     * @param k >= number of distinct connected components
     * @return a set of graph partitions such that a vertex in one partition
     * is no closer to a vertex in a different partition than it is to a vertex
     * in its own partition.
     */
    @Override
    public Set<ImGraph<V, E>> minimumSpanningComponents(int k) {
        Set<ImGraph<V, E>> kConnected = new HashSet<>();
        int totalVertices = allVertices().size();
        ImGraph<V, E> kGraph;

        if (k == 1) {
            ArrayList<HashSet<V>> vertexGroups = new ArrayList<>();
            ArrayList<V> allVertices = new ArrayList<>(allVertices());
            ArrayList<E> allEdges = new ArrayList<>(allEdges());
            Set<E> addedEdges = new HashSet<>();
            Graph<V, E> temp = new Graph<>();

            //First we create an ArrayList, where each element represents a Set that contains one unique vertex
            for (V v : allVertices) {
                temp.addVertex(v);
                HashSet<V> vertex = new HashSet<>();
                vertex.add(v);
                vertexGroups.add(vertex);
            }

            while (addedEdges.size() < allVertices().size() - 1) {
                int state = 0;

                int min = allEdges.get(0).length();
                E smallestEdge = allEdges.get(0);
                for (E allEdge : allEdges) {
                    if (allEdge.length() < min) {
                        min = allEdge.length();
                        smallestEdge = allEdge;
                    }
                }

                //Only continue if the vertices of our smallest edge are not both contained in a singular set
                for (HashSet<V> vertexGroup : vertexGroups) {
                    if (vertexGroup.contains(smallestEdge.v1()) && vertexGroup.contains(smallestEdge.v2())) {
                        state = 1;
                    }
                }

                if (state == 0) {

                    //Passes the condition, so we add it to our 'addedEdges' set
                    addedEdges.add(smallestEdge);
                    temp.addEdge(smallestEdge);
                    HashSet<V> merged = new HashSet<>();

                    //Combines the two sets containing each vertex of our smallest edge into one set containing both vertex's
                    for (int j = 0; j < vertexGroups.size(); j++) {
                        if (vertexGroups.get(j).contains(smallestEdge.v1()) || vertexGroups.get(j).contains(smallestEdge.v2())) {
                            merged.addAll(vertexGroups.get(j));
                            vertexGroups.remove(vertexGroups.get(j));
                            j--;
                        }
                    }
                    vertexGroups.add(merged);
                }

                //We remove this edge from our allEdges list, whether it is valid or not
                allEdges.remove(smallestEdge);
            }

            kGraph = temp;
            kConnected.add(kGraph);
        }

        if (k == totalVertices) {

            ArrayList<V> vertices = new ArrayList<>(allVertices());

            for (V vertex : vertices) {
                Graph<V, E> temp = new Graph<>();
                temp.addVertex(vertex);
                kGraph = temp;
                kConnected.add(kGraph);
            }
        }

        if (k >= 2 && k < totalVertices) {
            ArrayList<HashSet<V>> vertexGroups = new ArrayList<>();
            ArrayList<V> allVertices = new ArrayList<>(allVertices());
            ArrayList<E> allEdges = new ArrayList<>(allEdges());
            Set<E> addedEdges = new HashSet<>();


            //First we create an ArrayList, where each element represents a Set that contains one unique vertex
            for (V v : allVertices) {
                HashSet<V> vertex = new HashSet<>();
                vertex.add(v);
                vertexGroups.add(vertex);
            }

            while (vertexGroups.size() != k) {
                int state = 0;

                int min = allEdges.get(0).length();
                E smallestEdge = allEdges.get(0);
                for (E allEdge : allEdges) {
                    if (allEdge.length() < min) {
                        min = allEdge.length();
                        smallestEdge = allEdge;
                    }
                }

                //Only continue if the vertices of our smallest edge are not both contained in a singular set
                for (HashSet<V> vertexGroup : vertexGroups) {
                    if (vertexGroup.contains(smallestEdge.v1()) && vertexGroup.contains(smallestEdge.v2())) {
                        state = 1;
                    }
                }

                if (state == 0) {

                    //Passes the condition, so we add it to our 'addedEdges' set
                    addedEdges.add(smallestEdge);
                    HashSet<V> merged = new HashSet<>();

                    //Combines the two sets containing each vertex of our smallest edge into one set containing both vertex's
                    for (int j = 0; j < vertexGroups.size(); j++) {
                        if (vertexGroups.get(j).contains(smallestEdge.v1()) || vertexGroups.get(j).contains(smallestEdge.v2())) {
                            merged.addAll(vertexGroups.get(j));
                            vertexGroups.remove(vertexGroups.get(j));
                            j--;
                        }
                    }
                    vertexGroups.add(merged);
                }

                //We remove this edge from our allEdges list, whether it is valid or not
                allEdges.remove(smallestEdge);
            }

            List<E> edges = new ArrayList<>(addedEdges);

            for (HashSet<V> vertexGroup : vertexGroups) {
                Graph<V, E> temp = new Graph<>();
                Object[] group = vertexGroup.toArray();
                for (Object o : group) {
                    temp.addVertex((V) o);
                }

                for (E edge : edges) {
                    temp.addEdge(edge);
                }

                kGraph = temp;
                kConnected.add(kGraph);
            }
        }
        return kConnected;
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
        int min = Integer.MAX_VALUE;
        V center = null;

        Set<V> largestConnected = new HashSet<>();
        for (V vertex : allVertices()) {
            Set<V> connected = new HashSet<>();
            connected.add(vertex);
            for (V vertex2 : allVertices()) {
                if (vertex != vertex2) {
                    List<V> path = shortestPath(vertex, vertex2);
                    if (pathLength(path) != 0) {
                        connected.add(vertex2);
                    }
                }
            }
            if (connected.size() > largestConnected.size()) {
                largestConnected = connected;
            }
        }

        for (V vertex : largestConnected) {
            int max = 0;
            for (V vertex2 : largestConnected) {
                if (vertex != vertex2) {
                    List<V> path = shortestPath(vertex, vertex2);
                    if (pathLength(path) > max) {
                        max = pathLength(path);
                    }
                }
            }
            if (max < min) {
                min = max;
                center = vertex;
            }
        }
        return center;
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
