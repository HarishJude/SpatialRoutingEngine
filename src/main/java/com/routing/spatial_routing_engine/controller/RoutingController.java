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
}