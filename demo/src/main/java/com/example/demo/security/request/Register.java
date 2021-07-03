package com.example.demo.security.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Register {

    private String username;
    private String email;
    private String password;
    private String fullname;
    private Integer gender;
    private Integer status;
    private String address;
    private String phoneNumber;
    private String nimOrNik;
    private Set<String> role;
}
