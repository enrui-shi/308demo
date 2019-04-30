package com.example.demo.repository;

import com.example.demo.Entity.Precinct;
import com.example.demo.Entity.PrecinctEdge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrecinctEdgeRepository extends JpaRepository<PrecinctEdge, Long> {

}
