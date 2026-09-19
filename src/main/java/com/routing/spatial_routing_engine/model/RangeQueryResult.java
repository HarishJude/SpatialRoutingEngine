package com.routing.spatial_routing_engine.model;

import java.util.List;

public class RangeQueryResult {
    private int count;
    private List<Point> points;

    public RangeQueryResult(int count, List<Point> points) {
        this.count = count;
        this.points = points;
    }

    public int getCount() { return count; }
    public List<Point> getPoints() { return points; }
}