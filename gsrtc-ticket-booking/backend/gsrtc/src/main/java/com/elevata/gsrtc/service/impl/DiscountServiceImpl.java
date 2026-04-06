package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.DiscountDTO;
import com.elevata.gsrtc.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {
    @Override
    public DiscountDTO create(DiscountDTO dto) {
        return null;
    }

    @Override
    public List<DiscountDTO> getAll() {
        return List.of();
    }

    @Override
    public DiscountDTO update(Long id, DiscountDTO dto) {
        return null;
    }

    @Override
    public void updateStatus(Long id, Boolean active) {

    }
}
