package com.elevata.gsrtc.controller;


import com.elevata.gsrtc.dto.DiscountDTO;
import com.elevata.gsrtc.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/central/discount")
public class DiscountController {
    private final DiscountService discountService;

    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping
    public DiscountDTO create(@RequestBody DiscountDTO dto) {
        return discountService.create(dto);
    }

    @GetMapping
    public List<DiscountDTO> getAll() {
        return discountService.getAll();
    }

    @PutMapping("/{id}")
    public DiscountDTO update(@PathVariable Long id,
                              @RequestBody DiscountDTO dto) {
        return discountService.update(id, dto);
    }

    @PatchMapping("/{id}/status")
    public void changeStatus(@PathVariable Long id,
                             @RequestBody Map<String, Boolean> body) {
        discountService.updateStatus(id, body.get("active"));
    }
}
