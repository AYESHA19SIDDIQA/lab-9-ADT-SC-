package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

public abstract class GraphInstanceTest {
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // ensure assertions are enabled with VM argument: -ea
    }
    
    // 1. Test that a new graph has no vertices
    @Test
    public void testInitialVerticesEmpty() {
        assertEquals("expected new graph to have no vertices", Collections.emptySet(), emptyInstance().vertices());
    }
    
    // 2. Test adding vertices
    @Test
    public void testAddVertex() {
        Graph<String> graph = emptyInstance();
        assertTrue("expected add to return true", graph.add("A"));
        assertTrue("expected vertex 'A' to exist", graph.vertices().contains("A"));
    }

    // 3. Test adding duplicate vertices
    @Test
    public void testAddDuplicateVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertFalse("expected add to return false for duplicate vertex", graph.add("A"));
        assertEquals("expected only one vertex 'A'", 1, graph.vertices().size());
    }
    
    // 4. Test adding and checking edges
    @Test
    public void testSetEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        assertEquals("expected set to return 0 for new edge", 0, graph.set("A", "B", 5));
        assertEquals("expected weight of edge A->B to be 5", Map.of("B", 5), graph.targets("A"));
    }

    // 5. Test removing a vertex
    @Test
    public void testRemoveVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertTrue("expected remove to return true for existing vertex", graph.remove("A"));
        assertFalse("expected vertex 'A' to no longer exist", graph.vertices().contains("A"));
    }
    
    // 6. Test sources and targets for directed edges
    @Test
    public void testSourcesAndTargets() {
        Graph<String> graph = emptyInstance();
        graph.set("A", "B", 3);
        assertEquals("expected source of B to be A with weight 3", Map.of("A", 3), graph.sources("B"));
        assertEquals("expected target of A to be B with weight 3", Map.of("B", 3), graph.targets("A"));
    }
}
