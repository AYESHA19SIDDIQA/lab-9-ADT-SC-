package graph;

import java.util.*;

/**
 * An implementation of a directed, weighted graph using vertices that store edges.
 */
public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();

    // Abstraction function:
    //   Represents a directed graph where vertices list holds all vertices in the graph, and each vertex has a map of edges to other vertices with associated weights.
    // Representation invariant:
    //   - vertices is non-null.
    //   - No two vertices have the same label.
    // Safety from rep exposure:
    //   - vertices is private and final.
    //   - The methods return unmodifiable views or defensive copies where necessary.
    
    // Constructor
    public ConcreteVerticesGraph() {
        checkRep();
    }
    
    private void checkRep() {
        assert vertices != null;
        Set<String> labels = new HashSet<>();
        for (Vertex v : vertices) {
            assert v != null;
            assert !labels.contains(v.label);
            labels.add(v.label);
        }
    }

    @Override
    public boolean add(String vertex) {
        if (getVertex(vertex) != null) {
            return false;
        }
        vertices.add(new Vertex(vertex));
        checkRep();
        return true;
    }
    
    @Override
    public int set(String source, String target, int weight) {
        Vertex srcVertex = getVertex(source);
        if (srcVertex == null) {
            srcVertex = new Vertex(source);
            vertices.add(srcVertex);
        }
        Vertex tgtVertex = getVertex(target);
        if (tgtVertex == null) {
            tgtVertex = new Vertex(target);
            vertices.add(tgtVertex);
        }
        
        int previousWeight = srcVertex.addEdge(target, weight);
        if (weight == 0) {
            srcVertex.removeEdge(target);
        }
        checkRep();
        return previousWeight;
    }
    
    @Override
    public boolean remove(String vertex) {
        Vertex v = getVertex(vertex);
        if (v == null) {
            return false;
        }
        vertices.remove(v);
        for (Vertex vert : vertices) {
            vert.removeEdge(vertex);
        }
        checkRep();
        return true;
    }
    
    @Override
    public Set<String> vertices() {
        Set<String> labels = new HashSet<>();
        for (Vertex v : vertices) {
            labels.add(v.label);
        }
        return Collections.unmodifiableSet(labels);
    }
    
    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Vertex v : vertices) {
            Integer weight = v.getEdgeWeight(target);
            if (weight != null) {
                sources.put(v.label, weight);
            }
        }
        return Collections.unmodifiableMap(sources);
    }
    
    @Override
    public Map<String, Integer> targets(String source) {
        Vertex v = getVertex(source);
        if (v == null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(v.edges);
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Vertex v : vertices) {
            result.append(v.toString()).append("\n");
        }
        return result.toString();
    }

    private Vertex getVertex(String label) {
        for (Vertex v : vertices) {
            if (v.label.equals(label)) {
                return v;
            }
        }
        return null;
    }

    private static class Vertex {
        private final String label;
        private final Map<String, Integer> edges = new HashMap<>();

        Vertex(String label) {
            this.label = label;
        }

        int addEdge(String target, int weight) {
            Integer previousWeight = edges.put(target, weight == 0 ? null : weight);
            return previousWeight != null ? previousWeight : 0;
        }


        void removeEdge(String target) {
            edges.remove(target);
        }

        Integer getEdgeWeight(String target) {
            return edges.get(target);
        }

        @Override
        public String toString() {
            return label + " -> " + edges;
        }
    }
}
