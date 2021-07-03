package com.example.demo.controller;

import com.example.demo.dto.dtoRuang;
import com.example.demo.model.Ruang;
import com.example.demo.service.RuangService;
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
public class RuangController {

    @Autowired
    private RuangService ruangService;

    @PostMapping(value = "/ruang", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getRuangByPage(@RequestBody String param){
        Map<String, Object> response;
        JSONObject jsonObject = new JSONObject(param);

        try {
            Integer page = jsonObject.optInt("page", 0);
            if (page > 0) page--;
            Integer per_page = jsonObject.optInt("per_page", 10);
            Integer id = jsonObject.optInt("id", 0);
            String name = jsonObject.optString("name");
            Integer nomor = jsonObject.optInt("nomor");
            Integer floor = jsonObject.optInt("floor");

            Pageable pageable = PageRequest.of(page, per_page);
            Page<dtoRuang> dtoRuangPage = ruangService.getRuangByPage(id, name, nomor, floor, pageable);
            List<dtoRuang> ruangList = dtoRuangPage.getContent();

            response = Functions.page("success", dtoRuangPage.getTotalElements(), dtoRuangPage.getTotalPages(), per_page, page++, ruangList);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/ruang/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getRuangByList(){
        Map<String, Object> response;

        try {
            List<dtoRuang> list = ruangService.getRuangByList();
            if (list.size() > 0){
                response = Functions.response("success", "Get Ruang Success", list);
            } else {
                response = Functions.response("failed", "Data is Empty", list);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/ruang/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> createRuang(){
        Map<String, Object> response;

        try {
            Ruang ruang = new Ruang();
            if (ruang == null){
                response = Functions.error(500, "No Data Saved", "Process Failed");
            } else {
                Ruang save = ruangService.save(ruang);
                response = Functions.response("success", "Data Saved Successfully", save);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/ruang/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getRuangById(@PathVariable Integer id){
        Map<String, Object> response;

        try {
            if (id <= 0){
                response = Functions.error(400, "Bad Parameters", "Error Get Data");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Optional<Ruang> ruang = ruangService.findById(id);
            if (!ruang.isPresent()){
                response = Functions.error(404, "Ruang Not Found", "Error Get Data");
            } else {
                response = Functions.response("success", "Get Ruang Success", ruang);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/ruang/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> deleteRuangById(@PathVariable Integer id){
        Map<String, Object> response;

        try {
            if (id <= 0){
                response = Functions.error(400, "Bad Parameters", "Pcess Failed");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Optional<Ruang> ruang = ruangService.findById(id);
            if (!ruang.isPresent()){
                response = Functions.error(404, "Ruang Not Found", "Process Failed");
            } else {
                ruangService.delete(id);
                response = Functions.response("success", "Success Delete Ruang", "Process Success");
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/ruang/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> updateRuangById(@PathVariable Integer id){
        Map<String, Object> response;

        try {
            if (id <= 0){
                response = Functions.error(400, "Bad Parameters", "Process Failed");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Optional<Ruang> exist = ruangService.findById(id);
            if (!exist.isPresent()){
                response = Functions.error(404, "Ruang Not Found", "Process Failed");
            } else {
                Ruang ruang = new Ruang();
                Ruang updateRuang = ruangService.save(ruang);
                response = Functions.response("success", "Update Data Success", updateRuang);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
