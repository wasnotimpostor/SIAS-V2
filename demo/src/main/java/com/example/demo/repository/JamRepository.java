package com.example.demo.repository;

import com.example.demo.model.Jam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JamRepository extends JpaRepository<Jam, Integer> {
}
