package com.example.demo.service;

import com.example.demo.dto.dtoMataPelajaran;
import com.example.demo.model.MataPelajaran;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MataPelajaranService {
    Page<dtoMataPelajaran> getMatpelByPage(Integer id, String name, Long koordinator, Pageable pageable);
    List<dtoMataPelajaran> getMatpelByList();
    MataPelajaran save(MataPelajaran mataPelajaran);
    MataPelajaran delete(Integer id);
    Optional<MataPelajaran> findById(Integer id);
}
