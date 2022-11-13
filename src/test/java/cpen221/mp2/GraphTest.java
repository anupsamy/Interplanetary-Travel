package cpen221.mp2;

import cpen221.mp2.graph.Edge;
import cpen221.mp2.graph.Graph;
import cpen221.mp2.graph.ImGraph;
import cpen221.mp2.graph.Vertex;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GraphTest {

    @Test
    public void testCreateGraph() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        assertEquals(21, g.pathLength(g.shortestPath(v3, v4)));
    }

    @Test
    public void testPath() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex v5 = new Vertex(5, "E");
        Vertex v6 = new Vertex(6, "F");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v1, v4, 9);
        Edge<Vertex> e3 = new Edge<>(v2, v6, 2);
        Edge<Vertex> e4 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e5 = new Edge<>(v3, v5, 4);
        Edge<Vertex> e6 = new Edge<>(v4, v5, 8);
        Edge<Vertex> e7 = new Edge<>(v4, v6, 3);
        Edge<Vertex> e8 = new Edge<>(v5, v6, 3);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);

        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);
        g.addEdge(e6);
        g.addEdge(e7);
        g.addEdge(e8);

        assertEquals(10, g.pathLength(g.shortestPath(v3, v4)));
    }

    @Test
    public void testShortestPath() {
        Vertex v1 = new Vertex(1, "A");

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);

        List<Vertex> expected = new ArrayList<>();
        expected.add(v1);

        assertEquals(expected, g.shortestPath(v1, v1));
    }

    @Test
    public void testShortestPath2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);

        List<Vertex> expected = new ArrayList<>();

        assertEquals(expected, g.shortestPath(v1, v2));
    }

    @Test
    public void testEdge() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex v5 = new Vertex(5, "E");
        Vertex v6 = new Vertex(6, "F");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v1, v4, 9);
        Edge<Vertex> e3 = new Edge<>(v2, v6, 2);
        Edge<Vertex> e4 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e5 = new Edge<>(v3, v5, 4);
        Edge<Vertex> e6 = new Edge<>(v4, v5, 8);
        Edge<Vertex> e7 = new Edge<>(v4, v6, 3);
        Edge<Vertex> e8 = new Edge<>(v5, v6, 3);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);

        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);
        g.addEdge(e6);
        g.addEdge(e7);
        g.addEdge(e8);

        assertEquals(e1, g.getEdge(v1, v2));
    }

    @Test
    public void testEdge2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 6);
        Edge<Vertex> e2 = new Edge<>(v1, v3, 9);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);
        g.addEdge(e2);

        assertEquals(null, g.getEdge(v2, v3));
    }

    @Test
    public void testNeighbours() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex v5 = new Vertex(5, "E");
        Vertex v6 = new Vertex(6, "F");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v1, v4, 9);
        Edge<Vertex> e3 = new Edge<>(v2, v6, 2);
        Edge<Vertex> e4 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e5 = new Edge<>(v3, v5, 4);
        Edge<Vertex> e6 = new Edge<>(v4, v5, 8);
        Edge<Vertex> e7 = new Edge<>(v4, v6, 3);
        Edge<Vertex> e8 = new Edge<>(v5, v6, 3);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);

        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);
        g.addEdge(e6);
        g.addEdge(e7);
        g.addEdge(e8);

        Map<Vertex, Edge> expected = new HashMap<>();
        expected.put(v2, e4);
        expected.put(v5, e5);
        expected.put(v6, e8);

        assertEquals(expected, g.getNeighbours(v3, 9));
    }

    @Test
    public void testNeighbours2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 5);

        Map<Vertex, Edge> expected = new HashMap<>();

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);

        assertEquals(expected, g.getNeighbours(v1, 2));
    }

    @Test
    public void testDiameter() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex v5 = new Vertex(5, "E");

        Edge<Vertex> e1 = new Edge(v1, v2, 12);
        Edge<Vertex> e2 = new Edge(v1, v3, 6);
        Edge<Vertex> e3 = new Edge(v1, v4, 10);
        Edge<Vertex> e4 = new Edge(v1, v5, 25);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);

        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);

        assertEquals(37, g.diameter());
    }

    @Test
    public void testCenter() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex v5 = new Vertex(5, "E");
        Vertex v6 = new Vertex(6, "F");

        Edge<Vertex> e1 = new Edge(v1, v2, 12);
        Edge<Vertex> e2 = new Edge(v1, v3, 6);
        Edge<Vertex> e3 = new Edge(v1, v4, 10);
        Edge<Vertex> e4 = new Edge(v1, v5, 25);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);

        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);

        assertEquals(v1, g.getCenter());
    }

    @Test
    public void testCenter2() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge(v1, v2, 12);
        Edge<Vertex> e2 = new Edge(v1, v3, 12);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);

        g.addEdge(e1);
        g.addEdge(e2);

        assertEquals(v1, g.getCenter());
    }

    @Test
    public void noEdge() {
        Vertex v1 = new Vertex(1, "A");

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);

        List<Vertex> test = new ArrayList<Vertex>();
        test.add(v1);

        assertEquals(0, g.pathLength(test));
    }

    @Test
    public void Span() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");

        Edge<Vertex> e1 = new Edge(v1, v2, 12);
        Edge<Vertex> e2 = new Edge(v1, v3, 12);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);

        g.addEdge(e1);
        g.addEdge(e2);

        HashSet<Graph<Vertex, Edge<Vertex>>> test = new HashSet<Graph<Vertex, Edge<Vertex>>>();
        test.add(g);

        assertEquals(test, g.minimumSpanningComponents(1));
    }
}
