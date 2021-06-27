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
@Entity(name = "Jadwal")
public class Jadwal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "hari")
    private Integer hari;

    @Column(name = "jam")
    private Integer jam;

    @Column(name = "matpel")
    private Integer matpel;

    @Column(name = "guruPengajar")
    private Long guruPengajar;

    @Column(name = "kelas")
    private Integer kelas;

    @Column(name = "ruang")
    private Integer ruang;

    @JsonIgnore
    @Column(name = "createdAt")
    private Date createdAt;

    @JsonIgnore
    @Column(name = "updatedAt")
    private Date updatedAt;
}
