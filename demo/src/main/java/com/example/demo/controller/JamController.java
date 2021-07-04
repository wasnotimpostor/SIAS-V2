package com.example.demo.controller;

import com.example.demo.dto.dtoJam;
import com.example.demo.model.Jam;
import com.example.demo.service.JamService;
import com.example.demo.utils.Functions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/sias", produces = MediaType.APPLICATION_JSON_VALUE)
public class JamController {

    @Autowired
    private JamService jamService;

    @PostMapping(value = "/jam", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getJamByPage(@RequestBody String param){
        Map<String, Object> response;
        JSONObject jsonObject = new JSONObject(param);

        try {
            Integer page = jsonObject.optInt("page", 0);
            if (page > 0) page--;
            Integer per_page = jsonObject.optInt("per_page", 10);
            Integer id = jsonObject.optInt("id", 0);
            String jam = jsonObject.optString("jam");

            Pageable pageable = PageRequest.of(page, per_page);
            Page<dtoJam> dtoJams = jamService.getJamByPage(id, jam, pageable);
            List<dtoJam> list = dtoJams.getContent();

            response = Functions.page("success", dtoJams.getTotalElements(), dtoJams.getTotalPages(), per_page, page++, list);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/jam/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getJamByList(){
        Map<String, Object> response;

        try {
            List<dtoJam> list = jamService.getJamByList();
            if (list.size() > 0){
                response = Functions.response("success", "Get Jam Success", list);
            } else {
                response = Functions.response("failed", "Get Jam Failed", list);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/jam/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> createJam(){
        Map<String, Object> response;

        try {
            Jam jam = new Jam();
            if (jam == null){
                response = Functions.error(500, "No Data Saved", "Process Failed");
            } else {
                Jam save = jamService.save(jam);
                response = Functions.response("success", "Data Saved Successfully", save);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/jam/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getJamById(@PathVariable Integer id){
        Map<String, Object> response;

        try {
            if (id <= 0){
                response = Functions.error(400, "Bad Parameters", "Error Get Data");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Optional<Jam> jam = jamService.findById(id);
            if (!jam.isPresent()){
                response = Functions.error(404, "Jam Not Found", "Error Get Data");
            } else {
                response = Functions.response("success", "Get Data Success", jam);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/jam/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> updateJamById(@PathVariable Integer id){
        Map<String, Object> response;

        try {
            if (id <= 0){
                response = Functions.error(400, "Bad Parameters", "Process Failed");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Optional<Jam> exist = jamService.findById(id);
            if (!exist.isPresent()){
                response = Functions.error(404, "Jam Not Found", "Process Failed");
            } else {
                Jam jam = new Jam();
                Jam updateJam = jamService.save(jam);
                response = Functions.response("success", "Update Data Success", updateJam);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/jam/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> deleteJamById(@PathVariable Integer id){
        Map<String, Object> response;

        try {
            if (id <= 0){
                response = Functions.error(400, "Bad Parameters", "Process Failed");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Optional<Jam> jam = jamService.findById(id);
            if (!jam.isPresent()){
                response = Functions.error(404, "Jam Not Found", "Process Failed");
            } else {
                response = Functions.response("success", "Success Delete Jam", "Process Success");
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
