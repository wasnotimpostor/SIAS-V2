package com.example.demo.service;

import com.example.demo.dto.dtoRuang;
import com.example.demo.model.Ruang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RuangService {
    Page<dtoRuang> getRuangByPage(Integer id, String name, Integer nomor, Pageable pageable);
    List<dtoRuang> getRuangByList();
    Ruang save(Ruang ruang);
    Ruang delete(Integer id);
    Optional<Ruang> findById(Integer id);
}
