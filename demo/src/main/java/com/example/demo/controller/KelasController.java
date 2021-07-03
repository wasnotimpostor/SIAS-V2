package com.example.demo.controller;

import com.example.demo.dto.dtoKelas;
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
}
