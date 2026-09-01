package com.routing.spatial_routing_engine.model;

import java.util.*;

public class Graph {
    private final Map<String, Node> nodes = new HashMap<>();
    private final Map<String, List<Edge>> adjacencyList = new HashMap<>();

    public void addNode(Node node) {
        nodes.put(node.getId(), node);
        adjacencyList.putIfAbsent(node.getId(), new ArrayList<>());
    }

    public void addEdge(String fromId, String toId, double weight) {
        adjacencyList.get(fromId).add(new Edge(toId, weight));
        adjacencyList.get(toId).add(new Edge(fromId, weight));
    }

    public List<Edge> getNeighbors(String nodeId) {
        return adjacencyList.getOrDefault(nodeId, Collections.emptyList());
    }

    public Map<String, Node> getNodes() { return nodes; }
}