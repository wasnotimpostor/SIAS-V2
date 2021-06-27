package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Kelas")
public class Kelas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "waliKelas")
    private Long waliKelas;

    @Column(name = "ruang")
    private Integer ruang;

    @JsonIgnore
    @Column(name = "createdAt")
    private Date createdAt;

    @JsonIgnore
    @Column(name = "updatedAt")
    private Date updatedAt;
}
