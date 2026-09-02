package com.routing.spatial_routing_engine.model;

import java.util.List;
import java.util.Map;

public class DijkstraResult {
    private Map<String, Double> distances;
    private List<String> path;
    private double totalCost;
    private int nodesExplored;

    public DijkstraResult(Map<String, Double> distances, List<String> path, double totalCost, int nodesExplored) {
        this.distances = distances;
        this.path = path;
        this.totalCost = totalCost;
        this.nodesExplored = nodesExplored;
    }

    public Map<String, Double> getDistances() { return distances; }
    public List<String> getPath() { return path; }
    public double getTotalCost() { return totalCost; }
    public int getNodesExplored() { return nodesExplored; }
}