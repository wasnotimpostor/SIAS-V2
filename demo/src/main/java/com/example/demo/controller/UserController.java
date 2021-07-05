package com.example.demo.controller;

import com.example.demo.dto.dtoUser;
import com.example.demo.model.Users;
import com.example.demo.service.UserService;
import com.example.demo.utils.Functions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/sias", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

//    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getUserByPage(@RequestBody String param){
        JSONObject jsonObject = new JSONObject(param);
        Map<String, Object> response;

        try {
            Integer page = jsonObject.optInt("page", 0);
            if (page > 0) page--;
            Integer per_page = jsonObject.optInt("per_page", 10);
            Long id = jsonObject.optLong("id", 0);
            String username = jsonObject.optString("username");
            Integer status = jsonObject.optInt("status");

            Pageable pageable = PageRequest.of(page, per_page);
            Page<dtoUser> userPage = userService.getUserByPage(id, username, status, pageable);
            List<dtoUser> userList = userPage.getContent();

            response = Functions.page("success", userPage.getTotalElements(), userPage.getTotalPages(), per_page, page++, userList);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/user/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getUserByList(){
        Map<String, Object> response;

        try {
            List<dtoUser> list = userService.getUserByList();
            if (list.size() > 0){
                response = Functions.response("success", "Get User Success", list);
            } else {
                response = Functions.response("failed", "Data is Empty", list);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    Users parseJson(Long id, JSONObject jsonObject){
        String username = jsonObject.optString("username");
        String email = jsonObject.optString("email");
        String password = jsonObject.optString("password");
        String fullname = jsonObject.optString("fullname");
        Integer gender = jsonObject.optInt("gender");
        Integer status = jsonObject.optInt("status");
        String address = jsonObject.optString("address");
        String phoneNumber = jsonObject.optString("phoneNumber");
        Integer idKelas = jsonObject.optInt("idKelas");
        String nimOrNik = jsonObject.optString("nimOrNik");

        Users users;
        if (id == 0){
            users = new Users(null, username, email, password, fullname, gender, status, address, phoneNumber, idKelas, nimOrNik, Functions.getTimeStamp(), Functions.getTimeStamp(), null);
        } else {
            Optional<Users> exist = userService.findById(id);
            if (exist.isPresent()){
                users = new Users(id, username, email, password, fullname, gender, status, address, phoneNumber, idKelas, nimOrNik, Functions.getTimeStamp(), Functions.getTimeStamp(), exist.get().getRoles());
            } else {
                return null;
            }
        }
        return users;
    }

    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id){
        Map<String, Object> response;

        try {
            if (id <= 0){
                response = Functions.error(400, "Bad Parameters", "Error Get Data");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Optional<Users> users = userService.findById(id);
            if (!users.isPresent()){
                response = Functions.error(404, "User Not Found", "Error Get Data");
            } else {
                response = Functions.response("success", "Get Data Success", users);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/user/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> deleteUserById(@PathVariable Long id){
        Map<String, Object> response;

        try {
            if (id <= 0){
                response = Functions.error(400, "Bad Parameters", "Process Failed");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Optional<Users> users = userService.findById(id);
            if (!users.isPresent()){
                response = Functions.error(404, "User Not Found", "Process Failed");
            } else {
                userService.delete(id);
                response = Functions.response("success", "Success Delete User", "Process Success");
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/user/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> updateUserById(@PathVariable Long id, @RequestBody String param){
        Map<String, Object> response;
        JSONObject jsonObject = new JSONObject(param);

        if (id <= 0){
            response = Functions.error(400, "Bad Parameters", "Process Failed");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        try {
            Users users = parseJson(id, jsonObject);
            if (users == null){
                response = Functions.error(404, "User Not Found", "Process Failed");
            } else {
                Users updateUser = userService.save(users);
                response = Functions.response("success", "Update Data Success", updateUser);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/user/changepass/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody String param, @PathVariable Long id){
        Map<String, Object> response;
        JSONObject jsonObject = new JSONObject(param);

        try {
            Optional<Users> exist = userService.findById(id);
            if (!exist.isPresent()){
                response = Functions.error(404, "User Not Found", "Process Failed");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Users currentUser = userService.getUserByUsername(username);
            String password = exist.get().getPassword();
            String oldPass = jsonObject.getString("oldPass");
            String newPass = jsonObject.getString("newPass");

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            Boolean oldPassMatch = encoder.matches(oldPass, password);
            Boolean newPassMatch = encoder.matches(newPass, password);

            if (!oldPassMatch){
                response = Functions.responseChangePass("oldPass","The currently entered password is incorrect.");
            } else if (newPassMatch){
                response = Functions.responseChangePass("newPass","Please use different password.");
            } else {
                currentUser.setPassword(passwordEncoder.encode(newPass));
                Users changePass = userService.save(currentUser);
                response = Functions.response("success", "Password Updated Successfully", changePass);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
