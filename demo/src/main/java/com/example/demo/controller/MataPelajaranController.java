package com.example.demo.controller;

import com.example.demo.dto.dtoMataPelajaran;
import com.example.demo.model.MataPelajaran;
import com.example.demo.service.MataPelajaranService;
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

import java.security.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "sias", produces = MediaType.APPLICATION_JSON_VALUE)
public class MataPelajaranController {

    @Autowired
    private MataPelajaranService mataPelajaranService;

    @PostMapping(value = "/matpel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getAllByPage(@RequestBody String param){
        JSONObject jsonObject = new JSONObject(param);
        Map<String, Object> response;

        try {
            Integer page = jsonObject.optInt("page", 0);
            if (page > 0) page--;
            Integer per_page = jsonObject.optInt("per_page", 10);
            Integer id = jsonObject.optInt("id", 0);
            String matpel = jsonObject.optString("matpel");
            Long koordinator = jsonObject.optLong("koordinator",0);

            Pageable pageable = PageRequest.of(page, per_page);
            Page<dtoMataPelajaran> matpelPage = mataPelajaranService.getMatpelByPage(id, matpel, koordinator, pageable);
            List<dtoMataPelajaran> mataPelajaranList = matpelPage.getContent();

            response = Functions.page("success", matpelPage.getTotalElements(), matpelPage.getTotalPages(), per_page, page++, mataPelajaranList);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/matpel/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getAllByList(){
        Map<String, Object> response;

        try {
            List<dtoMataPelajaran> list = mataPelajaranService.getMatpelByList();
            response = Functions.response("success", "Get Mata Pelajaran Success", list);
        } catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    MataPelajaran parseJson(Integer id, JSONObject jsonObject){
        String kode = "";
        String name = jsonObject.optString("name");
        Long koordinator = jsonObject.optLong("koordinator");

        MataPelajaran mataPelajaran;
        while (id == 0 & kode == ""){
            Long count = mataPelajaranService.count();
            String result = Functions.generateRandom(count.intValue()+1);
            kode = "MP" + result;
        }

        if (id == 0){
            mataPelajaran = new MataPelajaran(null, name, kode, koordinator, Functions.getTimeStamp(), Functions.getTimeStamp());
        } else {
            Optional<MataPelajaran> matpel = mataPelajaranService.findById(id);
            if (matpel.isPresent()){
                kode = matpel.get().getKode();
                mataPelajaran = new MataPelajaran(id, name, kode, koordinator, Functions.getTimeStamp(), Functions.getTimeStamp());
            } else {
                return null;
            }
        }
        return mataPelajaran;
    }

    @PostMapping(value = "/matpel/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> createMatpel(@RequestBody String param){
        Map<String, Object> response;
        JSONObject jsonObject = new JSONObject(param);

        try {
            MataPelajaran mataPelajaran = parseJson(0, jsonObject);
            MataPelajaran matpel = mataPelajaranService.save(mataPelajaran);

            if (mataPelajaran.getId() > 0){
                response = Functions.response("success", "Data Saved Successfully", matpel);
            } else {
                response = Functions.error(500, "No Data Saved", "Process Failed");
            }
        } catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/matpel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getMatpelById(@PathVariable Integer id){
        Map<String, Object> response;

        try {
            if (id <= 0){
                response = Functions.error(400, "Bad Parameters", "Error Get Data");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Optional<MataPelajaran> mataPelajaran = mataPelajaranService.findById(id);
            if (!mataPelajaran.isPresent()){
                response = Functions.error(404, "Mata Pelajaran Not Found", "Error Get Data");
            } else {
                response = Functions.response("success", "Get Data Success", mataPelajaran.get());
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/matpel/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> updateMatpel(@PathVariable Integer id, @RequestBody String param){
        Map<String, Object> response;
        JSONObject jsonObject = new JSONObject(param);

        try {
            Optional<MataPelajaran> mataPelajaran = mataPelajaranService.findById(id);
            if (!mataPelajaran.isPresent()){
                response = Functions.error(400, "Bad Parameters", "Error Get Data");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            MataPelajaran matpel = parseJson(id, jsonObject);
            if (matpel == null){
                response = Functions.error(500, "No Data Saved", "Process Failed");
            } else {
                MataPelajaran updateMatpel = mataPelajaranService.save(matpel);
                response = Functions.response("success", "Update Mata Pelajaran Success", updateMatpel);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/matpel/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> deleteMatpelById(@PathVariable Integer id){
        Map<String, Object> response;

        try {
            if (id <= 0){
                response = Functions.error(400, "Bad Parameters", "Process Failed");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Optional<MataPelajaran> mataPelajaran = mataPelajaranService.findById(id);
            if (!mataPelajaran.isPresent()){
                response = Functions.error(404, "Mata Pelajaran Not Found", "Process Failed");
            } else {
                mataPelajaranService.delete(id);
                response = Functions.response("success", "Success Delete Mata Pelajaran", "Process Success");
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
