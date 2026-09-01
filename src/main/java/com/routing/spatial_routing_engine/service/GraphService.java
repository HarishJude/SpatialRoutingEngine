package com.routing.spatial_routing_engine.service;

import com.routing.spatial_routing_engine.model.Graph;
import com.routing.spatial_routing_engine.model.Node;
import org.springframework.stereotype.Service;

@Service
public class GraphService {
    public Graph buildToyGraph() {
        Graph graph = new Graph();
        graph.addNode(new Node("A", 6.9271, 79.8612));
        graph.addNode(new Node("B", 6.9147, 79.8730));
        graph.addNode(new Node("C", 6.8905, 79.8570));
        graph.addNode(new Node("D", 6.9344, 79.8428));
        graph.addNode(new Node("E", 6.8600, 79.8730));

        graph.addEdge("A", "B", 3.2);
        graph.addEdge("B", "C", 2.8);
        graph.addEdge("A", "D", 2.1);
        graph.addEdge("D", "B", 4.0);
        graph.addEdge("C", "E", 3.5);
        graph.addEdge("B", "E", 5.0);

        return graph;
    }
}