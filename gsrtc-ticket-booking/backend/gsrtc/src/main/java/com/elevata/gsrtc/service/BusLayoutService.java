package com.elevata.gsrtc.service;

import com.elevata.gsrtc.entity.BusLayout;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BusLayoutService {
    List<BusLayout> findAll();

    BusLayout findById(int layoutId);

    void save(BusLayout BusLayout);

    void delete(int layoutId);
}
