package com.example.demo.controller;

import com.example.demo.model.RoleName;
import com.example.demo.model.Roles;
import com.example.demo.model.Users;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtProvider;
import com.example.demo.security.request.Login;
import com.example.demo.security.request.Register;
import com.example.demo.security.response.JwtResponse;
import com.example.demo.utils.Functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/sias", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserAuth {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> authenticateUser(@Valid @RequestBody Login login){

        Map<String, Object> response;

        try {

            Optional<Users> currentUser = userRepository.findByUsernameOrEmail(login.getUsernameOrEmail(), login.getUsernameOrEmail());
            if (!currentUser.isPresent()){
                response = Functions.error(400, "Bad Parameters", "Wrong Username or Email");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            String currentUserPassword = currentUser.get().getPassword();
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            Boolean passMatch = bCryptPasswordEncoder.matches(login.getPassword(), currentUserPassword);

            if (!passMatch){
                response = Functions.error(400, "Bad Parameters", "Wrong Password");
            } else {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(login.getUsernameOrEmail(), login.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                String jwt = jwtProvider.generateToken(authentication);

                response = Functions.response("success", "Login Success", new JwtResponse(jwt));
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> registerUser(@Valid @RequestBody Register register){
        Map<String, Object> response;

        try {
            if (userRepository.existsByUsername(register.getUsername())){
                response = Functions.error(500, "Username Already Exist", "Register Failed");
            } else if (userRepository.existsByEmail(register.getEmail())){
                response = Functions.error(500, "Email Already Exist", "Register Failed");
            } else {
                Users users = new Users(register.getUsername(), register.getEmail(), passwordEncoder.encode(register.getPassword()), register.getFullname(), register.getGender(),
                        register.getStatus(), register.getAddress(), register.getPhoneNumber(), register.getNimOrNik());

                Set<String> strRoles = register.getRole();
                Set<Roles> roles = new HashSet<>();

                strRoles.forEach(role -> {
                    switch (role) {
                        case "ROLE_SUPERADMIN":
                            Roles superadmin = roleRepository.findByName(RoleName.ROLE_SUPERADMIN).orElseThrow(() ->
                                    new RuntimeException("Role Not Found"));
                            roles.add(superadmin);
                            break;
                        case "ROLE_ADMIN":
                            Roles admin = roleRepository.findByName(RoleName.ROLE_ADMIN).orElseThrow(() ->
                                    new RuntimeException("Role Not Found"));
                            roles.add(admin);
                            break;
                        case "ROLE_GURU":
                            Roles guru = roleRepository.findByName(RoleName.ROLE_GURU).orElseThrow(() ->
                                    new RuntimeException("Role Not Found"));
                            roles.add(guru);
                            break;
                        case "ROLE_MURID":
                            Roles murid = roleRepository.findByName(RoleName.ROLE_MURID).orElseThrow(() ->
                                    new RuntimeException("Role Not Found"));
                            roles.add(murid);
                            break;
                    }
                });
                users.setRoles(roles);
                Users registerUser = userRepository.save(users);
                response = Functions.response("success", "Register Success", registerUser);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/user/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getCurrentUser(Authentication authentication){
        return authentication.getPrincipal();
    }
}
