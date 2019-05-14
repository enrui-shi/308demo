package com.example.demo.repository;

import com.example.demo.Entity.PrecinctEdge;
import com.example.demo.Enum.StateName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrecinctEdgeRepository extends JpaRepository<PrecinctEdge, Long> {
    List<PrecinctEdge> findAllByStateName(StateName stateName);
}
