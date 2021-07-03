package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    // berlaku untuk user status 6
    @Column(name = "idKelas")
    private Integer idKelas;

    @Column(name = "nimOrNik")
    private String nimOrNik;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "createdAt")
    protected Timestamp createdAt;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "updatedAt")
    protected Timestamp updatedAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Roles> roles = new HashSet<>();

    public Users(String username, String email, String password, String fullname, Integer gender, Integer status, String address, String phoneNumber, String nimOrNik) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.gender = gender;
        this.status = status;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.nimOrNik = nimOrNik;
    }
}
