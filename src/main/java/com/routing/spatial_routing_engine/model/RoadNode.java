package com.routing.spatial_routing_engine.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RoadNode {
    @Id
    private String id;
    private double latitude;
    private double longitude;

    public RoadNode() {}

    public RoadNode(String id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() { return id; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}