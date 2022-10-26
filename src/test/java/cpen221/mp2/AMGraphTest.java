package cpen221.mp2;
import cpen221.mp2.graph.ALGraph;
import cpen221.mp2.graph.Edge;
import cpen221.mp2.graph.Vertex;
import org.junit.jupiter.api.Test;
import cpen221.mp2.graph.AMGraph;

import static org.junit.jupiter.api.Assertions.*;

public class AMGraphTest {
    @Test
    public void testCreateAMGraph() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        AMGraph g = new AMGraph(4);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        assertTrue(g.edge(e2));
    }

    @Test
    public void testVertexAMGraph() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex v5 = new Vertex(4, "D");
        Vertex v6 = new Vertex(5, "T");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        AMGraph g = new AMGraph(4);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        assertTrue(g.edge(e2));
        assertFalse(g.addVertex(v5));
        assertFalse(g.addVertex(v4));
        assertFalse(g.addVertex(v6));
        assertFalse(g.vertex(v6));
        assertTrue(g.vertex(v1));
    }

    @Test
    public void testEdgeAMGraph() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex v5 = new Vertex(4, "D");
        Vertex v6 = new Vertex(5, "T");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);
        Edge<Vertex> e4 = new Edge<>(v5, v6, 4);
        Edge<Vertex> e5 = new Edge<>(v2, v4, 4);



        AMGraph g = new AMGraph(4);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        assertFalse(g.addEdge(e4));
        assertFalse(g.edge(e4));
        assertFalse(g.edge(e5));
        assertTrue(g.edge(v1, v2));
        assertFalse(g.edge(v2, v4));
        assertFalse(g.edge(v5, v6));
        assertEquals(5, g.edgeLength(v1, v2));
        assertEquals(0, g.edgeLength(v2, v4));
        assertEquals(0, g.edgeLength(v5, v6));
    }
}
