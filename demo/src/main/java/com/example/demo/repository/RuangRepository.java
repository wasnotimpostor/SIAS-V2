package com.example.demo.repository;

import com.example.demo.model.Ruang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuangRepository extends JpaRepository<Ruang, Integer> {
}
