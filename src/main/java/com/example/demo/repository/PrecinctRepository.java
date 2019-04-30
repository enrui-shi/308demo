package com.example.demo.repository;

import com.example.demo.Entity.Precinct;
import com.example.demo.Enum.StateName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrecinctRepository extends JpaRepository<Precinct, Long> {

    List<Precinct> findAllByStateName(StateName stateName);
}
