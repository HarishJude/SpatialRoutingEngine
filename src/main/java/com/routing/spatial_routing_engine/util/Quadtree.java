package com.routing.spatial_routing_engine.util;

import com.routing.spatial_routing_engine.model.BoundingBox;
import com.routing.spatial_routing_engine.model.Point;

import java.util.ArrayList;
import java.util.List;

public class Quadtree {
    private static final int CAPACITY = 4;

    private BoundingBox boundary;
    private List<Point> points = new ArrayList<>();
    private boolean divided = false;

    private Quadtree northwest, northeast, southwest, southeast;

    public Quadtree(BoundingBox boundary) {
        this.boundary = boundary;
    }

    public boolean insert(Point point) {
        if (!boundary.contains(point)) {
            return false;
        }

        if (points.size() < CAPACITY && !divided) {
            points.add(point);
            return true;
        }

        if (!divided) {
            subdivide();
        }

        return northwest.insert(point) || northeast.insert(point)
                || southwest.insert(point) || southeast.insert(point);
    }

    private void subdivide() {
        double midLat = (boundary.getMinLat() + boundary.getMaxLat()) / 2;
        double midLon = (boundary.getMinLon() + boundary.getMaxLon()) / 2;

        northwest = new Quadtree(new BoundingBox(midLat, boundary.getMaxLat(), boundary.getMinLon(), midLon));
        northeast = new Quadtree(new BoundingBox(midLat, boundary.getMaxLat(), midLon, boundary.getMaxLon()));
        southwest = new Quadtree(new BoundingBox(boundary.getMinLat(), midLat, boundary.getMinLon(), midLon));
        southeast = new Quadtree(new BoundingBox(boundary.getMinLat(), midLat, midLon, boundary.getMaxLon()));

        divided = true;

        // redistribute existing points into children
        for (Point p : points) {
            boolean inserted = northwest.insert(p) || northeast.insert(p) || southwest.insert(p) || southeast.insert(p);
        }
        points.clear();
    }

    public List<Point> queryRange(BoundingBox range) {
        List<Point> found = new ArrayList<>();

        if (!boundary.intersects(range)) {
            return found; // prune this branch entirely
        }

        for (Point p : points) {
            if (range.contains(p)) {
                found.add(p);
            }
        }

        if (divided) {
            found.addAll(northwest.queryRange(range));
            found.addAll(northeast.queryRange(range));
            found.addAll(southwest.queryRange(range));
            found.addAll(southeast.queryRange(range));
        }

        return found;
    }
}