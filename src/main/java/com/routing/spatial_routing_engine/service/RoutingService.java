package com.routing.spatial_routing_engine.service;

import com.routing.spatial_routing_engine.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoutingService {

    public DijkstraResult dijkstra(Graph graph, String sourceId, String targetId) {
        Map<String, Double> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<Map.Entry<String, Double>> pq =
                new PriorityQueue<>(Comparator.comparingDouble(Map.Entry::getValue));

        for (String nodeId : graph.getNodes().keySet()) {
            distances.put(nodeId, Double.MAX_VALUE);
        }
        distances.put(sourceId, 0.0);
        pq.add(new AbstractMap.SimpleEntry<>(sourceId, 0.0));

        Set<String> visited = new HashSet<>();
        int nodesExplored = 0;

        while (!pq.isEmpty()) {
            String current = pq.poll().getKey();
            if (visited.contains(current)) continue;
            visited.add(current);
            nodesExplored++;

            if (current.equals(targetId)) break; // early exit once target is finalized

            for (Edge edge : graph.getNeighbors(current)) {
                double newDist = distances.get(current) + edge.getWeight();
                if (newDist < distances.get(edge.getTargetNodeId())) {
                    distances.put(edge.getTargetNodeId(), newDist);
                    previous.put(edge.getTargetNodeId(), current);
                    pq.add(new AbstractMap.SimpleEntry<>(edge.getTargetNodeId(), newDist));
                }
            }
        }

        List<String> path = reconstructPath(previous, sourceId, targetId);
        double totalCost = distances.getOrDefault(targetId, -1.0);

        return new DijkstraResult(distances, path, totalCost, nodesExplored);
    }

    public List<String> reconstructPath(Map<String, String> previous, String sourceId, String targetId) {
        List<String> path = new LinkedList<>();
        String current = targetId;

        if (!previous.containsKey(current) && !current.equals(sourceId)) {
            return path; // no path found
        }

        while (current != null && !current.equals(sourceId)) {
            path.add(0, current);
            current = previous.get(current);
        }
        if (current != null) path.add(0, sourceId);

        return path;
    }
}