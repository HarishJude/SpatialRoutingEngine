package com.routing.spatial_routing_engine.model;

public class Edge {
    private String targetNodeId;
    private double weight;

    public Edge(String targetNodeId, double weight) {
        this.targetNodeId = targetNodeId;
        this.weight = weight;
    }

    public String getTargetNodeId() { return targetNodeId; }
    public double getWeight() { return weight; }
}