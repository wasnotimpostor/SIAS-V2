package com.example.demo.service;

import com.example.demo.dto.dtoJam;
import com.example.demo.model.Jam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface JamService {

    Page<dtoJam> getJamByPage(Integer id, String jam, Pageable pageable);
    List<dtoJam> getJamByList();
    Jam save(Jam jam);
    Jam delete(Integer id);
    Optional<Jam> findById(Integer id);
}
