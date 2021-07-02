package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class dtoKelas {

    private Integer id;
    private String namaKelas;
    private String waliKelas;
    private String namaRuang;
    private Integer lantai;
    private String namaSiswa;
    private String nim;
    private Long count_siswa;
}
