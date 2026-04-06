package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.BusDepotSummaryDTO;
import com.elevata.gsrtc.dto.DepotDTO;
import com.elevata.gsrtc.service.BusDepotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/central/bus-depot")
public class SuperDepotController {
    private final BusDepotService busDepotService;

    @Autowired
    public SuperDepotController(BusDepotService busDepotService) {
        this.busDepotService = busDepotService;
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody DepotDTO dto) {
        return ResponseEntity.ok(busDepotService.save(dto));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<DepotDTO>> getList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(busDepotService.getList(page, size));
    }

    @GetMapping("/summary/{depotName}")
    public ResponseEntity<BusDepotSummaryDTO> getSummaryByName(
            @PathVariable String depotName
    ) {
        return ResponseEntity.ok(busDepotService.getSummaryByName(depotName));
    }

    @GetMapping("/{depotName}")
    public ResponseEntity<DepotDTO> getByName(
            @PathVariable String depotName
    ) {
        return ResponseEntity.ok(busDepotService.getByName(depotName));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(
            @PathVariable int id,
            @RequestParam(required = false) String depotName,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String password
    ) {
        return ResponseEntity.ok(busDepotService.update(id, depotName, address, password));
    }

    // Won't work
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        return ResponseEntity.ok(busDepotService.delete(id));
    }
}
