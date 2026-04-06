package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.DiscountDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DiscountService {
    DiscountDTO create(DiscountDTO dto);

    List<DiscountDTO> getAll();

    DiscountDTO update(Long id, DiscountDTO dto);

    void updateStatus(Long id, Boolean active);
}
