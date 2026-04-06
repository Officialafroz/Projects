package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.BusDepotSummaryDTO;
import com.elevata.gsrtc.dto.DepotDropDownDTO;
import com.elevata.gsrtc.entity.BusDepot;
import com.elevata.gsrtc.service.BusDepotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api/depot/bus-depot")
@RequestMapping("/api/end-user/bus-depot")
public class DepotController {
    private final BusDepotService busDepotService;

    @Autowired
    public DepotController(BusDepotService busDepotService) {
        this.busDepotService = busDepotService;
    }

    @GetMapping("/summary/list")
    public ResponseEntity<Page<BusDepotSummaryDTO>> getBusDepotList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(busDepotService.getAllDepots(page, size));
    }

    @GetMapping("/{depotName}")
    public ResponseEntity<BusDepotSummaryDTO> getDepotByName(
            @PathVariable String depotName
    ) {
        return ResponseEntity.ok(busDepotService.getSummaryByName(depotName));
    }

    @GetMapping("/name-list")
    public List<String> getDepotNameList() {
        return busDepotService.getDepotNameList();
    }

    @GetMapping("/depots")
    public List<BusDepot> getDepotList() {
        return busDepotService.findAll();
    }

    @GetMapping("/list")
    public List<DepotDropDownDTO> getDepots() {
        return busDepotService.getDepots();
    }

    @GetMapping("/depot/{depotId}")
    public BusDepot getDepot(@PathVariable int depotId) {
        return busDepotService.findById(depotId);
    }

    @PostMapping("/add")
    public void add(@RequestBody BusDepot busDepot) {
        busDepotService.save(busDepot);
    }

    @DeleteMapping("/{depotId}")
    public void remove(@PathVariable int depotId) {
        busDepotService.delete(depotId);
    }
}
