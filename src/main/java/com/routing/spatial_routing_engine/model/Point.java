package com.routing.spatial_routing_engine.model;

public class Point {
    private String id;
    private double latitude;
    private double longitude;

    public Point(String id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() { return id; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}