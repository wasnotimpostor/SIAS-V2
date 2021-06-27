package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class dtoJadwal {

    private Integer id;
    private String hari;
    private String jam;
    private String matpel;
    private String guru;
    private String kelas;
    private String ruang;
}
