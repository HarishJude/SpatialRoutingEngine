package com.routing.spatial_routing_engine.model;

public class BoundingBox {
    private double minLat, maxLat, minLon, maxLon;

    public BoundingBox(double minLat, double maxLat, double minLon, double maxLon) {
        this.minLat = minLat;
        this.maxLat = maxLat;
        this.minLon = minLon;
        this.maxLon = maxLon;
    }

    public boolean contains(Point p) {
        return p.getLatitude() >= minLat && p.getLatitude() <= maxLat
                && p.getLongitude() >= minLon && p.getLongitude() <= maxLon;
    }

    public boolean intersects(BoundingBox other) {
        return !(other.minLat > maxLat || other.maxLat < minLat
                || other.minLon > maxLon || other.maxLon < minLon);
    }

    public double getMinLat() { return minLat; }
    public double getMaxLat() { return maxLat; }
    public double getMinLon() { return minLon; }
    public double getMaxLon() { return maxLon; }
}