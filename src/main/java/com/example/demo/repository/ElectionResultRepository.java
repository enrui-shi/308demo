package com.example.demo.repository;

import com.example.demo.Entity.ElectionResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionResultRepository extends JpaRepository<ElectionResult,Long> {
}
