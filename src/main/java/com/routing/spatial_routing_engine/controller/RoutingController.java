package com.routing.spatial_routing_engine.controller;

import com.routing.spatial_routing_engine.model.Node;
import com.routing.spatial_routing_engine.service.GraphService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/graph")
public class RoutingController {

    private final GraphService graphService;

    public RoutingController(GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping("/toy")
    public Map<String, Node> getToyGraphNodes() {
        return graphService.buildToyGraph().getNodes();
    }
}