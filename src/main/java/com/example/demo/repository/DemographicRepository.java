package com.example.demo.repository;

import com.example.demo.Entity.Demographic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemographicRepository extends JpaRepository<Demographic,Long> {

}
