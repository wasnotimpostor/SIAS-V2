package com.example.demo.repository;

import com.example.demo.model.MataPelajaran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MataPelajaranRepository extends JpaRepository<MataPelajaran, Integer> {
}
