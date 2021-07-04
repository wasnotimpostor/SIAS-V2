package com.example.demo.controller;

import com.example.demo.dto.dtoKelas;
import com.example.demo.model.Kelas;
import com.example.demo.service.KelasService;
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
public class KelasController {

    @Autowired
    private KelasService kelasService;

    @PostMapping(value = "/kelas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getKelasByPage(@RequestBody String param){
        JSONObject jsonObject = new JSONObject(param);
        Map<String, Object> response;

        try {
            Integer page = jsonObject.optInt("page", 0);
            if (page > 0) page--;
            Integer per_page = jsonObject.optInt("per_page", 10);
            Integer id = jsonObject.optInt("id", 0);
            String namaKelas = jsonObject.optString("namaKelas");
            Integer lantai = jsonObject.optInt("lantai");

            Pageable pageable = PageRequest.of(page, per_page);
            Page<dtoKelas> kelasPage = kelasService.getKelasByPage(id, namaKelas, lantai, pageable);
            List<dtoKelas> kelasList = kelasPage.getContent();

            response = Functions.page("success", kelasPage.getTotalElements(), kelasPage.getTotalPages(), per_page, page++, kelasList);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/kelas/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getKelasByList(){
        Map<String, Object> response;

        try {
            List<dtoKelas> list = kelasService.getKelasByList();
            if (list.size() > 0){
                response = Functions.response("success", "Get Kelas Success", list);
            } else {
                response = Functions.response("failed", "Data is Empty", list);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "kelas/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> createKelas(){
        Map<String, Object> response;

        try {
            Kelas kelas = new Kelas();
            if (kelas == null){
                response = Functions.error(500, "No Data Saved", "Process Failed");
            } else {
                Kelas save = kelasService.save(kelas);
                response = Functions.response("success", "Data Saved Successfully", save);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/kelas/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getKelasById(@PathVariable Integer id){
        Map<String, Object> response;

        try {
            if (id <= 0){
                response = Functions.error(400, "Bad Parameters", "Error Get Data");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Optional<Kelas> kelas = kelasService.findById(id);
            if (!kelas.isPresent()){
                response = Functions.error(404, "Kelas Not Found", "Error Get Data");
            } else {
                response = Functions.response("success", "Get Data Success", kelas);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/kelas/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> deleteKelasById(@PathVariable Integer id){
        Map<String, Object> response;

        try {
            if (id <= 0){
                response = Functions.error(400, "Bad Parameters", "Process Failed");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Optional<Kelas> kelas = kelasService.findById(id);
            if (!kelas.isPresent()){
                response = Functions.error(404, "Kelas Not Found", "Process Failed");
            } else {
                kelasService.delete(id);
                response = Functions.response("success", "Success Delete User", "Process Success");
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/kelas/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> updateKelasById(@PathVariable Integer id){
        Map<String, Object> response;

        try {
            if (id <= 0){
                response = Functions.error(400, "Bad Parameters", "Process Failed");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Optional<Kelas> exist = kelasService.findById(id);
            if (!exist.isPresent()){
                response = Functions.error(404, "Kelas Not Found", "Process Failed");
            } else {
                Kelas kelas = new Kelas();
                Kelas updateKelas = kelasService.save(kelas);
                response = Functions.response("success", "Update Data Success", updateKelas);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
