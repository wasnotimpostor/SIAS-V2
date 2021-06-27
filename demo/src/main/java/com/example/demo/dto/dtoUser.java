package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class dtoUser {

    private Long id;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private Integer gender;
    private Integer status;
}
