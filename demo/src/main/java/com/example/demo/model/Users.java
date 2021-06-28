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
@Entity(name = "Users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "fullname")
    private String fullname;

    // 1 = laki, 2 = perempuan
    @Column(name = "gender")
    private Integer gender;

    // status 1 = admin tu, 2 = guru, 3 = karyawan, 4 = kepsek
    // 5 = wakasek, 6 = murid
    @Column(name = "status")
    private Integer status;

    @Column(name = "address")
    private String address;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "createdAt")
    protected Timestamp createdAt;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "updatedAt")
    protected Timestamp updatedAt;
}
