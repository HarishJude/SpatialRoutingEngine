package com.routing.spatial_routing_engine.model;

public class Node {
    private String id;
    private double latitude;
    private double longitude;

    public Node(String id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() { return id; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}