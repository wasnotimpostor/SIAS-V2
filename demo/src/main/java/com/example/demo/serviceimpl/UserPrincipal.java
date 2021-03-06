package com.example.demo.serviceimpl;

import com.example.demo.model.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPrincipal implements UserDetails {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String fullname;
    private Integer gender;
    private Integer status;
    private String address;
    private String phoneNumber;
    private Integer idKelas;
    private String nimOrNik;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal build(Users users){
        List<GrantedAuthority> authorities = users.getRoles().stream().map(roles ->
                new SimpleGrantedAuthority(roles.getName().name())).collect(Collectors.toList());
        return new UserPrincipal(users.getId(), users.getUsername(), users.getEmail(), users.getPassword(), users.getFullname(), users.getGender(),
                users.getStatus(), users.getAddress(), users.getPhoneNumber(), users.getIdKelas(), users.getNimOrNik(), authorities);
    }

    public Long getId(){
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPrincipal user = (UserPrincipal) o;
        return Objects.equals(id, user.id);
    }
}
