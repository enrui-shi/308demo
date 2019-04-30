package com.example.demo.repository;

import com.example.demo.Entity.ClusterEdge;
import com.example.demo.Enum.StateName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClusterEdgeRepository extends JpaRepository<ClusterEdge, Long> {
    List<ClusterEdge> findAllByStateName(StateName stateName);
}
