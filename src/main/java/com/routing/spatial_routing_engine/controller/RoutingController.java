package com.routing.spatial_routing_engine.controller;

import com.routing.spatial_routing_engine.model.DijkstraResult;
import com.routing.spatial_routing_engine.model.Graph;
import com.routing.spatial_routing_engine.model.Node;
import com.routing.spatial_routing_engine.service.GraphService;
import com.routing.spatial_routing_engine.service.RoutingService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/graph")
public class RoutingController {

    private final GraphService graphService;
    private final RoutingService routingService;

    public RoutingController(GraphService graphService, RoutingService routingService) {
        this.graphService = graphService;
        this.routingService = routingService;
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
}