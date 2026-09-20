package com.routing.spatial_routing_engine.controller;

import com.routing.spatial_routing_engine.model.DijkstraResult;
import com.routing.spatial_routing_engine.model.Graph;
import com.routing.spatial_routing_engine.model.Node;
import com.routing.spatial_routing_engine.service.GraphService;
import com.routing.spatial_routing_engine.service.RoutingService;
import com.routing.spatial_routing_engine.model.BenchmarkResult;
import org.springframework.web.bind.annotation.*;
import com.routing.spatial_routing_engine.service.GeoJsonImportService;
import java.util.List;
import java.util.Map;
import com.routing.spatial_routing_engine.util.Quadtree;
import com.routing.spatial_routing_engine.model.Point;
import com.routing.spatial_routing_engine.model.BoundingBox;
import com.routing.spatial_routing_engine.model.RangeQueryResult;
import java.util.HashMap;



import java.util.Map;

@RestController
@RequestMapping("/api/graph")
public class RoutingController {

    private final GraphService graphService;
    private final RoutingService routingService;
    private final GeoJsonImportService importService;

    public RoutingController(GraphService graphService, RoutingService routingService, GeoJsonImportService importService) {
        this.graphService = graphService;
        this.routingService = routingService;
        this.importService = importService;
    }

    @GetMapping("/toy")
    public Map<String, Node> getToyGraphNodes() {
        return graphService.buildToyGraph().getNodes();
    }

    @GetMapping("/dijkstra")
    public DijkstraResult runDijkstra(@RequestParam String from, @RequestParam String to) {
        Graph graph = graphService.buildToyGraph();
        return routingService.dijkstra(graph, from, to);
    }

    @GetMapping("/astar")
    public DijkstraResult runAStar(@RequestParam String from, @RequestParam String to) {
        Graph graph = graphService.buildToyGraph();
        return routingService.aStar(graph, from, to);
    }

    @GetMapping("/benchmark")
    public List<BenchmarkResult> runBenchmark(@RequestParam String from, @RequestParam String to) {
        Graph graph = graphService.buildToyGraph();
        return routingService.benchmark(graph, from, to);
    }

    @PostMapping("/import")
    public String importRoads() throws Exception {
        return importService.importFromClasspath("data/roads.geojson");
    }

    @GetMapping("/real/dijkstra")
    public DijkstraResult runRealDijkstra(@RequestParam String from, @RequestParam String to) {
        Graph graph = graphService.buildRealGraph();
        return routingService.dijkstra(graph, from, to);
    }

    @GetMapping("/real/astar")
    public DijkstraResult runRealAStar(@RequestParam String from, @RequestParam String to) {
        Graph graph = graphService.buildRealGraph();
        return routingService.aStar(graph, from, to);
    }

    @GetMapping("/real/benchmark")
    public List<BenchmarkResult> runRealBenchmark(@RequestParam String from, @RequestParam String to) {
        Graph graph = graphService.buildRealGraph();
        return routingService.benchmark(graph, from, to);
    }

    @GetMapping("/spatial/range")
    public RangeQueryResult queryRange(
            @RequestParam double minLat, @RequestParam double maxLat,
            @RequestParam double minLon, @RequestParam double maxLon) {

        Quadtree tree = graphService.buildQuadtree();
        BoundingBox range = new BoundingBox(minLat, maxLat, minLon, maxLon);
        List<Point> results = tree.queryRange(range);

        return new RangeQueryResult(results.size(), results);
    }

    @GetMapping("/spatial/benchmark")
    public Map<String, Object> spatialBenchmark(
            @RequestParam double minLat, @RequestParam double maxLat,
            @RequestParam double minLon, @RequestParam double maxLon) {

        BoundingBox range = new BoundingBox(minLat, maxLat, minLon, maxLon);

        long startBrute = System.nanoTime();
        List<Point> bruteResults = graphService.bruteForceRange(range);
        long bruteTime = System.nanoTime() - startBrute;

        long startTree = System.nanoTime();
        Quadtree tree = graphService.buildQuadtree();
        List<Point> treeResults = tree.queryRange(range);
        long treeTime = System.nanoTime() - startTree;

        Map<String, Object> result = new HashMap<>();
        result.put("bruteForceCount", bruteResults.size());
        result.put("bruteForceTimeMs", bruteTime / 1_000_000.0);
        result.put("quadtreeCount", treeResults.size());
        result.put("quadtreeTimeMs", treeTime / 1_000_000.0);
        return result;
    }

    @GetMapping("/drivers/nearby")
    public List<Point> nearbyDrivers(
            @RequestParam double lat, @RequestParam double lon,
            @RequestParam(defaultValue = "5.0") double radiusKm) {
        Quadtree drivers = graphService.buildDriverQuadtree(200); // 200 simulated drivers
        return routingService.findWithinRadius(drivers, lat, lon, radiusKm);
    }

    @GetMapping("/drivers/nearest")
    public Point nearestDriver(@RequestParam double lat, @RequestParam double lon) {
        Quadtree drivers = graphService.buildDriverQuadtree(200);
        return routingService.findNearest(drivers, lat, lon, 10.0); // expand search radius if needed
    }
}