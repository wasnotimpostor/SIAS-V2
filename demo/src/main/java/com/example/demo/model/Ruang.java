package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Ruang")
public class Ruang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "nomor")
    private Integer nomor;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "createdAt")
    protected Timestamp createdAt;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "updatedAt")
    protected Timestamp updatedAt;
}
