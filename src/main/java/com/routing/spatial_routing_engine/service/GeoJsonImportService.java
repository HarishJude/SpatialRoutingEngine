package com.routing.spatial_routing_engine.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.routing.spatial_routing_engine.model.RoadEdge;
import com.routing.spatial_routing_engine.model.RoadNode;
import com.routing.spatial_routing_engine.repository.RoadEdgeRepository;
import com.routing.spatial_routing_engine.repository.RoadNodeRepository;
import com.routing.spatial_routing_engine.util.HaversineUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class GeoJsonImportService {

    private final RoadNodeRepository nodeRepository;
    private final RoadEdgeRepository edgeRepository;

    public GeoJsonImportService(RoadNodeRepository nodeRepository, RoadEdgeRepository edgeRepository) {
        this.nodeRepository = nodeRepository;
        this.edgeRepository = edgeRepository;
    }

    public String importFromClasspath(String path) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = new ClassPathResource(path).getInputStream();
        JsonNode root = mapper.readTree(is);
        JsonNode features = root.get("features");

        Map<String, RoadNode> nodeCache = new HashMap<>();
        int edgeCount = 0;

        for (JsonNode feature : features) {
            JsonNode geometry = feature.get("geometry");
            if (geometry == null) continue;
            String type = geometry.get("type").asText();
            if (!type.equals("LineString")) continue; // only roads, skip standalone points

            JsonNode coords = geometry.get("coordinates");
            RoadNode previous = null;

            for (JsonNode coord : coords) {
                double lon = coord.get(0).asDouble();
                double lat = coord.get(1).asDouble();
                String nodeId = roundedKey(lat, lon);

                RoadNode current = nodeCache.computeIfAbsent(nodeId, k -> new RoadNode(nodeId, lat, lon));

                if (previous != null && !previous.getId().equals(current.getId())) {
                    double dist = HaversineUtil.distance(
                            previous.getLatitude(), previous.getLongitude(),
                            current.getLatitude(), current.getLongitude());
                    edgeRepository.save(new RoadEdge(previous.getId(), current.getId(), dist));
                    edgeRepository.save(new RoadEdge(current.getId(), previous.getId(), dist)); // bidirectional
                    edgeCount++;
                }
                previous = current;
            }
        }

        nodeRepository.saveAll(nodeCache.values());

        return "Imported " + nodeCache.size() + " nodes and " + edgeCount + " road segments (" + (edgeCount * 2) + " directed edges).";
    }

    private String roundedKey(double lat, double lon) {
        return String.format("%.6f_%.6f", lat, lon); // ~0.1m precision, dedupes shared intersections
    }
}