package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.entity.BusLayout;
import com.elevata.gsrtc.service.BusLayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/depot/busLayout")
public class BusLayoutController {
    private BusLayoutService busLayoutService;

    @Autowired
    public BusLayoutController(BusLayoutService busLayoutService) {
        this.busLayoutService = busLayoutService;
    }

    @GetMapping("/layouts")
    public List<BusLayout> getBusLayoutList() {
        return busLayoutService.findAll();
    }

    @GetMapping("/BusLayout/{layoutId}")
    public BusLayout getBusLayout(@PathVariable int layoutId) {
        return busLayoutService.findById(layoutId);
    }

    @PostMapping("/add")
    public void add(@RequestBody BusLayout busLayout) {
        busLayoutService.save(busLayout);
    }

    @DeleteMapping("/delete/{layoutId}")
    public void remove(@PathVariable int layoutId) {
        busLayoutService.delete(layoutId);
    }
}
