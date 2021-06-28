package com.example.demo.service;

import com.example.demo.dto.dtoJadwal;
import com.example.demo.model.Jadwal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface JadwalService {
    Page<dtoJadwal> getJadwalByPage(Integer id, Integer idHari, Long guruPengajar, Pageable pageable);
    List<dtoJadwal> getJadwalByList();
    Jadwal save(Jadwal jadwal);
    Jadwal delete(Integer id);
    Optional<Jadwal> findById(Integer id);
}
