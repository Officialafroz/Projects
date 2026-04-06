package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.service.ModificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class ModificationController {

    private ModificationService modificationService;

    @Autowired
    public ModificationController(ModificationService modificationService) {
        this.modificationService = modificationService;
    }

    @DeleteMapping("/user-depot/{depotId}")
    public ResponseEntity<String> delete(@PathVariable int depotId) {
        return ResponseEntity.ok(modificationService.delete(depotId));
    }
}
