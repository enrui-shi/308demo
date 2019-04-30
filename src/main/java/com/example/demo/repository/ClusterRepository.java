package com.example.demo.repository;

import com.example.demo.Entity.Cluster;
import com.example.demo.Enum.StateName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClusterRepository extends JpaRepository<Cluster,Long>{
    List<Cluster> findAllByStateName(StateName stateName);
}
