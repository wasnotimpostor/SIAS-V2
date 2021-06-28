package com.example.demo.controller;

import com.example.demo.dto.dtoJadwal;
import com.example.demo.model.Jadwal;
import com.example.demo.service.JadwalService;
import com.example.demo.utils.Functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "sias", produces = MediaType.APPLICATION_JSON_VALUE)
public class JadwalController {

    @Autowired
    private JadwalService jadwalService;

    @PostMapping(value = "/jadwal", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getAllByPage(@RequestBody String param){
        JSONObject jsonObject = new JSONObject(param);
        Map<String, Object> response;

        try {
            Integer page = jsonObject.optInt("page", 0);
            if (page > 0) page--;
            Integer per_page = jsonObject.optInt("per_page", 10);
            Integer id = jsonObject.optInt("id", 0);
            Integer idHari = jsonObject.optInt("idHari");
            Long guruPengajar = jsonObject.optLong("guruPengajar", 0);

            Pageable pageable = PageRequest.of(page, per_page);
            Page<dtoJadwal> jadwalPage = jadwalService.getJadwalByPage(id, idHari, guruPengajar, pageable);
            List<dtoJadwal> jadwalList = jadwalPage.getContent();

            response = Functions.page("success", jadwalPage.getTotalElements(), jadwalPage.getTotalPages(), per_page, page++, jadwalList);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/jadwal/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getAllByList(){
        Map<String, Object> response;
        try {
            List<dtoJadwal> jadwalList = jadwalService.getJadwalByList();
            response = Functions.response("success", "Get Jadwal Success", jadwalList);
        } catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/jadwal/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> createJadwal(){
        Map<String, Object> response;

        try {
            Jadwal jadwal = new Jadwal();
            if (jadwal == null){
                response = Functions.error(500, "No Data Saved", "Process Failed");
            } else {
                Jadwal save = jadwalService.save(jadwal);
                response = Functions.response("success", "Data Saved Successfully", save);
            }
        } catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/jadwal/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getJadwalById(@PathVariable Integer id){
        Map<String, Object> response;

        try {
            if (id <= 0){
                response = Functions.error(400, "Bad Parameters", "Error Get Data");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Optional<Jadwal> jadwal = jadwalService.findById(id);
            if (!jadwal.isPresent()){
                response = Functions.error(404, "Jadwal Not Found", "Error Get Data");
            } else {
                response = Functions.response("success", "Get Data Success", jadwal.get());
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/jadwal/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> deleteJadwalById(@PathVariable Integer id){
        Map<String, Object> response;

        try {
            if (id <= 0){
                response = Functions.error(400, "Bad Parameters", "Process Failed");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Optional<Jadwal> jadwal = jadwalService.findById(id);
            if (!jadwal.isPresent()){
                response = Functions.error(404, "Jadwal Not Found", "Process Failed");
            } else {
                jadwalService.delete(id);
                response = Functions.response("success", "Success Delete Jadwal", "Process Success");
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/jadwal/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> updateJadwalById(@PathVariable Integer id){
        Map<String, Object> response;

        try {
            if (id <= 0){
                response = Functions.error(400, "Bad Parameters", "Process Failed");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Optional<Jadwal> exist = jadwalService.findById(id);
            if (!exist.isPresent()){
                response = Functions.error(404, "Jadwal Not Found", "Process Failed");
            } else {
                Jadwal jadwal = new Jadwal();
                Jadwal updateJadwal = jadwalService.save(jadwal);
                response = Functions.response("success", "Update Data Success", updateJadwal);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
