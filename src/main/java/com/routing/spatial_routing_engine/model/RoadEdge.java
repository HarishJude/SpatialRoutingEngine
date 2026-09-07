package com.routing.spatial_routing_engine.model;

import jakarta.persistence.*;

@Entity
public class RoadEdge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fromNodeId;
    private String toNodeId;
    private double weightKm;

    public RoadEdge() {}

    public RoadEdge(String fromNodeId, String toNodeId, double weightKm) {
        this.fromNodeId = fromNodeId;
        this.toNodeId = toNodeId;
        this.weightKm = weightKm;
    }

    public Long getId() { return id; }
    public String getFromNodeId() { return fromNodeId; }
    public String getToNodeId() { return toNodeId; }
    public double getWeightKm() { return weightKm; }
}