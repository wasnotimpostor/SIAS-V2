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
@Entity(name = "MataPelajaran")
public class MataPelajaran {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // nama matpel
    @Column(name = "name", unique = true)
    private String name;

    // guru koordinator matpel
    @Column(name = "koordinator")
    private Long koordinator;

    @JsonIgnore
    @Column(name = "createdAt")
    private Date createdAt;

    @JsonIgnore
    @Column(name = "updatedAt")
    private Date updatedAt;
}
