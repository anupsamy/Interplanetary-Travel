package cpen221.mp2;

import cpen221.mp2.graph.ALGraph;
import cpen221.mp2.graph.Edge;
import cpen221.mp2.graph.Vertex;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ALGraphTest {
    @Test
    public void testCreateALGraph() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex v5 = new Vertex(5, "E");
        Vertex v6 = new Vertex(6, "F");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);
        Edge<Vertex> e4 = new Edge<>(v1, v5, 2);
        Edge<Vertex> e5 = new Edge<>(v1, v3, 2);

        Set<Edge> allEdges = new HashSet<>();
        allEdges.add(e1);
        allEdges.add(e2);

        Map<Vertex, ArrayList<Edge>> mappy = new HashMap<>();
        mappy.put(v1, new ArrayList<>());
        mappy.get(v1).add(e1);
        mappy.get(v1).add(e3);
        mappy.get(v1).add(e4);
        mappy.get(v1).add(e5);
        mappy.put(v2, new ArrayList<>());
        mappy.get(v2).add(e1);
        mappy.get(v2).add(e2);
        mappy.put(v3, new ArrayList<>());
        mappy.get(v3).add(e2);
        mappy.get(v3).add(e5);
        mappy.put(v4, new ArrayList<>());
        mappy.get(v4).add(e3);
        mappy.put(v5, new ArrayList<>());
        mappy.get(v5).add(e4);

        ALGraph<Vertex, Edge<Vertex>> g = new ALGraph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);

        assertTrue(g.edge(e2));
        assertFalse(g.remove(e4));
        assertFalse(g.remove(e5));
        assertEquals(allEdges, g.allEdges(v2));
        assertNull(g.allEdges(v6));
        assertNull(g.getNeighbours(v6));
        assertEquals(mappy, g.getMap()); //check if this works
    }

    @Test
    public void testVertex() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex dupe = new Vertex(4, "D");
        Vertex v5 = new Vertex(5, "E");

        ALGraph<Vertex, Edge<Vertex>> g = new ALGraph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.remove(v2);

        assertFalse(g.vertex(v2));
        assertFalse(g.addVertex(dupe));
        assertFalse(g.remove(v5));
    }

    @Test
    public void testEdge () {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex v5 = new Vertex(5, "E");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);
        Edge<Vertex> e4 = new Edge<>(v1, v5, 3);

        ALGraph<Vertex, Edge<Vertex>> g = new ALGraph<>();

        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.remove(e2);

        assertFalse(g.edge(e2));
        assertFalse(g.addEdge(e4));
        assertFalse(g.edge(e4));
        assertTrue(g.edge(v1, v2));
        assertFalse(g.edge(v1, v5));
        assertFalse(g.edge(v1, v3));

        assertEquals(5, g.edgeLength(v1, v2));
        assertEquals(0, g.edgeLength(v1, v3));
        assertEquals(14, g.edgeLengthSum());

    }
}
