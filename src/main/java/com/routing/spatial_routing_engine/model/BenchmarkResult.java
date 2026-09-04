package com.routing.spatial_routing_engine.model;

public class BenchmarkResult {
    private String algorithm;
    private long executionTimeNanos;
    private int nodesExplored;
    private double routeCost;
    private java.util.List<String> path;

    public BenchmarkResult(String algorithm, long executionTimeNanos, int nodesExplored,
                           double routeCost, java.util.List<String> path) {
        this.algorithm = algorithm;
        this.executionTimeNanos = executionTimeNanos;
        this.nodesExplored = nodesExplored;
        this.routeCost = routeCost;
        this.path = path;
    }

    public String getAlgorithm() { return algorithm; }
    public long getExecutionTimeNanos() { return executionTimeNanos; }
    public double getExecutionTimeMs() { return executionTimeNanos / 1_000_000.0; }
    public int getNodesExplored() { return nodesExplored; }
    public double getRouteCost() { return routeCost; }
    public java.util.List<String> getPath() { return path; }
}