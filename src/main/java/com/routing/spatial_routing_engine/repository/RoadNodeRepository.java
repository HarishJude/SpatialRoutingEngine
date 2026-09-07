package com.routing.spatial_routing_engine.repository;

import com.routing.spatial_routing_engine.model.RoadNode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadNodeRepository extends JpaRepository<RoadNode, String> {}