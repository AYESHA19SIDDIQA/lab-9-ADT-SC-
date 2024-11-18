package graph;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.Map;
import java.util.Collections;
/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {

    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override 
    public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }

    /*
     * Testing ConcreteVerticesGraph...
     */

    // Test the toString method for the graph
    @Test
    public void testToString() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        
        String expected = "A -> {B=5}\nB -> {}\n";
        assertEquals("toString should return the expected string representation of the graph",
                     expected, graph.toString());
    }

    /*
     * Testing Vertex operations...
     */

    // Test adding a vertex
    @Test
    public void testAddVertex() {
        Graph<String> graph = emptyInstance();
        assertTrue("expected add to return true for a new vertex", graph.add("A"));
        assertTrue("expected vertex 'A' to exist", graph.vertices().contains("A"));
    }

    // Test adding a duplicate vertex
    @Test
    public void testAddDuplicateVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertFalse("expected add to return false for duplicate vertex", graph.add("A"));
        assertEquals("expected only one vertex 'A'", 1, graph.vertices().size());
    }

    // Test removing a vertex
    @Test
    public void testRemoveVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertTrue("expected remove to return true for existing vertex", graph.remove("A"));
        assertFalse("expected vertex 'A' to no longer exist", graph.vertices().contains("A"));
    }

    // Test removing a non-existent vertex
    @Test
    public void testRemoveNonExistentVertex() {
        Graph<String> graph = emptyInstance();
        assertFalse("expected remove to return false for non-existent vertex", graph.remove("NonExistent"));
    }
    
    // Test adding and checking edges
    @Test
    public void testSetEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        assertEquals("expected set to return 0 for new edge", 0, graph.set("A", "B", 5));
        assertEquals("expected weight of edge A->B to be 5", Map.of("B", 5), graph.targets("A"));
    }

    // Test setting the same edge with a different weight (update edge)
    @Test
    public void testUpdateEdgeWeight() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5); // Add edge with weight 5
        int previousWeight = graph.set("A", "B", 10);  // Update weight
        assertEquals("expected previous weight to be 5", 5, previousWeight);
        assertEquals("expected updated edge weight to be 10", Map.of("B", 10), graph.targets("A"));
    }

    // Test removing an edge by setting its weight to 0
    @Test
    public void testRemoveEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5); // Add edge with weight 5
        
        int previousWeight = graph.set("A", "B", 0); // Remove the edge
        assertEquals("expected previous weight to be 5", 5, previousWeight);
        
        // Ensure the edge is removed
        assertTrue("expected no outgoing edges from A", graph.targets("A").isEmpty());
    }

    // Test sources and targets for directed edges
    @Test
    public void testSourcesAndTargets() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        
        graph.set("A", "B", 5);
        graph.set("C", "A", 10);
        
        // Test sources for vertex B
        Map<String, Integer> sourcesB = graph.sources("B");
        assertEquals("expected sources of B to be A with weight 5", Map.of("A", 5), sourcesB);
        
        // Test targets for vertex A
        Map<String, Integer> targetsA = graph.targets("A");
        assertEquals("expected targets of A to be B with weight 5", Map.of("B", 5), targetsA);
    }

    // Test removing a vertex and checking if its edges are removed
    @Test
    public void testRemoveVertexWithEdges() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        
        graph.set("A", "B", 5); // A -> B with weight 5
        graph.set("C", "A", 10); // C -> A with weight 10
        
        assertTrue("expected remove to return true for vertex A", graph.remove("A"));
        
        // Ensure A is removed and that edges to/from A are also removed
        assertFalse("expected vertex 'A' to no longer exist", graph.vertices().contains("A"));
        assertTrue("expected no sources for B", graph.sources("B").isEmpty());
        assertTrue("expected no targets for C", graph.targets("C").isEmpty());
    }
}
