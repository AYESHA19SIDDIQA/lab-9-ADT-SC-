package graph;

import java.util.*;

/**
 * An implementation of a directed, weighted graph using edges that store their endpoints.
 */
public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();

    // Abstraction function:
    //   Represents a directed graph where vertices is a set of vertex labels and edges is a list of edges with associated weights.
    // Representation invariant:
    //   - vertices is non-null.
    //   - edges is non-null.
    //   - Each edge's source and target are in vertices.
    // Safety from rep exposure:
    //   - vertices and edges are private and final.
    //   - Methods return unmodifiable views or defensive copies where necessary.
    
    // Constructor
    public ConcreteEdgesGraph() {
        checkRep();
    }
    
    private void checkRep() {
        assert vertices != null;
        assert edges != null;
        for (Edge e : edges) {
            assert vertices.contains(e.getSource());
            assert vertices.contains(e.getTarget());
        }
    }

    @Override
    public boolean add(String vertex) {
        if (!vertices.add(vertex)) {
            return false;
        }
        checkRep();
        return true;
    }
    
    @Override
    public int set(String source, String target, int weight) {
        add(source);
        add(target);
        
        for (Edge e : edges) {
            if (e.getSource().equals(source) && e.getTarget().equals(target)) {
                int previousWeight = e.getWeight();
                if (weight == 0) {
                    edges.remove(e);
                } else {
                    e.setWeight(weight);
                }
                checkRep();
                return previousWeight;
            }
        }

        if (weight > 0) {
            edges.add(new Edge(source, target, weight));
        }
        checkRep();
        return 0;
    }
    
    @Override
    public boolean remove(String vertex) {
        if (!vertices.remove(vertex)) {
            return false;
        }
        edges.removeIf(e -> e.getSource().equals(vertex) || e.getTarget().equals(vertex));
        checkRep();
        return true;
    }
    
    @Override
    public Set<String> vertices() {
        return Collections.unmodifiableSet(vertices);
    }
    
    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Edge e : edges) {
            if (e.getTarget().equals(target)) {
                sources.put(e.getSource(), e.getWeight());
            }
        }
        return Collections.unmodifiableMap(sources);
    }
    
    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> targets = new HashMap<>();
        for (Edge e : edges) {
            if (e.getSource().equals(source)) {
                targets.put(e.getTarget(), e.getWeight());
            }
        }
        return Collections.unmodifiableMap(targets);
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Edge e : edges) {
            result.append(e.toString()).append("\n");
        }
        return result.toString();
    }

    private static class Edge {
        private final String source;
        private final String target;
        private int weight;

        Edge(String source, String target, int weight) {
            if (source == null || target == null || weight < 0) {
                throw new IllegalArgumentException("Invalid edge parameters.");
            }
            this.source = source;
            this.target = target;
            this.weight = weight;
        }

        String getSource() {
            return source;
        }

        String getTarget() {
            return target;
        }

        int getWeight() {
            return weight;
        }

        void setWeight(int weight) {
            this.weight = weight;
        }

        @Override
        public String toString() {
            return source + " -> " + target + " (" + weight + ")";
        }
    }
}
