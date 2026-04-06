package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.InstituteDTO;
import com.elevata.gsrtc.dto.InstituteRegistrationDTO;
import com.elevata.gsrtc.entity.InstituteRegistry;
import com.elevata.gsrtc.service.InstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/end-user/institute")
public class InstituteController {
    private InstituteService instituteService;

    @Autowired
    public InstituteController(InstituteService instituteService) {
        this.instituteService = instituteService;
    }

    // NO end-user or DA access
//    @PostMapping("/register")
//    public ResponseEntity<InstituteRegistry> registerInstitute(
//            @RequestBody InstituteRegistrationDTO institute) {
//
//        InstituteRegistry savedInstitute = instituteService.registerInstitute(institute);
//        return ResponseEntity.ok(savedInstitute);
//    }

//    @GetMapping("/code/{instituteCode}")
//    public ResponseEntity<InstituteRegistry> getInstituteByCode(
//            @PathVariable String instituteCode) {
//
//        InstituteRegistry institute = instituteService.getInstituteByCode(instituteCode);
//        return ResponseEntity.ok(institute);
//    }

    @PostMapping("/verify/{code}")
    public ResponseEntity<InstituteDTO> verify(@PathVariable String code) {
        return ResponseEntity.ok(instituteService.verify(code));
    }
}
