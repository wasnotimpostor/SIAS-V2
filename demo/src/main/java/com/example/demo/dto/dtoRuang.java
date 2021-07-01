package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class dtoRuang {

    private Integer id;
    private String name;
    private Integer nomor;
    private Integer floor;
}
