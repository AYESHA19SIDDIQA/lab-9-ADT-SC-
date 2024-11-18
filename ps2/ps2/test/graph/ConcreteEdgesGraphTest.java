package graph;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.Map;
import java.util.Collections;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   We expect the toString method to return a string representation of the edges.
    //   The string should be formatted as "source -> target (weight)".
    
    @Test
    public void testToString() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        
        String expected = "A -> B (5)\n";
        assertEquals("toString should return the expected string representation of the graph",
                     expected, graph.toString());
    }
    
    /*
     * Testing Edge...
     */
    
    // Test that edges are created correctly with valid parameters
    @Test
    public void testEdgeCreation() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        
        // Create an edge from A to B with weight 3
        int previousWeight = graph.set("A", "B", 3);
        assertEquals("expected previous weight to be 0 for new edge", 0, previousWeight);
        
        // Check the edge weight and direction
        Map<String, Integer> targetsA = graph.targets("A");
        assertEquals("expected one edge from A to B with weight 3", Map.of("B", 3), targetsA);
    }
    
    // Test that an edge's weight can be updated
    @Test
    public void testEdgeWeightUpdate() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        
        // Add an edge A -> B with weight 5
        graph.set("A", "B", 5);
        
        // Update the weight of the edge A -> B to 10
        int previousWeight = graph.set("A", "B", 10);
        assertEquals("expected previous weight to be 5", 5, previousWeight);
        
        // Check the new weight
        Map<String, Integer> targetsA = graph.targets("A");
        assertEquals("expected updated weight of edge A -> B to be 10", Map.of("B", 10), targetsA);
    }
    
    // Test that an edge can be removed by setting its weight to 0
    @Test
    public void testEdgeRemoval() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        
        // Add an edge A -> B with weight 5
        graph.set("A", "B", 5);
        
        // Remove the edge by setting the weight to 0
        int previousWeight = graph.set("A", "B", 0);
        assertEquals("expected previous weight to be 5", 5, previousWeight);
        
        // Ensure the edge is removed
        Map<String, Integer> targetsA = graph.targets("A");
        assertTrue("expected A to have no outgoing edges", targetsA.isEmpty());
    }
    
    // Test removing a vertex and its edges
    @Test
    public void testRemoveVertexAndEdges() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        
        // Remove vertex A
        graph.remove("A");
        
        // Check that A is removed from the graph and that there are no outgoing edges from A
        assertFalse("expected A to be removed from vertices", graph.vertices().contains("A"));
        assertTrue("expected no edges for A", graph.sources("A").isEmpty());
        assertTrue("expected no edges from A", graph.targets("A").isEmpty());
    }

    // Test removing a vertex that doesn't exist in the graph
    @Test
    public void testRemoveNonExistentVertex() {
        Graph<String> graph = emptyInstance();
        
        // Try removing a non-existent vertex
        assertFalse("expected remove to return false for non-existent vertex", graph.remove("NonExistent"));
    }

    // Test that the sources and targets functions return the correct maps
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
}
