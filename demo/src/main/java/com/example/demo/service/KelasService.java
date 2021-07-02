package com.example.demo.service;

import com.example.demo.dto.dtoKelas;
import com.example.demo.model.Kelas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface KelasService {
    Page<dtoKelas> getKelasByPage(Integer id, String namaKelas, Integer lantai, Pageable pageable);
    List<dtoKelas> getKelasByList();
    Kelas save(Kelas kelas);
    Kelas delete(Integer id);
    Optional<Kelas> findById(Integer id);
}
